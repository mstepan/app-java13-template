package com.max.app;


import java.util.Arrays;
import java.util.Random;
import java.util.stream.IntStream;

public final class Main {

    public static void main(String[] args) throws Exception {

        for (int it = 0; it < 10_000; ++it) {
            int[] arr1 = generateRandomArray(RAND.nextInt(10_000));
            int[] arr2 = Arrays.copyOf(arr1, arr1.length);
            int[] arr3 = Arrays.copyOf(arr1, arr1.length);

            Arrays.sort(arr1);
            insertionSort(arr2);
            fastInsertionSort(arr3);

            if (!Arrays.equals(arr1, arr2)) {
                throw new IllegalStateException("sort != insertionSort");
            }

            if (!Arrays.equals(arr1, arr3)) {
                throw new IllegalStateException("sort != fastInsertionSort");
            }
        }

        System.out.printf("java: %s%n", System.getProperty("java.version"));
    }

    /**
     * Typical insertion sort algorithm with linear scan.
     * <p>
     * time: O(N^2)
     * space: O(1)
     */
    private static void insertionSort(int[] arr) {
        assert arr != null : "null 'arr' detected";

        int temp;
        for (int i = 1; i < arr.length; ++i) {

            temp = arr[i];

            int j = i - 1;

            while (j >= 0 && arr[j] > temp) {
                arr[j + 1] = arr[j];
                --j;
            }

            arr[j + 1] = temp;
        }
    }

    private static void fastInsertionSort(int[] arr) {
        assert arr != null : "null 'arr' detected";
        heapify(arr);
        insertionSort(arr);
    }


    private static void heapify(int[] arr) {
        assert arr != null : "null 'arr' detected";

        for (int parent = arr.length / 2 - 1; parent >= 0; --parent) {
            fixDown(arr, parent);
        }
    }

    private static void fixDown(int[] arr, int parent) {
        assert arr != null;
        assert parent >= 0 && parent < arr.length : String.format("arr.length: %d, parent: %d", arr.length, parent);

        final int left = 2 * parent + 1;
        final int right = 2 * parent + 2;

        int minIndex = parent;

        if (left < arr.length) {

            // check left child
            if (arr[left] < arr[minIndex]) {
                minIndex = left;
            }

            // check right child
            if (right < arr.length && arr[right] < arr[minIndex]) {
                minIndex = right;
            }
        }

        if (minIndex != parent) {
            swap(arr, parent, minIndex);
            fixDown(arr, minIndex);
        }
    }

    private static void swap(int[] arr, int from, int to) {
        assert arr != null : "null 'arr' passed";
        assert from >= 0 && from < arr.length : "'from' out of bound";
        assert to >= 0 && to < arr.length : "'to' out of bound";

        int temp = arr[from];
        arr[from] = arr[to];
        arr[to] = temp;
    }

    private static final Random RAND = new Random();

    private static int[] generateRandomArray(int length) {
        assert length >= 0 : "negative arr length";
        return IntStream.range(0, length).
                map(notUsed -> RAND.nextInt()).
                toArray();
    }

}
