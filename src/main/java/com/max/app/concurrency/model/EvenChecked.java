package com.max.app.concurrency.model;

public class EvenChecked implements Even {

    private final Object mutex = new Object();
    private final Even delegate;

    public EvenChecked(Even delegate) {
        this.delegate = delegate;
    }

    private void checkInvariant() {
        final int curValue = delegate.getValue();
        assert (curValue & 1) == 0 : "not even value detected: " + curValue;
    }

    @Override
    public int next() {
        synchronized (mutex) {
            checkInvariant();
            try {
                return delegate.next();
            }
            finally {
                checkInvariant();
            }
        }
    }

    @Override
    public int getValue() {
        return delegate.getValue();
    }
}
