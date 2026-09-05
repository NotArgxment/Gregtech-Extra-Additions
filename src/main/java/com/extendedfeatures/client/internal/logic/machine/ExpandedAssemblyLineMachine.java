package com.extendedfeatures.client.internal.logic.machine;

import com.gregtechceu.gtceu.GTCEu;
import com.gregtechceu.gtceu.api.capability.recipe.FluidRecipeCapability;
import com.gregtechceu.gtceu.api.capability.recipe.IO;
import com.gregtechceu.gtceu.api.capability.recipe.IRecipeHandler;
import com.gregtechceu.gtceu.api.capability.recipe.ItemRecipeCapability;
import com.gregtechceu.gtceu.api.capability.recipe.RecipeCapability;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.feature.IRecipeLogicMachine;
import com.gregtechceu.gtceu.api.machine.trait.NotifiableFluidTank;
import com.gregtechceu.gtceu.api.machine.trait.NotifiableItemStackHandler;
import com.gregtechceu.gtceu.api.machine.trait.RecipeLogic;
import com.gregtechceu.gtceu.api.recipe.ActionResult;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.RecipeHelper;
import com.gregtechceu.gtceu.api.recipe.content.Content;
import com.gregtechceu.gtceu.api.recipe.ingredient.FluidIngredient;
import com.gregtechceu.gtceu.common.machine.multiblock.electric.AssemblyLineMachine;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.fluids.FluidStack;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Whole purpose of this class:
 * <p>
 * - Removes "Ordering Index" that asks a regular assembly line to put items in order
 * <p>
 * - Allows fluids to be used in stocking hatches
 * <p>
 * - Allows slices to use stocking hatches (for parallelization)
 * <p>
 * This does not mean that you need only 1 input for items and fluids, layers for making recipes are still required
 */
public class ExpandedAssemblyLineMachine extends AssemblyLineMachine {

    public ExpandedAssemblyLineMachine(IMachineBlockEntity holder, boolean allowCircuitSlots) {
        super(holder, allowCircuitSlots);
    }

    public ExpandedAssemblyLineMachine(IMachineBlockEntity holder) {
        this(holder, false);
    }

    @Override
    protected @NotNull RecipeLogic createRecipeLogic(Object... args) {
        return new ExpandedAsslineRecipeLogic(this);
    }

    private record SlotEntry(IRecipeHandler<?> handler, Object stack, int amount) {}

    private boolean checkItemInputsExpanded(GTRecipe recipe, boolean isTick) {
        return assignItemBuses(
                (isTick ? recipe.tickInputs : recipe.inputs).getOrDefault(ItemRecipeCapability.CAP,
                        Collections.emptyList()),
                getCapabilitiesFlat(IO.IN, ItemRecipeCapability.CAP).stream()
                        .filter(IRecipeHandler::shouldSearchContent)
                        .toList()) != null;
    }

    private ActionResult consumeItemContentsExpanded(GTRecipe recipe, boolean isTick) {
        var itemInputs = (isTick ? recipe.tickInputs : recipe.inputs).getOrDefault(ItemRecipeCapability.CAP,
                Collections.emptyList());
        if (itemInputs.isEmpty()) return ActionResult.SUCCESS;

        var itemHandlers = getCapabilitiesFlat(IO.IN, ItemRecipeCapability.CAP).stream()
                .filter(IRecipeHandler::shouldSearchContent)
                .toList();

        Map<IRecipeHandler<?>, List<Ingredient>> grouped = assignItemBusesGrouped(itemInputs, itemHandlers);
        if (grouped == null) return ActionResult.FAIL_NO_REASON;

        for (var entry : grouped.entrySet()) {
            if (!(entry.getKey() instanceof NotifiableItemStackHandler itemBus)) throw new RuntimeException(
                    "Handler in ExpandedAssline.consumeItemContentsExpanded was not of type NotifiableItemStackHandler");
            var left = itemBus.handleRecipeInner(IO.IN, recipe, new ArrayList<>(entry.getValue()), true);
            if (!(left == null || left.isEmpty())) return ActionResult.FAIL_NO_REASON;
        }

        for (var entry : grouped.entrySet()) {
            if (!(entry.getKey() instanceof NotifiableItemStackHandler itemBus)) throw new RuntimeException(
                    "Handler in ExpandedAssline.consumeItemContentsExpanded was not of type NotifiableItemStackHandler");
            var left = itemBus.handleRecipeInner(IO.IN, recipe, new ArrayList<>(entry.getValue()), false);
            if (!(left == null || left.isEmpty())) {
                GTCEu.LOGGER.error(
                        "Recipe in ExpandedAssline.consumeItemContentsExpanded was true when simulating, but false when consuming.");
                return ActionResult.FAIL_NO_REASON;
            }
        }
        return ActionResult.SUCCESS;
    }

