package com.max.app.erickson.dynamic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.google.common.base.Preconditions.checkArgument;

public final class CoinChange {

    private CoinChange() {
        throw new AssertionError("Can't instantiate utility only class");
    }

    public static Optional<int[]> changeMoneyGreedy(int amount, int[] coins) {
        checkArgument(amount >= 0, "Negative 'amount' passed: " + amount);
        checkArgument(coins != null, "null 'coins' array passed");

        Arrays.sort(coins);

        int index = coins.length - 1;
        int money = amount;

        List<Integer> change = new ArrayList<>();

        while (money != 0 && index >= 0) {
            if (coins[index] > money) {
                --index;
            }
            else {
                change.add(coins[index]);
                money -= coins[index];
            }
        }

        int[] res = new int[change.size()];

        for (int i = 0; i < res.length; ++i) {
            res[i] = change.get(i);
        }

        Arrays.sort(res);

        return Optional.of(res);
    }

    public static Optional<int[]> changeMoney(int amount, int[] coins) {

        checkArgument(amount >= 0, "Negative 'amount' passed: " + amount);
        checkArgument(coins != null, "null 'coins' array passed");

        if (amount == 0) {
            return Optional.of(new int[]{});
        }

        int[][] opt = new int[coins.length + 1][amount + 1];

        final int rows = opt.length;
        final int cols = opt[0].length;

        for (int col = 1; col < cols; ++col) {
            opt[0][col] = Integer.MAX_VALUE;
        }

        if (opt[rows - 1][cols - 1] == Integer.MAX_VALUE) {
            return Optional.empty();
        }

        for (int row = 1; row < rows; ++row) {
            for (int col = 1; col < cols; ++col) {
                final int cur = coins[row - 1];
                final int money = col;

                if (cur > money) {
                    // skip cur
                    opt[row][col] = opt[row - 1][money];
                }
                else {
                    opt[row][col] = Math.min(
                            opt[row - 1][money], // without cur
                            1 + opt[row][money - cur] // with cur
                    );
                }
            }
        }

        if (opt[rows - 1][cols - 1] == Integer.MAX_VALUE) {
            return Optional.empty();
        }

        return Optional.of(rebuildSolution(opt, coins));
    }

    private static int[] rebuildSolution(int[][] opt, int[] coins) {

        assert opt != null : "null 'opt' detected";
        assert coins != null : "null 'coins' detected";

        int row = opt.length - 1;
        int col = opt[row].length - 1;

        List<Integer> res = new ArrayList<>();

        while (row != 0 && col != 0) {

            int curCoin = coins[row - 1];
            int money = col;

            if (opt[row - 1][col] == opt[row][col]) {
                // skip current element
                --row;
            }
            else {
                int newCol = money - curCoin;

                if (opt[row][newCol] + 1 == opt[row][col]) {
                    // use current element
                    res.add(curCoin);
                }

                col = newCol;
            }

        }

        int[] arr = new int[res.size()];

        for (int i = 0; i < arr.length; ++i) {
            arr[i] = res.get(i);
        }

        Arrays.sort(arr);

        return arr;
    }

}
