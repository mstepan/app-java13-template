package com.max.app.crypto;

final class CryptoUtils {

    private static final char[] HEX_DIGITS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    private static final int HEX_DIGIT_MASK = 0x0F;

    private CryptoUtils() {
        throw new AssertionError("Can't instantiate utility only class");
    }

    static String toHex(byte[] data) {
        StringBuilder buf = new StringBuilder(2 * data.length + (data.length / 4) + 1);

        for (byte singleValue : data) {
            buf.append(HEX_DIGITS[(singleValue >> 4) & HEX_DIGIT_MASK]);
            buf.append(HEX_DIGITS[singleValue & HEX_DIGIT_MASK]);
        }

        return buf.toString();
    }
}
