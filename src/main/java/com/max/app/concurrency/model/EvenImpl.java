package com.max.app.concurrency.model;

public class EvenImpl implements Even {

    private int value;

    private final Object mutex = new Object();

    @Override
    public int next() {
        synchronized (mutex) {
            ++value;
            ++value;
            return value;
        }
    }

    @Override
    public int getValue() {
        synchronized (mutex) {
            return value;
        }
    }
}
