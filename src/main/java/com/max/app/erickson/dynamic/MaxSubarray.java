package com.max.app.erickson.dynamic;

import java.math.BigInteger;

import static com.google.common.base.Preconditions.checkArgument;

public final class MaxSubarray {

    private MaxSubarray() {
        throw new AssertionError("Can't instantiate utility only class");
    }

    /**
     * Find max subarray product using dynamic programming approach.
     * <p>
     * N = arr.length
     * <p>
     * time: O(N), if we neglect the fact that BigInteger multiplication is not O(1)
     * space: O(1)
     */
    public static BigInteger findMaxProduct(int[] arr) {
        checkArgument(arr != null, "null 'arr' argument passed");

        //Use BigInteger here to prevent overflow/underflow during multiplication
        BigInteger maxSoFar = BigInteger.ZERO;

        BigInteger positive = BigInteger.ZERO;
        BigInteger negative = BigInteger.ZERO;

        for (int value : arr) {

            if (value == 0) {
                positive = BigInteger.ZERO;
                negative = BigInteger.ZERO;
            }
            else if (value > 0) {
                if (BigInteger.ZERO.equals(positive)) {
                    positive = BigInteger.valueOf(value);
                }
                else {
                    positive = positive.multiply(BigInteger.valueOf(value));
                }

                negative = negative.multiply(BigInteger.valueOf(value));
            }
            // value < 0
            else {
                if (BigInteger.ZERO.equals(negative)) {
                    negative = (BigInteger.ZERO.equals(positive) ?
                            BigInteger.valueOf(value) : positive.multiply(BigInteger.valueOf(value)));
                    positive = BigInteger.ZERO;
                }
                else {

                    BigInteger newPositive = negative.multiply(BigInteger.valueOf(value));

                    negative = (BigInteger.ZERO.equals(positive) ?
                            BigInteger.valueOf(value) : positive.multiply(BigInteger.valueOf(value)));
                    positive = newPositive;
                }
            }

            maxSoFar = maxSoFar.compareTo(positive) >= 0 ? maxSoFar : positive;
        }

        return maxSoFar;
    }

    /**
     * Find max subarray product using bruteforce approach.
     * <p>
     * time: O(N^2), if we neglect the fact that BigInteger multiplication is not O(1)
     * space: O(1)
     */
    public static BigInteger findMaxProductBruteforce(int[] arr) {
        checkArgument(arr != null, "null 'arr' argument passed");

        //Use BigInteger here to prevent overflow/underflow during multiplication
        BigInteger maxSoFar = BigInteger.ZERO;

        for (int i = 0; i < arr.length; ++i) {
            BigInteger curSum = BigInteger.ONE;

            for (int j = i; j < arr.length; ++j) {
                curSum = curSum.multiply(BigInteger.valueOf(arr[j]));
                maxSoFar = maxSoFar.compareTo(curSum) >= 0 ? maxSoFar : curSum;
            }
        }

        return maxSoFar;
    }

    /**
     * Wrapping around: Suppose A is a circular array. In this setting, a
     * "contiguous subarray" can be either an interval A[i .. j] or a suffix followed
     * by a prefix A[i .. n]  A[1 .. j]. Describe and analyze an algorithm that
     * finds a contiguous subarray of A with the largest sum.
     * <p>
     * N = arr.length
     * <p>
     * time: O(N)
     * space: O(N), because we need to store 'maxPrefix'
     */
    public static long findMaxSumWrapping(int[] arr) {
        checkArgument(arr != null, "null 'arr' argument passed");

        long maxSum = findMaxSum(arr);

        long[] maxPrefix = calculateMaxPrefix(arr);

        long maxSumWrapping = 0L;

        long suffix = 0L;

        for (int i = arr.length - 1; i > 0; --i) {
            suffix += arr[i];
            maxSumWrapping = Math.max(maxSumWrapping, suffix + maxPrefix[i - 1]);
        }

        return Math.max(maxSum, maxSumWrapping);
    }

    private static long[] calculateMaxPrefix(int[] arr) {
        assert arr != null : "null 'arr' parameter passed";

        if (arr.length == 0) {
            return new long[]{};
        }

        long[] maxPrefix = new long[arr.length];
        maxPrefix[0] = arr[0];

        int cur = arr[0];

        for (int i = 1; i < arr.length; ++i) {
            cur += arr[i];
            maxPrefix[i] = Math.max(maxPrefix[i - 1], cur);
        }
        return maxPrefix;
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
