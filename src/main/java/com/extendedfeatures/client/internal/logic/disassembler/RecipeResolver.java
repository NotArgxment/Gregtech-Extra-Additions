package com.extendedfeatures.client.internal.logic.disassembler;

import com.extendedfeatures.client.internal.ConfigClass;
import com.extendedfeatures.init.contents.misc.UniversalCircuits;

import com.gregtechceu.gtceu.api.*;
import com.gregtechceu.gtceu.api.capability.recipe.ItemRecipeCapability;
import com.gregtechceu.gtceu.api.item.MetaMachineItem;
import com.gregtechceu.gtceu.api.machine.MachineDefinition;
import com.gregtechceu.gtceu.api.recipe.*;
import com.gregtechceu.gtceu.api.recipe.content.Content;
import com.gregtechceu.gtceu.api.recipe.ingredient.SizedIngredient;
import com.gregtechceu.gtceu.data.recipe.CustomTags;

import com.tterrag.registrate.util.entry.ItemEntry;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.*;

public class RecipeResolver {

    // Tools tags exceptions since it should never be returned in a normal disassembler recipe
    public static final Set<TagKey<Item>> TOOL_TAGS = Set.of(
            CustomTags.CRAFTING_WRENCHES,
            CustomTags.CRAFTING_HAMMERS,
            CustomTags.CRAFTING_FILES,
            CustomTags.CRAFTING_SCREWDRIVERS,
            CustomTags.CRAFTING_CROWBARS,
            CustomTags.CRAFTING_SAWS,
            CustomTags.CRAFTING_MALLETS);

    // This piece of code will try to match any circuit tag in the recipe to convert them into Universal circuits
    // So avoids giving a bad/good circuit, instead gives all of them in just one
    public static final Map<TagKey<Item>, ItemStack> CIRCUIT_TAG_TO_UNIVERSAL = buildCircuitTagMap();

    public static Optional<Integer> getMachineTier(ItemStack stack) {
        return getMachineDefinition(stack).map(MachineDefinition::getTier);
    }

    public static Optional<MachineDefinition> getMachineDefinition(ItemStack stack) {
        if (stack.getItem() instanceof MetaMachineItem machineItem) {
            return Optional.of(machineItem.getDefinition());
        }
        return Optional.empty();
    }

    private static Map<TagKey<Item>, ItemStack> buildCircuitTagMap() {
        Map<TagKey<Item>, ItemStack> map = new HashMap<>();

        if (!ConfigClass.INSTANCE.UniversalCircuits) {
            return Map.of();
        }

        ifApplicable(map, CustomTags.ULV_CIRCUITS, GTValues.ULV);
        ifApplicable(map, CustomTags.LV_CIRCUITS, GTValues.LV);
        ifApplicable(map, CustomTags.MV_CIRCUITS, GTValues.MV);
        ifApplicable(map, CustomTags.HV_CIRCUITS, GTValues.HV);
        ifApplicable(map, CustomTags.EV_CIRCUITS, GTValues.EV);
        ifApplicable(map, CustomTags.IV_CIRCUITS, GTValues.IV);
        ifApplicable(map, CustomTags.LuV_CIRCUITS, GTValues.LuV);
        ifApplicable(map, CustomTags.ZPM_CIRCUITS, GTValues.ZPM);
        ifApplicable(map, CustomTags.UV_CIRCUITS, GTValues.UV);

        if (GTCEuAPI.isHighTier()) {
            ifApplicable(map, CustomTags.UHV_CIRCUITS, GTValues.UHV);
            ifApplicable(map, CustomTags.UEV_CIRCUITS, GTValues.UEV);
            ifApplicable(map, CustomTags.UIV_CIRCUITS, GTValues.UIV);
            ifApplicable(map, CustomTags.UXV_CIRCUITS, GTValues.UXV);
            ifApplicable(map, CustomTags.OpV_CIRCUITS, GTValues.OpV);
        }

        return Map.copyOf(map);
    }

    private static void ifApplicable(Map<TagKey<Item>, ItemStack> map,
                                     TagKey<Item> tag, int tier) {
        ItemEntry<Item> entry = UniversalCircuits.UNIVERSAL_CIRCUITS[tier];
        if (entry != null) {
            map.put(tag, entry.asStack());
        }
    }

    public static Optional<List<ItemStack>> resolveFromGTRecipeType(ServerLevel level,
                                                                    GTRecipeType recipeType,
                                                                    ItemStack targetStack) {
        for (GTRecipe recipe : level.getRecipeManager().getAllRecipesFor(recipeType)) {

            if (!recipeProducesItem(recipe, targetStack))
                continue;

            if (recipeRequiresTool(recipe))
                continue;

            return Optional.of(extractItemInputs(recipe));
        }
        return Optional.empty();
    }

    private static boolean recipeRequiresTool(GTRecipe recipe) {
        for (Content content : recipe.getInputContents(ItemRecipeCapability.CAP)) {
            if (!(content.content instanceof Ingredient ingredient))
                continue;
            if (requiresTool(ingredient))
                return true;
        }
        return false;
    }

    public static boolean requiresTool(Ingredient ingredient) {
        for (ItemStack stack : ingredient.getItems()) {
            for (TagKey<Item> tag : TOOL_TAGS) {
                if (stack.is(tag))
                    return true;
            }
        }
        return false;
    }

    private static boolean recipeProducesItem(GTRecipe recipe, ItemStack targetStack) {
        List<Content> outputContents = recipe.getOutputContents(ItemRecipeCapability.CAP);
        if (outputContents.isEmpty())
            return false;

        for (Content content : outputContents) {
            if (!(content.content instanceof Ingredient ingredient))
                continue;

            for (ItemStack stack : ingredient.getItems()) {
                if (ItemStack.isSameItem(stack, targetStack)) {
                    return true;
                }
            }
        }
        return false;
    }

    private static List<ItemStack> extractItemInputs(GTRecipe recipe) {
        List<ItemStack> components = new ArrayList<>();

        for (Content content : recipe.getInputContents(ItemRecipeCapability.CAP)) {

            if (!(content.content instanceof Ingredient ingredient))
                continue;

            if (ingredient.isEmpty())
                continue;

            Optional<ItemStack> circuitReplacement = findCircuitReplacement(ingredient);

            if (circuitReplacement.isPresent()) {
                components.add(circuitReplacement.get().copy());
                continue;
            }

            ItemStack[] matches = ingredient.getItems();

            if (matches.length == 0)
                continue;

            ItemStack representative = matches[0].copy();

            if (ingredient instanceof SizedIngredient sized) {
                representative.setCount(sized.getAmount());
            }

            components.add(representative);
        }

        return components;
    }

    public static Optional<ItemStack> findCircuitReplacement(Ingredient ingredient) {
        for (ItemStack stack : ingredient.getItems()) {
            for (var entry : CIRCUIT_TAG_TO_UNIVERSAL.entrySet()) {
                if (stack.is(entry.getKey())) {
                    return Optional.of(entry.getValue());
                }
            }
        }
        return Optional.empty();
    }
}