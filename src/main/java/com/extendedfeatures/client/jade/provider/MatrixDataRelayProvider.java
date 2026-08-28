package com.extendedfeatures.client.jade.provider;

import com.extendedfeatures.ExtendedFeaturesCore;
import com.extendedfeatures.client.internal.logic.multiblock.MatrixDataRelayMachine;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.MetaMachine;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.IServerDataProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;

public class MatrixDataRelayProvider implements IBlockComponentProvider, IServerDataProvider<BlockAccessor> {

    private static final String tagRelay = "IsMatrixDataRelay";
    private static final String tagCoolantUsed = "CoolantPerSecond";
    private static final String tagSupplyCoolant = "CoolantStarved";
    private static final String tagFormed = "Formed";

    @Override
    public ResourceLocation getUid() {
        return ExtendedFeaturesCore.id("matrix_data_relay");
    }

    // Server side: gather data
    @Override
    public void appendServerData(CompoundTag data, BlockAccessor blockAccessor) {
        if (!(blockAccessor.getBlockEntity() instanceof IMachineBlockEntity be)) return;

        MetaMachine machine = be.getMetaMachine();
        if (!(machine instanceof MatrixDataRelayMachine relay)) return;

        data.putBoolean(tagRelay, true);
        data.putBoolean(tagFormed, relay.isFormed());
        data.putInt(tagCoolantUsed, MatrixDataRelayMachine.coolantAmount);
        data.putBoolean(tagSupplyCoolant, relay.isCoolantStarved());
    }

    // Client side: build the tooltip
    @Override
    public void appendTooltip(ITooltip tooltip, BlockAccessor blockAccessor, IPluginConfig config) {
        CompoundTag data = blockAccessor.getServerData();
        if (!data.getBoolean(tagRelay)) return;
        if (!data.getBoolean(tagFormed)) return;

        boolean starved = data.getBoolean(tagSupplyCoolant);
        tooltip.add(Component.translatable("extendedfeatures.jade.matrix_data_relay.coolant_usage",
                data.getInt(tagCoolantUsed)).withStyle(ChatFormatting.GRAY));
        tooltip.add(Component.translatable(starved
                        ? "extendedfeatures.jade.matrix_data_relay.coolant_starved"
                        : "extendedfeatures.jade.matrix_data_relay.coolant_supplied")
                .withStyle(starved ? ChatFormatting.RED : ChatFormatting.GREEN));
    }
}