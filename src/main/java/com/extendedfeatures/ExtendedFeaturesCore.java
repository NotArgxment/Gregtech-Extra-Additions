package com.extendedfeatures;

import com.extendedfeatures.client.EFRecipeTypes;
import com.extendedfeatures.client.internal.ConfigClass;
import com.extendedfeatures.init.contents.electric.Machines;
import com.extendedfeatures.init.contents.electric.Multiblocks;
import com.extendedfeatures.init.contents.misc.UniversalCircuits;
import com.extendedfeatures.client.internal.rendering.PacketManager;

import com.gregtechceu.gtceu.api.GTCEuAPI;
import com.gregtechceu.gtceu.api.machine.MachineDefinition;
import com.gregtechceu.gtceu.api.recipe.GTRecipeType;
import com.gregtechceu.gtceu.api.registry.registrate.GTRegistrate;

import net.minecraft.resources.ResourceLocation;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(ExtendedFeaturesCore.MOD_ID)
@SuppressWarnings("removal")
public class ExtendedFeaturesCore {

    public static final String MOD_ID = "extendedfeatures";
    public static final Logger LOGGER = LogManager.getLogger();
    public static final GTRegistrate ExtendedFeaturesRegister = GTRegistrate.create(ExtendedFeaturesCore.MOD_ID);

    public ExtendedFeaturesCore() {

        ConfigClass.init();

        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        UniversalCircuits.register(modEventBus);

        ExtendedFeaturesRegister.registerRegistrate();

        // Has to be executed before common setup
        PacketManager.register();

        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::clientSetup);
        modEventBus.addListener(this::onBuildCreativeTab);

        modEventBus.addGenericListener(GTRecipeType.class, this::registerRecipeTypes);
        modEventBus.addGenericListener(MachineDefinition.class, this::registerMachines);

        MinecraftForge.EVENT_BUS.register(this);

    }

    public static ResourceLocation id(String path) {
        return new ResourceLocation(ExtendedFeaturesCore.MOD_ID, path);
    }

    // tracks "isHighTier" condition from GTCEu
    private void onBuildCreativeTab(BuildCreativeModeTabContentsEvent event) {
        if (!event.getTabKey().location().getNamespace().equals(ExtendedFeaturesCore.MOD_ID)) return;
        if (GTCEuAPI.isHighTier()) return;
        UniversalCircuits.getHighTierCircuits().forEach(entry -> event.getEntries().remove(entry.asStack()));
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> LOGGER.info("Hello from FMLCommonSetupEvent!"));
    }

    private void clientSetup(final FMLClientSetupEvent event) {
        LOGGER.info("If you see this, congrats, the addon has been loaded!");
    }

    private void registerRecipeTypes(GTCEuAPI.RegisterEvent<ResourceLocation, GTRecipeType> event) {
        EFRecipeTypes.init();
    }

    private void registerMachines(GTCEuAPI.RegisterEvent<ResourceLocation, MachineDefinition> event) {
        Multiblocks.init();
        Machines.init();
    }

}
