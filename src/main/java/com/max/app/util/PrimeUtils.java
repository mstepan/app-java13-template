package com.max.app.util;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public final class PrimeUtils {

    private static final Random RAND = ThreadLocalRandom.current();

    private static final Set<Integer> PRIMES_BELOW_100 = new HashSet<>();

    static {
        PRIMES_BELOW_100.add(2);
        PRIMES_BELOW_100.add(3);
        PRIMES_BELOW_100.add(5);
        PRIMES_BELOW_100.add(7);
        PRIMES_BELOW_100.add(11);
        PRIMES_BELOW_100.add(13);
        PRIMES_BELOW_100.add(17);
        PRIMES_BELOW_100.add(19);
        PRIMES_BELOW_100.add(23);
        PRIMES_BELOW_100.add(29);
        PRIMES_BELOW_100.add(31);
        PRIMES_BELOW_100.add(37);
        PRIMES_BELOW_100.add(41);
        PRIMES_BELOW_100.add(43);
        PRIMES_BELOW_100.add(47);
        PRIMES_BELOW_100.add(53);
        PRIMES_BELOW_100.add(59);
        PRIMES_BELOW_100.add(61);
        PRIMES_BELOW_100.add(67);
        PRIMES_BELOW_100.add(71);
        PRIMES_BELOW_100.add(73);
        PRIMES_BELOW_100.add(79);
        PRIMES_BELOW_100.add(83);
        PRIMES_BELOW_100.add(89);
        PRIMES_BELOW_100.add(97);
    }

    public static boolean isPrime(int value) {

        // even value, not prime
        if ((value & 1) == 0) {
            return value == 2;
        }

        // check small primes
        if (value <= 100) {
            return PRIMES_BELOW_100.contains(value);
        }

        // use Ferma's little theorem to check for prime
        for (int i = 0; i < 10; ++i) {

            int a = 2 + RAND.nextInt(value - 2);

            int res = modExp(a, value - 1, value);
            if (res != 1) {
                // composite number
                return false;
            }
        }

        // probably prime
        return true;
    }

    // time: log(N), where N - value
    // space: O(logN), because recursion is used
    private static int modExp(int value, int exp, int mod) {

        if (exp == 0) {
            return 1;
        }

        if (exp == 1) {
            return value % mod;
        }

        if (exp == 2) {
            return (value * value) % mod;
        }

        // even 'exp'
        if ((exp & 1) == 0) {
            int res = modExp(value, exp >> 1, mod);
            return (res * res) % mod;
        }

        // odd 'exp'
        return (value * modExp(value, exp - 1, mod)) % mod;
    }

    private PrimeUtils() {
        throw new AssertionError("Can't instantiate utility-only class");
    }
}