    private Map<Content, IRecipeHandler<?>> assignItemBuses(
            List<Content> itemInputs, List<IRecipeHandler<?>> itemHandlers) {
        List<SlotEntry> slots = new ArrayList<>();
        for (var handler : itemHandlers) {
            for (Object o : handler.getContents()) {
                if (o instanceof ItemStack stack && !stack.isEmpty()) {
                    slots.add(new SlotEntry(handler, stack, stack.getCount()));
                }
            }
        }

        Map<SlotEntry, Integer> remaining = new IdentityHashMap<>();
        for (var s : slots) remaining.put(s, s.amount());

        Map<Content, IRecipeHandler<?>> result = new LinkedHashMap<>();
        for (var input : itemInputs) {
            Ingredient recipeStack = ItemRecipeCapability.CAP.of(input.content);
            int needed = 1;
            SlotEntry chosen = null;
            for (var s : slots) {
                if (remaining.get(s) <= 0) continue;
                if (s.stack() instanceof ItemStack stack && recipeStack.test(stack)) {
                    chosen = s;
                    break;
                }
            }
            if (chosen == null) return null;
            remaining.put(chosen, remaining.get(chosen) - needed);
            result.put(input, chosen.handler());
        }
        return result;
    }

    private Map<IRecipeHandler<?>, List<Ingredient>> assignItemBusesGrouped(
            List<Content> itemInputs, List<IRecipeHandler<?>> itemHandlers) {
        var perInput = assignItemBuses(itemInputs, itemHandlers);
        if (perInput == null) return null;
        Map<IRecipeHandler<?>, List<Ingredient>> grouped = new LinkedHashMap<>();
        for (var entry : perInput.entrySet()) {
            grouped.computeIfAbsent(entry.getValue(), h -> new ArrayList<>())
                    .add(ItemRecipeCapability.CAP.of(entry.getKey().content));
        }
        return grouped;
    }

    private boolean checkFluidInputsExpanded(GTRecipe recipe, boolean isTick) {
        return assignFluidTanks(
                (isTick ? recipe.tickInputs : recipe.inputs).getOrDefault(FluidRecipeCapability.CAP,
                        Collections.emptyList()),
                getCapabilitiesFlat(IO.IN, FluidRecipeCapability.CAP).stream()
                        .filter(IRecipeHandler::shouldSearchContent)
                        .toList()) != null;
    }

    private ActionResult consumeFluidContentsExpanded(GTRecipe recipe, boolean isTick) {
        var fluidInputs = (isTick ? recipe.tickInputs : recipe.inputs).getOrDefault(FluidRecipeCapability.CAP,
                Collections.emptyList());
        if (fluidInputs.isEmpty()) return ActionResult.SUCCESS;

        var fluidHandlers = getCapabilitiesFlat(IO.IN, FluidRecipeCapability.CAP).stream()
                .filter(IRecipeHandler::shouldSearchContent)
                .toList();

        Map<IRecipeHandler<?>, List<FluidIngredient>> grouped = assignFluidTanksGrouped(fluidInputs, fluidHandlers);
        if (grouped == null) return ActionResult.FAIL_NO_REASON;

        for (var entry : grouped.entrySet()) {
            if (!(entry.getKey() instanceof NotifiableFluidTank fluidTank)) throw new RuntimeException(
                    "Handler in ExpandedAssline.consumeFluidContentsExpanded was not of type NotifiableFluidTank");
            var left = fluidTank.handleRecipeInner(IO.IN, recipe, new ArrayList<>(entry.getValue()), true);
            if (!(left == null || left.isEmpty())) return ActionResult.FAIL_NO_REASON;
        }
        for (var entry : grouped.entrySet()) {
            if (!(entry.getKey() instanceof NotifiableFluidTank fluidTank)) throw new RuntimeException(
                    "Handler in ExpandedAssline.consumeFluidContentsExpanded was not of type NotifiableFluidTank");
            var left = fluidTank.handleRecipeInner(IO.IN, recipe, new ArrayList<>(entry.getValue()), false);
            if (!(left == null || left.isEmpty())) {
                GTCEu.LOGGER.error(
                        "Recipe in ExpandedAssline.consumeFluidContentsExpanded was true when simulating, but false when consuming.");
                return ActionResult.FAIL_NO_REASON;
            }
        }
        return ActionResult.SUCCESS;
    }

    private Map<Content, IRecipeHandler<?>> assignFluidTanks(
            List<Content> fluidInputs, List<IRecipeHandler<?>> fluidHandlers) {
        List<SlotEntry> slots = new ArrayList<>();
        for (var handler : fluidHandlers) {
            for (Object o : handler.getContents()) {
                if (o instanceof FluidStack stack && !stack.isEmpty()) {
                    slots.add(new SlotEntry(handler, stack, stack.getAmount()));
                }
            }
        }
        Map<SlotEntry, Integer> remaining = new IdentityHashMap<>();
        for (var s : slots) remaining.put(s, s.amount());

        Map<Content, IRecipeHandler<?>> result = new LinkedHashMap<>();
        for (var input : fluidInputs) {
            FluidIngredient recipeStack = FluidRecipeCapability.CAP.of(input.content);
            int needed = recipeStack.getAmount();
            SlotEntry chosen = null;
            for (var s : slots) {
                if (!(s.stack() instanceof FluidStack stack)) continue;
                if (remaining.get(s) < needed) continue;
                if (recipeStack.test(stack)) {
                    chosen = s;
                    break;
                }
            }
            if (chosen == null) return null;
            remaining.put(chosen, remaining.get(chosen) - needed);
            result.put(input, chosen.handler());
        }
        return result;
    }

