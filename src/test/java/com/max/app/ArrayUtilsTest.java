package com.max.app;

import com.max.app.util.ArrayUtils;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

public class ArrayUtilsTest {

    private static final Random RAND = new Random();

    @Test
    public void shuffleNormalCase() {
        for (int it = 0; it < 1000; ++it) {
            final int[] baseArr = createRandomArray(1 + RAND.nextInt(100));

            int[] arr = Arrays.copyOf(baseArr, baseArr.length);
            ArrayUtils.shuffle(arr);
            assertValidPermutationForArray(arr, baseArr);
        }
    }

    private static int[] createRandomArray(int length) {
        int[] arr = new int[length];

        for (int i = 0; i < arr.length; ++i) {
            arr[i] = RAND.nextInt();
        }
        return arr;
    }

    private static void assertValidPermutationForArray(int[] permutation, int[] arr) {

        assert permutation != null : "null 'permutation' detected";
        assert arr != null : "null 'arr' detected";

        if (permutation.length != arr.length) {
            throw new AssertionError(String.format("permutation: %s is not from array: %s",
                                                   Arrays.toString(permutation), Arrays.toString(arr)));
        }

        Map<Integer, Integer> data = toFrequencyMap(arr);

        for (int value : permutation) {

            assertThat(data).
                    as(String.format("permutation: %s is not from array: %s", Arrays.toString(permutation),
                                     Arrays.toString(arr))).
                    containsKey(value);

            int count = data.get(value);

            if (count == 1) {
                data.remove(value);
            }
            else {
                data.put(value, count - 1);
            }
        }

        assertThat(data).
                as(String.format("permutation: %s is not from array: %s", Arrays.toString(permutation), Arrays.toString(arr))).
                isEmpty();
    }

    private static Map<Integer, Integer> toFrequencyMap(int[] arr) {

        Map<Integer, Integer> data = new HashMap<>();

        for (int value : arr) {
            data.compute(value, (key, oldValue) -> oldValue == null ? 1 : oldValue + 1);
        }
        return data;
    }

}
