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
        StringBuilder ciphertextBuilder = new StringBuilder();
        Map<Character, Character> substitutionMap = new HashMap<>();

        char lowercaseAlphabet = 'a';
        char uppercaseAlphabet = 'A';

        String lowercaseKey = key.toLowerCase();
        String uppercaseKey = key.toUpperCase();

        for (int index = 0; index < lowercaseKey.length(); index++) {
            substitutionMap.put(lowercaseAlphabet++, lowercaseKey.charAt(index));
            substitutionMap.put(uppercaseAlphabet++, uppercaseKey.charAt(index));
        }

        for (int index = 0; index < plaintext.length(); index++) {
            char currentCharacter = plaintext.charAt(index);
            if (Character.isAlphabetic(currentCharacter)) {
                ciphertextBuilder.append(substitutionMap.get(currentCharacter));
            } else {
                ciphertextBuilder.append(currentCharacter);
            }
        }

        return ciphertextBuilder.toString();
    }

    /**
     * Decodes the given ciphertext using the provided key.
     *
     * @param ciphertext the text to decode
     * @param key        the substitution key (sequence of letters)
     * @return the decoded plaintext
     */
    public String decode(String ciphertext, String key) {
        StringBuilder plaintextBuilder = new StringBuilder();
        Map<Character, Character> reverseSubstitutionMap = new HashMap<>();

        char lowercaseAlphabet = 'a';
        char uppercaseAlphabet = 'A';

        String lowercaseKey = key.toLowerCase();
        String uppercaseKey = key.toUpperCase();

        for (int index = 0; index < lowercaseKey.length(); index++) {
            reverseSubstitutionMap.put(lowercaseKey.charAt(index), lowercaseAlphabet++);
            reverseSubstitutionMap.put(uppercaseKey.charAt(index), uppercaseAlphabet++);
        }

        for (int index = 0; index < ciphertext.length(); index++) {
            char currentCharacter = ciphertext.charAt(index);
            if (Character.isAlphabetic(currentCharacter)) {
                plaintextBuilder.append(reverseSubstitutionMap.get(currentCharacter));
            } else {
                plaintextBuilder.append(currentCharacter);
            }
        }

        return plaintextBuilder.toString();
    }
}