package com.extendedfeatures.client.internal.rendering;

import com.extendedfeatures.ExtendedFeaturesCore;

import com.extendedfeatures.client.internal.rendering.network.*;
import com.extendedfeatures.client.internal.rendering.range.RangePackets;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class PacketManager {

    private static final String PROTOCOL_VERSION = "1";

    public static final SimpleChannel CHANNEL = NetworkRegistry
            .newSimpleChannel(new ResourceLocation(ExtendedFeaturesCore.MOD_ID, "main"),
                    () -> PROTOCOL_VERSION, // Network
                    PROTOCOL_VERSION::equals, // Client
                    PROTOCOL_VERSION::equals); // Server

    private static int nextId = 0;

    public static void register() {

        CHANNEL.registerMessage(nextId++,
                RangePackets.class, RangePackets::encode, RangePackets::decode, RangePackets::handle);

        CHANNEL.registerMessage(nextId++,
                RequestState.class, RequestState::encode,
                RequestState::decode, RequestState::handle);

        CHANNEL.registerMessage(nextId++,
                RangeTogglePacket.class, RangeTogglePacket::encode,
                RangeTogglePacket::decode, RangeTogglePacket::handle);

        CHANNEL.registerMessage(nextId++,
                LinksTogglePacket.class, LinksTogglePacket::encode,
                LinksTogglePacket::decode, LinksTogglePacket::handle);

        CHANNEL.registerMessage(nextId++,
                ScanPacket.class, ScanPacket::encode,
                ScanPacket::decode, ScanPacket::handle);

        CHANNEL.registerMessage(nextId++,
                StateSync.class, StateSync::encode,
                StateSync::decode, StateSync::handle);
    }
}
