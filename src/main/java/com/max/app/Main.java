package com.max.app;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public final class Main {

    private static final Logger LOG = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {

        Random rand = new Random();

        for (int it = 0; it < 10_000_000; ++it) {

            long x = rand.nextInt(Integer.MAX_VALUE);
            long y = rand.nextInt(Integer.MAX_VALUE);

            String expectedRes = String.valueOf(x * y);

            int[] xArr = toDecimalArray(x);
            int[] yArr = toDecimalArray(y);
            int[] tempRes = mul(xArr, yArr);

            String actualRes = toDecimalString(tempRes);

            if (!expectedRes.equals(actualRes)) {
                LOG.info("{} * {} = {}", x, y, expectedRes);
                LOG.info("{} * {} = {}", x, y, actualRes);
                throw new IllegalStateException("Error occurred, results aren't equal");
            }
        }

        LOG.info("Main done. java version {}", System.getProperty("java.version"));
    }

    private static int[] toDecimalArray(long value) {

        assert value >= 0;

        if (value == 0) {
            return ZERO;
        }

        List<Integer> data = new ArrayList<>();

        long x = value;

        while (x != 0) {
            data.add((int) (x % 10));
            x /= 10;
        }

        int[] res = new int[data.size()];

        for (int i = 0; i < res.length; ++i) {
            res[i] = data.get(i);
        }

        return res;
    }

    private static final int[] ZERO = {0};

    /**
     * Standard multiplication method with quadratic complexity.
     * time: O(N^2), space: O(N)
     */
    private static int[] mul(int[] x, int[] y) {

        if (isZero(x) || isZero(y)) {
            return ZERO;
        }

        int[] res = new int[x.length + y.length];

        for (int offset = 0; offset < x.length; ++offset) {

            int digit = x[offset];

            for (int i = 0; i < y.length; ++i) {
                // store not normalized value here, will be normalized at the end
                res[offset + i] += digit * y[i];
            }
        }

        return normalize(res);
    }

    private static boolean isZero(int[] arr) {
        return arr.length == 1 && arr[0] == 0;
    }

    private static int[] normalize(int[] arr) {

        for (int i = 0; i < arr.length; ++i) {
            if (arr[i] >= 10) {
                int carry = arr[i] / 10;
                arr[i] %= 10;
                assert i + 1 < arr.length;
                arr[i + 1] += carry;
            }
        }

        return removeLeadingZeros(arr);
    }

    private static int[] removeLeadingZeros(int[] arr) {
        int zerosCount = 0;

        for (int i = arr.length - 1; i >= 0 && arr[i] == 0; --i) {
            ++zerosCount;
        }

        return Arrays.copyOf(arr, arr.length - zerosCount);
    }

    private static String toDecimalString(int[] arr) {
        StringBuilder buf = new StringBuilder(arr.length);

        for (int i = arr.length - 1; i >= 0; --i) {
            buf.append(arr[i]);
        }

        return buf.toString();
    }
}
