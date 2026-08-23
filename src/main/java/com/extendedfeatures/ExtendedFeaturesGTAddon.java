package com.extendedfeatures;

import com.extendedfeatures.client.LangHandler;
import com.extendedfeatures.init.utils.internal.recipes.*;

import com.gregtechceu.gtceu.api.addon.GTAddon;
import com.gregtechceu.gtceu.api.addon.IGTAddon;
import com.gregtechceu.gtceu.api.registry.registrate.GTRegistrate;

import com.tterrag.registrate.providers.ProviderType;

import net.minecraft.data.recipes.FinishedRecipe;

import java.util.function.Consumer;

@SuppressWarnings("unused")
@GTAddon
public class ExtendedFeaturesGTAddon implements IGTAddon {

    @Override
    public GTRegistrate getRegistrate() {
        return ExtendedFeaturesCore.ExtendedFeaturesRegister;
    }

    @Override
    public void initializeAddon() {
        ExtendedFeaturesCore.ExtendedFeaturesRegister.addDataGenerator(ProviderType.LANG, LangHandler::init);
    }

    @Override
    public String addonModId() {
        return ExtendedFeaturesCore.MOD_ID;
    }

    @Override
    public void addRecipes(Consumer<FinishedRecipe> provider) {
        MultiblockControllers.init(provider);
        RockProcessing.init(provider);
        UniversalCircuits.init(provider);
        GreenhouseWood.init(provider);
        GreenhouseCrops.init(provider);
        ChemicalSkips.init(provider);
        AirCollector.init(provider);
    }

}
