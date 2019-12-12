package com.max.app.concurrency.counter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public final class LazySingletonCounter {

    private static final Logger LOG = LoggerFactory.getLogger(LazySingletonCounter.class);

    private static final Random RAND = ThreadLocalRandom.current();

    private static final Object classMutex = LazySingletonCounter.class;

    private static LazySingletonCounter inst;

    private final long initialValue;
    private long value;

    public static LazySingletonCounter getInstance() {
        synchronized (classMutex) {
            if (inst == null) {
                inst = new LazySingletonCounter();
            }

            return inst;
        }
    }

    private LazySingletonCounter() {
        LOG.info("LazySingletonCounter created");
        initialValue = RAND.nextLong();
    }

    public long next() {
        synchronized (classMutex) {
            return value++;
        }
    }

    public void reset() {
        synchronized (classMutex) {
            value = initialValue;
        }
    }

}
