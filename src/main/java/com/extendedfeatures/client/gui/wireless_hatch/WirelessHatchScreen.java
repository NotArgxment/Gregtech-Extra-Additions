package com.extendedfeatures.client.gui.wireless_hatch;

import com.extendedfeatures.client.internal.rendering.network.RequestState;
import com.extendedfeatures.client.internal.rendering.network.ScanPacket;
import com.extendedfeatures.client.internal.rendering.network.LinksTogglePacket;
import com.extendedfeatures.client.internal.rendering.network.RangeTogglePacket;
import com.extendedfeatures.client.internal.rendering.PacketManager;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;

public class WirelessHatchScreen extends Screen {

    private static final int PANEL_WIDTH = 250;
    private static final int PANEL_HEIGHT = 112;
    private static final int BUTTON_HEIGHT = 20;
    private static final int BUTTON_SPACING = 8;

    private static final int COLOR_BG = 0xE00B0F14;
    private static final int COLOR_BORDER = 0xFF3AAFD9;
    private static final int COLOR_TITLE = 0xFFAEEFFF;
    private static final int COLOR_COOLDOWN = 0xFFFF8A80;

    private final BlockPos hatchPos;

    private boolean showRangeEnabled = false;
    private boolean showLinksEnabled = false;
    private boolean hasScannedOnce = false;
    private long cooldownEndGameTime = 0L;

    private Button rangeButton;
    private Button linksButton;
    private Button scanButton;

    private int panelLeft;
    private int panelTop;

    public WirelessHatchScreen(BlockPos hatchPos) {
        super(Component.translatable("gui.extendedfeatures.wireless_hatch.title"));
        this.hatchPos = hatchPos;
    }

    public BlockPos getHatchPos() {
        return hatchPos;
    }

    @Override
    protected void init() {
        super.init();

        panelLeft = (this.width - PANEL_WIDTH) / 2;
        panelTop = (this.height - PANEL_HEIGHT) / 2;

        int buttonWidth = PANEL_WIDTH - 20;
        int x = panelLeft + 10;
        int y = panelTop + 30;

        rangeButton = Button.builder(rangeLabel(), btn -> onToggleRange())
                .bounds(x, y, buttonWidth, BUTTON_HEIGHT)
                .build();
        addRenderableWidget(rangeButton);
        y += BUTTON_HEIGHT + BUTTON_SPACING;

        linksButton = Button.builder(linksLabel(), btn -> onToggleLinks())
                .bounds(x, y, buttonWidth, BUTTON_HEIGHT)
                .build();
        addRenderableWidget(linksButton);
        refreshLinksButtonState();
        y += BUTTON_HEIGHT + BUTTON_SPACING;

        scanButton = Button.builder(scanLabel(), btn -> onScan())
                .bounds(x, y, buttonWidth, BUTTON_HEIGHT)
                .build();
        addRenderableWidget(scanButton);

        // Ask the server for the authoritative toggle/cooldown state — the local defaults
        // above are just a neutral placeholder until this reply lands (usually same tick).
        PacketManager.CHANNEL.sendToServer(new RequestState(hatchPos));
    }

    // Invoked by WirelessHatchScreenHandler whenever a WirelessStateSyncPacket for this
    // hatch arrives (initial open, and after every toggle/scan action).
    public void applyStateSync(boolean showRange, boolean showLinks, long cooldownEndGameTime, boolean hasScannedOnce) {
        this.showRangeEnabled = showRange;
        this.showLinksEnabled = showLinks;
        this.cooldownEndGameTime = cooldownEndGameTime;
        this.hasScannedOnce = hasScannedOnce;
        refreshButtonLabels();
        refreshLinksButtonState();
    }

    private void onToggleRange() {
        PacketManager.CHANNEL.sendToServer(new RangeTogglePacket(hatchPos));
    }

    private void onToggleLinks() {
        PacketManager.CHANNEL.sendToServer(new LinksTogglePacket(hatchPos));
    }

    private void onScan() {
        if (isOnCooldown())
            return;
        PacketManager.CHANNEL.sendToServer(new ScanPacket(hatchPos));
    }

    private boolean isOnCooldown() {
        return this.minecraft != null && this.minecraft.level != null
                && this.minecraft.level.getGameTime() < cooldownEndGameTime;
    }

    private void refreshButtonLabels() {
        if (rangeButton != null) rangeButton.setMessage(rangeLabel());
        if (linksButton != null) linksButton.setMessage(linksLabel());
    }

    // Greys out "Show current links" (and sets an explanatory tooltip) until the first
    // scan has run — enabling it earlier would just show an empty loop with nothing linked.
    private void refreshLinksButtonState() {
        if (linksButton == null) return;
        boolean canToggle = hasScannedOnce || showLinksEnabled;
        linksButton.active = canToggle;
        linksButton.setTooltip(canToggle
                ? null
                : net.minecraft.client.gui.components.Tooltip.create(
                Component.translatable("gui.extendedfeatures.wireless_hatch.show_links_locked")));
    }

    private Component stateSuffix(boolean enabled) {
        return Component.translatable(enabled
                ? "gui.extendedfeatures.wireless_hatch.state_on"
                : "gui.extendedfeatures.wireless_hatch.state_off");
    }

    private Component rangeLabel() {
        return Component.translatable("gui.extendedfeatures.wireless_hatch.show_range")
                .append(" ")
                .append(stateSuffix(showRangeEnabled));
    }

    private Component linksLabel() {
        return Component.translatable("gui.extendedfeatures.wireless_hatch.show_links")
                .append(" ")
                .append(stateSuffix(showLinksEnabled));
    }

    private Component scanLabel() {
        return Component.translatable("gui.extendedfeatures.wireless_hatch.scan_link");
    }

    @Override
    public void tick() {
        super.tick();
        // The cooldown is purely a client-side display concern; keep the scan button's
        // enabled state in sync every tick using the client's own level game time.
        if (scanButton != null) {
            scanButton.active = !isOnCooldown();
        }
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        renderBackground(graphics);

        graphics.fill(panelLeft, panelTop, panelLeft + PANEL_WIDTH, panelTop + PANEL_HEIGHT, COLOR_BG);
        graphics.renderOutline(panelLeft, panelTop, PANEL_WIDTH, PANEL_HEIGHT, COLOR_BORDER);

        graphics.drawCenteredString(this.font, this.title, this.width / 2, panelTop + 10, COLOR_TITLE);

        super.render(graphics, mouseX, mouseY, partialTick);

        if (isOnCooldown() && this.minecraft != null && this.minecraft.level != null) {
            long remainingTicks = cooldownEndGameTime - this.minecraft.level.getGameTime();
            float seconds = Math.max(0f, remainingTicks / 20f);
            Component cooldownText = Component.translatable(
                    "gui.extendedfeatures.wireless_hatch.cooldown", String.format("%.1f", seconds));
            graphics.drawCenteredString(this.font, cooldownText, this.width / 2,
                    panelTop + PANEL_HEIGHT - 12, COLOR_COOLDOWN);
        }
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}