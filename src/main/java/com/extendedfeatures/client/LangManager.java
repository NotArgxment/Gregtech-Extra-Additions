package com.extendedfeatures.client;

import com.tterrag.registrate.providers.RegistrateLangProvider;

import static com.gregtechceu.gtceu.data.lang.LangHandler.replace;

public class LangManager {

    public static void init(RegistrateLangProvider provider) {
        Common(provider);
        Tooltips(provider);
    }

    private static void Common(RegistrateLangProvider provider) {

        // Multiblocks
        replace(provider, "block.extendedfeatures.robust_alloy_materializer", "Robust Alloy Materializer [RAM]");
        replace(provider, "block.extendedfeatures.large_cracking_machine", "Large Cracking Machine [LCM]");
        replace(provider, "block.extendedfeatures.synthesis_vessel", "Synthesis Vessel [SyVe]");
        replace(provider, "block.extendedfeatures.large_pyrolysis_oven", "Large Pyrolysis Oven [LPO]");
        replace(provider, "block.extendedfeatures.compact_assembly_line", "Compact Assembly Line [CAL]");
        replace(provider, "block.extendedfeatures.rock_processing_plant", "Rock Processing Plant [RPP]");
        replace(provider, "block.extendedfeatures.industrial_greenhouse", "Industrial Greenhouse [IGh]");
        replace(provider, "block.extendedfeatures.tree_growing_chamber", "Tree Growing Chamber [TGCh]");
        replace(provider, "block.extendedfeatures.disassembler", "Disassembler [DA]");
        replace(provider, "block.extendedfeatures.rock_processing_plant", "Rock Processing Plant [RPP]");
        replace(provider, "block.extendedfeatures.large_gas_collector", "Large Gas Collector [LGC]");
        replace(provider, "block.extendedfeatures.expanded_databank", "Expanded Databank [EDb]");
        replace(provider, "block.extendedfeatures.matrix_data_relay", "Matrix Data Relay [MDR]");

        // Expanded Data Hatches
        replace(provider, "block.extendedfeatures.zpm_data_access_hatch", "Elite Data Access Hatch");
        replace(provider, "block.extendedfeatures.uv_data_access_hatch", "Ultimate Data Access Hatch");
        replace(provider, "block.extendedfeatures.uhv_data_access_hatch", "Epic Data Access Hatch");

        // RecipeTypes lang keys
        replace(provider, "extendedfeatures.greenhouse_wood_recipes", "Tree Growing");
        replace(provider, "extendedfeatures.greenhouse_crop_recipes", "Crop Growing ");
        replace(provider, "extendedfeatures.disassembler_machine", "Disassembly");
        replace(provider, "extendedfeatures.rock_processing_plant", "Rock Processing");
        replace(provider, "extendedfeatures.chemical_skips", "Chemical Reduction");
        replace(provider, "extendedfeatures.gas_collection", "Gas Collector");

        // Configuration lang
        replace(provider, "config.screen.extendedfeatures", "§7Mod Configuration §c(Restart to Apply Changes)");

        replace(provider, "config.extendedfeatures.option.Multiblocks", "§7Multiblocks");
        replace(provider, "config.extendedfeatures.option.RegularMachines", "§7Machines");
        replace(provider, "config.extendedfeatures.option.UniversalCircuits", "§7Universal Circuits");

        replace(provider, "config.extendedfeatures.option.RobustAlloyMaterializer", "§7Robust Alloy Materializer");
        replace(provider, "config.extendedfeatures.option.LargeCrackingMachine", "§7Large Cracking Machine");
        replace(provider, "config.extendedfeatures.option.SynthesisVessel", "§7Synthesis Vessel");
        replace(provider, "config.extendedfeatures.option.LargePyrolysisOven", "§7Large Pyrolysis Oven");
        replace(provider, "config.extendedfeatures.option.CompactAssemblyLine", "§7Compact Assembly Line");
        replace(provider, "config.extendedfeatures.option.RockProcessingPlant", "§7Rock Processing Plant");
        replace(provider, "config.extendedfeatures.option.IndustrialGreenhouse", "§7Industrial Greenhouse");
        replace(provider, "config.extendedfeatures.option.TreeGrowingChamber", "§7Tree Growing Chamber");
        replace(provider, "config.extendedfeatures.option.Disassembler", "§7Disassembler");
        replace(provider, "config.extendedfeatures.option.LargeGasCollector", "§7Large Gas Collector");
        replace(provider, "config.extendedfeatures.option.MatrixDataRelay", "§7Matrix Data Relay");
        replace(provider, "config.extendedfeatures.option.ExpandedDatabank", "§7Expanded Data Bank");

        replace(provider, "config.extendedfeatures.option.ExpandedDataAccessHatches", "§7Expanded Data Access Hatches");
        replace(provider, "config.extendedfeatures.option.WirelessOpticalHatches", "§7Wireless Optical Tranmisssors/Receptors");
        replace(provider, "config.extendedfeatures.option.CCMHatch", "§7Configurable Cleaning Maintenance Hatch");

        // Optical
        replace(provider, "extendedfeatures.machine.wireless_optical_hatch.tooltip.range", "§fScan range:§f %s blocks §8(right-click with an empty hand)");
        replace(provider, "extendedfeatures.machine.wireless_optical_hatch.tooltip.connections", "§7Max links allowed:§f %s");
        replace(provider, "extendedfeatures.machine.wireless_optical_hatch.tooltip.scan", "§7Links to nearby Wireless Receptors and Data Access Hatches across its range");
        replace(provider, "extendedfeatures.machine.wireless_optical_hatch.tooltip.receptor", "§7Gets linked automatically when scanned by a Wireless Transmissor of the same tier");
        replace(provider, "extendedfeatures.machine.wireless_optical_hatch.linked_summary", "Linked %s new receptor(s) and %s new data hatch(es)");
        replace(provider, "extendedfeatures.machine.wireless_optical_hatch.range_shown", "Displaying current range of connections: %s blocks");
        replace(provider, "extendedfeatures.machine.wireless_optical_hatch.no_receptors_found", "§cNo new receptors or data hatches were found in range");
        replace(provider, "extendedfeatures.machine.wireless_optical_hatch.not_formed", "§cThe hatch is not placed on a valid strucure!");

        // Jade integration
        replace(provider, "config.jade.plugin_extendedfeatures.wireless_optical_hatch", "Wireless Optical Info");
        replace(provider, "extendedfeatures.jade.wireless_optical_hatch.linked_data_hatches", "§fLinked Data Access Hatches: §6%s");
        replace(provider, "extendedfeatures.jade.wireless_optical_hatch.linked_receptors_header", "§fLinked Wireless Optical Receptors:");
        replace(provider, "extendedfeatures.jade.wireless_optical_hatch.receptors_entry", "    - Receptor %s: %s");
        replace(provider, "extendedfeatures.jade.wireless_optical_hatch.no_receptors", "§c    - No receptors found");

    }

