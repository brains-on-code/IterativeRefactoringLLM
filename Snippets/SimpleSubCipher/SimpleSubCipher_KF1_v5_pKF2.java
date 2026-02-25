package com.thealgorithms.ciphers;

import java.util.HashMap;
import java.util.Map;

/**
 * Simple substitution cipher using a key-derived alphabet.
 *
 * <p>Only English letters (A–Z, a–z) are substituted; all other characters
 * are left unchanged. Letter case is preserved.</p>
 */
public class SubstitutionCipher {

    private static final int ALPHABET_LENGTH = 26;

    /**
     * Encrypts plaintext using a substitution alphabet derived from {@code key}.
     *
     * @param plainText text to encrypt
     * @param key       substitution key; its characters are used in order
     * @return encrypted text
     */
    public String encrypt(String plainText, String key) {
        Map<Character, Character> substitutionMap = buildSubstitutionMap(key);
        StringBuilder encrypted = new StringBuilder(plainText.length());

        for (int i = 0; i < plainText.length(); i++) {
            char current = plainText.charAt(i);
            encrypted.append(
                Character.isAlphabetic(current)
                    ? substitutionMap.getOrDefault(current, current)
                    : current
            );
        }

        return encrypted.toString();
    }

    /**
     * Decrypts ciphertext using the same key that was used for encryption.
     *
     * @param cipherText text to decrypt
     * @param key        substitution key used during encryption
     * @return decrypted text
     */
    public String decrypt(String cipherText, String key) {
        Map<Character, Character> reverseSubstitutionMap = buildReverseSubstitutionMap(key);
        StringBuilder decrypted = new StringBuilder(cipherText.length());

        for (int i = 0; i < cipherText.length(); i++) {
            char current = cipherText.charAt(i);
            decrypted.append(
                Character.isAlphabetic(current)
                    ? reverseSubstitutionMap.getOrDefault(current, current)
                    : current
            );
        }

        return decrypted.toString();
    }

    /**
     * Builds a map from the standard alphabet to the key-derived alphabet.
     *
     * <p>Mappings:</p>
     * <ul>
     *   <li>'a'–'z' → {@code key} in lowercase</li>
     *   <li>'A'–'Z' → {@code key} in uppercase</li>
     * </ul>
     *
     * @param key substitution key
     * @return map from alphabet characters to key characters
     */
    private Map<Character, Character> buildSubstitutionMap(String key) {
        Map<Character, Character> substitutionMap = new HashMap<>(ALPHABET_LENGTH * 2);

        String lowerKey = key.toLowerCase();
        String upperKey = lowerKey.toUpperCase();

        char lowerAlphabetChar = 'a';
        char upperAlphabetChar = 'A';

        int limit = Math.min(lowerKey.length(), ALPHABET_LENGTH);
        for (int i = 0; i < limit; i++) {
            substitutionMap.put(lowerAlphabetChar++, lowerKey.charAt(i));
            substitutionMap.put(upperAlphabetChar++, upperKey.charAt(i));
        }

        return substitutionMap;
    }

    /**
     * Builds a map from the key-derived alphabet back to the standard alphabet.
     *
     * <p>Mappings:</p>
     * <ul>
     *   <li>{@code key} in lowercase → 'a'–'z'</li>
     *   <li>{@code key} in uppercase → 'A'–'Z'</li>
     * </ul>
     *
     * @param key substitution key
     * @return map from key characters to alphabet characters
     */
    private Map<Character, Character> buildReverseSubstitutionMap(String key) {
        Map<Character, Character> reverseSubstitutionMap = new HashMap<>(ALPHABET_LENGTH * 2);

        String lowerKey = key.toLowerCase();
        String upperKey = lowerKey.toUpperCase();

        char lowerAlphabetChar = 'a';
        char upperAlphabetChar = 'A';

        int limit = Math.min(lowerKey.length(), ALPHABET_LENGTH);
        for (int i = 0; i < limit; i++) {
            reverseSubstitutionMap.put(lowerKey.charAt(i), lowerAlphabetChar++);
            reverseSubstitutionMap.put(upperKey.charAt(i), upperAlphabetChar++);
        }

        return reverseSubstitutionMap;
    }
}