package com.extendedfeatures.client.internal.rendering.network;

import com.extendedfeatures.client.internal.logic.machine.WirelessOpticalHatch;

import com.gregtechceu.gtceu.api.machine.MetaMachine;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;

import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ScanPacket {

    private final BlockPos pos;

    public ScanPacket(BlockPos pos) {
        this.pos = pos;
    }

    public static void encode(ScanPacket msg, FriendlyByteBuf buf) {
        buf.writeBlockPos(msg.pos);
    }

    public static ScanPacket decode(FriendlyByteBuf buf) {
        return new ScanPacket(buf.readBlockPos());
    }

    public static void handle(ScanPacket msg, Supplier<NetworkEvent.Context> ctxSupplier) {
        NetworkEvent.Context ctx = ctxSupplier.get();
        ctx.enqueueWork(() -> {
            if (ctx.getDirection().getReceptionSide() != LogicalSide.SERVER)
                return;

            ServerPlayer player = ctx.getSender();
            if (player == null)
                return;

            if (MetaMachine.getMachine(player.serverLevel(), msg.pos) instanceof WirelessOpticalHatch hatch
                    && hatch.isTransmitter()) {
                hatch.handleScanRequest(player);
            }
        });
        ctx.setPacketHandled(true);
    }
}
