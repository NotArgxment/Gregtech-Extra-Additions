package com.extendedfeatures.client.internal.logic.machine;

import com.extendedfeatures.client.gui.wireless_hatch.WirelessHatchScreen;
import com.extendedfeatures.client.internal.ConfigClass;
import com.extendedfeatures.client.internal.logic.multiblock.MatrixDataRelayMachine;
import com.extendedfeatures.client.internal.rendering.network.StateSync;
import com.extendedfeatures.client.internal.rendering.linking.ParticleAnimator;
import com.extendedfeatures.client.internal.rendering.PacketManager;
import com.extendedfeatures.client.internal.rendering.linking.ParticleRenderer;
import com.extendedfeatures.client.internal.rendering.range.RangePackets;

import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.capability.IDataAccessHatch;
import com.gregtechceu.gtceu.api.machine.*;
import com.gregtechceu.gtceu.api.machine.feature.*;
import com.gregtechceu.gtceu.api.machine.feature.multiblock.IMultiPart;
import com.gregtechceu.gtceu.api.machine.multiblock.part.MultiblockPartMachine;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.common.machine.multiblock.part.*;

import com.lowdragmc.lowdraglib.syncdata.annotation.Persisted;
import com.lowdragmc.lowdraglib.syncdata.field.ManagedFieldHolder;

import lombok.Getter;

import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.phys.*;

