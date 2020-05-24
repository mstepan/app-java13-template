package com.max.app;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

import static com.google.common.base.Preconditions.checkArgument;

public final class ArrayUtils {

    private static final ThreadLocalRandom RAND = ThreadLocalRandom.current();

    private ArrayUtils() {
        throw new IllegalStateException("Can't instantiate utility-only class");
    }

    /**
     * Random shuffle array in-place using Fisher-Yates algorithm.
     */
    public static void shuffle(int[] arr) {
        checkArgument(arr != null, "null 'arr' parameter detected");

        if (arr.length < 2) {
            return;
        }

        for (int i = 0; i < arr.length - 2; ++i) {
            int swapIndex = i + RAND.nextInt(arr.length - i);
            swap(arr, i, swapIndex);
        }
    }

    /**
     * Swap two elements of array in-place.
     */
    public static void swap(int[] arr, int from, int to) {
        if (from == to) {
            return;
        }

        int temp = arr[from];
        arr[from] = arr[to];
        arr[to] = temp;
    }

    /**
     * time: O(N^2), can be improved to O(N) if we will know all permutation cycles in advance(we can do this)
     * space: O(1)
     */
    public static void shiftArrayRight(int[] arr, int baseOffset) {
        checkArgument(arr != null, "null 'arr' parameter passed");
        checkArgument(baseOffset >= 0, "negative 'baseOffset' passed (should be positive or zero), %s", baseOffset);

        int offset = baseOffset % arr.length;

        if (offset == 0) {
            return;
        }

        for (int i = 0; i < arr.length; ++i) {
            int cycleBase = findCycleBase(i, offset, arr.length);

            if (cycleBase == i) {
                permuteInPlace(arr, i, offset);
            }
        }
    }

    /**
     * N = arr.length
     * K = current permutation cycle length
     * time: O(K)
     * space: O(1)
     */
    private static int findCycleBase(int i, int offset, int n) {

        int startIndex = i;
        int minIndex = i;

        int index = (i + offset) % n;

        while (index != startIndex) {
            minIndex = Math.min(minIndex, index);
            index = (index + offset) % n;
        }

        return minIndex;
    }

    /**
     * N = arr.length
     * K = current permutation cycle length
     * time: O(K)
     * space: O(1)
     */
    private static void permuteInPlace(int[] arr, int i, int offset) {

        final int n = arr.length;
        final int cycleBase = i;

        int prev = arr[i];
        int index = (i + offset) % n;

        while (index != cycleBase) {
            int temp = arr[index];
            arr[index] = prev;
            prev = temp;

            index = (index + offset) % n;
        }

        arr[index] = prev;
    }

    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 4, 5, 6, 7};
        int offset = 9;

        System.out.println(Arrays.toString(arr));

        shiftArrayRight(arr, offset);

        System.out.println(Arrays.toString(arr));
    }
}
