package com.extendedfeatures.init.contents.electric;

import com.extendedfeatures.CreativeTabs;
import com.extendedfeatures.client.internal.ConfigClass;
import com.extendedfeatures.client.internal.ExtendedAbilities;
import com.extendedfeatures.client.internal.logic.machine.ConfigurableCleanroomHatch;
import com.extendedfeatures.client.internal.logic.machine.ExpandedDataAccessHatch;
import com.extendedfeatures.client.internal.logic.machine.WirelessOpticalHatch;

import com.gregtechceu.gtceu.GTCEu;
import com.gregtechceu.gtceu.api.data.RotationState;
import com.gregtechceu.gtceu.api.machine.MachineDefinition;
import com.gregtechceu.gtceu.api.machine.multiblock.CleanroomType;
import com.gregtechceu.gtceu.api.machine.multiblock.PartAbility;
import com.gregtechceu.gtceu.api.registry.registrate.MachineBuilder;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;

import static com.extendedfeatures.ExtendedFeaturesCore.ExtendedFeaturesRegister;
import static com.gregtechceu.gtceu.api.GTValues.*;
import static com.gregtechceu.gtceu.api.machine.property.GTMachineModelProperties.IS_FORMED;

public class Machines {

    static {
        ExtendedFeaturesRegister.creativeModeTab(() -> CreativeTabs.MACHINES_TAB);
    }

    // Configurable Cleaning Maintenance Hatch
    public static MachineDefinition CONFIGURABLE_CLEANING_MAINTENANCE_HATCH = null;

    static {
        if (ConfigClass.INSTANCE.RegularMachines.CCMHatch || GTCEu.isDataGen()) {
            CONFIGURABLE_CLEANING_MAINTENANCE_HATCH = ExtendedFeaturesRegister
                    .machine("configurable_cleaning_maintenance_hatch", (holder) -> new ConfigurableCleanroomHatch(holder, CleanroomType.CLEANROOM))
                    .tooltips(
                            Component.translatable("gtceu.part_sharing.disabled"),
                            Component.translatable("extendedfeatures.configurable_cleaning_maintenance_hatch"),
                            Component.translatable("gtceu.machine.maintenance_hatch_tape_slot.tooltip"),
                            Component.translatable("gtceu.machine.maintenance_hatch_tool_slot.tooltip"),
                            Component.translatable("gtceu.machine.maintenance_hatch_cleanroom_auto.tooltip.1")
                    )
                    .tooltipBuilder((stack, tooltips) -> tooltips.add(
                            Component.literal("  ")
                                    .append(Component.translatable(CleanroomType.CLEANROOM.getTranslationKey())
                                            .withStyle(ChatFormatting.GREEN)))
                    )
                    .tier(HV)
                    .rotationState(RotationState.ALL)
                    .abilities(PartAbility.MAINTENANCE)
                    .modelProperty(IS_FORMED, false)
                    .overlayTieredHullModel("configurable_cleaning_maintenance_hatch")
                    .register();
        }
    }

    // Expanded Data Access Hatches
    public static MachineDefinition ZPM_DATA_ACCESS_HATCH = null;
    public static MachineDefinition UV_DATA_ACCESS_HATCH = null;
    public static MachineDefinition UHV_DATA_ACCESS_HATCH = null;

    static {
        if (ConfigClass.INSTANCE.RegularMachines.ExpandedDataAccessHatches || GTCEu.isDataGen()) {
            ZPM_DATA_ACCESS_HATCH = ExtendedFeaturesRegister
                    .machine("zpm_data_access_hatch", (holder) -> new ExpandedDataAccessHatch(holder, ZPM, false) {
                                @Override
                                protected int getInventorySize() {
                                    return 36;
                                }
                            })
                    .tier(ZPM)
                    .rotationState(RotationState.ALL)
                    .abilities(PartAbility.DATA_ACCESS)
                    .modelProperty(IS_FORMED, false)
                    .tooltips(
                            Component.translatable("gtceu.machine.data_access_hatch.tooltip.0"),
                            Component.translatable("gtceu.machine.data_access_hatch.tooltip.1", 36),
                            Component.translatable("gtceu.part_sharing.disabled"))
                    .overlayTieredHullModel("expanded_data_access_hatch")
                    .register();

            UV_DATA_ACCESS_HATCH = ExtendedFeaturesRegister
                    .machine("uv_data_access_hatch",
                            (holder) -> new ExpandedDataAccessHatch(holder, UV, false) {
                                @Override
                                protected int getInventorySize() {
                                    return 49;
                                }
                            })
                    .tier(UV)
                    .rotationState(RotationState.ALL)
                    .abilities(PartAbility.DATA_ACCESS)
                    .modelProperty(IS_FORMED, false)
                    .tooltips(
                            Component.translatable("gtceu.machine.data_access_hatch.tooltip.0"),
                            Component.translatable("gtceu.machine.data_access_hatch.tooltip.1", 49),
                            Component.translatable("gtceu.part_sharing.disabled"))
                    .overlayTieredHullModel("expanded_data_access_hatch")
                    .register();

            UHV_DATA_ACCESS_HATCH = ExtendedFeaturesRegister
                    .machine("uhv_data_access_hatch",
                            (holder) -> new ExpandedDataAccessHatch(holder, UHV, false) {
                                @Override
                                protected int getInventorySize() {
                                    return 64;
                                }
                            })
                    .tier(UHV)
                    .rotationState(RotationState.ALL)
                    .abilities(PartAbility.DATA_ACCESS)
                    .modelProperty(IS_FORMED, false)
                    .tooltips(
                            Component.translatable("gtceu.machine.data_access_hatch.tooltip.0"),
                            Component.translatable("gtceu.machine.data_access_hatch.tooltip.1", 64),
                            Component.translatable("gtceu.part_sharing.disabled"))
                    .overlayTieredHullModel("expanded_data_access_hatch")
                    .register();
        }
    }

