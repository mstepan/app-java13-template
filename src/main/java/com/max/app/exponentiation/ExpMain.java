package com.max.app.exponentiation;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

public final class ExpMain {

    private static final Logger LOG = LoggerFactory.getLogger(ExpMain.class);

    /*
    Given a string, find the first non-repeating character in it. For example, if the input
    string is "GeeksforGeeks", then output should be 'f' and if input string is "GeekQuiz", then output should be 'G'.
    */
    public static void main(String[] args) throws Exception {


//        double v1 = -50.0;
//        double v2 = -50.0;
//        double v3 = -50.0;
//        double v4 = -50.0;
//
//        boolean res = isSame(v1, v2, v3, v4);
//
//        if( res ){
//            LOG.info("Same");
//        }
//        else {
//            LOG.info("Not same, res: {}, ulp: {}", res, Math.ulp(v1));
//        }

        final double percentageThreshold = 0.0000000001;

        Random rand = new Random();

        for (int it = 0; it < 1_000_000; ++it) {

            int x = -100 + rand.nextInt(200);
            int n = -10 + rand.nextInt(20);

            double expectedRes = Math.pow(x, n);
            double actual1 = power(x, n);
            double actual2 = fastPower(x, n, true);
            double actual3 = fastPower(x, n, false);

            if (!isSame(expectedRes, actual1, actual2, actual3, percentageThreshold)) {
                LOG.info("{} ^ {}", x, n);
                LOG.info("expected: {}", Math.pow(x, n));

                LOG.info("actual1:  {}", power(x, n));
                LOG.info("actual2:  {}", fastPower(x, n, true));
                LOG.info("actual3:  {}", fastPower(x, n, false));

                LOG.info("{}", Math.abs(expectedRes / actual1));
            }
        }

        LOG.info("Main done. java version {}", System.getProperty("java.version"));
    }

    private static boolean isSame(double expected, double actual1, double actual2, double actual3, double exp) {

        if (Double.isInfinite(expected)) {
            return Double.isInfinite(actual1) && Double.isInfinite(actual2) && Double.isInfinite(actual3);
        }

        if (Double.compare(expected, 0.0) == 0) {
            return Double.compare(actual1, 0.0) == 0 &&
                    Double.compare(actual2, 0.0) == 0 &&
                    Double.compare(actual3, 0.0) == 0;
        }

        return (1.0 - Math.abs(expected / actual1)) < exp &&
                (1.0 - Math.abs(expected / actual2)) < exp &&
                (1.0 - Math.abs(expected / actual3)) < exp;
    }

    /**
     * Compute X^n, using N multiplications.
     * Use recursive algorithm or iterative.
     */
    private static double fastPower(int x, int n, boolean recursive) {
        if (n == 0) {
            return 1;
        }

        int nAbs = Math.abs(n);

        double res;
        if (recursive) {
            res = fastRecPower(x, nAbs);
        }
        else {
            res = fastIterativePower(x, nAbs);
        }

        return n < 0 ? 1.0 / res : res;
    }

    private static double fastIterativePower(int x, int n) {

        int pow = n;
        double res = x;
        double additionalMul = 1.0;

        while (pow != 1) {

            // odd case
            if ((pow & 1) != 0) {
                additionalMul *= res;
            }

            res *= res;
            pow >>= 1;
        }

        return res * additionalMul;
    }

    private static double fastRecPower(int x, int n) {
        if (n == 0) {
            return 1.0;
        }

        double dx = fastRecPower(x, n >> 1);

        // odd case
        if ((n & 1) != 0) {
            return dx * dx * x;
        }

        return dx * dx;
    }

    /**
     * Compute X^n, using N multiplications.
     */
    private static double power(int x, int n) {
        if (n == 0) {
            return 1;
        }

        int nAbs = Math.abs(n);

        double res = x;

        for (int i = 2; i <= nAbs; ++i) {
            res *= x;
        }

        if (n < 0) {
            return 1.0 / res;
        }

        return res;
    }


}
