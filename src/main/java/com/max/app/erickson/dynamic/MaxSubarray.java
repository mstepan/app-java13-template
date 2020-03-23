package com.max.app.erickson.dynamic;

import static com.google.common.base.Preconditions.checkArgument;

public final class MaxSubarray {

    private MaxSubarray() {
        throw new AssertionError("Can't instantiate utility only class");
    }

    /**
     * Find max subarray sum using dynamic programming approach.
     * <p>
     * N = arr.length
     * <p>
     * time: O(N)
     * space: O(1)
     */
    public static long findMaxSum(int[] arr) {
        checkArgument(arr != null, "null 'arr' argument passed");

        // use long here to prevent integer overflow
        long maxSoFar = 0L;
        long maxCur = 0L;

        for (int value : arr) {
            maxCur = Math.max(maxCur + value, 0L);
            maxSoFar = Math.max(maxSoFar, maxCur);
        }

        return maxSoFar;
    }

    /**
     * Find max subarray sum using bruteforce approach.
     * <p>
     * time: O(N^2), because we need to consider all possible pair of indexes for subarrays.
     * space: O(1)
     */
    public static long findMaxSumBruteforce(int[] arr) {
        checkArgument(arr != null, "null 'arr' argument passed");

        long maxSoFar = 0L;

        for (int i = 0; i < arr.length; ++i) {
            long curSum = 0L;

            for (int j = i; j < arr.length; ++j) {
                curSum += arr[j];
                maxSoFar = Math.max(maxSoFar, curSum);
            }
        }

        return maxSoFar;
    }
}
