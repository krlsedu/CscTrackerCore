package com.csctracker.service;

import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@Component
public class CryptoService {
    private String cryptoKey;

    public String decrypt(String text) throws Exception {
        var cipher = getCipher(Cipher.DECRYPT_MODE);
        var decoder = Base64.getDecoder();
        byte[] encrypted = decoder.decode(text);
        byte[] decrypted = cipher.doFinal(encrypted);
        return new String(decrypted);
    }

    public String encrypt(String text) throws Exception {
        var cipher = getCipher(Cipher.ENCRYPT_MODE);
        byte[] encrypted = cipher.doFinal(text.getBytes());
        var encoder = Base64.getEncoder();
        return encoder.encodeToString(encrypted);
    }

    private Cipher getCipher(int mode) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException {
        if (cryptoKey == null) {
            cryptoKey = System.getenv("CRYPTO_KEY");
        }
        var aesKey = new SecretKeySpec(cryptoKey.getBytes(), "AES");
        var cipher = Cipher.getInstance("AES");
        cipher.init(mode, aesKey);
        return cipher;
    }
}
