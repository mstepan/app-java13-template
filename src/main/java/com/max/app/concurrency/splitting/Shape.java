package com.max.app.concurrency.splitting;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public final class Shape {

    private static final Random RAND = ThreadLocalRandom.current();

    // Splitting lock by usage
    private final Object locationLock = new Object();
    private final Object dimensionLock = new Object();

    private int x;
    private int y;

    private int height;
    private int width;

    public Shape(int x, int y, int height, int width) {
        this.x = x;
        this.y = y;
        this.height = height;
        this.width = width;
    }

    public int getX() {
        synchronized (locationLock) {
            return x;
        }
    }

    public void setX(int x) {
        synchronized (locationLock) {
            this.x = x;
        }
    }

    public int getY() {
        synchronized (locationLock) {
            return y;
        }
    }

    public void setY(int y) {
        synchronized (locationLock) {
            this.y = y;
        }
    }

    public void adjustLocation() {
        synchronized (locationLock) {
            x = RAND.nextInt(100);
            y = RAND.nextInt(100);
        }
    }

    public int getHeight() {
        synchronized (dimensionLock) {
            return height;
        }
    }

    public void setHeight(int height) {
        synchronized (dimensionLock) {
            this.height = height;
        }
    }

    public int getWidth() {
        synchronized (dimensionLock) {
            return width;
        }
    }

    public void setWidth(int width) {
        synchronized (dimensionLock) {
            this.width = width;
        }
    }

    public void adjustDimensions() {
        synchronized (dimensionLock) {
            width = 10 + RAND.nextInt(100);
            height = 20 + RAND.nextInt(100);
        }
    }
}