    // Wireless Optical T/R Hatches
    public static MachineDefinition LUV_WIRELESS_TRANSMISSOR = null;
    public static MachineDefinition LUV_WIRELESS_RECEPTOR = null;

    public static MachineDefinition ZPM_WIRELESS_TRANSMISSOR = null;
    public static MachineDefinition ZPM_WIRELESS_RECEPTOR = null;

    public static MachineDefinition UV_WIRELESS_TRANSMISSOR = null;
    public static MachineDefinition UV_WIRELESS_RECEPTOR = null;

    static {
        if (ConfigClass.INSTANCE.RegularMachines.WirelessOpticalHatches || GTCEu.isDataGen()) {
            LUV_WIRELESS_TRANSMISSOR = WirelessHatchRegister
                    ("luv_wireless_data_transmissor", "LuV Wireless Optical Transmissor", LuV, true)
                    .tooltips(
                            Component.translatable("extendedfeatures.machine.wireless_optical_hatch.tooltip.range", 16),
                            Component.translatable("extendedfeatures.machine.wireless_optical_hatch.tooltip.connections", 4),
                            Component.translatable("extendedfeatures.machine.wireless_optical_hatch.tooltip.scan"),
                            Component.translatable("gtceu.part_sharing.disabled"))
                    .register();

            LUV_WIRELESS_RECEPTOR = WirelessHatchRegister
                    ("luv_wireless_data_receptor", "LuV Wireless Optical Receptor", LuV, false)
                    .tooltips(
                            Component.translatable("extendedfeatures.machine.wireless_optical_hatch.tooltip.receptor"),
                            Component.translatable("gtceu.part_sharing.disabled"))
                    .register();

            ZPM_WIRELESS_TRANSMISSOR = WirelessHatchRegister
                    ("zpm_wireless_data_transmissor", "ZPM Wireless Optical Transmissor", ZPM, true)
                    .tooltips(
                            Component.translatable("extendedfeatures.machine.wireless_optical_hatch.tooltip.range", 32),
                            Component.translatable("extendedfeatures.machine.wireless_optical_hatch.tooltip.connections", 8),
                            Component.translatable("extendedfeatures.machine.wireless_optical_hatch.tooltip.scan"),
                            Component.translatable("gtceu.part_sharing.disabled"))
                    .register();

            ZPM_WIRELESS_RECEPTOR = WirelessHatchRegister
                    ("zpm_wireless_data_receptor", "ZPM Wireless Optical Receptor", ZPM, false)
                    .tooltips(
                            Component.translatable("extendedfeatures.machine.wireless_optical_hatch.tooltip.receptor"),
                            Component.translatable("gtceu.part_sharing.disabled"))
                    .register();

            UV_WIRELESS_TRANSMISSOR = WirelessHatchRegister
                    ("uv_wireless_data_transmissor", "UV Wireless Optical Transmissor", UV, true)
                    .tooltips(
                            Component.translatable("extendedfeatures.machine.wireless_optical_hatch.tooltip.range", 64),
                            Component.translatable("extendedfeatures.machine.wireless_optical_hatch.tooltip.connections", 16),
                            Component.translatable("extendedfeatures.machine.wireless_optical_hatch.tooltip.scan"),
                            Component.translatable("gtceu.part_sharing.disabled"))
                    .register();

            UV_WIRELESS_RECEPTOR = WirelessHatchRegister
                    ("uv_wireless_data_receptor", "UV Wireless Optical Receptor", UV, false)
                    .tooltips(
                            Component.translatable("extendedfeatures.machine.wireless_optical_hatch.tooltip.receptor"),
                            Component.translatable("gtceu.part_sharing.disabled"))
                    .register();

        }
    }

    private static MachineBuilder<MachineDefinition, ?> WirelessHatchRegister(String name, String displayName, int tier, boolean isTransmissor) {
        return ExtendedFeaturesRegister
                .machine(name, (holder) -> new WirelessOpticalHatch(holder, isTransmissor, tier))
                .langValue(displayName)
                .tier(tier)
                .rotationState(RotationState.ALL)
                .overlayTieredHullModel(isTransmissor ? "transmissor" : "receptor")
                .abilities(isTransmissor ? ExtendedAbilities.WIRELESS_OPTICAL_TRANSMISSOR : ExtendedAbilities.WIRELESS_OPTICAL_RECEPTOR);
    }

    public static void init() {}

}