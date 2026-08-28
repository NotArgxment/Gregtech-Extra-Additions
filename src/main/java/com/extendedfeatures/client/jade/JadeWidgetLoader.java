package com.extendedfeatures.client.jade;

import com.extendedfeatures.client.jade.provider.MatrixDataRelayProvider;
import com.extendedfeatures.client.jade.provider.WirelessOpticalProvider;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;

import snownee.jade.api.*;

@WailaPlugin
public class JadeWidgetLoader implements IWailaPlugin {

    @Override
    public void register(IWailaCommonRegistration registration) {
        registration.registerBlockDataProvider(new WirelessOpticalProvider(), BlockEntity.class);
        registration.registerBlockDataProvider(new MatrixDataRelayProvider(), BlockEntity.class);
    }

    @Override
    public void registerClient(IWailaClientRegistration registration) {
        registration.registerBlockComponent(new WirelessOpticalProvider(), Block.class);
        registration.registerBlockComponent(new MatrixDataRelayProvider(), Block.class);
    }
}
