package com.max.app.concurrency;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

final class MyGraphics {

    private static final Logger LOG = LoggerFactory.getLogger(MyGraphics.class);

    void drawRect(int bottomX, int bottomY, int topX, int topY) {
        LOG.info("Rectangle [{}, {}][{}, {}]", bottomX, bottomY, topX, topY);
    }
}
