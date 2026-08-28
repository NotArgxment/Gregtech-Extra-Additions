package com.extendedfeatures.client.internal.rendering.network;

import com.extendedfeatures.client.gui.wireless_hatch.ScreenHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class StateSync {

    private final BlockPos pos;
    private final boolean showRange;
    private final boolean showLinks;
    private final long cooldownEndGameTime;
    private final boolean hasScannedOnce;

    public StateSync(BlockPos pos, boolean showRange, boolean showLinks, long cooldownEndGameTime, boolean hasScannedOnce) {
        this.pos = pos;
        this.showRange = showRange;
        this.showLinks = showLinks;
        this.cooldownEndGameTime = cooldownEndGameTime;
        this.hasScannedOnce = hasScannedOnce;
    }

    public static void encode(StateSync msg, FriendlyByteBuf buf) {
        buf.writeBlockPos(msg.pos);
        buf.writeBoolean(msg.showRange);
        buf.writeBoolean(msg.showLinks);
        buf.writeVarLong(msg.cooldownEndGameTime);
        buf.writeBoolean(msg.hasScannedOnce);
    }

    public static StateSync decode(FriendlyByteBuf buf) {
        return new StateSync(
                buf.readBlockPos(), buf.readBoolean(), buf.readBoolean(), buf.readVarLong(), buf.readBoolean());
    }

    public static void handle(StateSync msg, Supplier<NetworkEvent.Context> ctxSupplier) {
        NetworkEvent.Context ctx = ctxSupplier.get();
        ctx.enqueueWork(() -> {

            if (ctx.getDirection().getReceptionSide() != LogicalSide.CLIENT)
                return;

            DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () ->
                    ScreenHandler.handleStateSync(msg.pos, msg.showRange, msg.showLinks, msg.cooldownEndGameTime, msg.hasScannedOnce));
        });
        ctx.setPacketHandled(true);
    }
}