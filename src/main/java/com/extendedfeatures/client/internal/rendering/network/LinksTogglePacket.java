package com.extendedfeatures.client.internal.rendering.network;

import com.extendedfeatures.client.internal.logic.machine.WirelessOpticalHatch;

import com.gregtechceu.gtceu.api.machine.MetaMachine;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;

import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class LinksTogglePacket {

    private final BlockPos pos;

    public LinksTogglePacket(BlockPos pos) {
        this.pos = pos;
    }

    public static void encode(LinksTogglePacket msg, FriendlyByteBuf buf) {
        buf.writeBlockPos(msg.pos);
    }

    public static LinksTogglePacket decode(FriendlyByteBuf buf) {
        return new LinksTogglePacket(buf.readBlockPos());
    }

    public static void handle(LinksTogglePacket msg, Supplier<NetworkEvent.Context> ctxSupplier) {
        NetworkEvent.Context ctx = ctxSupplier.get();
        ctx.enqueueWork(() -> {
            if (ctx.getDirection().getReceptionSide() != LogicalSide.SERVER)
                return;

            ServerPlayer player = ctx.getSender();
            if (player == null)
                return;

            if (MetaMachine.getMachine(player.serverLevel(), msg.pos) instanceof WirelessOpticalHatch hatch
                    && hatch.isTransmitter()) {
                hatch.toggleShowLinks(player);
            }
        });
        ctx.setPacketHandled(true);
    }
}
