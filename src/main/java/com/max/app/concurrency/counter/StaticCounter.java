package com.max.app.concurrency.counter;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public final class StaticCounter {

    private static final Random RAND = ThreadLocalRandom.current();

    private static final long INITIAL_VALUE = RAND.nextLong();
    private static long value;

    public synchronized static long next() {
        return value++;
    }

    public synchronized static void reset() {
        value = INITIAL_VALUE;
    }

    private StaticCounter() {
        System.out.println("StaticCounter created");
    }
}
