package com.extendedfeatures.init.contents.recipes;

import com.gregtechceu.gtceu.common.data.GTBlocks;
import com.gregtechceu.gtceu.common.data.GTItems;
import net.minecraft.data.recipes.FinishedRecipe;

import java.util.function.Consumer;

import static com.extendedfeatures.client.EFRecipeTypes.DISASSEMBER_COMPONENTS;
import static com.gregtechceu.gtceu.api.GTValues.*;
import static com.gregtechceu.gtceu.api.data.tag.TagPrefix.*;
import static com.gregtechceu.gtceu.common.data.GTMaterials.*;

public class Disassembler {

    public static void init(Consumer<FinishedRecipe> provider) {

        // Recipes for expensive casings disassembly

        // MK1 Casing
        DISASSEMBER_COMPONENTS.recipeBuilder("mk1_casing")
                .inputItems(GTBlocks.FUSION_CASING, 2)
                .outputItems(GTBlocks.MACHINE_CASING_LuV)
                .outputItems(GTBlocks.SUPERCONDUCTING_COIL)
                .outputItems(GTItems.NEUTRON_REFLECTOR)
                .outputItems(GTItems.ELECTRIC_PUMP_LuV)
                .outputItems(plate, TungstenSteel, 6)
                .outputFluids(Polybenzimidazole.getFluid(288))
                .EUt(VA[LuV])
                .duration(200)
                .save(provider);

       // MK2 Casing
        DISASSEMBER_COMPONENTS.recipeBuilder("mk2_casing")
                .inputItems(GTBlocks.FUSION_CASING_MK2, 2)
                .outputItems(GTBlocks.MACHINE_CASING_ZPM)
                .outputItems(GTBlocks.FUSION_COIL)
                .outputItems(GTItems.VOLTAGE_COIL_ZPM)
                .outputItems(GTItems.FIELD_GENERATOR_LuV)
                .outputItems(plate, Europium, 6)
                .outputFluids(Polybenzimidazole.getFluid(288))
                .EUt(VA[ZPM])
                .duration(200)
                .save(provider);

        // MK3 Casing
        DISASSEMBER_COMPONENTS.recipeBuilder("mk3_casing")
                .inputItems(GTBlocks.FUSION_CASING_MK3, 2)
                .outputItems(GTBlocks.MACHINE_CASING_UV)
                .outputItems(GTBlocks.FUSION_COIL)
                .outputItems(GTItems.VOLTAGE_COIL_UV)
                .outputItems(GTItems.FIELD_GENERATOR_ZPM)
                .outputItems(plate, Americium, 6)
                .outputFluids(Polybenzimidazole.getFluid(576))
                .EUt(VA[UV])
                .duration(200)
                .save(provider);
    }

}
