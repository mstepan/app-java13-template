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

            return CryptoUtils.toHex(hash);
        }
        catch (NoSuchAlgorithmException ex) {
            throw new IllegalStateException(ex);
        }
    }

}
