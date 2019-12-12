package com.max.app.concurrency.list;

import java.util.AbstractList;
import java.util.List;

/**
 * Fully synchronized list adapter.
 */
public final class SynchronizedList<T> extends AbstractList<T> implements List<T> {

    private final Object lock = new Object();

    private final List<T> delegate;

    public SynchronizedList(List<T> delegate) {
        this.delegate = delegate;
    }

    @Override
    public void add(int index, T element) {
        synchronized (lock) {
            delegate.add(index, element);
        }
    }

    @Override
    public T remove(int index) {
        synchronized (lock) {
            return delegate.remove(index);
        }
    }

    @Override
    public T get(int index) {
        synchronized (lock) {
            return delegate.get(index);
        }
    }

    @Override
    public int size() {
        synchronized (lock) {
            return delegate.size();
        }
    }
}
