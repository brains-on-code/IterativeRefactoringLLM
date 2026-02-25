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

        char alphabetLowercaseChar = 'a';
        char alphabetUppercaseChar = 'A';

        String keyLowercase = key.toLowerCase();
        String keyUppercase = key.toUpperCase();

        for (int index = 0; index < keyLowercase.length(); index++) {
            substitutionMap.put(alphabetLowercaseChar++, keyLowercase.charAt(index));
            substitutionMap.put(alphabetUppercaseChar++, keyUppercase.charAt(index));
        }

        for (int index = 0; index < plaintext.length(); index++) {
            char currentChar = plaintext.charAt(index);
            if (Character.isAlphabetic(currentChar)) {
                ciphertextBuilder.append(substitutionMap.get(currentChar));
            } else {
                ciphertextBuilder.append(currentChar);
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

        char alphabetLowercaseChar = 'a';
        char alphabetUppercaseChar = 'A';

        String keyLowercase = key.toLowerCase();
        String keyUppercase = key.toUpperCase();

        for (int index = 0; index < keyLowercase.length(); index++) {
            reverseSubstitutionMap.put(keyLowercase.charAt(index), alphabetLowercaseChar++);
            reverseSubstitutionMap.put(keyUppercase.charAt(index), alphabetUppercaseChar++);
        }

        for (int index = 0; index < ciphertext.length(); index++) {
            char currentChar = ciphertext.charAt(index);
            if (Character.isAlphabetic(currentChar)) {
                plaintextBuilder.append(reverseSubstitutionMap.get(currentChar));
            } else {
                plaintextBuilder.append(currentChar);
            }
        }

        return plaintextBuilder.toString();
    }
}