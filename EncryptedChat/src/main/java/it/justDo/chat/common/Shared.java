package it.justDo.chat.common;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Pattern;

public class Shared {

    public static final String IP = "127.0.0.1";
    public static final int PORT = 9191;
    public static final String CHARSET_NAME = "UTF-8";

    public static String getSHA256(String input, String salt) {
        try {
            byte[] byteSalt = salt.getBytes(CHARSET_NAME);
            byte[] byteInput = input.getBytes(CHARSET_NAME);
            return String.format("%064x", new BigInteger(1, getSHA256Raw(byteInput, byteSalt, 10_000)));
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "error";
    }

    public static String getSHA256(String input) {
        return getSHA256(input, RandomString.ofSize(64));
    }

    private static byte[] getSHA256Raw(byte[] input, byte[] salt, int iterations) throws NoSuchAlgorithmException {
        if (iterations < 1) {
            return new byte[0];
        }
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        digest.reset();
        digest.update(salt);
        byte[] output = digest.digest(input);
        for (int i = 0; i < iterations; i++) {
            digest.reset();
            output = digest.digest(output);
        }
        return output;
    }

    public static boolean isInPortRange(int port) {
        return port > 0 && port < 65535;
    }

    public static boolean isIP(String ip) {
        Pattern pattern = Pattern.compile("^(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$");
        return pattern.matcher(ip).matches();
    }
}
