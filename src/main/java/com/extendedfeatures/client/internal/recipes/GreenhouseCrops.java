package com.extendedfeatures.client.internal.recipes;

import com.extendedfeatures.client.RecipeTypes;
import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.common.data.*;

import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.world.item.*;

import java.util.function.Consumer;

public class GreenhouseCrops {

    public static void init(Consumer<FinishedRecipe> provider) {
        // =====================================================
        addCropRecipe(provider, "sugar_cane", false,
                new ItemStack(Items.SUGAR_CANE), 1000,
                new ItemStack(Items.SUGAR_CANE, 32));

        addCropRecipe(provider, "sugar_cane_boosted", true,
                new ItemStack(Items.SUGAR_CANE), 1000,
                new ItemStack(Items.SUGAR_CANE, 64));
        // =====================================================
        addCropRecipe(provider, "kelp", false,
                new ItemStack(Items.KELP), 2000,
                new ItemStack(Items.KELP, 32));

        addCropRecipe(provider, "kelp_boosted", true,
                new ItemStack(Items.KELP), 2000,
                new ItemStack(Items.KELP, 64));
        // =====================================================
        addCropRecipe(provider, "bamboo", false,
                new ItemStack(Items.BAMBOO), 1000,
                new ItemStack(Items.BAMBOO, 32));

        addCropRecipe(provider, "bamboo_boosted", true,
                new ItemStack(Items.BAMBOO), 1000,
                new ItemStack(Items.BAMBOO, 64));
        // =====================================================
        addCropRecipe(provider, "cactus", false,
                new ItemStack(Items.CACTUS), 1000,
                new ItemStack(Items.CACTUS, 32));

        addCropRecipe(provider, "cactus_boosted", true,
                new ItemStack(Items.CACTUS), 1000,
                new ItemStack(Items.CACTUS, 64));
        // =====================================================
        addCropRecipe(provider, "wheat", false,
                new ItemStack(Items.WHEAT_SEEDS), 1000,
                new ItemStack(Items.WHEAT, 32));

        addCropRecipe(provider, "wheat_boosted", true,
                new ItemStack(Items.WHEAT_SEEDS), 1000,
                new ItemStack(Items.WHEAT, 64));
        // =====================================================
        addCropRecipe(provider, "carrot", false,
                new ItemStack(Items.CARROT), 1000,
                new ItemStack(Items.CARROT, 32));

        addCropRecipe(provider, "carrot_boosted", true,
                new ItemStack(Items.CARROT), 1000,
                new ItemStack(Items.CARROT, 64));
        // =====================================================
        addCropRecipe(provider, "potato", false,
                new ItemStack(Items.POTATO), 1000,
                new ItemStack(Items.POTATO, 32));

        addCropRecipe(provider, "potato_boosted", true,
                new ItemStack(Items.POTATO), 1000,
                new ItemStack(Items.POTATO, 64));
        // =====================================================
        addCropRecipe(provider, "beetroot", false,
                new ItemStack(Items.BEETROOT_SEEDS), 1000,
                new ItemStack(Items.BEETROOT, 32));

        addCropRecipe(provider, "beetroot_boosted", true,
                new ItemStack(Items.BEETROOT_SEEDS), 1000,
                new ItemStack(Items.BEETROOT, 64));
        // =====================================================
        addCropRecipe(provider, "melon", false,
                new ItemStack(Items.MELON_SEEDS), 1000,
                new ItemStack(Items.MELON, 16));

        addCropRecipe(provider, "melon_boosted", true,
                new ItemStack(Items.MELON_SEEDS), 1000,
                new ItemStack(Items.MELON, 32));
        // =====================================================
        addCropRecipe(provider, "pumpkin", false,
                new ItemStack(Items.PUMPKIN_SEEDS), 1000,
                new ItemStack(Items.PUMPKIN, 16));

        addCropRecipe(provider, "pumpkin_boosted", true,
                new ItemStack(Items.PUMPKIN_SEEDS), 1000,
                new ItemStack(Items.PUMPKIN, 32));
        // =====================================================
        addCropRecipe(provider, "nether_wart", false,
                new ItemStack(Items.NETHER_WART), 1000,
                new ItemStack(Items.NETHER_WART, 16));

        addCropRecipe(provider, "nether_wart_boosted", true,
                new ItemStack(Items.NETHER_WART), 1000,
                new ItemStack(Items.NETHER_WART, 32));
        // =====================================================
        addCropRecipe(provider, "red_mushroom", false,
                new ItemStack(Items.RED_MUSHROOM), 1000,
                new ItemStack(Items.RED_MUSHROOM, 16));

        addCropRecipe(provider, "red_mushroom_boosted", true,
                new ItemStack(Items.RED_MUSHROOM), 1000,
                new ItemStack(Items.RED_MUSHROOM, 32));
        // =====================================================
        addCropRecipe(provider, "brown_mushroom", false,
                new ItemStack(Items.BROWN_MUSHROOM), 1000,
                new ItemStack(Items.BROWN_MUSHROOM, 16));

        addCropRecipe(provider, "brown_mushroom_boosted", true,
                new ItemStack(Items.BROWN_MUSHROOM), 1000,
                new ItemStack(Items.BROWN_MUSHROOM, 32));
    }

    private static void addCropRecipe(Consumer<FinishedRecipe> provider,
                                      String id, boolean boosted,
                                      ItemStack input, int waterMb,
                                      ItemStack output) {
        var builder = RecipeTypes.GREENHOUSE_CROPS.recipeBuilder(id)
                .circuitMeta(boosted ? 2 : 1)
                .notConsumable(input)
                .inputFluids(GTMaterials.Water.getFluid(waterMb))
                .outputItems(output)
                .duration(boosted ? 250 : 320)
                .EUt(GTValues.VA[GTValues.MV]);

        if (boosted) {
            builder.inputItems(GTItems.FERTILIZER.asStack(4));
        }

        builder.save(provider);
    }
}
