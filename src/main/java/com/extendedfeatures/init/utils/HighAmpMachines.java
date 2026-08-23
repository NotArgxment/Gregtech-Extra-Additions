package com.extendedfeatures.init.utils;

import com.gregtechceu.gtceu.GTCEu;
import com.gregtechceu.gtceu.api.capability.compat.FeCompat;
import com.gregtechceu.gtceu.api.capability.recipe.IO;
import com.gregtechceu.gtceu.api.data.RotationState;
import com.gregtechceu.gtceu.api.machine.MachineDefinition;
import com.gregtechceu.gtceu.api.machine.multiblock.PartAbility;
import com.gregtechceu.gtceu.common.machine.electric.ConverterMachine;
import com.gregtechceu.gtceu.common.machine.multiblock.part.LaserHatchPartMachine;
import com.gregtechceu.gtceu.utils.FormattingUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import static com.extendedfeatures.CreativeTabs.HIGH_AMP_MACHINES;
import static com.extendedfeatures.ExtendedFeaturesCore.ExtendedFeaturesRegister;
import static com.extendedfeatures.init.utils.internal.StaticConverterOverlay.converterOverlay;
import static com.gregtechceu.gtceu.api.GTValues.*;
import static com.gregtechceu.gtceu.api.capability.recipe.IO.*;
import static com.gregtechceu.gtceu.api.machine.property.GTMachineModelProperties.*;
import static com.gregtechceu.gtceu.common.data.machines.GTMachineUtils.*;
import static com.gregtechceu.gtceu.common.machine.multiblock.part.EnergyHatchPartMachine.*;

public class HighAmpMachines {

    static {
        ExtendedFeaturesRegister.creativeModeTab(() -> HIGH_AMP_MACHINES);
    }

    // Laser Hatches
    public static final MachineDefinition[] LASER_TARGET_HATCH_16384 = new MachineDefinition[MAX + 1];
    public static final MachineDefinition[] LASER_SOURCE_HATCH_16384 = new MachineDefinition[MAX + 1];
    public static final MachineDefinition[] LASER_TARGET_HATCH_65536 = new MachineDefinition[MAX + 1];
    public static final MachineDefinition[] LASER_SOURCE_HATCH_65536 = new MachineDefinition[MAX + 1];

    static {
        for (int tier = UV; tier <= MAX; tier++) {
            LASER_TARGET_HATCH_16384[tier] = ExtendedFeaturesLaserHatch(tier, IN, 16384, PartAbility.INPUT_LASER);
            LASER_SOURCE_HATCH_16384[tier] = ExtendedFeaturesLaserHatch(tier, OUT, 16384, PartAbility.OUTPUT_LASER);
            LASER_TARGET_HATCH_65536[tier] = ExtendedFeaturesLaserHatch(tier, IN, 65536, PartAbility.INPUT_LASER);
            LASER_SOURCE_HATCH_65536[tier] = ExtendedFeaturesLaserHatch(tier, OUT, 65536, PartAbility.OUTPUT_LASER);

        }
    }

    private static MachineDefinition ExtendedFeaturesLaserHatch(int tier, IO io, int amperage, PartAbility ability) {

        String name = io == IN ? "target" : "source";

        return ExtendedFeaturesRegister
                .machine(VN[tier].toLowerCase() + "_" + amperage + "a_laser_" + name + "_hatch",
                        (holder) -> new LaserHatchPartMachine(holder, io, tier, amperage))
                .tier(tier)
                .rotationState(RotationState.ALL)
                .langValue(VNF[tier] + "§r " + FormattingUtil.formatNumbers(amperage) + "§eA§r Laser " + FormattingUtil.toEnglishName(name) + " Hatch")
                .tooltips(
                        Component.translatable("gtceu.machine.laser_hatch." + name + ".tooltip"),
                        Component.translatable("gtceu.machine.laser_hatch.both.tooltip"),
                        Component.translatable("gtceu.universal.tooltip.voltage_" + (io == IN ? "in" : "out"),
                                FormattingUtil.formatNumbers(V[tier]), VNF[tier]),
                        Component.translatable("gtceu.universal.tooltip.amperage_in", amperage),
                        Component.translatable("gtceu.universal.tooltip.energy_storage_capacity",
                                FormattingUtil.formatNumbers(getHatchEnergyCapacity(tier, amperage))),
                        Component.translatable("gtceu.part_sharing.disabled")
                )
                .abilities(ability)
                .modelProperty(IS_FORMED, false)
                .overlayTieredHullModel(
                        new ResourceLocation(
                                GTCEu.MOD_ID, "block/machine/part/laser_" + name + "_hatch")
                )
                .register();
    }

    // High Amp Converters
    public static final MachineDefinition[] ENERGY_CONVERTER_64A = registerHighAmpConverter(64);
    public static final MachineDefinition[] ENERGY_CONVERTER_256A = registerHighAmpConverter(256);
    public static final MachineDefinition[] ENERGY_CONVERTER_1024A = registerHighAmpConverter(1024);
    public static final MachineDefinition[] ENERGY_CONVERTER_4096A = registerHighAmpConverter(4096);

    private static MachineDefinition[] registerHighAmpConverter(int amperage) {
        return registerTieredMachines(ExtendedFeaturesRegister, amperage + "a_energy_converter",
                (holder, tier) -> new ConverterMachine(holder, tier, amperage),
                (tier, builder) -> builder
                        .rotationState(RotationState.ALL)
                        .langValue("%s %s§eA§r Energy Converter"
                                .formatted(VCF[tier] + VN[tier] + ChatFormatting.RESET, amperage))
                        .modelProperty(IS_FE_TO_EU, true)
                        .model(converterOverlay()) // set to 64A substation hatches overlay
                        .tooltips(
                                Component.translatable("gtceu.machine.energy_converter.description"),
                                Component.translatable("gtceu.machine.energy_converter.tooltip_tool_usage"),
                                Component.translatable("gtceu.machine.energy_converter.tooltip_conversion_native",
                                        FeCompat.toFeLong(V[tier] * amperage, FeCompat.ratio(true)),
                                        amperage, V[tier], VNF[tier]),
                                Component.translatable("gtceu.machine.energy_converter.tooltip_conversion_eu",
                                        amperage, V[tier], VNF[tier],
                                        FeCompat.toFeLong(V[tier] * amperage, FeCompat.ratio(false)))
                        )
                        .register(),
                tiersBetween(UV, MAX));
    }

    public static void init() {}

}
