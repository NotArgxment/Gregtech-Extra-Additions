package com.extendedfeatures.client;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

import java.util.List;
import java.util.function.BiConsumer;

import static com.gregtechceu.gtceu.client.util.TooltipHelper.RAINBOW_HSL_SLOW;

public class EFTooltipHelper {

    // Regular Tooltips
    public static final List<Component> RAMTooltip = List.of(
            Component.translatable("gtceu.machine.electric_blast_furnace.tooltip.0"),
            Component.translatable("gtceu.machine.electric_blast_furnace.tooltip.1"),
            Component.translatable("gtceu.machine.electric_blast_furnace.tooltip.2"),
            Component.translatable("extendedfeatures.separator_line_small")
    );

    public static final List<Component> LCMTooltip = List.of(
            Component.translatable("gtceu.machine.cracker.tooltip"),
            Component.translatable("extendedfeatures.separator_line_small"),
            Component.translatable("gtceu.machine.cracker.tooltip.1")
    );

    public static final List<Component> SVTooltip = List.of(
            Component.translatable("extendedfeatures.synthesis_vessel.tooltip.0"),
            Component.translatable("extendedfeatures.synthesis_vessel.tooltip.1"),
            Component.translatable("extendedfeatures.separator_line_small")
    );

    public static final List<Component> LPOTooltip = List.of(
            Component.translatable("gtceu.machine.pyrolyse_oven.tooltip"),
            Component.translatable("extendedfeatures.separator_line_small"),
            Component.translatable("gtceu.machine.pyrolyse_oven.tooltip.1")
    );

    public static final List<Component> EALTooltip = List.of(
            Component.translatable("extendedfeatures.expanded_assembly_line.tooltip.0"),
            Component.translatable("extendedfeatures.separator_line_small"),
            Component.translatable("extendedfeatures.expanded_assembly_line.tooltip.1"),
            Component.translatable("extendedfeatures.expanded_assembly_line.tooltip.2"),
            Component.translatable("extendedfeatures.expanded_assembly_line.tooltip.3")
    );

    public static final List<Component> RPPTooltip = List.of(
            Component.translatable("extendedfeatures.rock_processing_plant.tooltip.0")
    );

    public static final List<Component> IGTooltip = List.of(
            Component.translatable("extendedfeatures.greenhouse.tooltip.0"),
            Component.translatable("extendedfeatures.separator_line_small"),
            Component.translatable("extendedfeatures.industrial_greenhouse.tooltip.1")
    );

    public static final List<Component> TGCTooltip = List.of(
            Component.translatable("extendedfeatures.greenhouse.tooltip.0")
    );

    public static final List<Component> UDMTooltip = List.of(
            Component.translatable("extendedfeatures.disassembler.tooltip.0"),
            Component.translatable("extendedfeatures.separator_line_small"),
            Component.translatable("extendedfeatures.disassembler.tooltip.1"),
            Component.translatable("extendedfeatures.disassembler.tooltip.2"),
            Component.translatable("extendedfeatures.separator_line_small"),
            Component.translatable("extendedfeatures.disassembler.tooltip.3")
    );

    public static final List<Component> LGCTooltip = List.of(
            Component.translatable("extendedfeatures.large_air_collector.tooltip.0")
    );

    public static final List<Component> MDRTooltip = List.of(
            Component.translatable("extendedfeatures.matrix_data_relay.tooltip.1"),
            Component.translatable("extendedfeatures.matrix_data_relay.tooltip.2"),
            Component.translatable("extendedfeatures.matrix_data_relay.tooltip.3"),
            Component.translatable("extendedfeatures.separator_line_small"),
            Component.translatable("extendedfeatures.matrix_data_relay.tooltip.4"),
            Component.translatable("extendedfeatures.matrix_data_relay.tooltip.5"),
            Component.translatable("extendedfeatures.matrix_data_relay.tooltip.6"),
            Component.translatable("extendedfeatures.matrix_data_relay.tooltip.7"),
            Component.translatable("extendedfeatures.matrix_data_relay.tooltip.8"),
            Component.translatable("extendedfeatures.separator_line_small"),
            Component.translatable("extendedfeatures.matrix_data_relay.tooltip.9")
    );

    // Tooltip Builders
    public static final BiConsumer<ItemStack, List<Component>> RAMTooltipExtra =
            (stack, list) -> list.add(
                    Component.translatable("extendedfeatures.regular.tooltip.1")
                            .append(Component.translatable("extendedfeatures.styled.tooltip.1")
                                    .withStyle(RAINBOW_HSL_SLOW)));

    public static final BiConsumer<ItemStack, List<Component>> ParallelTooltip =
            (stack, list) -> list.add(
                    Component.translatable("extendedfeatures.regular.tooltip.1")
                            .append(Component.translatable("extendedfeatures.styled.tooltip.2")
                                    .withStyle(RAINBOW_HSL_SLOW)));

}