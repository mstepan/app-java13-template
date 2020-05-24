package com.max.app.sorting;

final class InsertionSort {

    private InsertionSort() {
        throw new AssertionError("Can't instantiate utility only class");
    }

    static void sort(int[] arr, int from, int to) {
        assert arr != null : "null 'arr' detected";

        int temp;
        for (int i = from + 1; i <= to; ++i) {

            temp = arr[i];

            int j = i - 1;

            while (j >= 0 && arr[j] > temp) {
                arr[j + 1] = arr[j];
                --j;
            }

            arr[j + 1] = temp;
        }
    }
}