    private static void Tooltips(RegistrateLangProvider provider) {

        provider.add("extendedfeatures.compact_assembly_line.tooltip.0", "§fThis machine performs the work of the Assembly Line without §9Ordered Inputs");
        provider.add("extendedfeatures.compact_assembly_line.tooltip.1", "§fAllows §bone §fenergy hatch");
        provider.add("extendedfeatures.compact_assembly_line.tooltip.2", "§fRuns §b4 §frecipes in parallel");
        provider.add("extendedfeatures.compact_assembly_line.tooltip.3", "§fOnly works using");

        provider.add("extendedfeatures.synthesis_vessel.tooltip.0", "§fEven larger Black Box Reactor");
        provider.add("extendedfeatures.synthesis_vessel.tooltip.1", "§fThis machine is an improved version of the Large Chemical reactor capable of performing whole chemical lines in just 1 step");

        provider.add("extendedfeatures.rock_processing_plant.tooltip.0", "§fAn all in one processing line, turns the rocks you normally get from the rock breaker into their direct processed outputs");

        provider.add("extendedfeatures.greenhouse.tooltip.0", "§fDesigned to make wood generation easier");
        provider.add("extendedfeatures.greenhouse.tooltip.1", "§fRuns §b8 Recipes §fin parallel");

        provider.add("extendedfeatures.disassembler.tooltip.0", "§fAllows any type of §emachine/controller §fto be recycled, returning what was used for that recipe");
        provider.add("extendedfeatures.disassembler.tooltip.1", "§fEach tier of machine needs the same energy hatch of that tier in order to work");

        provider.add("extendedfeatures.expanded_databank_tootip.1", "§fAllows 16 independent Optical Transmission Hatches");

        provider.add("extendedfeatures.matrix_data_relay.tooltip.1", "§fYour personal Data Center");
        provider.add("extendedfeatures.matrix_data_relay.tooltip.2", "§fOnly allows"); // followed by "extendedfeatures.styled.tooltip.4"

        provider.add("extendedfeatures.large_air_collector.tooltip.0", "§fA Bigger Gas Collector");

        provider.add("extendedfeatures.configurable_cleaning_maintenance_hatch", "§fFor configurable multiblock maintenance with Cleaning!");

        provider.add("extendedfeatures.regular.tooltip.1", "§fAllows");
        provider.add("extendedfeatures.regular.tooltip.2", "§fand");
        provider.add("extendedfeatures.styled.tooltip.1", " Laser Hatches ");
        provider.add("extendedfeatures.styled.tooltip.2", " Parallel Hatches ");
        provider.add("extendedfeatures.styled.tooltip.3", " Wireless Optical Receptors ");
        provider.add("extendedfeatures.styled.tooltip.4", " Wireless Optical Transmissors ");

    }
}
