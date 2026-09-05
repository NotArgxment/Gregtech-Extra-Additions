package com.extendedfeatures.init.contents.recipes;

import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.common.data.GTBlocks;
import com.gregtechceu.gtceu.common.data.GTItems;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.function.Consumer;

import static com.extendedfeatures.client.EFRecipeTypes.GREENHOUSE_CROPS;
import static com.extendedfeatures.client.EFRecipeTypes.GREENHOUSE_WOOD;
import static com.gregtechceu.gtceu.common.data.GTItems.*;
import static com.gregtechceu.gtceu.common.data.GTMaterials.*;

public class Greenhouse {

    public static void init(Consumer<FinishedRecipe> provider) {

        addCropRecipe(provider, "sugar_cane", false,
                new ItemStack(Items.SUGAR_CANE), 1000,
                new ItemStack(Items.SUGAR_CANE, 32));

        addCropRecipe(provider, "sugar_cane_boosted", true,
                new ItemStack(Items.SUGAR_CANE), 1000,
                new ItemStack(Items.SUGAR_CANE, 64));

        addCropRecipe(provider, "kelp", false,
                new ItemStack(Items.KELP), 2000,
                new ItemStack(Items.KELP, 32));

        addCropRecipe(provider, "kelp_boosted", true,
                new ItemStack(Items.KELP), 2000,
                new ItemStack(Items.KELP, 64));

        addCropRecipe(provider, "bamboo", false,
                new ItemStack(Items.BAMBOO), 1000,
                new ItemStack(Items.BAMBOO, 32));

        addCropRecipe(provider, "bamboo_boosted", true,
                new ItemStack(Items.BAMBOO), 1000,
                new ItemStack(Items.BAMBOO, 64));

        addCropRecipe(provider, "cactus", false,
                new ItemStack(Items.CACTUS), 1000,
                new ItemStack(Items.CACTUS, 32));

        addCropRecipe(provider, "cactus_boosted", true,
                new ItemStack(Items.CACTUS), 1000,
                new ItemStack(Items.CACTUS, 64));

        addCropRecipe(provider, "wheat", false,
                new ItemStack(Items.WHEAT_SEEDS), 1000,
                new ItemStack(Items.WHEAT, 32));

        addCropRecipe(provider, "wheat_boosted", true,
                new ItemStack(Items.WHEAT_SEEDS), 1000,
                new ItemStack(Items.WHEAT, 64));

        addCropRecipe(provider, "carrot", false,
                new ItemStack(Items.CARROT), 1000,
                new ItemStack(Items.CARROT, 32));

        addCropRecipe(provider, "carrot_boosted", true,
                new ItemStack(Items.CARROT), 1000,
                new ItemStack(Items.CARROT, 64));

        addCropRecipe(provider, "potato", false,
                new ItemStack(Items.POTATO), 1000,
                new ItemStack(Items.POTATO, 32));

        addCropRecipe(provider, "potato_boosted", true,
                new ItemStack(Items.POTATO), 1000,
                new ItemStack(Items.POTATO, 64));

        addCropRecipe(provider, "beetroot", false,
                new ItemStack(Items.BEETROOT_SEEDS), 1000,
                new ItemStack(Items.BEETROOT, 32));

        addCropRecipe(provider, "beetroot_boosted", true,
                new ItemStack(Items.BEETROOT_SEEDS), 1000,
                new ItemStack(Items.BEETROOT, 64));

        addCropRecipe(provider, "melon", false,
                new ItemStack(Items.MELON_SEEDS), 1000,
                new ItemStack(Items.MELON, 16));

        addCropRecipe(provider, "melon_boosted", true,
                new ItemStack(Items.MELON_SEEDS), 1000,
                new ItemStack(Items.MELON, 32));

        addCropRecipe(provider, "pumpkin", false,
                new ItemStack(Items.PUMPKIN_SEEDS), 1000,
                new ItemStack(Items.PUMPKIN, 16));

        addCropRecipe(provider, "pumpkin_boosted", true,
                new ItemStack(Items.PUMPKIN_SEEDS), 1000,
                new ItemStack(Items.PUMPKIN, 32));

        addCropRecipe(provider, "nether_wart", false,
                new ItemStack(Items.NETHER_WART), 1000,
                new ItemStack(Items.NETHER_WART, 16));

        addCropRecipe(provider, "nether_wart_boosted", true,
                new ItemStack(Items.NETHER_WART), 1000,
                new ItemStack(Items.NETHER_WART, 32));

        addCropRecipe(provider, "red_mushroom", false,
                new ItemStack(Items.RED_MUSHROOM), 1000,
                new ItemStack(Items.RED_MUSHROOM, 16));

        addCropRecipe(provider, "red_mushroom_boosted", true,
                new ItemStack(Items.RED_MUSHROOM), 1000,
                new ItemStack(Items.RED_MUSHROOM, 32));

        addCropRecipe(provider, "brown_mushroom", false,
                new ItemStack(Items.BROWN_MUSHROOM), 1000,
                new ItemStack(Items.BROWN_MUSHROOM, 16));

        addCropRecipe(provider, "brown_mushroom_boosted", true,
                new ItemStack(Items.BROWN_MUSHROOM), 1000,
                new ItemStack(Items.BROWN_MUSHROOM, 32));
        // ==========================================================================
        addTreeRecipe(provider, "rubber_sapling", false,
                GTBlocks.RUBBER_SAPLING.asStack(),
                GTBlocks.RUBBER_LOG.asStack(32),
                GTItems.STICKY_RESIN.asStack(8),
                GTBlocks.RUBBER_SAPLING.asStack(4));

        addTreeRecipe(provider, "rubber_sapling_boosted", true,
                GTBlocks.RUBBER_SAPLING.asStack(),
                GTBlocks.RUBBER_LOG.asStack(64),
                GTItems.STICKY_RESIN.asStack(16),
                GTBlocks.RUBBER_SAPLING.asStack(4));

        addTreeRecipe(provider, "oak_sapling", false,
                new ItemStack(Items.OAK_SAPLING),
                new ItemStack(Items.OAK_LOG, 64),
                new ItemStack(Items.OAK_SAPLING, 4));

        addTreeRecipe(provider, "oak_sapling_boosted", true,
                new ItemStack(Items.OAK_SAPLING),
                new ItemStack(Items.OAK_LOG, 64),
                new ItemStack(Items.OAK_LOG, 64),
                new ItemStack(Items.OAK_SAPLING, 4));

        addTreeRecipe(provider, "dark_oak_sapling", false,
                new ItemStack(Items.DARK_OAK_SAPLING),
                new ItemStack(Items.DARK_OAK_LOG, 64),
                new ItemStack(Items.DARK_OAK_SAPLING, 4));

        addTreeRecipe(provider, "dark_oak_sapling_boosted", true,
                new ItemStack(Items.DARK_OAK_SAPLING),
                new ItemStack(Items.DARK_OAK_LOG, 64),
                new ItemStack(Items.DARK_OAK_LOG, 64),
                new ItemStack(Items.DARK_OAK_SAPLING, 4));

        addTreeRecipe(provider, "spruce_sapling", false,
                new ItemStack(Items.SPRUCE_SAPLING),
                new ItemStack(Items.SPRUCE_LOG, 64),
                new ItemStack(Items.SPRUCE_SAPLING, 4));

        addTreeRecipe(provider, "spruce_sapling_boosted", true,
                new ItemStack(Items.SPRUCE_SAPLING),
                new ItemStack(Items.SPRUCE_LOG, 64),
                new ItemStack(Items.SPRUCE_LOG, 64),
                new ItemStack(Items.SPRUCE_SAPLING, 4));

        addTreeRecipe(provider, "birch_sapling", false,
                new ItemStack(Items.BIRCH_SAPLING),
                new ItemStack(Items.BIRCH_LOG, 64),
                new ItemStack(Items.BIRCH_SAPLING, 4));

        addTreeRecipe(provider, "birch_sapling_boosted", true,
                new ItemStack(Items.BIRCH_SAPLING),
                new ItemStack(Items.BIRCH_LOG, 64),
                new ItemStack(Items.BIRCH_LOG, 64),
                new ItemStack(Items.BIRCH_SAPLING, 4));

        addTreeRecipe(provider, "acacia_sapling", false,
                new ItemStack(Items.ACACIA_SAPLING),
                new ItemStack(Items.ACACIA_LOG, 64),
                new ItemStack(Items.ACACIA_SAPLING, 4));

        addTreeRecipe(provider, "acacia_sapling_boosted", true,
                new ItemStack(Items.ACACIA_SAPLING),
                new ItemStack(Items.ACACIA_LOG, 64),
                new ItemStack(Items.ACACIA_LOG, 64),
                new ItemStack(Items.ACACIA_SAPLING, 4));

        addTreeRecipe(provider, "jungle_sapling", false,
                new ItemStack(Items.JUNGLE_SAPLING),
                new ItemStack(Items.JUNGLE_LOG, 64),
                new ItemStack(Items.JUNGLE_SAPLING, 4));

        addTreeRecipe(provider, "jungle_sapling_boosted", true,
                new ItemStack(Items.JUNGLE_SAPLING),
                new ItemStack(Items.JUNGLE_LOG, 64),
                new ItemStack(Items.JUNGLE_LOG, 64),
                new ItemStack(Items.JUNGLE_SAPLING, 4));

        addTreeRecipe(provider, "mangrove_propagule", false,
                new ItemStack(Items.MANGROVE_PROPAGULE),
                new ItemStack(Items.MANGROVE_LOG, 64),
                new ItemStack(Items.MANGROVE_PROPAGULE, 4));

        addTreeRecipe(provider, "mangrove_propagule_boosted", true,
                new ItemStack(Items.MANGROVE_PROPAGULE),
                new ItemStack(Items.MANGROVE_LOG, 64),
                new ItemStack(Items.MANGROVE_LOG, 64),
                new ItemStack(Items.MANGROVE_PROPAGULE, 4));
    }

