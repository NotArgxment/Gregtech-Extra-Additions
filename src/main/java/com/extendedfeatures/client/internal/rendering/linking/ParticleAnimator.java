package com.extendedfeatures.client.internal.rendering.linking;

import com.gregtechceu.gtceu.api.machine.TickableSubscription;

import java.util.function.Function;

public class ParticleAnimator {

    private final int durationTicks;
    private final int intervalTicks;
    private final Runnable onFrame;

    private int ticksElapsed = 0;
    private TickableSubscription subscription;
    private Runnable onComplete;

    public ParticleAnimator(int durationTicks, int intervalTicks, Runnable onFrame) {
        this.durationTicks = durationTicks;
        this.intervalTicks = intervalTicks;
        this.onFrame = onFrame;
    }

    public void start(Function<Runnable, TickableSubscription> subscribe, Runnable onComplete) {
        this.onComplete = onComplete;
        this.subscription = subscribe.apply(this::tick);
    }

    private void tick() {
        if (durationTicks >= 0 && ticksElapsed >= durationTicks) {
            stop();
            return;
        }
        if (ticksElapsed % intervalTicks == 0) {
            onFrame.run();
        }
        ticksElapsed++;
    }

    public void stop() {
        if (subscription != null) {
            subscription.unsubscribe();
            subscription = null;
        }
        if (onComplete != null) {
            Runnable callback = onComplete;
            onComplete = null;
            callback.run();
        }
    }
}