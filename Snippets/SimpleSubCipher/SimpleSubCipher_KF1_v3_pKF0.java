package com.thealgorithms.ciphers;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple substitution cipher that maps the standard English alphabet
 * to a custom key and vice versa.
 */
public class SubstitutionCipher {

    private static final int ALPHABET_LENGTH = 26;

    /**
     * Encodes the given plaintext using the provided key.
     *
     * @param plaintext the text to encode
     * @param key       a 26-character string representing the substitution alphabet
     * @return the encoded text
     */
    public String encode(String plaintext, String key) {
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
    public String decode(String ciphertext, String key) {
        Map<Character, Character> decodeMap = buildDecodeMap(key);
        return transform(ciphertext, decodeMap);
    }

    private Map<Character, Character> buildEncodeMap(String key) {
        Map<Character, Character> map = new HashMap<>();
        String lowerKey = key.toLowerCase();
        String upperKey = key.toUpperCase();

        int limit = Math.min(ALPHABET_LENGTH, key.length());
        for (int i = 0; i < limit; i++) {
            char lowerAlphabetChar = (char) ('a' + i);
            char upperAlphabetChar = (char) ('A' + i);
            map.put(lowerAlphabetChar, lowerKey.charAt(i));
            map.put(upperAlphabetChar, upperKey.charAt(i));
        }

        return map;
    }

    private Map<Character, Character> buildDecodeMap(String key) {
        Map<Character, Character> map = new HashMap<>();
        String lowerKey = key.toLowerCase();
        String upperKey = key.toUpperCase();

        int limit = Math.min(ALPHABET_LENGTH, key.length());
        for (int i = 0; i < limit; i++) {
            char lowerAlphabetChar = (char) ('a' + i);
            char upperAlphabetChar = (char) ('A' + i);
            map.put(lowerKey.charAt(i), lowerAlphabetChar);
            map.put(upperKey.charAt(i), upperAlphabetChar);
        }

        return map;
    }

    private String transform(String text, Map<Character, Character> substitutionMap) {
        StringBuilder result = new StringBuilder(text.length());

        for (char currentChar : text.toCharArray()) {
            Character mappedChar = substitutionMap.get(currentChar);
            if (mappedChar != null) {
                result.append(mappedChar);
            } else {
                result.append(currentChar);
            }
        }

        return result.toString();
    }
}