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
    public static int findMaxSum(int[] arr) {
        checkArgument(arr != null, "null 'arr' argument passed");

        int maxSoFar = 0;
        int maxCur = 0;

        for (int value : arr) {
            // TODO: here 'maxCur + value' overflow possible
            maxCur = Math.max(maxCur + value, 0);
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
    public static int findMaxSumBruteforce(int[] arr) {
        checkArgument(arr != null, "null 'arr' argument passed");

        int maxSoFar = 0;

        for (int i = 0; i < arr.length; ++i) {
            int curSum = 0;

            for (int j = i; j < arr.length; ++j) {
                curSum += arr[j];
                maxSoFar = Math.max(maxSoFar, curSum);
            }
        }

        return maxSoFar;
    }
}
