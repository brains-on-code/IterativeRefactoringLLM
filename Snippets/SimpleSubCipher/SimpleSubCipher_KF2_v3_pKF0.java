package com.thealgorithms.ciphers;

import java.util.HashMap;
import java.util.Map;

public class SimpleSubCipher {

    private static final char LOWERCASE_START = 'a';
    private static final char UPPERCASE_START = 'A';

    public String encode(String message, String cipherAlphabet) {
        Map<Character, Character> cipherMap = buildEncodeMap(cipherAlphabet);
        return transform(message, cipherMap);
    }

    public String decode(String encryptedMessage, String cipherAlphabet) {
        Map<Character, Character> cipherMap = buildDecodeMap(cipherAlphabet);
        return transform(encryptedMessage, cipherMap);
    }

    private Map<Character, Character> buildEncodeMap(String cipherAlphabet) {
        Map<Character, Character> cipherMap = new HashMap<>();
        String lowerCipher = cipherAlphabet.toLowerCase();
        String upperCipher = lowerCipher.toUpperCase();

        char lowerPlainChar = LOWERCASE_START;
        char upperPlainChar = UPPERCASE_START;

        for (int i = 0; i < lowerCipher.length(); i++) {
            cipherMap.put(lowerPlainChar++, lowerCipher.charAt(i));
            cipherMap.put(upperPlainChar++, upperCipher.charAt(i));
        }

        return cipherMap;
    }

    private Map<Character, Character> buildDecodeMap(String cipherAlphabet) {
        Map<Character, Character> cipherMap = new HashMap<>();
        String lowerCipher = cipherAlphabet.toLowerCase();
        String upperCipher = lowerCipher.toUpperCase();

        char lowerPlainChar = LOWERCASE_START;
        char upperPlainChar = UPPERCASE_START;

        for (int i = 0; i < lowerCipher.length(); i++) {
            cipherMap.put(lowerCipher.charAt(i), lowerPlainChar++);
            cipherMap.put(upperCipher.charAt(i), upperPlainChar++);
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