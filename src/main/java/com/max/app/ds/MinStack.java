package com.max.app.ds;

import java.util.Arrays;


public final class MinStack<T extends Comparable<T>> {

    private static final int INITIAL_CAPACITY = 8;

    private T[] data;
    private int index;

    private T[] minData;
    private int minIndex;

    @SuppressWarnings("unchecked")
    public MinStack() {
        this.data = (T[]) new Comparable<?>[INITIAL_CAPACITY];
        this.minData = (T[]) new Comparable<?>[INITIAL_CAPACITY];
    }

    /**
     * time: O(1)
     */
    public void push(T value) {

        assert minIndex <= index : String.format("minIndex > index: minIndex: %d, index: %d", minIndex, index);

        data = resizeIfNeeded(data, index);

        data[index] = value;
        ++index;

        if (minIndex == 0 || minData[minIndex - 1].compareTo(value) >= 0) {
            minData = resizeIfNeeded(minData, minIndex);

            minData[minIndex] = value;
            ++minIndex;
        }

        assert minIndex <= index : String.format("minIndex > index: minIndex: %d, index: %d", minIndex, index);
    }

    private static <U> U[] resizeIfNeeded(U[] arr, int index) {
        if (index == arr.length) {
            return Arrays.copyOf(arr, arr.length * 2);
        }
        return arr;
    }

    /**
     * time: O(1)
     */
    public T pop() {

        if (index == 0) {
            throw new IllegalStateException("Stack is empty");
        }

        assert index > 0 : "index <= 0, index: " + index;

        --index;
        T valueToPop = data[index];
        data[index] = null;

        assert minIndex > 0 : "minIndex <= 0, minIndex: " + minIndex;

        if (minData[minIndex - 1].compareTo(valueToPop) == 0) {
            minData[minIndex - 1] = null;
            --minIndex;
        }

        return valueToPop;
    }

    /**
     * time: O(1)
     */
    public T min() {
        if (minIndex == 0) {
            throw new IllegalStateException("Stack is empty");
        }

        return minData[minIndex - 1];
    }

    /**
     * time: O(1)
     */
    public int size() {
        return index;
    }

    /**
     * time: O(1)
     */
    public boolean isEmpty() {
        return index == 0;
    }
}
