package com.max.app.concurrency.list;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class COWList<T> implements Iterable<T> {

    private static final int DEFAULT_INITIAL_CAPACITY = 8;

    private final Object lock = new Object();

    @SuppressWarnings("unchecked")
    private T[] data = (T[]) new Object[DEFAULT_INITIAL_CAPACITY];
    private int size;

    public void add(T newValue) {
        synchronized (lock) {
            int newLength = data.length;

            if (size == data.length) {
                newLength <<= 1;
            }

            data = Arrays.copyOf(data, newLength);
            data[size++] = newValue;
        }
    }

    @Override
    public Iterator<T> iterator() {
        T[] dataRef;
        int curSize;
        synchronized (lock) {
            dataRef = data;
            curSize = size;
        }
        return new SnapshotIterator<>(dataRef, curSize);
    }

    private static class SnapshotIterator<U> implements Iterator<U> {

        private final U[] snapshot;
        private final int size;

        private int index;

        SnapshotIterator(U[] snapshot, int size) {
            this.snapshot = snapshot;
            this.size = size;
        }

        @Override
        public boolean hasNext() {
            return index < size;
        }

        @Override
        public U next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            return snapshot[index++];
        }
    }
}
