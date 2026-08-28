package com.extendedfeatures.init.contents.misc;

import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.utils.GradientUtil;

import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;

import java.util.function.UnaryOperator;

public class CustomTooltipStyles {

    private static final int WHITE = 0xFFFFFFFF;

    // Tier main colors
    private static final int LV = 0xFFAAAAAA; // Gray
    private static final int MV = 0xFF55FFFF; // Aqua
    private static final int HV = 0xFFFFAA00; // Gold
    private static final int EV = 0xFFAA00AA; // Dark Purple
    private static final int IV = 0xFF5555FF; // Blue
    private static final int LuV = 0xFFFF55FF; // Light Purple
    private static final int ZPM = 0xFFFF5555; // Red
    private static final int UV = 0xFF00AAAA; // Dark Aqua
    private static final int UHV = 0xFFAA0000; // Dark Red
    private static final int UEV = 0xFF55FF55; // Dark Green
    private static final int UIV = 0xFF00AA00; // Green
    private static final int UXV = 0xFFFFFF55; // Yellow
    // private static final int OpV = IV; // OpV uses the same color as IV, but with Bold code
    // private static final int MAX = ZPM; // MAx uses the same color as ZPM, but with Bold Code

    // Speed modifiers
    private static final double SPEED_SLOW = 0.1;
    private static final double SPEED_MEDIUM = 0.2;
    private static final double SPEED_FAST = 0.3;

    // Tries to replicate GTCEu Tooltip Helper
    public static TextColor movingGradient(double speed, int colorA, int colorB) {
        float t = (float) (Math.sin(GTValues.CLIENT_TIME * speed) * 0.5 + 0.5);
        int blended = GradientUtil.blend(colorA, colorB, t);
        return TextColor.fromRgb(blended & 0xFFFFFF);
    }

    /**
     * Side note:
     * Some of this gradients may already exist in GTCEu, look at TooltipHelper.class for reference.
     * UV is "BLINKING_CYAN", ZPM is "BLINKING_RED", HV is "BLINKING_ORANGE", LV is "BLINKING_GRAY",
     */

    public static final UnaryOperator<Style> LV_GRADIENT = style -> style
            .withColor(movingGradient(SPEED_SLOW, LV, WHITE));

    public static final UnaryOperator<Style> MV_GRADIENT = style -> style
            .withColor(movingGradient(SPEED_SLOW, MV, WHITE));

    public static final UnaryOperator<Style> HV_GRADIENT = style -> style
            .withColor(movingGradient(SPEED_SLOW, HV, WHITE));

    public static final UnaryOperator<Style> EV_GRADIENT = style -> style
            .withColor(movingGradient(SPEED_SLOW, EV, WHITE));

    public static final UnaryOperator<Style> IV_GRADIENT = style -> style
            .withColor(movingGradient(SPEED_SLOW, IV, WHITE));

    public static final UnaryOperator<Style> LuV_GRADIENT = style -> style
            .withColor(movingGradient(SPEED_SLOW, LuV, WHITE));

    public static final UnaryOperator<Style> ZPM_GRADIENT = style -> style
            .withColor(movingGradient(SPEED_SLOW, ZPM, WHITE));

    public static final UnaryOperator<Style> UV_GRADIENT = style -> style
            .withColor(movingGradient(SPEED_SLOW, UV, WHITE));

    public static final UnaryOperator<Style> UHV_GRADIENT = style -> style
            .withColor(movingGradient(SPEED_SLOW, UHV, WHITE));

    public static final UnaryOperator<Style> UEV_GRADIENT = style -> style
            .withColor(movingGradient(SPEED_SLOW, UEV, WHITE));

    public static final UnaryOperator<Style> UIV_GRADIENT = style -> style
            .withColor(movingGradient(SPEED_SLOW, UIV, WHITE));

    public static final UnaryOperator<Style> UXV_GRADIENT = style -> style
            .withColor(movingGradient(SPEED_SLOW, UXV, WHITE));
    /*
    public static final UnaryOperator<Style> OpV_GRADIENT = style -> style
            .withColor(movingGradient(SPEED_SLOW, OpV, WHITE));

    public static final UnaryOperator<Style> MAX_GRADIENT = style -> style
            .withColor(movingGradient(SPEED_SLOW, MAX, WHITE));
     */

}
