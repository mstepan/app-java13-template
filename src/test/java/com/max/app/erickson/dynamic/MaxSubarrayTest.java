package com.max.app.erickson.dynamic;

import org.junit.Test;

import java.util.Arrays;
import java.util.Random;

import static com.max.app.erickson.dynamic.MaxSubarray.findMaxSum;
import static com.max.app.erickson.dynamic.MaxSubarray.findMaxSumBruteforce;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


public class MaxSubarrayTest {

    @Test
    public void findMaxSumNormalCase() {
        int[] arr = {-3, 8, 4, -9, -5, 12, 6, -1, 0, 8, -5};

        assertThat(findMaxSum(arr)).as("dynamic programming 'findMaxSum' failed").isEqualTo(25);
        assertThat(findMaxSumBruteforce(arr)).as("bruteforce 'findMaxSumBruteforce' failed").isEqualTo(25);
    }

    @Test
    public void findMaxSumCheckForRandomArrays() {

        for (int it = 0; it < 1000; ++it) {
            int[] arr = generateRandomArray();

            assertThat(findMaxSum(arr)).as("dynamic programming values is different from bruteforce for array %s",
                                           Arrays.toString(arr)).
                    isEqualTo(findMaxSumBruteforce(arr));

        }

    }

    private static final Random RAND = new Random();

    private static int[] generateRandomArray() {
        int[] arr = new int[10 + RAND.nextInt(1000)];

        for (int i = 0; i < arr.length; ++i) {
            arr[i] = -1000 + RAND.nextInt(2000);
        }

        return arr;
    }
}
