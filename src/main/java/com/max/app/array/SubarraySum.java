package com.max.app.array;

import java.util.Optional;

import static com.google.common.base.Preconditions.checkArgument;

public final class SubarraySum {

    private SubarraySum() {
        throw new AssertionError("Can't instantiate utility only class");
    }

    /**
     * Given an unsorted array 'arr' of NON-NEGATIVE integers,
     * find a continuous sub-array which adds to a given number 'expected'.
     *
     * time: O(N)
     * space: O(1)
     */
    public static Optional<Boundary> findSubarraySum(int[] arr, int expected) {

        checkArgument(arr != null, "null 'arr' parameter passed");
        checkArgument(expected >= 0, "negative expected sum detected: " + expected + ", should be positive or 0 value");

        if (expected == 0) {
            int zeroIndex = searchValue(arr, 0);
            return zeroIndex == -1 ? Optional.empty() : Optional.ofNullable(new Boundary(zeroIndex, zeroIndex));
        }

        if (arr.length == 0) {
            return Optional.empty();
        }

        int left = 0;
        int right = 1;

        checkValueNotNegativeAtIndex(arr, 0);

        // use 'long' as sum parameter to prevent integer overflow
        long sum = arr[0];

        while (true) {
            if (sum == expected) {
                return Optional.of(new Boundary(left, right - 1));
            }

            if (sum > expected) {
                // move 'left' pointer
                sum -= arr[left];
                ++left;
            }
            else {
                // move 'right' pointer
                if (right >= arr.length) {
                    break;
                }

                sum += arr[right];

                checkValueNotNegativeAtIndex(arr, right);
                ++right;
            }
        }

        return Optional.empty();
    }

    private static int searchValue(int[] arr, int value) {
        for (int i = 0; i < arr.length; ++i) {
            if (arr[i] == value) {
                return i;
            }
        }

        return -1;
    }

    private static void checkValueNotNegativeAtIndex(int[] arr, int index) {
        if (arr[index] < 0) {
            throw new IllegalStateException("Negative array value detected at index: " + index);
        }
    }

    static final class Boundary {

        private static final Boundary EMPTY = new Boundary(-1, -1);

        final int from;
        final int to;

        Boundary(int from, int to) {
            this.from = from;
            this.to = to;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Boundary)) {
                return false;
            }
            Boundary that = (Boundary) obj;
            if (from != that.from) {
                return false;
            }
            return to == that.to;
        }

        @Override
        public int hashCode() {
            return 31 * Integer.hashCode(from) + Integer.hashCode(to);
        }
    }
}
