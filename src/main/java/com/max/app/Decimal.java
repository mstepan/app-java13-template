package com.max.app;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;

public final class Decimal {

    private final int sign;
    private final int[] arr;

    public static Decimal positive(int[] arr) {
        return new Decimal(1, arr);
    }

    private Decimal(int sign, int[] arr) {
        checkArgument(sign == 1 || sign == -1, "Incorrect 'sign' value detected");
        checkArgument(arr != null, "Can't create Decimal from null aray");
        this.sign = sign;
        // make defensive copy
        this.arr = Arrays.copyOf(arr, arr.length);
    }

    public Decimal mul(Decimal other) {

        //TODO:

        return null;
    }

    public Decimal add(Decimal other) {

        int cmpRes = cmpAbsValues(this, other);

        if (cmpRes == 0) {
            //TODO: both values are equals by ABS
            throw new IllegalStateException("Not implemented yet");
        }

        int resSign = cmpRes > 0 ? sign : other.sign;

        return new Decimal(resSign, addAbsValues(this.arr, other.arr));
    }

    public Decimal negative() {
        return new Decimal((-1) * sign, arr);
    }

    public Decimal sub(Decimal other) {
        return add(other.negative());
    }

    public int[] toDecimalArray() {
        return Arrays.copyOf(arr, arr.length);
    }

    private static int cmpAbsValues(Decimal cur, Decimal other) {

        if (cur.arr.length > other.arr.length) {
            return 1;
        }
        else if (cur.arr.length < other.arr.length) {
            return -1;
        }

        // both arrays size are equal
        int[] arr = cur.arr;
        int[] otherArr = other.arr;

        for (int i = arr.length - 1; i >= 0; --i) {
            if (arr[i] > otherArr[i]) {
                return 1;
            }
            else if (arr[i] < otherArr[i]) {
                return -1;
            }
        }

        return 0;
    }

    private static int[] addAbsValues(int[] x, int[] y) {

        int maxLength = Math.max(x.length, y.length);

        List<Integer> res = new ArrayList<>(maxLength + 1);

        int carry = 0;

        for (int i = 0; i < maxLength; ++i) {

            int xDigit = i < x.length ? x[i] : 0;
            int yDigit = i < y.length ? y[i] : 0;

            int digitSum = xDigit + yDigit + carry;
            res.add(digitSum % 10);
            carry = digitSum / 10;
        }

        if (carry != 0) {
            res.add(carry);
        }

        return toIntArr(res);
    }

    private static int[] toIntArr(List<Integer> resList) {
        int[] arr = new int[resList.size()];

        for (int i = 0; i < arr.length; ++i) {
            arr[i] = resList.get(i);
        }
        return arr;
    }

}
