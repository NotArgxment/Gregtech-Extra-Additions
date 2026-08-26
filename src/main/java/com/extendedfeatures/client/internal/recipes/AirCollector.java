package com.extendedfeatures.client.internal.recipes;

import com.gregtechceu.gtceu.api.GTValues;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.world.level.Level;

import java.util.function.Consumer;

import static com.extendedfeatures.client.RecipeTypes.AIR_COLLECTOR;
import static com.gregtechceu.gtceu.common.data.GTMaterials.*;

public class AirCollector {

    public static void init(Consumer<FinishedRecipe> provider) {

        /*
         * These recipes are separated from the normal Gas Collector recipes
         * to be "balanced" with parallel hatches
         *
         * I PERSONALLY ENCOURAGE TO NOT SPAM SINGLE BLOCK GAS COLLECTORS JUST BECAUSE THE RECIPE GIVES 10B!
         *                           ^^^^^^^^
         */

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

    }
}