import net.minecraftforge.network.PacketDistributor;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.*;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class WirelessOpticalHatch extends OpticalDataHatchMachine implements IMachineLife, IInteractedMachine {

    protected static final ManagedFieldHolder MANAGED_FIELD_HOLDER = new ManagedFieldHolder(
            WirelessOpticalHatch.class, MultiblockPartMachine.MANAGED_FIELD_HOLDER);

    private static final int durationTicks = 200;
    private static final int intervalTicks = 4;
    private static final int scanCooldown = 100;
    private static final int resyncInterval = 100;

    public enum WirelessTier {
        LuV(GTValues.LuV, 16, 4),
        ZPM(GTValues.ZPM, 32, 8),
        UV(GTValues.UV, 64, 16);

        public final int gtTier;
        public final int range;
        public final int maxConnections;

        WirelessTier(int gtTier, int range, int maxConnections) {
            this.gtTier = gtTier;
            this.range = range;
            this.maxConnections = maxConnections;
        }

        public static @Nullable WirelessTier byGTTier(int gtTier) {
            for (WirelessTier value : values()) {
                if (value.gtTier == gtTier) return value;
            }
            return null;
        }
    }

    // Only the same tier
    @Getter
    private final WirelessTier wirelessTier;

    // stores the receptor pos
    @Persisted
    private BlockPos linkedTransmissorPos;

    // Makes a list of the linked receptors
    @Persisted
    private final List<BlockPos> linkedReceptorPositions = new ArrayList<>();

    // Looks for any physical data hatch nearby inside the given range, calls the part. animator
    @Persisted
    private final List<BlockPos> linkedDataHatchPositions = new ArrayList<>();

    // GUI toggle state
    @Persisted
    private boolean showRangeEnabled = false;

    @Persisted
    private boolean showLinksEnabled = false;

    // Game time of the last successful manual scan
    @Persisted
    private long lastScanGameTime = Long.MIN_VALUE / 2;

    // Not persisted: live server-side loop handles, re-armed lazily via ensureLoopsRunning()
    // whenever the machine is touched (GUI opened, recipe check runs) after a world reload.
    private ParticleAnimator linksLoopAnimator;
    private TickableSubscription rangeResyncSubscription;
    private int rangeResyncCounter = 0;

    // Calls the particles (one-shot "new link" flashes triggered during a scan)
    private final List<ParticleAnimator> particleAnimators = new ArrayList<>();

    // Filters by TIER
    public WirelessOpticalHatch(IMachineBlockEntity holder, boolean isTransmissor, int gtTier) {
        super(holder, isTransmissor);
        this.wirelessTier = WirelessTier.byGTTier(gtTier);
    }

    // Transmissor view of currently linked receptor positions
    public List<BlockPos> getLinkedReceptorPositions() {
        return List.copyOf(linkedReceptorPositions);
    }

    // Transmissor view of currently linked physical Data Access Hatch positions
    public List<BlockPos> getLinkedDataHatchPositions() {
        return List.copyOf(linkedDataHatchPositions);
    }

    // Compares between a linked receptor and a linked data hatch
    public boolean isLinked() {
        return isTransmitter()
                ? !linkedReceptorPositions.isEmpty() || !linkedDataHatchPositions.isEmpty()
                : linkedTransmissorPos != null;
    }

    @Override
    public ManagedFieldHolder getFieldHolder() {
        return MANAGED_FIELD_HOLDER;
    }

    // Lifecycle
    @Override
    public void onMachineRemoved() {
        for (ParticleAnimator animator : particleAnimators) {
            animator.stop();
        }
        particleAnimators.clear();

        if (linksLoopAnimator != null) {
            linksLoopAnimator.stop();
            linksLoopAnimator = null;
        }

        if (rangeResyncSubscription != null) {
            rangeResyncSubscription.unsubscribe();
            rangeResyncSubscription = null;
            if (showRangeEnabled) {
                broadcastRangeState(false);
            }
        }

        // Clean up bidirectional bookkeeping so stale links don't linger
        Level level = getLevel();
        if (level != null && isTransmitter()) {
            for (BlockPos receptorPos : linkedReceptorPositions) {
                if (MetaMachine.getMachine(level, receptorPos) instanceof WirelessOpticalHatch receptor &&
                        getPos().equals(receptor.linkedTransmissorPos)) {
                    receptor.linkedTransmissorPos = null;
                }
            }
        } else
        if (level != null && linkedTransmissorPos != null
                && MetaMachine.getMachine(level, linkedTransmissorPos) instanceof WirelessOpticalHatch transmissor) {
            transmissor.linkedReceptorPositions.remove(getPos());
        }
    }

    // Recipe Logic
    @Override
    public boolean isRecipeAvailable(GTRecipe recipe, Collection<IDataAccessHatch> seen) {

        if (isTransmitter()) {
            seen.add(this);

            ensureLoopsRunning();

            if (!isFormed())
                return false;

            Level level = getLevel();

            if (level == null)
                return false;

            for (BlockPos dataHatchPos : linkedDataHatchPositions) {

                if (!level.isLoaded(dataHatchPos)) continue;
                if (!(MetaMachine.getMachine(level, dataHatchPos) instanceof DataAccessHatchMachine dataHatch)) continue;
                if (seen.contains(dataHatch)) continue;

                if (dataHatch.isRecipeAvailable(recipe, seen)) {
                    return true;
                }
            }
            return false;
        }

        seen.add(this);

        if (!isFormed())
            return false;

        if (linkedTransmissorPos == null)
            return false;

        Level level = getLevel();

        if (level == null || !level.isLoaded(linkedTransmissorPos))
            return false;

        if (!(MetaMachine.getMachine(level, linkedTransmissorPos) instanceof WirelessOpticalHatch partner) ||
                !partner.isTransmitter()) {
            return false;
        }
        if (seen.contains(partner))
            return false;

        return partner.isRecipeAvailable(recipe, seen);
    }

    @Override
    public InteractionResult onUse(BlockState state, Level world,
                                   BlockPos pos, Player player,
                                   InteractionHand hand, BlockHitResult hit) {
        if (!player.getItemInHand(hand).isEmpty())
            return InteractionResult.PASS;

        if (!isTransmitter())
            return InteractionResult.PASS;

        if (world.isClientSide) {
            openClientScreen(pos);
            return InteractionResult.SUCCESS;
        }

        ensureLoopsRunning();
        return InteractionResult.SUCCESS;
    }

    private void openClientScreen(BlockPos pos) {
        net.minecraft.client.Minecraft.getInstance()
                .setScreen(new WirelessHatchScreen(pos));
    }

    // GUI Actions
    public void toggleShowRange(ServerPlayer player) {
        if (!isFormed()) {
            player.sendSystemMessage(Component.translatable("extendedfeatures.machine.wireless_optical_hatch.not_formed"));
            sendStateSyncTo(player);
            return;
        }
        showRangeEnabled = !showRangeEnabled;
        updateRangeLoop();
        sendStateSyncTo(player);
    }

    public void toggleShowLinks(ServerPlayer player) {
        if (!isFormed()) {
            player.sendSystemMessage(Component.translatable("extendedfeatures.machine.wireless_optical_hatch.not_formed"));
            sendStateSyncTo(player);
            return;
        }
        if (!showLinksEnabled && !hasScannedOnce()) {
            player.sendSystemMessage(Component.translatable("extendedfeatures.machine.wireless_optical_hatch.scan_required"));
            sendStateSyncTo(player);
            return;
        }
        showLinksEnabled = !showLinksEnabled;
        updateLinksLoop();
        sendStateSyncTo(player);
    }

    public boolean hasScannedOnce() {
        return lastScanGameTime != Long.MIN_VALUE / 2;
    }

    public void handleScanRequest(ServerPlayer player) {
        if (!isFormed()) {
            player.sendSystemMessage(Component.translatable("extendedfeatures.machine.wireless_optical_hatch.not_formed"));
            sendStateSyncTo(player);
            return;
        }

        Level level = getLevel();
        if (level == null)
            return;

        long now = level.getGameTime();
        if (now - lastScanGameTime < scanCooldown) {
            player.sendSystemMessage(
                    Component.translatable("extendedfeatures.machine.wireless_optical_hatch.scan_cooldown"));
            sendStateSyncTo(player);
            return;
        }

        lastScanGameTime = now;
        scanAndLink(player);
        sendStateSyncTo(player);
    }

    public void sendStateSyncTo(ServerPlayer player) {
        long cooldownEnd = lastScanGameTime + scanCooldown;
        PacketManager.CHANNEL.send(PacketDistributor.PLAYER.with(() -> player),
                new StateSync(getPos(), showRangeEnabled, showLinksEnabled, cooldownEnd, hasScannedOnce()));
    }

    private void updateRangeLoop() {
        boolean shouldRun = showRangeEnabled && isFormed();
        if (shouldRun && rangeResyncSubscription == null) {
            rangeResyncCounter = 0;
            broadcastRangeState(true);
            rangeResyncSubscription = subscribeServerTick(this::rangeResyncTick);
        } else if (!shouldRun && rangeResyncSubscription != null) {
            rangeResyncSubscription.unsubscribe();
            rangeResyncSubscription = null;
            broadcastRangeState(false);
        }
    }

    private void rangeResyncTick() {
        if (!isFormed())
            return;
        if (++rangeResyncCounter >= resyncInterval) {
            rangeResyncCounter = 0;
            broadcastRangeState(true);
        }
    }

    private void broadcastRangeState(boolean show) {
        Level level = getLevel();
        if (!(level instanceof ServerLevel serverLevel))
            return;
        PacketManager.CHANNEL.send(
                PacketDistributor.TRACKING_CHUNK.with(() -> serverLevel.getChunkAt(getPos())),
                new RangePackets(getPos(), wirelessTier.range, show));
    }

    private void updateLinksLoop() {
        boolean shouldRun = showLinksEnabled && isFormed();
        if (shouldRun && linksLoopAnimator == null) {

            linksLoopAnimator = new ParticleAnimator(-1, intervalTicks, this::drawAllLinkBeams);

            linksLoopAnimator.start(this::subscribeServerTick, () -> linksLoopAnimator = null);

        } else if (!shouldRun && linksLoopAnimator != null) {
            linksLoopAnimator.stop();
        }
    }

    private void drawAllLinkBeams() {
        if (!isFormed())
            return;

        Level level = getLevel();
        if (!(level instanceof ServerLevel serverLevel))
            return;

        BlockPos center = getPos();

        for (BlockPos receptorPos : linkedReceptorPositions) {
            if (!level.isLoaded(receptorPos))
                continue;
            emitBeam(serverLevel, center, receptorPos, ParticleTypes.END_ROD);
        }
        for (BlockPos dataHatchPos : linkedDataHatchPositions) {
            if (!level.isLoaded(dataHatchPos))
                continue;
            emitBeam(serverLevel, center, dataHatchPos, ParticleTypes.FLAME);
        }
    }

    private void emitBeam(ServerLevel level, BlockPos from, BlockPos to, ParticleOptions particle) {
        Vec3 start = ParticleRenderer.faceCenterTowards(from, to);
        Vec3 end = ParticleRenderer.faceCenterTowards(to, from);
        ParticleRenderer.emitLine(level, start, end, particle);
    }

    private void ensureLoopsRunning() {
        if (getLevel() == null)
            return;
        updateRangeLoop();
        updateLinksLoop();
    }

    // Scan & Link
    private void scanAndLink(Player player) {
        if (!isFormed()) {
            player.sendSystemMessage(
                    Component.translatable(
                            "extendedfeatures.machine.wireless_optical_hatch.not_formed")
            );
            return;
        }

        Level level = getLevel();
        if (!(level instanceof ServerLevel serverLevel))
            return;

        BlockPos center = getPos();
        int range = wirelessTier.range;

        int newReceiverLinks = linkReceivers(serverLevel, center, range);
        int newDataHatchLinks = linkDataHatches(serverLevel, center, range);

        if (newReceiverLinks > 0 || newDataHatchLinks > 0) {
            player.sendSystemMessage(
                    Component.translatable(
                            "extendedfeatures.machine.wireless_optical_hatch.linked_summary",
                            newReceiverLinks, newDataHatchLinks));
        } else {
            player.sendSystemMessage(
                    Component.translatable("extendedfeatures.machine.wireless_optical_hatch.no_receptors_found")
            );
        }
    }

    private int linkReceivers(ServerLevel level,
                              BlockPos center,
                              int range) {

        int remainingSlots = wirelessTier.maxConnections - linkedReceptorPositions.size();

        if (remainingSlots <= 0) return 0;

        List<WirelessOpticalHatch> candidates = findCandidateReceptors(level, center, range);
        candidates.sort(Comparator.comparingDouble(a -> a.getPos().distSqr(center)));

        int newLinks = 0;

        for (WirelessOpticalHatch receiver : candidates) {

            if (remainingSlots <= 0)
                break;

            if (linkedReceptorPositions.contains(receiver.getPos()))
                continue;

            receiver.linkedTransmissorPos = center;
            linkedReceptorPositions.add(receiver.getPos());
            spawnLinkBeam(level, center, receiver.getPos(), ParticleTypes.END_ROD);
            remainingSlots--;
            newLinks++;
        }
        return newLinks;
    }

    private int linkDataHatches(ServerLevel level,
                                BlockPos center,
                                int range) {
        List<DataAccessHatchMachine> candidates = findCandidateDataHatches(level, center, range);

        int newLinks = 0;

        for (DataAccessHatchMachine dataHatch : candidates) {

            BlockPos dataHatchPos = dataHatch.getPos();

            if (linkedDataHatchPositions.contains(dataHatchPos))
                continue;

            linkedDataHatchPositions.add(dataHatchPos);
            spawnLinkBeam(level, center, dataHatchPos, ParticleTypes.FLAME);
            newLinks++;
        }
        return newLinks;
    }

    private List<WirelessOpticalHatch> findCandidateReceptors(ServerLevel level,
                                                              BlockPos center,
                                                              int range) {

        List<WirelessOpticalHatch> found = new ArrayList<>();

        forEachBlockEntityInRange(level, center, range, (candidatePos, blockEntity) -> {
            if (MetaMachine.getMachine(level, candidatePos) instanceof WirelessOpticalHatch other && !other.isTransmitter() && other.wirelessTier == this.wirelessTier && other.isFormed()) {
                found.add(other);
            }
        });
        return found;
    }

    // Behavior Switch (See ConfigClass#159)
    private List<DataAccessHatchMachine> findCandidateDataHatches(ServerLevel level, BlockPos center, int range) {

        List<DataAccessHatchMachine> found = new ArrayList<>();

        forEachBlockEntityInRange(level, center, range, (candidatePos, blockEntity) -> {
            if (MetaMachine.getMachine(level, candidatePos) instanceof DataAccessHatchMachine
                    dataHatch && dataHatch.isFormed() && validTarget(dataHatch)) {
                found.add(dataHatch);
            }
        });
        return found;
    }

    private boolean validTarget(DataAccessHatchMachine dataHatch) {
        if (!ConfigClass.INSTANCE.DataHatchLinkingBehavior) return true;
        return ((IMultiPart) dataHatch).getControllers().stream()
                .anyMatch(controller -> controller instanceof MatrixDataRelayMachine);
    }

    private interface BlockEntityVisitor {
        void visit(BlockPos pos, BlockEntity blockEntity);
    }

    // Draws a Cube following each transmitter max distance and search's for receptors, replacing the Sphere searching method
    private boolean isWithinCubeRange(BlockPos candidate,
                                      BlockPos center,
                                      int range) {
        return Math.abs(candidate.getX() - center.getX()) <= range &&
                Math.abs(candidate.getY() - center.getY()) <= range &&
                Math.abs(candidate.getZ() - center.getZ()) <= range;
    }

    private void forEachBlockEntityInRange(ServerLevel level, BlockPos center, int range, BlockEntityVisitor visitor) {
        int chunkMinX = (center.getX() - range) >> 4;
        int chunkMaxX = (center.getX() + range) >> 4;
        int chunkMinZ = (center.getZ() - range) >> 4;
        int chunkMaxZ = (center.getZ() + range) >> 4;

        for (int cx = chunkMinX; cx <= chunkMaxX; cx++) {
            for (int cz = chunkMinZ; cz <= chunkMaxZ; cz++) {

                if (!level.hasChunk(cx, cz))
                    continue;

                LevelChunk chunk = level.getChunk(cx, cz);

                for (BlockEntity blockEntity : chunk.getBlockEntities().values()) {
                    BlockPos candidatePos = blockEntity.getBlockPos();

                    if (!isWithinCubeRange(candidatePos, center, range))
                        continue;

                    if (candidatePos.equals(center))
                        continue;

                    visitor.visit(candidatePos, blockEntity);
                }
            }
        }
    }

    // Visual Feedback
    private void spawnLinkBeam(ServerLevel level, BlockPos from, BlockPos to, ParticleOptions particle) {

        Vec3 start = ParticleRenderer.faceCenterTowards(from, to);
        Vec3 end = ParticleRenderer.faceCenterTowards(to, from);

        ParticleAnimator animator = new ParticleAnimator(
                durationTicks, intervalTicks, () -> ParticleRenderer
                .emitLine(level, start, end, particle));

        particleAnimators.add(animator);

        animator.start(this::subscribeServerTick, () -> particleAnimators.remove(animator));
    }

    public String getWirelessTierName() {
        return wirelessTier.name().toLowerCase(Locale.ROOT);
    }
}