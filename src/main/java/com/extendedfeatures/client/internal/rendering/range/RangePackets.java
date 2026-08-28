package com.extendedfeatures.client.internal.rendering.range;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class RangePackets {

    private final BlockPos position;
    private final int range;
    private final boolean show;

    public RangePackets(BlockPos position, int range, boolean show) {
        this.position = position;
        this.range = range;
        this.show = show;
    }

    public static void encode(RangePackets msg, FriendlyByteBuf buf) {
        buf.writeBlockPos(msg.position);
        buf.writeVarInt(msg.range);
        buf.writeBoolean(msg.show);
    }

    public static RangePackets decode(FriendlyByteBuf buf) {
        return new RangePackets(buf.readBlockPos(), buf.readVarInt(), buf.readBoolean());
    }

    public static void handle(RangePackets msg, Supplier<NetworkEvent.Context> ctxSupplier) {
        NetworkEvent.Context ctx = ctxSupplier.get();
        ctx.enqueueWork(() -> {
            if (ctx.getDirection().getReceptionSide() != LogicalSide.CLIENT)
                return;
            if (msg.show) {
                RangeRenderer.showBoxAtPosition(msg.position, msg.range);
            } else {
                RangeRenderer.hideBoxAtPosition(msg.position);
            }
        });
        ctx.setPacketHandled(true);
    }
}
