package com.extendedfeatures.client.internal.logic.disassembler;

import com.extendedfeatures.client.RecipeTypes;
import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.capability.recipe.IO;
import com.gregtechceu.gtceu.api.capability.recipe.IRecipeCapabilityHolder;
import com.gregtechceu.gtceu.api.capability.recipe.ItemRecipeCapability;
import com.gregtechceu.gtceu.api.machine.MachineDefinition;
import com.gregtechceu.gtceu.api.machine.MetaMachine;
import com.gregtechceu.gtceu.api.machine.feature.IRecipeLogicMachine;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.GTRecipeType;
import com.gregtechceu.gtceu.api.registry.GTRegistries;
import com.gregtechceu.gtceu.data.recipe.builder.GTRecipeBuilder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;

import javax.annotation.ParametersAreNullableByDefault;
import java.util.List;

import static com.gregtechceu.gtceu.GTCEu.getMinecraftServer;

@ParametersAreNullableByDefault
public enum DisassemblerRecipeLogic implements GTRecipeType.ICustomRecipeLogic {

    INSTANCE;

    private static final int baseDuration = 20;
    private static final int tickTier = 30;

    @Override
    public GTRecipe createCustomRecipe(IRecipeCapabilityHolder holder) {
        ServerLevel serverLevel = getServerLevel(holder);

        if (!(holder instanceof IRecipeLogicMachine recipeLogicMachine))
            return null;

        GTRecipeType recipeType = recipeLogicMachine.getRecipeType();

        var recipeHandlers = holder.getCapabilitiesFlat(IO.IN, ItemRecipeCapability.CAP);

        for (var handler : recipeHandlers) {
            for (var content : handler.getContents()) {
                if (!(content instanceof ItemStack stack))
                    continue;

                if (stack.isEmpty())
                    continue;

                GTRecipe recipe = tryBuildRecipe(serverLevel, recipeType, stack);
                return recipe;
            }
        }
        return null;
    }

    private GTRecipe tryBuildRecipe(ServerLevel serverLevel, GTRecipeType recipeType,
                                              ItemStack inputStack) {
        Integer tier = MachineUtil.getMachineTier(inputStack).orElse(null);
        if (tier == null)
            return null;

        assert inputStack != null;
        List<ItemStack> components = ComponentResolver.resolve(serverLevel, inputStack);

        if (components.isEmpty())
            return null;

        long euT = GTValues.VA[tier];
        int duration = baseDuration + (tier * tickTier);

        assert recipeType != null;
        GTRecipeBuilder builder = recipeType
                .recipeBuilder("disassemble/" + inputStack.getItem().builtInRegistryHolder().key().location().getPath())
                .inputItems(inputStack.copyWithCount(1))
                .EUt(euT)
                .duration(duration);

        for (ItemStack component : components) {
            builder.outputItems(component);
        }

        return builder.buildRawRecipe();
    }

    private ServerLevel getServerLevel(IRecipeCapabilityHolder holder) {
        if (holder instanceof MetaMachine machine && machine.getLevel() instanceof ServerLevel serverLevel) {
            return serverLevel;
        }
        return null;
    }

    @Override
    public void buildRepresentativeRecipes() {
        GTRecipeType recipeType = RecipeTypes.DISASSEMBLER_MACHINES;

        ServerLevel serverLevel = getRepresentativeServerLevel();

        for (MachineDefinition definition : GTRegistries.MACHINES) {

            if (definition.getRecipeTypes().length == 0)
                continue;

            if (isExcludedFromDisassembly(definition))
                continue;

            ItemStack stack = definition.asStack();
            if (stack.isEmpty())
                continue;

            GTRecipe recipe = tryBuildRecipe(serverLevel, recipeType, stack);
            if (recipe == null)
                continue;

            recipe.setId(recipe.getId().withPrefix("/"));
            recipeType.getCategory().addRecipe(recipe);
        }
    }

    private static boolean isExcludedFromDisassembly(MachineDefinition definition) {
        assert definition != null;
        String path = definition.getId().getPath();

        return path.contains("transformer")
                || path.contains("energy_converter")
                || path.contains("_bus")
                || path.contains("hatch")
                || path.contains("diode");
    }

    private ServerLevel getRepresentativeServerLevel() {
        var server = getMinecraftServer();
        return server == null ? null : server.overworld();
    }
}
