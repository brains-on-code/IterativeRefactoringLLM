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
        Map<Character, Character> encodeMap = buildSubstitutionMap(key, true);
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
        Map<Character, Character> decodeMap = buildSubstitutionMap(key, false);
        return transform(ciphertext, decodeMap);
    }

    /**
     * Builds a substitution map for encoding or decoding.
     *
     * @param key     the substitution key
     * @param encode  true to build an encode map, false for decode map
     * @return the substitution map
     */
    private Map<Character, Character> buildSubstitutionMap(String key, boolean encode) {
        Map<Character, Character> map = new HashMap<>();
        String lowerKey = key.toLowerCase();
        String upperKey = key.toUpperCase();

        int limit = Math.min(ALPHABET_LENGTH, key.length());
        for (int i = 0; i < limit; i++) {
            char lowerAlphabetChar = (char) ('a' + i);
            char upperAlphabetChar = (char) ('A' + i);
            char lowerKeyChar = lowerKey.charAt(i);
            char upperKeyChar = upperKey.charAt(i);

            if (encode) {
                map.put(lowerAlphabetChar, lowerKeyChar);
                map.put(upperAlphabetChar, upperKeyChar);
            } else {
                map.put(lowerKeyChar, lowerAlphabetChar);
                map.put(upperKeyChar, upperAlphabetChar);
            }
        }

        return map;
    }

    /**
     * Applies the given substitution map to the input text.
     *
     * @param text             the text to transform
     * @param substitutionMap  the character substitution map
     * @return the transformed text
     */
    private String transform(String text, Map<Character, Character> substitutionMap) {
        StringBuilder result = new StringBuilder(text.length());

        for (char currentChar : text.toCharArray()) {
            result.append(substitutionMap.getOrDefault(currentChar, currentChar));
        }

        return result.toString();
    }
}