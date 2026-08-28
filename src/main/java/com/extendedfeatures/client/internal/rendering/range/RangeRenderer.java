package com.extendedfeatures.client.internal.rendering.range;

import com.extendedfeatures.ExtendedFeaturesCore;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.*;
import net.minecraft.core.BlockPos;
import net.minecraftforge.api.distmarker.*;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.joml.Matrix4f;

import java.util.*;

@Mod.EventBusSubscriber(
        modid = ExtendedFeaturesCore.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT
)
@OnlyIn(Dist.CLIENT)
public class RangeRenderer {

    // Persistent (toggle-driven) range boxes: pos -> range. No expiry — a box stays until
    // an explicit hide (toggle OFF) removes it, or a fresh show() replaces its range value.
    private static final Map<BlockPos, Integer> activeBoxes = new HashMap<>();

    public static void showBoxAtPosition(BlockPos position, int range) {
        activeBoxes.put(position.immutable(), range);
    }

    public static void hideBoxAtPosition(BlockPos position) {
        activeBoxes.remove(position.immutable());
    }

    // --- Render types -----------------------------------------------------------------
    // Two passes: a very faint translucent tint so the volume still reads as a box, and an
    // additive "glow" pass for the mesh wireframe + vertex sparkle so it actually pops
    // against a dark background instead of just being semi-transparent.

    private static final RenderStateShard.TransparencyStateShard TRANSLUCENT_TRANSPARENCY = new RenderStateShard
            .TransparencyStateShard("translucent",
            () -> {
                RenderSystem.enableBlend();
                RenderSystem.defaultBlendFunc();
            },
            RenderSystem::disableBlend);

