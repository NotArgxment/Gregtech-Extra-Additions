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
        replace(provider, "block.extendedfeatures.expanded_assembly_line", "Expanded Assembly Line [EAL]");
        replace(provider, "block.extendedfeatures.rock_processing_plant", "Rock Processing Plant [RPP]");
        replace(provider, "block.extendedfeatures.industrial_greenhouse", "Industrial Greenhouse [IGh]");
        replace(provider, "block.extendedfeatures.tree_growing_chamber", "Tree Growing Chamber [TGCh]");
        replace(provider, "block.extendedfeatures.disassembler", "Disassembler [DA]");
        replace(provider, "block.extendedfeatures.rock_processing_plant", "Rock Processing Plant [RPP]");
        replace(provider, "block.extendedfeatures.large_gas_collector", "Large Gas Collector [LGC]");
        replace(provider, "block.extendedfeatures.matrix_data_relay", "Matrix Data Relay [MDR]");

        // Expanded Data Hatches
        replace(provider, "block.extendedfeatures.zpm_data_access_hatch", "Elite Data Access Hatch");
        replace(provider, "block.extendedfeatures.uv_data_access_hatch", "Ultimate Data Access Hatch");
        replace(provider, "block.extendedfeatures.uhv_data_access_hatch", "Epic Data Access Hatch");

        // RecipeTypes lang keys
        replace(provider, "extendedfeatures.greenhouse_wood_recipes", "Greenhouse: Trees");
        replace(provider, "extendedfeatures.greenhouse_crop_recipes", "Greenhouse: Crops");
        replace(provider, "extendedfeatures.component_disassembly", "Component Disassembly");
        replace(provider, "extendedfeatures.machine_disassembly", "Machine Disassembly");
        replace(provider, "extendedfeatures.rock_processing_plant", "Rock Processing");
        replace(provider, "extendedfeatures.chemical_skips", "Chemical Reduction");
        replace(provider, "extendedfeatures.gas_collection", "Gas Collection");

        // Configuration lang
        replace(provider, "config.screen.extendedfeatures", "§7Mod Configuration §c(Restart to Apply Changes)");

        replace(provider, "config.extendedfeatures.option.Multiblocks", "§7Multiblocks");
        replace(provider, "config.extendedfeatures.option.RegularMachines", "§7Machines");
        replace(provider, "config.extendedfeatures.option.UniversalCircuits", "§7Universal Circuits");

        replace(provider, "config.extendedfeatures.option.RobustAlloyMaterializer", "§7Robust Alloy Materializer");
        replace(provider, "config.extendedfeatures.option.LargeCrackingMachine", "§7Large Cracking Machine");
        replace(provider, "config.extendedfeatures.option.SynthesisVessel", "§7Synthesis Vessel");
        replace(provider, "config.extendedfeatures.option.LargePyrolysisOven", "§7Large Pyrolysis Oven");
        replace(provider, "config.extendedfeatures.option.ExpandedAssemblyLine", "§7Expanded Assembly Line");
        replace(provider, "config.extendedfeatures.option.RockProcessingPlant", "§7Rock Processing Plant");
        replace(provider, "config.extendedfeatures.option.IndustrialGreenhouse", "§7Industrial Greenhouse");
        replace(provider, "config.extendedfeatures.option.TreeGrowingChamber", "§7Tree Growing Chamber");
        replace(provider, "config.extendedfeatures.option.Disassembler", "§7Disassembler");
        replace(provider, "config.extendedfeatures.option.LargeGasCollector", "§7Large Gas Collector");
        replace(provider, "config.extendedfeatures.option.MatrixDataRelay", "§7Matrix Data Relay");
        replace(provider, "config.extendedfeatures.option.DataHatchLinkingBehavior", "§7Restrict Data Hatch Linking Behavior");

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
        replace(provider, "extendedfeatures.machine.wireless_optical_hatch.scan_cooldown", "§cLink scan is on cooldown, try again shortly");
        replace(provider, "extendedfeatures.machine.wireless_optical_hatch.scan_required", "§cRun a scan first before showing current links");

        // Wireless Hatch GUI
        replace(provider, "gui.extendedfeatures.wireless_hatch.title", "Wireless Optical Transmissor");
        replace(provider, "gui.extendedfeatures.wireless_hatch.show_range", "Show Max Distance:");
        replace(provider, "gui.extendedfeatures.wireless_hatch.show_links", "Show current links:");
        replace(provider, "gui.extendedfeatures.wireless_hatch.scan_link", "Link nearby Receptors & Data Hatches");
        replace(provider, "gui.extendedfeatures.wireless_hatch.state_on", "§aON");
        replace(provider, "gui.extendedfeatures.wireless_hatch.state_off", "§cOFF");
        replace(provider, "gui.extendedfeatures.wireless_hatch.cooldown", "§fAvailable in %ss");
        replace(provider, "gui.extendedfeatures.wireless_hatch.show_links_locked", "§7Run a scan first");

        // ========================
        //     Jade integration
        // ========================

        // Hatches
        replace(provider, "config.jade.plugin_extendedfeatures.wireless_optical_hatch", "Wireless Optical Info");
        replace(provider, "extendedfeatures.jade.wireless_optical_hatch.linked_data_hatches", "§fLinked Data Access Hatches: §6%s");
        replace(provider, "extendedfeatures.jade.wireless_optical_hatch.linked_receptors_header", "§fLinked Wireless Optical Receptors:");
        replace(provider, "extendedfeatures.jade.wireless_optical_hatch.receptors_entry", "    - Receptor %s: %s");
        replace(provider, "extendedfeatures.jade.wireless_optical_hatch.no_receptors", "§c    - No receptors found");

        // MDR
        replace(provider, "config.jade.plugin_extendedfeatures.matrix_data_relay", "Matrix Data Relay Info");
        replace(provider, "extendedfeatures.jade.matrix_data_relay.coolant_usage", "§fPCB Coolant Upkeep: §b%s mB/s");
        replace(provider, "extendedfeatures.jade.matrix_data_relay.coolant_supplied", "§a    - Currently supplying PCB Coolant");
        replace(provider, "extendedfeatures.jade.matrix_data_relay.coolant_starved", "§c    - Insufficient PCB Coolant");
        replace(provider, "extendedfeatures.jade.matrix_data_relay.wireless_hatch_tier", "§fWireless Hatch Tier: §b%s");

    }

    private static void Tooltips(RegistrateLangProvider provider) {

        provider.add("extendedfeatures.separator_line_small", "§8--------------------------------------");
        provider.add("extendedfeatures.separator_line_large", "§8---------------------------------------------------------");
        provider.add("extendedfeatures.empty_space", " ");
        provider.add("extendedfeatures.modifier.perfect_oc", "§fHas §6Perfect Overclock");

        provider.add("extendedfeatures.expanded_assembly_line.tooltip.0", "§fAn Assembly Line that takes advantage of §9AE2 Stocking Hatches");
        provider.add("extendedfeatures.expanded_assembly_line.tooltip.1", "§fAllows only §bone §fenergy hatch");
        provider.add("extendedfeatures.expanded_assembly_line.tooltip.2", "§fRuns §c16 §frecipes in parallel");
        provider.add("extendedfeatures.expanded_assembly_line.tooltip.3", "§fOnly works using Wireless Optical Receptors");

        provider.add("extendedfeatures.synthesis_vessel.tooltip.0", "§7A §3Chemical Plant §7variant based on the Large Chemical Reactor");
        provider.add("extendedfeatures.synthesis_vessel.tooltip.1", "§7Performs entire chemical processing lines in 1 cycle");

        provider.add("extendedfeatures.rock_processing_plant.tooltip.0", "§7All in One Processing Machine!");
        provider.add("extendedfeatures.rock_processing_plant.tooltip.1", "§7Turns the rocks you normally get from the rock breaker into their direct processed outputs");

        provider.add("extendedfeatures.greenhouse.tooltip.0", "§fAllows an easier way to obtain natural resources");
        provider.add("extendedfeatures.greenhouse_modes", "§fAvailable Recipes: Tree Growing, Crops Planting");

        provider.add("extendedfeatures.disassembler.tooltip.0", "§7Available Machine Modes: §fComponent Disassembly, Machine Disassembly");
        provider.add("extendedfeatures.disassembler.tooltip.1", "§7This machine can revert a §aMachine §7or §cComponent §7creation process in exchange of the components that were used to make it");
        provider.add("extendedfeatures.disassembler.tooltip.2", "§7If set to §fMachine Disassembly§7, every recipe requires the respective §benergy hatch §7of that tier");
        provider.add("extendedfeatures.disassembler.tooltip.3", "§7Allows §bone §7energy hatch");

        provider.add("extendedfeatures.matrix_data_relay.tooltip.1", "§7Your personal §fWireless Data Bank");
        provider.add("extendedfeatures.matrix_data_relay.tooltip.2", "§7This structure allows a maximum of 6 Data Hatches, both Normal and §6Expanded Data Hatches");
        provider.add("extendedfeatures.matrix_data_relay.tooltip.3", "§7Each tier of Wireless Transmissors has an energy usage of the next tier:");
        provider.add("extendedfeatures.matrix_data_relay.tooltip.4", "   §dLuV §fWireless Transmissor: §c131.072 §fEU/t");
        provider.add("extendedfeatures.matrix_data_relay.tooltip.5", "   §cZPM §fWireless Transmissor: §3524.288 §fEU/t");
        provider.add("extendedfeatures.matrix_data_relay.tooltip.6", "   §3UV §fWireless Transmissor: §42.097.152 §fEU/t");
        provider.add("extendedfeatures.matrix_data_relay.tooltip.7", "   §7Uses §f1920 EU/t §7per Data Hatch");
        provider.add("extendedfeatures.matrix_data_relay.tooltip.8", "   §7Uses §98192 EU/t §7per §6Expanded Data Access Hatch");
        provider.add("extendedfeatures.matrix_data_relay.tooltip.9", "§7While working, a constant supply of §fPCB Coolant (144 mb/t) §7is required");

        provider.add("extendedfeatures.large_air_collector.tooltip.0", "§fA Bigger Gas Collector");

        provider.add("extendedfeatures.configurable_cleaning_maintenance_hatch", "§fFor configurable multiblock maintenance with Cleaning!");

        provider.add("extendedfeatures.regular.tooltip.1", "§fAllows");
        provider.add("extendedfeatures.regular.tooltip.2", "§fand");
        provider.add("extendedfeatures.styled.tooltip.1", " Laser Hatches ");
        provider.add("extendedfeatures.styled.tooltip.2", " Parallel Hatches ");

    }
}
