package com.max.app.erickson.dynamic;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class CoinChangeTest {

    private static final Logger LOG = LoggerFactory.getLogger(CoinChangeTest.class);

    @Test
    public void changeMoney() {

        int[] coins = {1, 4, 7, 13, 28, 52, 91, 365};

        for (int amount = 20; amount <= 2000; ++amount) {
            int[] dynamicRes = CoinChange.changeMoney(amount, coins).orElse(new int[]{});
            int[] greedyRes = CoinChange.changeMoneyGreedy(amount, coins).orElse(new int[]{});

            assertThat(sum(dynamicRes)).isEqualTo(sum(greedyRes));
            assertThat(dynamicRes.length).isLessThanOrEqualTo(greedyRes.length);

//            if (dynamicRes.length < greedyRes.length) {
//                LOG.info("greedy failed for amount = " + amount);
//                LOG.info("dynamic: " + Arrays.toString(dynamicRes));
//                LOG.info("greedy: " + Arrays.toString(greedyRes));
//
//            }
        }
    }

    private static int sum(int[] arr) {
        assert arr != null;
        return Arrays.stream(arr).sum();
    }
}
