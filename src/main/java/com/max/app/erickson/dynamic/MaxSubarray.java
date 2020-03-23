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
