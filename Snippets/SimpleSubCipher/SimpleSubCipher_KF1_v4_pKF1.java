package com.thealgorithms.ciphers;

import java.util.HashMap;
import java.util.Map;

/**
 * Simple monoalphabetic substitution cipher using a provided key.
 */
public class SubstitutionCipher {

    /**
     * Encodes the given plaintext using the provided key.
     *
     * @param plaintext the text to encode
     * @param key       the substitution key (sequence of letters)
     * @return the encoded ciphertext
     */
    public String encode(String plaintext, String key) {
        StringBuilder encodedText = new StringBuilder();
        Map<Character, Character> substitutionMap = new HashMap<>();

        char lowercaseAlphabetChar = 'a';
        char uppercaseAlphabetChar = 'A';

        String lowercaseKey = key.toLowerCase();
        String uppercaseKey = key.toUpperCase();

        for (int i = 0; i < lowercaseKey.length(); i++) {
            substitutionMap.put(lowercaseAlphabetChar++, lowercaseKey.charAt(i));
            substitutionMap.put(uppercaseAlphabetChar++, uppercaseKey.charAt(i));
        }

        for (int i = 0; i < plaintext.length(); i++) {
            char currentChar = plaintext.charAt(i);
            if (Character.isAlphabetic(currentChar)) {
                encodedText.append(substitutionMap.get(currentChar));
            } else {
                encodedText.append(currentChar);
            }
        }

        return encodedText.toString();
    }

    /**
     * Decodes the given ciphertext using the provided key.
     *
     * @param ciphertext the text to decode
     * @param key        the substitution key (sequence of letters)
     * @return the decoded plaintext
     */
    public String decode(String ciphertext, String key) {
        StringBuilder decodedText = new StringBuilder();
        Map<Character, Character> reverseSubstitutionMap = new HashMap<>();

        char lowercaseAlphabetChar = 'a';
        char uppercaseAlphabetChar = 'A';

        String lowercaseKey = key.toLowerCase();
        String uppercaseKey = key.toUpperCase();

        for (int i = 0; i < lowercaseKey.length(); i++) {
            reverseSubstitutionMap.put(lowercaseKey.charAt(i), lowercaseAlphabetChar++);
            reverseSubstitutionMap.put(uppercaseKey.charAt(i), uppercaseAlphabetChar++);
        }

        for (int i = 0; i < ciphertext.length(); i++) {
            char currentChar = ciphertext.charAt(i);
            if (Character.isAlphabetic(currentChar)) {
                decodedText.append(reverseSubstitutionMap.get(currentChar));
            } else {
                decodedText.append(currentChar);
            }
        }

        return decodedText.toString();
    }
}