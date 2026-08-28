package com.extendedfeatures.client.internal.logic.disassembler;

import com.gregtechceu.gtceu.common.data.GTRecipeTypes;

import net.minecraft.core.RegistryAccess;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;

import java.util.*;

import static java.util.Objects.*;

public class ComponentResolver {

    public static List<ItemStack> resolve(ServerLevel level, ItemStack targetStack) {
        if (targetStack.isEmpty()) {
            return Collections.emptyList();
        }

        // Regular crafting recipes
        Optional<List<ItemStack>> craftingResult = resolveFromCraftingTable(level, targetStack);
        if (craftingResult.isPresent()) {
            return mergeStacks(craftingResult.get());
        }

        // Assembler Recipes
        Optional<List<ItemStack>> assemblerResult = RecipeResolver.resolveFromGTRecipeType(
                level, GTRecipeTypes.ASSEMBLER_RECIPES, targetStack);
        if (assemblerResult.isPresent()) {
            return mergeStacks(assemblerResult.get());
        }

        // Assembly Line Recipes
        Optional<List<ItemStack>> assemblyLineResult = RecipeResolver.resolveFromGTRecipeType(
                level, GTRecipeTypes.ASSEMBLY_LINE_RECIPES, targetStack);
            return assemblyLineResult.map(ComponentResolver::mergeStacks)
                    .orElse(Collections.emptyList());

    }

    private static List<ItemStack> mergeStacks(List<ItemStack> stacks) {
        Map<Object, ItemStack> merged = new LinkedHashMap<>();

        for (ItemStack stack : stacks) {
            if (stack.isEmpty())
                continue;

            Object key = new StackKey(stack);

            ItemStack existing = merged.get(key);
            if (existing != null) {
                existing.grow(stack.getCount());
            } else {
                merged.put(key, stack.copy());
            }
        }

        return new ArrayList<>(merged.values());
    }

    private record StackKey(net.minecraft.world.item.Item item, net.minecraft.nbt.CompoundTag tag) {

        StackKey(ItemStack stack) {
            this(stack.getItem(), stack.getTag());
        }

        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof StackKey other))
                return false;

            return item == other.item && Objects.equals(tag, other.tag);
        }

        @Override
        public int hashCode() {
            return hash(item, tag);
        }
    }

    private static Optional<List<ItemStack>> resolveFromCraftingTable(ServerLevel level, ItemStack targetStack) {

        RegistryAccess registryAccess = level.registryAccess();

        for (CraftingRecipe recipe : level.getRecipeManager()
                .getAllRecipesFor(RecipeType.CRAFTING)) {
            ItemStack result = recipe.getResultItem(registryAccess);

            if (result.isEmpty() || !ItemStack.isSameItem(result, targetStack))
                continue;

            List<ItemStack> components = new ArrayList<>();
            boolean requiresTool = false;
            for (Ingredient ingredient : recipe.getIngredients()) {
                if (ingredient.isEmpty())
                    continue;

                // Skip recipes that require a tool (wrench, hammer, file, screwdriver, crowbar) as an ingredient
                if (RecipeResolver.requiresTool(ingredient)) {
                    requiresTool = true;
                    break;
                }

                Optional<ItemStack> circuitReplacement = RecipeResolver.findCircuitReplacement(ingredient);
                if (circuitReplacement.isPresent()) {
                    components.add(circuitReplacement.get().copy());
                    continue;
                }

                ItemStack[] matches = ingredient.getItems();
                if (matches.length > 0) {
                    components.add(matches[0].copy());
                }
            }

            if (requiresTool)
                continue;

            return Optional.of(components);
        }
        return Optional.empty();
    }
}
