package com.max.app.concurrency.counter;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public final class EagerSingletonCounter {

    private static final Random RAND = ThreadLocalRandom.current();

    private static final EagerSingletonCounter INST = new EagerSingletonCounter();

    private final long initialValue;
    private long value;

    public static EagerSingletonCounter getInstance() {
        return INST;
    }

    private EagerSingletonCounter() {
        System.out.println("EagerSingletonCounter created");
        initialValue = RAND.nextLong();
    }

    public synchronized long next() {
        return value++;
    }

    public synchronized void reset() {
        value = initialValue;
    }

}
