package com.max.app.concurrency.buffer;

import java.nio.ByteBuffer;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.StampedLock;

public final class SafeBuffer {

    private static final int SIZE = 100;

    private final StampedLock stampedLock = new StampedLock();
    private final Lock writeLock = stampedLock.asWriteLock();
    private final Lock readLock = stampedLock.asReadLock();

    private final ByteBuffer buf = ByteBuffer.allocate(SIZE);

    public int size(){
        return SIZE;
    }

    public int get(int offset) {

        long stamp = stampedLock.tryOptimisticRead();

        int data = buf.get(offset);

        if (stampedLock.validate(stamp)) {
            return data;
        }

        readLock.lock();

        try {
            return buf.get(offset);
        }
        finally {
            readLock.unlock();
        }
    }

    public void put(int offset, int value) {
        writeLock.lock();
        try {
            buf.putInt(offset, value);
        }
        finally {
            writeLock.unlock();
        }
    }
}