    private Map<IRecipeHandler<?>, List<FluidIngredient>> assignFluidTanksGrouped(
            List<Content> fluidInputs, List<IRecipeHandler<?>> fluidHandlers) {
        var perInput = assignFluidTanks(fluidInputs, fluidHandlers);
        if (perInput == null) return null;
        Map<IRecipeHandler<?>, List<FluidIngredient>> grouped = new LinkedHashMap<>();
        for (var entry : perInput.entrySet()) {
            grouped.computeIfAbsent(entry.getValue(), h -> new ArrayList<>())
                    .add(FluidRecipeCapability.CAP.of(entry.getKey().content));
        }
        return grouped;
    }

    private ActionResult consumeAllExpanded(GTRecipe recipe, boolean isTick,
                                            Map<RecipeCapability<?>, Object2IntMap<?>> chanceCaches) {
        GTRecipe copyWithItems = recipe.copy();
        copyWithItems.inputs.clear();
        copyWithItems.tickInputs.clear();

        GTRecipe copyWithFluids = recipe.copy();
        copyWithFluids.inputs.clear();
        copyWithFluids.tickInputs.clear();

        GTRecipe copyWithoutItemsFluids = recipe.copy();
        copyWithoutItemsFluids.inputs.clear();
        copyWithoutItemsFluids.tickInputs.clear();

        for (var entry : recipe.inputs.entrySet()) {
            if (entry.getKey().equals(FluidRecipeCapability.CAP)) {
                copyWithFluids.inputs.put(entry.getKey(), entry.getValue());
            } else if (entry.getKey().equals(ItemRecipeCapability.CAP)) {
                copyWithItems.inputs.put(entry.getKey(), entry.getValue());
            } else {
                copyWithoutItemsFluids.inputs.put(entry.getKey(), entry.getValue());
            }
        }
        for (var entry : recipe.tickInputs.entrySet()) {
            if (entry.getKey().equals(FluidRecipeCapability.CAP)) {
                copyWithFluids.tickInputs.put(entry.getKey(), entry.getValue());
            } else if (entry.getKey().equals(ItemRecipeCapability.CAP)) {
                copyWithItems.tickInputs.put(entry.getKey(), entry.getValue());
            } else {
                copyWithoutItemsFluids.tickInputs.put(entry.getKey(), entry.getValue());
            }
        }

        ActionResult result = consumeItemContentsExpanded(copyWithItems, isTick);
        if (!result.isSuccess()) return result;

        result = consumeFluidContentsExpanded(copyWithFluids, isTick);
        if (!result.isSuccess()) return result;

        return isTick ?
                RecipeHelper.handleTickRecipeIO(this, copyWithoutItemsFluids, IO.IN, chanceCaches) :
                RecipeHelper.handleRecipeIO(this, copyWithoutItemsFluids, IO.IN, chanceCaches);
    }

    class ExpandedAsslineRecipeLogic extends RecipeLogic {

        public ExpandedAsslineRecipeLogic(IRecipeLogicMachine machine) {
            super(machine);
        }

        @Override
        protected ActionResult handleRecipeIO(GTRecipe recipe, IO io) {
            if (io.equals(IO.IN)) {
                return consumeAllExpanded(recipe, false, this.chanceCaches);
            }
            return RecipeHelper.handleRecipeIO(machine, recipe, io, this.chanceCaches);
        }

        @Override
        protected ActionResult handleTickRecipeIO(GTRecipe recipe, IO io) {
            if (io.equals(IO.IN)) {
                return consumeAllExpanded(recipe, true, this.chanceCaches);
            }
            return RecipeHelper.handleTickRecipeIO(machine, recipe, io, this.chanceCaches);
        }

        @Override
        protected ActionResult matchRecipe(GTRecipe recipe) {
            ActionResult normalMatch = RecipeHelper.matchContents(machine, recipe);
            if (!normalMatch.isSuccess()) return normalMatch;
            if (!checkItemInputsExpanded(recipe, false)) return ActionResult.FAIL_NO_REASON;
            if (!checkItemInputsExpanded(recipe, true)) return ActionResult.FAIL_NO_REASON;
            if (!checkFluidInputsExpanded(recipe, false)) return ActionResult.FAIL_NO_REASON;
            if (!checkFluidInputsExpanded(recipe, true)) return ActionResult.FAIL_NO_REASON;
            return ActionResult.SUCCESS;
        }
    }
}