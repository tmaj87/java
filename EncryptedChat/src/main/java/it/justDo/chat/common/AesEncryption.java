package it.justDo.chat.common;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

public class AesEncryption {

    private static final int KEY_LENGTH = 16; // TODO 24, 32 - Java Cryptography Extension (JCE) Unlimited Strength Jurisdiction Policy
    private SecretKey secretKey = null;
    private Cipher cipher;
    private final Log4j log4j = new Log4j(this);

    public ToServerMessage encrypt(String string) {
        ToServerMessage encrypted = null;
        try {
            checkKey();
            byte[] byteString = string.getBytes(Shared.CHARSET_NAME);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            encrypted = new ToServerMessage(cipher.doFinal(byteString), cipher.getIV());
        } catch (Exception e) {
            log4j.INFO(e.getMessage());
        }
        return encrypted;
    }

    public String decrypt(byte[] message, byte[] vector) {
        String decrypted;
        try {
            checkKey();
            cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(vector));
            decrypted = new String(cipher.doFinal(message), Shared.CHARSET_NAME);
        } catch (Exception e) {
            decrypted = "***";
        }
        return decrypted;
    }

    private void checkKey() throws Exception {
        if (secretKey == null) {
            throw new Exception("Key is not present.");
        }
    }

    public void setKey(String string) {
        int stringLength = string.length();
        if (stringLength < KEY_LENGTH) {
            StringBuilder tempString = new StringBuilder();
            for (int i = 0; i < KEY_LENGTH - stringLength; i++) {
                tempString.append(string.charAt(i * i % stringLength));
            }
            string += tempString;
        } else if (stringLength > KEY_LENGTH) {
            string = string.substring(0, KEY_LENGTH);
        }
        try {
            byte[] keyBytes = string.getBytes(Shared.CHARSET_NAME);
            secretKey = new SecretKeySpec(keyBytes, "AES");
            cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | UnsupportedEncodingException e) {
            log4j.WARN(e.getMessage());
        }
    }
}
