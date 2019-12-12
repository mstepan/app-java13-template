package com.max.app.concurrency;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ParticleSimulator implements Runnable {

    private static final Logger LOG = LoggerFactory.getLogger(ParticleSimulator.class);

    private final List<Particle> particles = new ArrayList<>();
    private final MyGraphics graphics = new MyGraphics();

    ParticleSimulator() {
        for (int i = 0; i < 3; ++i) {
            particles.add(new Particle());
        }
    }

    @Override
    public void run() {

        LOG.info("ParticleSimulator started with priority: {}", Thread.currentThread().getPriority());

        while (!Thread.currentThread().isInterrupted()) {

            try {

                for (Particle singleParticle : particles) {
                    singleParticle.draw(graphics);
                    singleParticle.move();
                }

                TimeUnit.SECONDS.sleep(1);
            }
            catch (InterruptedException interEx) {
                Thread.currentThread().interrupt();
                LOG.info("ParticleSimulator interrupted");
            }
        }

        LOG.info("ParticleSimulator done");
    }

}

