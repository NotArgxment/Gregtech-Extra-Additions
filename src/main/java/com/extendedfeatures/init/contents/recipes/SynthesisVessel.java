package com.extendedfeatures.init.contents.recipes;

import com.gregtechceu.gtceu.api.GTValues;
import net.minecraft.data.recipes.FinishedRecipe;

import java.util.function.Consumer;

import static com.extendedfeatures.client.EFRecipeTypes.CHEMICAL_REDUCTION;
import static com.gregtechceu.gtceu.api.data.tag.TagPrefix.*;
import static com.gregtechceu.gtceu.common.data.GTMaterials.*;

public class SynthesisVessel {

    public static void init(Consumer<FinishedRecipe> provider) {

        // Platline
        CHEMICAL_REDUCTION.recipeBuilder("platinum_processing_line")
                .inputItems(dust, PlatinumGroupSludge, 12)
                .inputFluids(AquaRegia.getFluid(1500))
                .outputItems(dust, Platinum, 4)
                .outputItems(dust, Palladium, 3)
                .outputItems(dust, Ruthenium, 2)
                .outputItems(dust, Rhodium, 3)
                .outputItems(dust, Osmium, 2)
                .outputItems(dust, Iridium, 2)
                .outputFluids(NitricAcid.getFluid(2300))
                .outputFluids(HydrochloricAcid.getFluid(1500))
                .EUt(GTValues.VA[GTValues.IV])
                .duration(400)
                .save(provider);

        // Naqline
        CHEMICAL_REDUCTION.recipeBuilder("naquadah_processing_line")
                .inputItems(dust, Naquadah, 16)
                .inputFluids(FluoroantimonicAcid.getFluid(4400))
                .outputItems(dust, NaquadahEnriched, 4)
                .outputItems(dust, Naquadria, 2)
                .outputItems(dust, Trinium, 3)
                .outputItems(dust, Antimony, 5)
                .outputItems(dust, Indium, 4)
                .outputFluids(Hydrogen.getFluid(2300))
                .outputFluids(Fluorine.getFluid(8100))
                .EUt(GTValues.VA[GTValues.LuV])
                .duration(500)
                .save(provider);

        /* For 3.1.0, WIP

        // ======================================
        //          Miscellaneous Skips
        // ======================================

        // Polyethylene (is this really needed?)
        CHEMICAL_REDUCTION.recipeBuilder("polyethylene")

                .outputFluids(Polyethylene.getFluid(8000))
                .circuitMeta(9)
                .duration(100)
                .EUt(GTValues.VA[GTValues.HV])
                .save(provider);

        // PVC
        CHEMICAL_REDUCTION.recipeBuilder("polyvinyl_chloride")

                .outputFluids(PolyvinylChloride.getFluid(16000))
                .circuitMeta(11)
                .duration(100)
                .EUt(GTValues.VA[GTValues.HV])
                .save(provider);

        // PTFE
        CHEMICAL_REDUCTION.recipeBuilder("polytetrafluoroethylene")

                .outputFluids(Polytetrafluoroethylene.getFluid(8000))
                .circuitMeta(6)
                .duration(300)
                .EUt(GTValues.VA[GTValues.EV])
                .save(provider);

        // PBI
        CHEMICAL_REDUCTION.recipeBuilder("polybenzimidazole")

                .outputFluids(Polybenzimidazole.getFluid(8000))
                .circuitMeta(18)
                .duration(600)
                .EUt(GTValues.VA[GTValues.LuV])
                .save(provider);

        // PVB
        CHEMICAL_REDUCTION.recipeBuilder("polyvinyl_butyral")

                .outputFluids(PolyvinylButyral.getFluid(8000))
                .circuitMeta(3)
                .duration(250)
                .EUt(GTValues.VA[GTValues.EV])
                .save(provider);

        // PPS
        CHEMICAL_REDUCTION.recipeBuilder("polyphenylene_sulfide")

                .outputFluids(PolyphenyleneSulfide.getFluid(6500))
                .circuitMeta(9)
                .duration(300)
                .EUt(GTValues.VA[GTValues.EV])
                .save(provider);

        // Epoxy
        CHEMICAL_REDUCTION.recipeBuilder("epoxy")

                .outputFluids(Epoxy.getFluid(7100))
                .circuitMeta(22)
                .duration(500)
                .EUt(GTValues.VA[GTValues.IV])
                .save(provider);

        // Sodium Persulfate


        // Iron III Chloride


        //


        // Mutagen

         */

    }
}
