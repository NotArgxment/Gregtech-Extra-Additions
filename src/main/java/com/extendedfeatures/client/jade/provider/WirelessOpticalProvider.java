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

public class WirelessOpticalProvider implements IBlockComponentProvider, IServerDataProvider<BlockAccessor> {

    private static final String tagTransmissor = "IsTransmitter";
    private static final String tagHatchCount = "DataHatchCount";
    private static final String tagReceptors = "Receptors";

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

        data.putBoolean(tagTransmissor, true);
        data.putInt(tagHatchCount, hatch.getLinkedDataHatchPositions().size());

        ListTag receptors = new ListTag();
        for (BlockPos pos : hatch.getLinkedReceptorPositions()) {
            receptors.add(new IntArrayTag(new int[] { pos.getX(), pos.getY(), pos.getZ() }));
        }
        data.put(tagReceptors, receptors);
    }

    // Client side: build the tooltip
    @Override
    public void appendTooltip(ITooltip tooltip, BlockAccessor blockAccessor, IPluginConfig config) {
        CompoundTag data = blockAccessor.getServerData();
        if (!data.getBoolean(tagTransmissor)) return;

        int dataHatchCount = data.getInt(tagHatchCount);
        tooltip.add(Component.translatable("extendedfeatures.jade.wireless_optical_hatch.linked_data_hatches",
                dataHatchCount).withStyle(ChatFormatting.GRAY));

        List<BlockPos> receptors = readReceptors(data.getList(tagReceptors, net.minecraft.nbt.Tag.TAG_INT_ARRAY));

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
        for (Tag tag : list) {
            int[] coords = ((IntArrayTag) tag).getAsIntArray();
            if (coords.length == 3) {
                result.add(new BlockPos(coords[0], coords[1], coords[2]));
            }
        }
        return result;
    }
}