package com.extendedfeatures.init.contents.recipes;

import net.minecraft.data.recipes.FinishedRecipe;

import java.util.function.Consumer;

import static com.gregtechceu.gtceu.api.GTValues.*;
import static com.gregtechceu.gtceu.api.data.tag.TagPrefix.*;
import static com.gregtechceu.gtceu.common.data.GTItems.*;
import static com.gregtechceu.gtceu.common.data.GTMaterials.*;
import static com.gregtechceu.gtceu.common.data.GTRecipeTypes.*;
import static com.gregtechceu.gtceu.data.recipe.CustomTags.*;

public class SolarPanels {

    public static void init(Consumer<FinishedRecipe> provider) {

        int duration = 240;

        // LV Solar Panel
        ASSEMBLER_RECIPES.recipeBuilder("lv_solar_panel")
                .inputItems(plate, Steel, 8)
                .inputItems(frameGt, Steel, 4)
                .inputItems(COVER_SOLAR_PANEL)
                .inputItems(LV_CIRCUITS, 4)
                .inputItems(SENSOR_LV, 8)
                .inputFluids(SolderingAlloy.getFluid(1008))
                .outputItems(COVER_SOLAR_PANEL_LV)
                .circuitMeta(20)
                .duration(duration)
                .EUt(VA[LV])
                .save(provider);

        // MV Solar Panel
        ASSEMBLER_RECIPES.recipeBuilder("mv_solar_panel")
                .inputItems(plate, BlackSteel, 8)
                .inputItems(frameGt, BlackSteel, 4)
                .inputItems(COVER_SOLAR_PANEL_LV)
                .inputItems(MV_CIRCUITS, 4)
                .inputItems(SENSOR_MV, 8)
                .inputFluids(SolderingAlloy.getFluid(1200))
                .outputItems(COVER_SOLAR_PANEL_MV)
                .circuitMeta(20)
                .duration(duration)
                .EUt(VA[MV])
                .save(provider);

        // HV Solar Panel
        ASSEMBLER_RECIPES.recipeBuilder("hv_solar_panel")
                .inputItems(plate, StainlessSteel, 8)
                .inputItems(frameGt, StainlessSteel, 4)
                .inputItems(COVER_SOLAR_PANEL_MV)
                .inputItems(HV_CIRCUITS, 4)
                .inputItems(SENSOR_HV, 8)
                .inputFluids(SolderingAlloy.getFluid(1200))
                .outputItems(COVER_SOLAR_PANEL_HV)
                .circuitMeta(20)
                .duration(duration)
                .EUt(VA[HV])
                .save(provider);

        // EV Solar Panel
        ASSEMBLER_RECIPES.recipeBuilder("ev_solar_panel")
                .inputItems(plate, Titanium, 8)
                .inputItems(frameGt, Titanium, 4)
                .inputItems(COVER_SOLAR_PANEL_HV)
                .inputItems(EV_CIRCUITS, 4)
                .inputItems(SENSOR_EV, 8)
                .inputFluids(SolderingAlloy.getFluid(1200))
                .outputItems(COVER_SOLAR_PANEL_EV)
                .circuitMeta(20)
                .duration(duration)
                .EUt(VA[EV])
                .save(provider);

        // IV Solar Panel
        ASSEMBLER_RECIPES.recipeBuilder("iv_solar_panel")
                .inputItems(plate, TungstenSteel, 8)
                .inputItems(frameGt, TungstenSteel, 4)
                .inputItems(COVER_SOLAR_PANEL_EV)
                .inputItems(IV_CIRCUITS, 4)
                .inputItems(SENSOR_IV, 8)
                .inputFluids(SolderingAlloy.getFluid(1200))
                .outputItems(COVER_SOLAR_PANEL_IV)
                .circuitMeta(20)
                .duration(duration)
                .EUt(VA[IV])
                .save(provider);

        // LuV Solar Panel
        ASSEMBLER_RECIPES.recipeBuilder("luv_solar_panel")
                .inputItems(plate, RhodiumPlatedPalladium, 8)
                .inputItems(frameGt, RhodiumPlatedPalladium, 4)
                .inputItems(COVER_SOLAR_PANEL_IV)
                .inputItems(LuV_CIRCUITS, 4)
                .inputItems(SENSOR_LuV, 8)
                .inputFluids(SolderingAlloy.getFluid(1200))
                .outputItems(COVER_SOLAR_PANEL_LuV)
                .circuitMeta(20)
                .duration(duration)
                .EUt(VA[LuV])
                .save(provider);

        // ZPM Solar Panel
        ASSEMBLER_RECIPES.recipeBuilder("zpm_solar_panel")
                .inputItems(plate, NaquadahAlloy, 8)
                .inputItems(frameGt, NaquadahAlloy, 4)
                .inputItems(COVER_SOLAR_PANEL_LuV)
                .inputItems(ZPM_CIRCUITS, 4)
                .inputItems(SENSOR_ZPM, 8)
                .inputFluids(SolderingAlloy.getFluid(1200))
                .outputItems(COVER_SOLAR_PANEL_ZPM)
                .circuitMeta(20)
                .duration(duration)
                .EUt(VA[ZPM])
                .save(provider);

        // UV Solar Panel
        ASSEMBLER_RECIPES.recipeBuilder("uv_solar_panel")
                .inputItems(plate, Darmstadtium, 8)
                .inputItems(frameGt, TungstenCarbide, 4)
                .inputItems(COVER_SOLAR_PANEL_ZPM)
                .inputItems(UV_CIRCUITS, 4)
                .inputItems(SENSOR_UV, 8)
                .inputFluids(SolderingAlloy.getFluid(1200))
                .outputItems(COVER_SOLAR_PANEL_UV)
                .circuitMeta(20)
                .duration(duration)
                .EUt(VA[UV])
                .save(provider);

    }

}

