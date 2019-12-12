package com.max.app.concurrency.cell;

public final class Cell {

    private long value;

    private final Object mutex = new Object();

    public long getValue() {
        synchronized (mutex) {
            return value;
        }
    }

    public void setValue(long value) {
        synchronized (mutex) {
            this.value = value;
        }
    }

    // no deadlock, global resource ordering used
    @SuppressWarnings("SynchronizationOnLocalVariableOrMethodParameter")
    public void swap(Cell other) {

        // handle aliasing
        if (this == other) {
            return;
        }

        Object first = this.mutex;
        Object second = other.mutex;

        if (System.identityHashCode(other) < System.identityHashCode(this)) {
            first = other.mutex;
            second = this.mutex;
        }

        synchronized (first) {
            synchronized (second) {
                long oldValue = value;
                value = other.value;
                other.value = oldValue;
            }
        }
    }
}
