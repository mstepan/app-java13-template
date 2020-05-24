package com.max.app;


import java.util.Arrays;
import java.util.Random;
import java.util.stream.IntStream;

public final class Main {

    public static void main(String[] args) {

        for (int it = 0; it < 10_000; ++it) {

            int[] baseArr = generateRandomArray(RAND.nextInt(1_000), -10_000, 20_000);

            int[] arr1 = Arrays.copyOf(baseArr, baseArr.length);
            int[] arr2 = Arrays.copyOf(baseArr, baseArr.length);

            Arrays.sort(arr1);
            Intrasort.intrasort(arr2);

            if (!Arrays.equals(arr1, arr2)) {

                System.out.println(Arrays.toString(baseArr));
                System.out.println(Arrays.toString(arr2));

                throw new IllegalStateException("jdk sort != intrasort");
            }
        }

        System.out.printf("java: %s%n", System.getProperty("java.version"));
    }

    private static final Random RAND = new Random();

    private static int[] generateRandomArray(int length, int from, int to) {
        assert length >= 0 : "negative arr length";

        final int range = to - from + 1;

        return IntStream.range(0, length).
                map(notUsed -> from + RAND.nextInt(range)).
                toArray();
    }

}
