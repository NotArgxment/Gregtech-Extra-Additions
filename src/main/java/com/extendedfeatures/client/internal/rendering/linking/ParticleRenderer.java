package com.extendedfeatures.client.internal.rendering.linking;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.phys.Vec3;

public class ParticleRenderer {

    private static final double DEFAULT_PARTICLES_PER_BLOCK = 2;

    private ParticleRenderer() {}

    public static Vec3 faceCenterTowards(BlockPos from, BlockPos to) {
        Vec3 fromCenter = Vec3.atCenterOf(from);
        Vec3 toCenter = Vec3.atCenterOf(to);
        Vec3 dir = toCenter.subtract(fromCenter);

        double ax = Math.abs(dir.x);
        double ay = Math.abs(dir.y);
        double az = Math.abs(dir.z);

        if (ax >= ay && ax >= az) {
            return fromCenter.add(Math.signum(dir.x) * 0.5, 0, 0);
        } else if (ay >= ax && ay >= az) {
            return fromCenter.add(0, Math.signum(dir.y) * 0.5, 0);
        } else {
            return fromCenter.add(0, 0, Math.signum(dir.z) * 0.5);
        }
    }

    // Draws a single straight line of particles between two points
    public static void emitLine(ServerLevel level, Vec3 start, Vec3 end, ParticleOptions particle) {
        emitLine(level, start, end, particle, DEFAULT_PARTICLES_PER_BLOCK);
    }

    public static void emitLine(ServerLevel level, Vec3 start, Vec3 end,
                                ParticleOptions particle, double particlesPerBlock) {
        double distance = start.distanceTo(end);
        int steps = Math.max(1, (int) (distance * particlesPerBlock));

        for (int i = 0; i <= steps; i++) {
            double t = (double) i / steps;
            Vec3 point = start.lerp(end, t);
            level.sendParticles(particle, point.x, point.y, point.z, 1, 0, 0, 0, 0);
        }
    }
}