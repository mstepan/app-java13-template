package com.max.app.crypto;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public final class SymmetricMain {

    private static final Logger LOG = LoggerFactory.getLogger(SymmetricMain.class);

    public static void main(String[] args) throws Exception {

        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(256);

        SecretKey key = keyGen.generateKey();

        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
        cipher.init(Cipher.ENCRYPT_MODE, key);

        String text = "hello world";

        LOG.info("base text: {}", text);
        byte[] encryptedData = cipher.doFinal(text.getBytes());

        LOG.info("encrypted text: {}", toHex(encryptedData));

        cipher.init(Cipher.DECRYPT_MODE, key);
        String decryptedText = new String(cipher.doFinal(encryptedData));

        LOG.info("decrypted text: {}", decryptedText);

        LOG.info("SymmetricMain done. java version {}", System.getProperty("java.version"));
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
