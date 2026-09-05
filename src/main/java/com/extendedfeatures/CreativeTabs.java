package com.extendedfeatures;

import com.gregtechceu.gtceu.common.data.GTCreativeModeTabs;
import com.gregtechceu.gtceu.common.data.GTItems;
import com.gregtechceu.gtceu.common.data.machines.GCYMMachines;
import com.gregtechceu.gtceu.common.data.machines.GTResearchMachines;
import com.tterrag.registrate.util.entry.RegistryEntry;
import net.minecraft.world.item.CreativeModeTab;

import static com.extendedfeatures.ExtendedFeaturesCore.ExtendedFeaturesRegister;

public class CreativeTabs {

    // Credits to Herr Jolo for making the first creative tab!

    public static RegistryEntry<CreativeModeTab> MULTIBLOCKS_TAB = ExtendedFeaturesRegister
            .defaultCreativeTab(ExtendedFeaturesCore.MOD_ID + "_multiblocks",
                    builder -> builder
                            .displayItems(
                                    new GTCreativeModeTabs.RegistrateDisplayItemsGenerator(
                                            ExtendedFeaturesCore.MOD_ID + "_multiblocks",
                                            ExtendedFeaturesRegister)
                            )
                            .title(ExtendedFeaturesRegister.addLang(
                                    "itemGroup", ExtendedFeaturesCore.id("creative_tab_1"),
                                    "Ext. Features: Multiblocks")
                            )
                            .icon(GCYMMachines.LARGE_ASSEMBLER::asStack)
                            .build()
            )
            .register();

    public static RegistryEntry<CreativeModeTab> CIRCUITS_TAB = ExtendedFeaturesRegister
            .defaultCreativeTab(ExtendedFeaturesCore.MOD_ID + "_circuits",
                    builder -> builder
                            .displayItems(
                                    new GTCreativeModeTabs.RegistrateDisplayItemsGenerator(
                                            ExtendedFeaturesCore.MOD_ID + "_circuits",
                                            ExtendedFeaturesRegister)
                            )
                            .title(ExtendedFeaturesRegister.addLang(
                                    "itemGroup", ExtendedFeaturesCore.id("creative_tab_2"),
                                    "Ext. Features: Universal Circuits"))
                            .icon(GTItems.CRYSTAL_ASSEMBLY_LuV::asStack)
                            .build()
            )
            .register();

    public static RegistryEntry<CreativeModeTab> MACHINES_TAB = ExtendedFeaturesRegister
            .defaultCreativeTab(ExtendedFeaturesCore.MOD_ID + "_machines",
                    builder -> builder
                            .displayItems(
                                    new GTCreativeModeTabs.RegistrateDisplayItemsGenerator(
                                            ExtendedFeaturesCore.MOD_ID + "_machines",
                                            ExtendedFeaturesRegister))
                            .title(ExtendedFeaturesRegister.addLang(
                                    "itemGroup", ExtendedFeaturesCore.id("creative_tab_3"),
                                    "Ext. Features: Machines")
                            )
                            .icon(GTResearchMachines.ADVANCED_DATA_ACCESS_HATCH::asStack)
                            .build()
            )
            .register();

}