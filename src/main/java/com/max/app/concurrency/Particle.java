package com.max.app.concurrency;

import java.awt.*;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

final class Particle {

    private int x;
    private int y;

    private static final Random RAND = ThreadLocalRandom.current();

    synchronized void move() {
        x += RAND.nextInt(10) - 5;
        y += RAND.nextInt(20) - 10;
    }

    void draw(MyGraphics g) {

        int tempX, tempY;

        synchronized (this) {
            tempX = x;
            tempY = y;
        }

        g.drawRect(tempX, tempY, 10, 10);
    }

}
