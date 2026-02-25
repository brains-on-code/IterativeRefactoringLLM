package com.thealgorithms.ciphers;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple substitution cipher that maps the standard English alphabet
 * to a custom key and vice versa.
 */
public class Class1 {

    private static final int ALPHABET_LENGTH = 26;

    /**
     * Encodes the given plaintext using the provided key.
     *
     * @param plaintext the text to encode
     * @param key       a 26-character string representing the substitution alphabet
     * @return the encoded text
     */
    public String method1(String plaintext, String key) {
        Map<Character, Character> encodeMap = buildEncodeMap(key);
        return transform(plaintext, encodeMap);
    }

    /**
     * Decodes the given ciphertext using the provided key.
     *
     * @param ciphertext the text to decode
     * @param key        a 26-character string representing the substitution alphabet
     * @return the decoded text
     */
    public String method2(String ciphertext, String key) {
        Map<Character, Character> decodeMap = buildDecodeMap(key);
        return transform(ciphertext, decodeMap);
    }

    private Map<Character, Character> buildEncodeMap(String key) {
        Map<Character, Character> map = new HashMap<>();
        String lowerKey = key.toLowerCase();
        String upperKey = key.toUpperCase();

        char lowerAlphabetChar = 'a';
        char upperAlphabetChar = 'A';

        for (int i = 0; i < ALPHABET_LENGTH && i < key.length(); i++) {
            map.put(lowerAlphabetChar++, lowerKey.charAt(i));
            map.put(upperAlphabetChar++, upperKey.charAt(i));
        }

        return map;
    }

    private Map<Character, Character> buildDecodeMap(String key) {
        Map<Character, Character> map = new HashMap<>();
        String lowerKey = key.toLowerCase();
        String upperKey = key.toUpperCase();

        char lowerAlphabetChar = 'a';
        char upperAlphabetChar = 'A';

        for (int i = 0; i < ALPHABET_LENGTH && i < key.length(); i++) {
            map.put(lowerKey.charAt(i), lowerAlphabetChar++);
            map.put(upperKey.charAt(i), upperAlphabetChar++);
        }

        return map;
    }

    private String transform(String text, Map<Character, Character> substitutionMap) {
        StringBuilder result = new StringBuilder(text.length());

        for (int i = 0; i < text.length(); i++) {
            char currentChar = text.charAt(i);
            if (Character.isAlphabetic(currentChar) && substitutionMap.containsKey(currentChar)) {
                result.append(substitutionMap.get(currentChar));
            } else {
                result.append(currentChar);
            }
        }

        return result.toString();
    }
}