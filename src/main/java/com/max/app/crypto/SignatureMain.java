package com.max.app.crypto;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.Signature;

public final class SignatureMain {

    private static final Logger LOG = LoggerFactory.getLogger(SignatureMain.class);

    public static void main(String[] args) throws Exception {
        /*
         * With big text size we will receive the following exception: 'Data must not be longer than 117 bytes'
         */
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(1024);

        KeyPair keyPair = keyGen.generateKeyPair();

        Signature signature = Signature.getInstance("SHA256withRSA");

        // private key used for signature generation
        signature.initSign(keyPair.getPrivate());

        final String text = "Devoxx!!".repeat(100);

        signature.update(text.getBytes());
        byte[] signedData = signature.sign();

        LOG.info("signature: {}", CryptoUtils.toHex(signedData));

        Signature signatureVerifier = Signature.getInstance("SHA256withRSA");

        // public key used for signature verification
        signatureVerifier.initVerify(keyPair.getPublic());

        signatureVerifier.update(text.getBytes());

        boolean signatureValid = signatureVerifier.verify(signedData);

        LOG.info("signature is valid: {}", signatureValid);

        LOG.info("SignatureMain done. java version {}", System.getProperty("java.version"));
    }


}
