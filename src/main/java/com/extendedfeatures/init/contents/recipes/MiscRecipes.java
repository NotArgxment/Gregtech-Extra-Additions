package com.extendedfeatures.init.contents.recipes;

import com.extendedfeatures.init.contents.misc.UniversalCircuits;
import com.gregtechceu.gtceu.api.GTCEuAPI;
import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.data.recipe.CustomTags;
import com.tterrag.registrate.util.entry.ItemEntry;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;

import java.util.function.Consumer;

import static com.extendedfeatures.client.EFRecipeTypes.*;
import static com.extendedfeatures.init.contents.electric.Multiblocks.LARGE_GAS_COLLECTOR;
import static com.gregtechceu.gtceu.api.GTValues.*;
import static com.gregtechceu.gtceu.api.data.tag.TagPrefix.*;
import static com.gregtechceu.gtceu.common.data.GTItems.*;
import static com.gregtechceu.gtceu.common.data.GTMachines.GAS_COLLECTOR;
import static com.gregtechceu.gtceu.common.data.GTMaterials.*;
import static com.gregtechceu.gtceu.common.data.GTRecipeTypes.*;

public class MiscRecipes {

    public static void init(Consumer<FinishedRecipe> provider) {

        // ===============
        // Rock Processing
        // ===============

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

        // ===================
        // Large Gas Collector
        // ===================

        ASSEMBLY_LINE_RECIPES.recipeBuilder("lgc_controller")
                .inputItems(GAS_COLLECTOR[IV], 4)
                .inputItems(CustomTags.IV_CIRCUITS, 8)
                .inputItems(gear, TungstenSteel, 4)
                .inputItems(plateDouble, TungstenSteel, 2)
                .inputItems(ELECTRIC_MOTOR_IV, 16)
                .inputItems(ELECTRIC_PUMP_IV, 16)
                .inputFluids(SolderingAlloy.getFluid(576))
                .inputFluids(Lubricant.getFluid(576))
                .outputItems(LARGE_GAS_COLLECTOR)
                .scannerResearch(b -> b
                        .researchStack(GAS_COLLECTOR[IV].asStack())
                        .duration(500)
                        .EUt(GTValues.VA[IV])
                )
                .duration(750)
                .EUt(GTValues.VA[GTValues.LuV])
                .save(provider);

        AIR_COLLECTOR.recipeBuilder("air")
                .circuitMeta(1)
                .outputFluids(Air.getFluid(4000))
                .dimension(Level.OVERWORLD.location())
                .duration(100)
                .EUt(GTValues.VA[GTValues.MV])
                .save(provider);

        AIR_COLLECTOR.recipeBuilder("nether_air")
                .circuitMeta(2)
                .outputFluids(NetherAir.getFluid(4000))
                .dimension(Level.NETHER.location())
                .duration(200)
                .EUt(GTValues.VA[GTValues.EV])
                .save(provider);

        AIR_COLLECTOR.recipeBuilder("ender_air")
                .circuitMeta(3)
                .outputFluids(EnderAir.getFluid(4000))
                .dimension(Level.END.location())
                .duration(300)
                .EUt(GTValues.VA[GTValues.LuV])
                .save(provider);

        // ===================
        // Universal Circuits
        // ===================
        buildCircuitRecipe(provider, "ulv_universal_circuit", CustomTags.ULV_CIRCUITS, GTValues.ULV);
        buildCircuitRecipe(provider, "lv_universal_circuit", CustomTags.LV_CIRCUITS, GTValues.LV);
        buildCircuitRecipe(provider, "mv_universal_circuit", CustomTags.MV_CIRCUITS, GTValues.MV);
        buildCircuitRecipe(provider, "hv_universal_circuit", CustomTags.HV_CIRCUITS, GTValues.HV);
        buildCircuitRecipe(provider, "ev_universal_circuit", CustomTags.EV_CIRCUITS, GTValues.EV);
        buildCircuitRecipe(provider, "iv_universal_circuit", CustomTags.IV_CIRCUITS, GTValues.IV);
        buildCircuitRecipe(provider, "luv_universal_circuit", CustomTags.LuV_CIRCUITS, GTValues.LuV);
        buildCircuitRecipe(provider, "zpm_universal_circuit", CustomTags.ZPM_CIRCUITS, GTValues.ZPM);
        buildCircuitRecipe(provider, "uv_universal_circuit", CustomTags.UV_CIRCUITS, GTValues.UV);

        if (GTCEuAPI.isHighTier()) {
            buildCircuitRecipe(provider, "uhv_universal_circuit", CustomTags.UHV_CIRCUITS, GTValues.UHV);
            buildCircuitRecipe(provider, "uev_universal_circuit", CustomTags.UEV_CIRCUITS, GTValues.UEV);
            buildCircuitRecipe(provider, "uiv_universal_circuit", CustomTags.UIV_CIRCUITS, GTValues.UIV);
            buildCircuitRecipe(provider, "uxv_universal_circuit", CustomTags.UXV_CIRCUITS, GTValues.UXV);
            buildCircuitRecipe(provider, "opv_universal_circuit", CustomTags.OpV_CIRCUITS, GTValues.OpV);
        }
    }

    private static void buildCircuitRecipe(Consumer<FinishedRecipe> provider, String recipeName,
                                           TagKey<Item> inputTag, int tier) {
        ItemEntry<Item> output = UniversalCircuits.UNIVERSAL_CIRCUITS[tier];
        if (output == null) return;

        ASSEMBLER_RECIPES.recipeBuilder(recipeName)
                .inputItems(inputTag)
                .outputItems(output)
                .duration(10)
                .EUt(GTValues.VA[GTValues.LV])
                .circuitMeta(10)
                .save(provider);
    }

}
