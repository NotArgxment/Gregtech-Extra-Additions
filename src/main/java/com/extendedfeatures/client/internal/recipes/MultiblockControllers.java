package com.extendedfeatures.client.internal.recipes;

import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.data.recipe.CustomTags;
import net.minecraft.data.recipes.FinishedRecipe;

import java.util.function.Consumer;

import static com.extendedfeatures.init.utils.electric.Multiblocks.LARGE_GAS_COLLECTOR;

import static com.gregtechceu.gtceu.api.GTValues.*;
import static com.gregtechceu.gtceu.api.data.tag.TagPrefix.*;
import static com.gregtechceu.gtceu.common.data.GTItems.*;
import static com.gregtechceu.gtceu.common.data.GTMachines.*;
import static com.gregtechceu.gtceu.common.data.GTMaterials.*;
import static com.gregtechceu.gtceu.common.data.GTRecipeTypes.ASSEMBLY_LINE_RECIPES;

public class MultiblockControllers {

    public static void init(Consumer<FinishedRecipe> provider) {

        // Large Air Collector: placeholder recipe at the moment
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

    }
}
