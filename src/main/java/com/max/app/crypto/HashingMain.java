package com.max.app.crypto;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public final class HashingMain {

    private static final Logger LOG = LoggerFactory.getLogger(HashingMain.class);

    public static void main(String[] args) throws Exception {

        String str1 = "The quick brown fox jumps over the lazy dog";
        String str2 = "The quick brown fox jumps over the lazy dog";

        LOG.info(cryptoHash(str1));
        LOG.info(cryptoHash(str2));

        LOG.info("HashingMain done. java version {}", System.getProperty("java.version"));
    }

    private static final String HASH_ALGORITHM = "SHA-256";

    private static String cryptoHash(String str) {
        try {
            MessageDigest digest = MessageDigest.getInstance(HASH_ALGORITHM);
            byte[] hash = digest.digest(str.getBytes());

            return toHex(hash);
        }
        catch (NoSuchAlgorithmException ex) {
            throw new IllegalStateException(ex);
        }
    }

    private static final char[] HEX_DIGITS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    private static final int HEX_DIGIT_MASK = 0x0F;

    private static String toHex(byte[] data) {
        StringBuilder buf = new StringBuilder(2 * data.length + (data.length / 4) + 1);

        for (int i = 0; i < data.length; ++i) {

            if (i != 0 && (i % 4) == 0) {
                buf.append(" ");
            }

            int singleValue = data[i];

            buf.append(HEX_DIGITS[(singleValue >> 4) & HEX_DIGIT_MASK]);
            buf.append(HEX_DIGITS[singleValue & HEX_DIGIT_MASK]);
        }

        return buf.toString();
    }

}
