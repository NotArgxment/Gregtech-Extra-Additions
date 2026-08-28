package com.extendedfeatures.client.gui.wireless_hatch;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;

public final class ScreenHandler {

    private ScreenHandler() {}

    public static void handleStateSync(BlockPos pos,
                                       boolean showRange,
                                       boolean showLinks,
                                       long cooldownEndGameTime,
                                       boolean hasScannedOnce) {
        if (Minecraft.getInstance().screen instanceof WirelessHatchScreen screen
                && screen.getHatchPos().equals(pos)) {
            screen.applyStateSync(showRange, showLinks, cooldownEndGameTime, hasScannedOnce);
        }
    }
}