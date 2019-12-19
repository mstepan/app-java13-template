package com.max.app.crypto;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public final class SymmetricMain {

    private static final Logger LOG = LoggerFactory.getLogger(SymmetricMain.class);

    public static void main(String[] args) throws Exception {

        // Use initialization vector to prevent repeated text to be encoded the same way
        IvParameterSpec ivParameterSpec = createInitializationVector();

        SecretKey key = generateKey();

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
        cipher.init(Cipher.ENCRYPT_MODE, key, ivParameterSpec);

        String text = "Devoxx!!".repeat(10);

        LOG.info("base text: {}", text);
        byte[] encryptedData = cipher.doFinal(text.getBytes());

        LOG.info("encrypted text: {}", CryptoUtils.toHex(encryptedData));

        cipher.init(Cipher.DECRYPT_MODE, key, ivParameterSpec);
        String decryptedText = new String(cipher.doFinal(encryptedData));

        LOG.info("decrypted text: {}", decryptedText);

        LOG.info("SymmetricMain done. java version {}", System.getProperty("java.version"));
    }

    private static IvParameterSpec createInitializationVector() {
        try {
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            byte[] initializationVector = new byte[16];
            random.nextBytes(initializationVector);
            return new IvParameterSpec(initializationVector);
        }
        catch (NoSuchAlgorithmException ex) {
            throw new IllegalStateException(ex);
        }
    }

    private static SecretKey generateKey() {
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance("AES");
            keyGen.init(192);

            return keyGen.generateKey();
        }
        catch (NoSuchAlgorithmException ex) {
            throw new IllegalStateException(ex);
        }
    }

}
