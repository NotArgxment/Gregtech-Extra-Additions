package com.extendedfeatures.client.internal.recipes;

import com.gregtechceu.gtceu.api.GTValues;

import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;

import java.util.function.Consumer;

import static com.extendedfeatures.client.RecipeTypes.ROCK_PROCESSING_RECIPES;
import static com.gregtechceu.gtceu.api.GTValues.L;
import static com.gregtechceu.gtceu.api.data.tag.TagPrefix.*;
import static com.gregtechceu.gtceu.common.data.GTMaterials.*;

public class RockProcessing {

    public static void init(Consumer<FinishedRecipe> provider) {

        final int rpfTime = 1200;
        final int rpfEnergy = GTValues.VA[GTValues.HV];

        ROCK_PROCESSING_RECIPES.recipeBuilder("deepslate_processing")
                .inputItems(new ItemStack(Blocks.DEEPSLATE), 1)
                .inputFluids(Lubricant.getFluid(L * 4))
                .outputItems(dust, Potassium, 1)
                .outputItems(dust, Magnesium, 1)
                .outputItems(dust, Aluminium, 1)
                .outputItems(dust, Silicon, 1)
                .outputFluids(Fluorine.getFluid(L * 2))
                .outputFluids(Oxygen.getFluid(L * 4))
                .duration(rpfTime)
                .EUt(rpfEnergy)
                .save(provider);

        ROCK_PROCESSING_RECIPES.recipeBuilder("andesite_processing")
                .inputItems(new ItemStack(Blocks.ANDESITE), 1)
                .inputFluids(Lubricant.getFluid(L * 4))
                .outputItems(dust, Magnesium, 1)
                .outputItems(dust, Silicon, 1)
                .outputFluids(Hydrogen.getFluid(L * 2))
                .outputFluids(Oxygen.getFluid(L * 4))
                .duration(rpfTime)
                .EUt(rpfEnergy)
                .save(provider);

        ROCK_PROCESSING_RECIPES.recipeBuilder("diorite_processing")
                .inputItems(new ItemStack(Blocks.DIORITE), 1)
                .inputFluids(Lubricant.getFluid(L * 4))
                .outputItems(dust, Sodium, 1)
                .outputItems(dust, Sulfur, 1)
                .outputFluids(Water.getFluid(L * 4))
                .outputFluids(Oxygen.getFluid(L * 2))
                .duration(rpfTime)
                .EUt(rpfEnergy)
                .save(provider);

        ROCK_PROCESSING_RECIPES.recipeBuilder("granite_processing")
                .inputItems(new ItemStack(Blocks.GRANITE), 1)
                .inputFluids(Lubricant.getFluid(L * 4))
                .outputItems(dust, SiliconDioxide, 1)
                .outputItems(dust, Calcite, 1)
                .outputItems(dust, Flint, 1)
                .duration(rpfTime)
                .EUt(rpfEnergy)
                .save(provider);

        ROCK_PROCESSING_RECIPES.recipeBuilder("end_stone_processing")
                .inputItems(new ItemStack(Blocks.END_STONE), 1)
                .inputFluids(Lubricant.getFluid(L * 4))
                .chancedOutput(new ItemStack(Blocks.SAND), 8000, 5)
                .chancedOutput(dust, Tungstate, 1, 5000, 5)
                .chancedOutput(dust, Platinum, 1, 2500, 5)
                .outputFluids(Helium.getFluid(L))
                .duration(rpfTime)
                .EUt(rpfEnergy)
                .save(provider);

        ROCK_PROCESSING_RECIPES.recipeBuilder("netherrack_processing")
                .inputItems(new ItemStack(Blocks.NETHERRACK), 1)
                .inputFluids(Lubricant.getFluid(L * 4))
                .chancedOutput(dust, Coal, 1, 6500, 25)
                .chancedOutput(dust, Sulfur, 1, 8000, 50)
                .chancedOutput(dust, Redstone, 1, 5000, 50)
                .chancedOutput(dust, Gold, 1, 2000, 10)
                .duration(rpfTime)
                .EUt(rpfEnergy)
                .save(provider);

        ROCK_PROCESSING_RECIPES.recipeBuilder("obsidian_processing")
                .inputItems(new ItemStack(Blocks.OBSIDIAN), 1)
                .inputFluids(Lubricant.getFluid(L * 4))
                .outputItems(dust, Magnesium, 1)
                .outputItems(dust, Iron, 1)
                .outputItems(dust, Silicon, 1)
                .outputFluids(Oxygen.getFluid(L * 3))
                .duration(rpfTime)
                .EUt(rpfEnergy)
                .save(provider);
    }
}
