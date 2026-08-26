package com.extendedfeatures.client.internal.recipes;

import com.gregtechceu.gtceu.api.GTValues;
import net.minecraft.data.recipes.FinishedRecipe;

import java.util.function.Consumer;

import static com.extendedfeatures.client.RecipeTypes.CHEMICAL_REDUCTION;
import static com.gregtechceu.gtceu.api.data.tag.TagPrefix.*;
import static com.gregtechceu.gtceu.common.data.GTMaterials.*;

public class ChemicalSkips {

    public static void init(Consumer<FinishedRecipe> provider) {

        // Platline
        CHEMICAL_REDUCTION.recipeBuilder("platinum_processing_line")
                .inputItems(dust, PlatinumGroupSludge, 16)
                .inputFluids(AquaRegia.getFluid(2000))
                .outputItems(dust, Platinum, 2)
                .outputItems(dust, Palladium, 2)
                .outputItems(dust, Ruthenium, 1)
                .outputItems(dust, Rhodium, 1)
                .outputItems(dust, Osmium, 1)
                .outputItems(dust, Iridium, 1)
                .outputFluids(NitricAcid.getFluid(2000))
                .outputFluids(HydrochloricAcid.getFluid(1000))
                .circuitMeta(1)
                .EUt(GTValues.VA[GTValues.LuV])
                .duration(700)
                .save(provider);

        // Naqline
        CHEMICAL_REDUCTION.recipeBuilder("naquadah_processing_line")
                .inputItems(dust, Naquadah, 12)
                .inputFluids(FluoroantimonicAcid.getFluid(4000))
                .outputItems(dust, NaquadahEnriched, 2)
                .outputItems(dust, Naquadria, 1)
                .outputItems(dust, Trinium, 2)
                .outputItems(dust, Antimony, 4)
                .outputItems(dust, Indium, 3)
                .outputFluids(Hydrogen.getFluid(2000))
                .outputFluids(Fluorine.getFluid(8000))
                .circuitMeta(4)
                .EUt(GTValues.VA[GTValues.LuV])
                .duration(2000)
                .save(provider);

        /* For 2.5.0, WIP

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
