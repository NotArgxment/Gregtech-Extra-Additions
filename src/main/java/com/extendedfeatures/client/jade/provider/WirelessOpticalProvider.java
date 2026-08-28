package com.extendedfeatures.client.jade.provider;

import com.extendedfeatures.ExtendedFeaturesCore;
import com.extendedfeatures.client.internal.logic.machine.WirelessOpticalHatch;

import com.gregtechceu.gtceu.api.machine.*;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.*;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import snownee.jade.api.*;
import snownee.jade.api.config.IPluginConfig;

import java.util.*;

/**
 * Shows, for {@link WirelessOpticalHatch} transmitters, the linked physical
 * Data Access Hatches (count) and linked Wireless Optical Receptors (positions) in the Jade tooltip.
 */
public class WirelessOpticalProvider implements IBlockComponentProvider, IServerDataProvider<BlockAccessor> {

    private static final String TAG_IS_TRANSMISSOR = "IsTransmitter";
    private static final String TAG_DATA_HATCH_COUNT = "DataHatchCount";
    private static final String TAG_RECEPTORS = "Receptors";

    @Override
    public ResourceLocation getUid() {
        return ExtendedFeaturesCore.id("wireless_optical_hatch");
    }

    // Server side: gather data
    @Override
    public void appendServerData(CompoundTag data, BlockAccessor blockAccessor) {
        if (!(blockAccessor.getBlockEntity() instanceof IMachineBlockEntity be)) return;

        MetaMachine machine = be.getMetaMachine();
        if (!(machine instanceof WirelessOpticalHatch hatch)) return;
        if (!hatch.isTransmitter()) return; // only transmitters expose this info

        data.putBoolean(TAG_IS_TRANSMISSOR, true);
        data.putInt(TAG_DATA_HATCH_COUNT, hatch.getLinkedDataHatchPositions().size());

        ListTag receptors = new ListTag();
        for (BlockPos pos : hatch.getLinkedReceptorPositions()) {
            receptors.add(new IntArrayTag(new int[] { pos.getX(), pos.getY(), pos.getZ() }));
        }
        data.put(TAG_RECEPTORS, receptors);
    }

    // Client side: build the tooltip
    @Override
    public void appendTooltip(ITooltip tooltip, BlockAccessor blockAccessor, IPluginConfig config) {
        CompoundTag data = blockAccessor.getServerData();
        if (!data.getBoolean(TAG_IS_TRANSMISSOR)) return;

        int dataHatchCount = data.getInt(TAG_DATA_HATCH_COUNT);
        tooltip.add(Component.translatable("extendedfeatures.jade.wireless_optical_hatch.linked_data_hatches",
                dataHatchCount).withStyle(ChatFormatting.GRAY));

        List<BlockPos> receptors = readReceptors(data.getList(TAG_RECEPTORS, net.minecraft.nbt.Tag.TAG_INT_ARRAY));

        if (receptors.isEmpty()) {
            tooltip.add(Component
                    .translatable("extendedfeatures.jade.wireless_optical_hatch.linked_receptors_header")
                    .withStyle(ChatFormatting.GOLD));
            tooltip.add(Component.translatable("extendedfeatures.jade.wireless_optical_hatch.no_receptors")
                    .withStyle(ChatFormatting.RED));
            return;
        }

        tooltip.add(Component.translatable("extendedfeatures.jade.wireless_optical_hatch.linked_receptors_header")
                .withStyle(ChatFormatting.GOLD));

        int index = 1;
        for (BlockPos pos : receptors) {
            Component coords = Component.literal(pos.getX() + " " + pos.getY() + " " + pos.getZ())
                    .withStyle(ChatFormatting.GOLD);
            tooltip.add(Component.translatable("extendedfeatures.jade.wireless_optical_hatch.receptors_entry",
                    index, coords).withStyle(ChatFormatting.AQUA));
            index++;
        }
    }

    private List<BlockPos> readReceptors(ListTag list) {
        List<BlockPos> result = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            int[] coords = ((IntArrayTag) list.get(i)).getAsIntArray();
            if (coords.length == 3) {
                result.add(new BlockPos(coords[0], coords[1], coords[2]));
            }
        }
        return result;
    }
}