    private static final RenderStateShard.TransparencyStateShard ADDITIVE_TRANSPARENCY = new RenderStateShard
            .TransparencyStateShard("additive_glow",
            () -> {
                RenderSystem.enableBlend();
                RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE);
            },
            RenderSystem::disableBlend);

    private static final RenderStateShard.WriteMaskStateShard COLOR_ONLY_WRITE = new RenderStateShard
            .WriteMaskStateShard(true, false);

    private static final RenderType TINT_FILL = RenderType.create(
            "wireless_range_tint",
            DefaultVertexFormat.POSITION_COLOR,
            VertexFormat.Mode.QUADS, 256, false, true,
            RenderType.CompositeState.builder()
                    .setShaderState(new RenderStateShard.ShaderStateShard(GameRenderer::getPositionColorShader))
                    .setTransparencyState(TRANSLUCENT_TRANSPARENCY)
                    .setWriteMaskState(COLOR_ONLY_WRITE)
                    .setCullState(new RenderStateShard.CullStateShard(false))
                    .createCompositeState(true));

    private static final RenderType GLOW_FILL = RenderType.create(
            "wireless_range_mesh_glow",
            DefaultVertexFormat.POSITION_COLOR,
            VertexFormat.Mode.QUADS, 256, false, true,
            RenderType.CompositeState.builder()
                    .setShaderState(new RenderStateShard.ShaderStateShard(GameRenderer::getPositionColorShader))
                    .setTransparencyState(ADDITIVE_TRANSPARENCY)
                    .setWriteMaskState(COLOR_ONLY_WRITE)
                    .setCullState(new RenderStateShard.CullStateShard(false))
                    .createCompositeState(true));

    @SubscribeEvent
    public static void onRenderWorld(RenderLevelStageEvent event) {
        if (event.getStage() != RenderLevelStageEvent.Stage.AFTER_TRANSLUCENT_BLOCKS)
            return;

        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || mc.level == null)
            return;

        if (activeBoxes.isEmpty()) return;

        PoseStack poseStack = event.getPoseStack();
        var camera = mc.gameRenderer.getMainCamera();

        double camX = camera.getPosition().x;
        double camY = camera.getPosition().y;
        double camZ = camera.getPosition().z;

        poseStack.pushPose();
        poseStack.translate(-camX, -camY, -camZ);

        RenderSystem.disableCull();
        RenderSystem.setShader(GameRenderer::getPositionColorShader);

        var bufferSource = mc.renderBuffers().bufferSource();
        VertexConsumer tintBuffer = bufferSource.getBuffer(TINT_FILL);
        VertexConsumer glowBuffer = bufferSource.getBuffer(GLOW_FILL);

        float animTime = mc.level.getGameTime() + event.getPartialTick();

        double worldMinY = mc.level.getMinBuildHeight();
        double worldMaxY = mc.level.getMaxBuildHeight();

        for (Map.Entry<BlockPos, Integer> entry : activeBoxes.entrySet()) {
            BlockPos pos = entry.getKey();
            int range = entry.getValue();

            double radius = (double) range - 0.01f;
            double x1 = pos.getX() - radius;
            double z1 = pos.getZ() - radius;
            double x2 = pos.getX() + radius + 1;
            double z2 = pos.getZ() + radius + 1;

            // Distance cull: skip the whole box if the camera is far enough that the
            // additive glow mesh would be effectively invisible anyway (fog/distance).
            // Measured to the nearest point of the box footprint, not the hatch itself,
            // so boxes the camera is standing inside never get culled.
            double nearestX = clamp(camX, x1, x2);
            double nearestZ = clamp(camZ, z1, z2);
            double horizDistSqr = square(camX - nearestX) + square(camZ - nearestZ);
            double cullDistance = Math.min(range + CULL_DISTANCE_PADDING, MAX_RENDER_DISTANCE);
            if (horizDistSqr > square(cullDistance)) continue;

            // Vertical span is fixed to the hatch's own range, same as X/Z — not tied to
            // camera Y. Tying it to camY caused the whole grid to resnap (visible vertical
            // "jump") every ~CELL_SIZE blocks of player movement, and conceptually a box's
            // height shouldn't depend on how high the player happens to be standing.
            double minY = Math.max(worldMinY, pos.getY() - radius);
            double maxY = Math.min(worldMaxY, pos.getY() + radius + 1);
            if (minY >= maxY) continue;

            long seed = pos.asLong();

            // LOD: coarser grid (fewer triangles) the farther the wall is from the camera.
            double horizDist = Math.sqrt(horizDistSqr);
            double cellSize = lodCellSize(horizDist);

            // Faint volumetric tint so the box still reads as a shape from a distance.
            renderTint(poseStack.last().pose(), tintBuffer, x1, minY, z1, x2, maxY, z2);

            // Four walls, each drawn as its own low-poly triangulated mesh.
            renderWallMesh(poseStack.last().pose(), glowBuffer, x1, x2, minY, maxY, true, z1, seed ^ 0x1L, animTime, cellSize);
            renderWallMesh(poseStack.last().pose(), glowBuffer, x1, x2, minY, maxY, true, z2, seed ^ 0x2L, animTime, cellSize);
            renderWallMesh(poseStack.last().pose(), glowBuffer, z1, z2, minY, maxY, false, x1, seed ^ 0x3L, animTime, cellSize);
            renderWallMesh(poseStack.last().pose(), glowBuffer, z1, z2, minY, maxY, false, x2, seed ^ 0x4L, animTime, cellSize);
        }

        bufferSource.endBatch(TINT_FILL);
        bufferSource.endBatch(GLOW_FILL);
        poseStack.popPose();
    }


    // Colors use additive blending (GLOW_FILL), so alpha acts more like "brightness" than "opacity", and overlapping bright colors can blow out to white.

    private static final float TINT_R = 0.35f, TINT_G = 0.65f, TINT_B = 0.85f; // faint base tint color
    private static final float TINT_ALPHA = 0.075f;                             // faint base tint opacity — keep low

    private static final float EDGE_R = 0.35f, EDGE_G = 0.85f, EDGE_B = 1.00f;       // triangle edge color
    private static final float VERTEX_R = 0.75f, VERTEX_G = 0.98f, VERTEX_B = 1.00f; // vertex sparkle color (brighter/whiter than edges)

    private static final float EDGE_ALPHA = 0.35f;       // max edge line brightness
    private static final float VERTEX_ALPHA_MIN = 0.20f; // vertex brightness at the dimmest point of its twinkle
    private static final float VERTEX_ALPHA_MAX = 0.85f; // vertex brightness at its brightest (twinkle peak or scanline pass)

    private static final double CELL_SIZE = 3.0;       // base grid cell size before jitter — lower = smaller/more triangles
    private static final double JITTER = 0.7;          // how far points wander from the grid (0 = perfect grid, 1 = can touch cell edge)
    private static final double EDGE_THICKNESS = 0.03; // line thickness, in blocks

    // --- Culling / LOD ------------------------------------------------------------------
    // These four constants are the actual fix for the multi-hatch FPS collapse: the old
    // code drew every wall from minBuildHeight to maxBuildHeight (384 blocks) and rebuilt
    // the full jittered mesh from scratch every single frame, with no distance falloff.

    private static final double CULL_DISTANCE_PADDING = 24.0; // extra slack beyond a box's own range before culling it entirely
    private static final double MAX_RENDER_DISTANCE = 96.0;   // hard cap regardless of hatch tier/range (UHV range=128 would otherwise never cull)
    private static final double LOD_NEAR_DISTANCE = 32.0;     // full detail (CELL_SIZE) within this distance
    private static final double LOD_FAR_DISTANCE = 96.0;      // coarsest detail beyond this distance
    private static final double LOD_MAX_CELL_SIZE = 8.0;      // cell size at/after LOD_FAR_DISTANCE

    private static double clamp(double v, double min, double max) {
        return v < min ? min : Math.min(v, max);
    }

    private static double square(double v) {
        return v * v;
    }

    // Linearly scales cell size (fewer/bigger triangles) with distance from the camera.
    private static double lodCellSize(double horizDist) {
        if (horizDist <= LOD_NEAR_DISTANCE) return CELL_SIZE;
        if (horizDist >= LOD_FAR_DISTANCE) return LOD_MAX_CELL_SIZE;
        double t = (horizDist - LOD_NEAR_DISTANCE) / (LOD_FAR_DISTANCE - LOD_NEAR_DISTANCE);
        return CELL_SIZE + t * (LOD_MAX_CELL_SIZE - CELL_SIZE);
    }
    private static final double VERTEX_SIZE = 0.09;    // vertex sparkle dot size, in blocks
    private static final double VERTEX_CHANCE = 3.0;   // 1 in N vertices gets a sparkle dot — lower = more sparkles

    private static final float SCAN_PERIOD_TICKS = 100f; // time for one full scanline sweep (up+down), in ticks
    private static final double SCAN_BAND = 3.0;         // how far (in blocks) the scanline's glow boost reaches

    private static void renderTint(Matrix4f matrix, VertexConsumer buffer,
                                   double x1, double y1, double z1,
                                   double x2, double y2, double z2) {
        quad(matrix, buffer, true, x1, x2, y1, y2, z1, TINT_R, TINT_G, TINT_B, TINT_ALPHA);
        quad(matrix, buffer, true, x1, x2, y1, y2, z2, TINT_R, TINT_G, TINT_B, TINT_ALPHA);
        quad(matrix, buffer, false, z1, z2, y1, y2, x1, TINT_R, TINT_G, TINT_B, TINT_ALPHA);
        quad(matrix, buffer, false, z1, z2, y1, y2, x2, TINT_R, TINT_G, TINT_B, TINT_ALPHA);
    }

    // Draws one wall as a low-poly triangulated mesh: a jittered grid of points, each cell
    // split into two triangles along a random diagonal, with only the edges drawn (no fill) —
    // the "shattered glass" look. axisIsX: true if the wall's horizontal axis maps to world X
    // (fixed coord is Z), false if it maps to world Z (fixed coord is X).
    private static void renderWallMesh(Matrix4f matrix, VertexConsumer buffer,
                                       double u1, double u2, double y1, double y2,
                                       boolean axisIsX, double fixed,
                                       long seed, float animTime, double cellSize) {
        double width = u2 - u1;
        double height = y2 - y1;

        int cols = (int) Math.max(1, Math.round(width / cellSize));
        int rows = (int) Math.max(1, Math.round(height / cellSize));
        double cellW = width / cols;
        double cellH = height / rows;

        // Jittered point grid — border points are pinned (no jitter) so the mesh doesn't
        // poke past the box's outline.
        double[][] pu = new double[rows + 1][cols + 1];
        double[][] pv = new double[rows + 1][cols + 1];
        for (int i = 0; i <= rows; i++) {
            for (int j = 0; j <= cols; j++) {
                double baseU = u1 + j * cellW;
                double baseV = y1 + i * cellH;
                boolean edgeCol = (j == 0 || j == cols);
                boolean edgeRow = (i == 0 || i == rows);
                double jitterU = edgeCol ? 0 : (hashFrac(seed, i, j, 1) - 0.5) * cellW * JITTER;
                double jitterV = edgeRow ? 0 : (hashFrac(seed, i, j, 2) - 0.5) * cellH * JITTER;
                pu[i][j] = baseU + jitterU;
                pv[i][j] = baseV + jitterV;
            }
        }

        float sweep = (animTime % SCAN_PERIOD_TICKS) / SCAN_PERIOD_TICKS;
        float pingPong = sweep < 0.5f ? sweep * 2f : 2f - sweep * 2f;
        double scanY = y1 + pingPong * height;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                double x00u = pu[i][j], x00v = pv[i][j];
                double x10u = pu[i][j + 1], x10v = pv[i][j + 1];
                double x01u = pu[i + 1][j], x01v = pv[i + 1][j];
                double x11u = pu[i + 1][j + 1], x11v = pv[i + 1][j + 1];

                double cellMidY = (x00v + x11v) / 2;
                float brightness = pulseBrightness(seed, i, j, animTime, scanY, cellMidY);
                float a = EDGE_ALPHA * brightness;

                // Cell outline (top, left, right, bottom) — shared edges get drawn twice by
                // neighboring cells, which is fine with additive blending, it just brightens
                // shared borders slightly, matching the uneven line weight in the reference.
                lineSegment(matrix, buffer, axisIsX, x00u, x00v, x10u, x10v, fixed, a);
                lineSegment(matrix, buffer, axisIsX, x00u, x00v, x01u, x01v, fixed, a);
                lineSegment(matrix, buffer, axisIsX, x10u, x10v, x11u, x11v, fixed, a);
                lineSegment(matrix, buffer, axisIsX, x01u, x01v, x11u, x11v, fixed, a);

                // Diagonal split — direction picked per-cell so the triangulation looks
                // irregular rather than a uniform herringbone pattern.
                if (hash(seed, i, j) % 2 == 0) {
                    lineSegment(matrix, buffer, axisIsX, x00u, x00v, x11u, x11v, fixed, a);
                } else {
                    lineSegment(matrix, buffer, axisIsX, x10u, x10v, x01u, x01v, fixed, a);
                }

                // Occasional sparkle at one of this cell's corners.
                if (hash(seed, i, j) % (long) VERTEX_CHANCE == 0) {
                    float vBrightness = pulseBrightness(seed, i, j + 100, animTime, scanY, x00v);
                    vertexDot(matrix, buffer, axisIsX, x00u, x00v, fixed, vBrightness);
                }
            }
        }
    }

    // Per-vertex brightness: a slow individual twinkle (own phase from the position hash),
    // boosted when the moving "data pulse" scanline passes near this point.
    private static float pulseBrightness(long seed, int i, int j, float animTime, double scanY, double posY) {
        double phase = (hash(seed, i, j) % 1000) / 1000.0 * Math.PI * 2;
        float twinkle = (float) (0.5 + 0.5 * Math.sin(animTime * 0.05 + phase));

        double distToScan = Math.abs(posY - scanY);
        float scanBoost = (float) Math.max(0.0, 1.0 - distToScan / SCAN_BAND);

        float base = VERTEX_ALPHA_MIN + (VERTEX_ALPHA_MAX - VERTEX_ALPHA_MIN) * twinkle;
        return Math.max(base, scanBoost);
    }

    private static long hash(long seed, int a, int b) {
        long h = seed ^ ((long) a * 0x9E3779B97F4A7C15L) ^ ((long) b * 0xBF58476D1CE4E5B9L);
        h = (h ^ (h >>> 30)) * 0xBF58476D1CE4E5B9L;
        h = (h ^ (h >>> 27)) * 0x94D049BB133111EBL;
        return Math.abs(h ^ (h >>> 31));
    }

    private static long hash(long seed, int a, int b, int salt) {
        return hash(seed ^ ((long) salt * 0xD6E8FEB86659FD93L), a, b);
    }

    private static double hashFrac(long seed, int a, int b, int salt) {
        return (hash(seed, a, b, salt) % 100000) / 100000.0;
    }

    // --- Primitive drawing (all coplanar with the wall — no camera-facing math needed) ---

    // Thick line between two arbitrary in-plane points — used for triangle edges, which are
    // diagonal (not axis-aligned) once points are jittered off the base grid.
    private static void lineSegment(Matrix4f matrix, VertexConsumer buffer, boolean axisIsX,
                                    double u1, double v1, double u2, double v2, double fixed,
                                    float a) {
        double du = u2 - u1, dv = v2 - v1;
        double len = Math.sqrt(du * du + dv * dv);
        if (len < 1e-6) return;

        double half = EDGE_THICKNESS / 2;
        double px = -dv / len * half;
        double py = du / len * half;

        vertex(matrix, buffer, axisIsX, u1 + px, v1 + py, fixed, EDGE_R, EDGE_G, EDGE_B, a);
        vertex(matrix, buffer, axisIsX, u2 + px, v2 + py, fixed, EDGE_R, EDGE_G, EDGE_B, a);
        vertex(matrix, buffer, axisIsX, u2 - px, v2 - py, fixed, EDGE_R, EDGE_G, EDGE_B, a);
        vertex(matrix, buffer, axisIsX, u1 - px, v1 - py, fixed, EDGE_R, EDGE_G, EDGE_B, a);
    }

    private static void vertexDot(Matrix4f matrix, VertexConsumer buffer, boolean axisIsX,
                                  double u, double v, double fixed, float brightness) {
        double half = VERTEX_SIZE / 2;
        quad(matrix, buffer, axisIsX, u - half, u + half, v - half, v + half, fixed,
                VERTEX_R, VERTEX_G, VERTEX_B, brightness);
    }

    // Draws a flat, axis-aligned quad lying in one wall's plane. u spans su1..su2 along the
    // wall's horizontal axis, v spans y1..y2 vertically, fixed is the constant world coordinate.
    private static void quad(Matrix4f matrix, VertexConsumer buffer, boolean axisIsX,
                             double su1, double su2, double v1, double v2, double fixed,
                             float r, float g, float b, float a) {
        vertex(matrix, buffer, axisIsX, su1, v1, fixed, r, g, b, a);
        vertex(matrix, buffer, axisIsX, su2, v1, fixed, r, g, b, a);
        vertex(matrix, buffer, axisIsX, su2, v2, fixed, r, g, b, a);
        vertex(matrix, buffer, axisIsX, su1, v2, fixed, r, g, b, a);
    }

    private static void vertex(Matrix4f matrix, VertexConsumer buffer, boolean axisIsX,
                               double u, double v, double fixed,
                               float r, float g, float b, float a) {
        if (axisIsX) {
            buffer.vertex(matrix, (float) u, (float) v, (float) fixed).color(r, g, b, a).endVertex();
        } else {
            buffer.vertex(matrix, (float) fixed, (float) v, (float) u).color(r, g, b, a).endVertex();
        }
    }
}