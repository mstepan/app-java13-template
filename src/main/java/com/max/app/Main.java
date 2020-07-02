package com.max.app;


public final class Main {

    public static void main(String[] args) {

        final int nodes = 15;
        final int edges = 144;

        System.out.println(clique(nodes, edges));

        System.out.printf("java: %s%n", System.getProperty("java.version"));
    }


    public static int clique(int nodes, int edges) {
        if (nodes >= edges) {
            return 2;
        }

        int edgesToRemove = 1;
        int leftEdges = edges - nodes;
        int cliqueSize = 2;

        for (int v = 1; v < nodes - 2 && leftEdges > 0; ++v, ++edgesToRemove) {
            if (leftEdges >= edgesToRemove) {
                ++cliqueSize;
            }

            leftEdges -= edgesToRemove;
        }

        // handle last node as corner case
        if (leftEdges == edgesToRemove - 1) {
            ++cliqueSize;
        }

        return cliqueSize;
    }

    /**
     * 3-way string quicksort.
     * <p>
     * This algorithm is better than classic quicksort,
     * if we sort strings with lot's of common prefixes (like web logs).
     */
    public static void digitalQuicksort(String[] arr) {
        digitalSortRec(arr, 0, arr.length - 1, 0);
    }

    //TODO: refactor from recursive call to iterative call with explicit stack
    private static void digitalSortRec(String[] arr, int from, int to, int digit) {
        final int size = to - from + 1;
        if (size < 2) {
            return;
        }

        char pivot = charAt(arr[from], digit);
        int lower = from - 1;
        int eq = from;

        for (int i = from + 1; i <= to; ++i) {
            char ch = charAt(arr[i], digit);

            if (ch < pivot) {
                swap(arr, eq + 1, i);
                ++eq;
                swap(arr, eq, lower + 1);
                ++lower;
            }
            else if (ch == pivot) {
                swap(arr, eq + 1, i);
                ++eq;
            }
        }

        // sort less part
        digitalSortRec(arr, from, lower, digit);

        // sort equal part using 'digit+1', if left any digits
        if (pivot != Character.MIN_VALUE) {
            digitalSortRec(arr, lower + 1, eq, digit + 1);
        }

        // sort greater part
        digitalSortRec(arr, eq + 1, to, digit);
    }

    private static <U> void swap(U[] arr, int from, int to) {
        U temp = arr[from];
        arr[from] = arr[to];
        arr[to] = temp;
    }

    private static char charAt(String str, int digit) {
        if (digit >= str.length()) {
            return Character.MIN_VALUE;
        }

        return str.charAt(digit);
    }
}

