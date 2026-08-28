package com.extendedfeatures.client.internal.rendering.network;

import com.extendedfeatures.client.internal.logic.machine.WirelessOpticalHatch;

import com.gregtechceu.gtceu.api.machine.MetaMachine;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;

import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class RequestState {

    private final BlockPos pos;

    public RequestState(BlockPos pos) {
        this.pos = pos;
    }

    public static void encode(RequestState msg, FriendlyByteBuf buf) {
        buf.writeBlockPos(msg.pos);
    }

    public static RequestState decode(FriendlyByteBuf buf) {
        return new RequestState(buf.readBlockPos());
    }

    public static void handle(RequestState msg, Supplier<NetworkEvent.Context> ctxSupplier) {
        NetworkEvent.Context ctx = ctxSupplier.get();
        ctx.enqueueWork(() -> {
            if (ctx.getDirection().getReceptionSide() != LogicalSide.SERVER)
                return;

            ServerPlayer player = ctx.getSender();
            if (player == null)
                return;

            if (MetaMachine.getMachine(player.serverLevel(), msg.pos) instanceof WirelessOpticalHatch hatch
                    && hatch.isTransmitter()) {
                hatch.sendStateSyncTo(player);
            }
        });
        ctx.setPacketHandled(true);
    }
}
