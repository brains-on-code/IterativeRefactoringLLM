package com.thealgorithms.ciphers;

import java.util.HashMap;
import java.util.Map;

public class SimpleSubCipher {

    public String encode(String message, String cipherSmall) {
        Map<Character, Character> cipherMap = buildEncodeMap(cipherSmall);
        return transform(message, cipherMap);
    }

    public String decode(String encryptedMessage, String cipherSmall) {
        Map<Character, Character> cipherMap = buildDecodeMap(cipherSmall);
        return transform(encryptedMessage, cipherMap);
    }

    private Map<Character, Character> buildEncodeMap(String cipherSmall) {
        Map<Character, Character> cipherMap = new HashMap<>();
        char lower = 'a';
        char upper = 'A';

        String lowerCipher = cipherSmall.toLowerCase();
        String upperCipher = lowerCipher.toUpperCase();

        for (int i = 0; i < lowerCipher.length(); i++) {
            cipherMap.put(lower++, lowerCipher.charAt(i));
            cipherMap.put(upper++, upperCipher.charAt(i));
        }

        return cipherMap;
    }

    private Map<Character, Character> buildDecodeMap(String cipherSmall) {
        Map<Character, Character> cipherMap = new HashMap<>();
        char lower = 'a';
        char upper = 'A';

        String lowerCipher = cipherSmall.toLowerCase();
        String upperCipher = lowerCipher.toUpperCase();

        for (int i = 0; i < lowerCipher.length(); i++) {
            cipherMap.put(lowerCipher.charAt(i), lower++);
            cipherMap.put(upperCipher.charAt(i), upper++);
        }

        return cipherMap;
    }

    private String transform(String text, Map<Character, Character> cipherMap) {
        StringBuilder result = new StringBuilder(text.length());

        for (char ch : text.toCharArray()) {
            if (Character.isAlphabetic(ch)) {
                result.append(cipherMap.getOrDefault(ch, ch));
            } else {
                result.append(ch);
            }
        }

        return result.toString();
    }
}