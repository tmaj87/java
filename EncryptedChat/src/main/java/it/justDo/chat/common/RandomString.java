package it.justDo.chat.common;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class RandomString {

    public static String ofSize(int size) {
        if (size < 1) {
            return "";
        }
        final String specialCharacters = "?!@.,():;*&^%$#|-_=+";
        StringBuilder randomString = new StringBuilder();
        Random random = new Random();
        while (randomString.length() < size) {
            switch (random.nextInt(4)) {
                case 0: // numbers
                    randomString.append(random.nextInt(10));
                    break;
                case 1: // small letters
                    randomString.append((char) (97 + random.nextInt(26)));
                    break;
                case 2: // big letters
                    randomString.append((char) (65 + random.nextInt(26)));
                    break;
                case 3: // special chars
                    randomString.append(specialCharacters.charAt(random.nextInt(specialCharacters.length())));
                    break;
            }
        }
        return randomString.toString();
    }

    public static String ofMessage() {
        Map<Integer, String> responseMap = new HashMap<>();
        responseMap.put(1, "Blurp..!");
        responseMap.put(3, "Hahaha.");
        responseMap.put(5, "You fool!");
        responseMap.put(7, "Support author by clicking on this link: http://google.com");
        responseMap.put(9, "USA government did 9/11 job.");
        responseMap.put(11, "");
        responseMap.put(13, "Python rocks or sucks? Vote now.");
        String response;
        if ((response = responseMap.get(new Random().nextInt(15))) != null) {
            return response;
        }
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss"));
    }
}
