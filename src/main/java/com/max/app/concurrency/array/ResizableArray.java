package com.max.app.concurrency.array;

import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.function.Consumer;

public final class ResizableArray<T> {

    private T[] data;
    private int size;

    @SuppressWarnings("unchecked")
    public ResizableArray(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Can't create ResizableArray with negative/zero capacity: " + capacity);
        }
        this.data = (T[]) new Object[capacity];
    }

    public synchronized void applyToAll(Consumer<T> op) {
        for (int i = 0; i < size; ++i) {
            op.accept(data[i]);
        }
    }

    public synchronized int size() {
        return size;
    }

    public synchronized T get(int i) {
        if (i < 0 || i >= size) {
            throw new NoSuchElementException("Can't get element with id " + i);
        }

        return data[i];
    }

    public synchronized void addLast(T value) {
        if (size == data.length) {
            resize();
        }

        data[size++] = value;
    }

    private void resize() {
        data = Arrays.copyOf(data, data.length * 2);
    }

    public synchronized void removeLast() {
        if (size == 0) {
            throw new IllegalStateException("Can't delete element from empty ResizableArray");
        }

        data[--size] = null;
    }
}
