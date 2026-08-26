package com.extendedfeatures.client.internal.recipes;

import com.extendedfeatures.client.RecipeTypes;

import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.common.data.*;

import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.world.item.*;

import java.util.function.Consumer;

public class GreenhouseWood {

    public static void init(Consumer<FinishedRecipe> provider) {
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
                                      ItemStack... outputs) {
        var builder = RecipeTypes.GREENHOUSE_WOOD.recipeBuilder(id)
                .circuitMeta(boosted ? 2 : 1) // If boosted, use 2, else 1
                .notConsumable(sapling)
                .inputFluids(GTMaterials.Water.getFluid(1000))
                .duration(boosted ? 250 : 320) // If boosted, last 250t, else 320t)
                .EUt(GTValues.VA[GTValues.MV]);

        if (boosted) {
            builder.inputItems(GTItems.FERTILIZER.asStack(4));
        }

        for (ItemStack output : outputs) {
            builder.outputItems(output);
        }

        builder.save(provider);
    }
}
