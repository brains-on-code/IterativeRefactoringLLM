package com.thealgorithms.ciphers;

import java.util.HashMap;
import java.util.Map;

public class SimpleSubCipher {

    private static final char LOWERCASE_START = 'a';
    private static final char UPPERCASE_START = 'A';

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
        String lowerCipher = cipherSmall.toLowerCase();
        String upperCipher = lowerCipher.toUpperCase();

        char lowerChar = LOWERCASE_START;
        char upperChar = UPPERCASE_START;

        for (int i = 0; i < lowerCipher.length(); i++) {
            cipherMap.put(lowerChar++, lowerCipher.charAt(i));
            cipherMap.put(upperChar++, upperCipher.charAt(i));
        }

        return cipherMap;
    }

    private Map<Character, Character> buildDecodeMap(String cipherSmall) {
        Map<Character, Character> cipherMap = new HashMap<>();
        String lowerCipher = cipherSmall.toLowerCase();
        String upperCipher = lowerCipher.toUpperCase();

        char lowerChar = LOWERCASE_START;
        char upperChar = UPPERCASE_START;

        for (int i = 0; i < lowerCipher.length(); i++) {
            cipherMap.put(lowerCipher.charAt(i), lowerChar++);
            cipherMap.put(upperCipher.charAt(i), upperChar++);
        }

        return cipherMap;
    }

    private String transform(String text, Map<Character, Character> cipherMap) {
        StringBuilder result = new StringBuilder(text.length());

        for (char ch : text.toCharArray()) {
            result.append(Character.isAlphabetic(ch) ? cipherMap.getOrDefault(ch, ch) : ch);
        }

        return result.toString();
    }
}