    private static void addTreeRecipe(Consumer<FinishedRecipe> provider,
                                      String id,
                                      boolean boosted,
                                      ItemStack sapling,
                                      ItemStack... outputsWood) {
        var builder = GREENHOUSE_WOOD.recipeBuilder(id)
                .circuitMeta(boosted ? 2 : 1) // If boosted, use 2, else 1
                .notConsumable(sapling)
                .inputFluids(Water.getFluid(1000))
                .duration(boosted ? 250 : 320) // If boosted, last 250t, else 320t)
                .EUt(GTValues.VA[GTValues.MV]);
        if (boosted) {
            builder.inputItems(FERTILIZER.asStack(4));
        }
        for (ItemStack output : outputsWood) {
            builder.outputItems(output);
        }
        builder.save(provider);
    }

    private static void addCropRecipe(Consumer<FinishedRecipe> provider,
                                      String id, boolean boosted,
                                      ItemStack input, int waterMb,
                                      ItemStack output) {
        var builder = GREENHOUSE_CROPS.recipeBuilder(id)
                .circuitMeta(boosted ? 2 : 1)
                .notConsumable(input)
                .inputFluids(Water.getFluid(waterMb))
                .outputItems(output)
                .duration(boosted ? 250 : 320)
                .EUt(GTValues.VA[GTValues.MV]);
        if (boosted) {
            builder.inputItems(FERTILIZER.asStack(4));
        }
        builder.save(provider);
    }
}
