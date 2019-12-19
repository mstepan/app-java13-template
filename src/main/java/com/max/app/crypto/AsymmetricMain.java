package com.max.app.crypto;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import java.security.KeyPair;
import java.security.KeyPairGenerator;

public final class AsymmetricMain {

    private static final Logger LOG = LoggerFactory.getLogger(AsymmetricMain.class);

    public static void main(String[] args) throws Exception {

        /*
         * With big text size we will receive the following exception: 'Data must not be longer than 117 bytes'
         */
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(1024);

        KeyPair keyPair = keyGen.generateKeyPair();

        Cipher cipher = Cipher.getInstance("RSA");

        // public key for encryption
        cipher.init(Cipher.ENCRYPT_MODE, keyPair.getPublic());

        String text = "Devoxx!!".repeat(10);

        LOG.info("original text: {}", text);

        byte[] encyptedData = cipher.doFinal(text.getBytes());

        LOG.info("encrypted text: {}", CryptoUtils.toHex(encyptedData));

        // private key for decryption
        cipher.init(Cipher.DECRYPT_MODE, keyPair.getPrivate());

        String decryptedText = new String(cipher.doFinal(encyptedData));

        LOG.info("decrypted text: {}", decryptedText);

        LOG.info("AsymmetricMain done. java version {}", System.getProperty("java.version"));
    }
}
