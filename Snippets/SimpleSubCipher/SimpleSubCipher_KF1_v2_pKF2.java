package com.thealgorithms.ciphers;

import java.util.HashMap;
import java.util.Map;

/**
 * Implements a simple substitution cipher based on a provided key.
 *
 * <p>The key defines a mapping between the standard English alphabet
 * (A–Z, a–z) and a custom alphabet derived from the key. Both encryption
 * and decryption preserve the case of the original text and leave
 * non-alphabetic characters unchanged.
 */
public class SubstitutionCipher {

    /**
     * Encrypts the given plaintext using a substitution cipher defined by the key.
     *
     * <p>The key is normalized to:
     * <ul>
     *   <li>lowercase for mapping from 'a'–'z'</li>
     *   <li>uppercase for mapping from 'A'–'Z'</li>
     * </ul>
     *
     * @param plainText the text to encrypt
     * @param key       the substitution key; its characters are mapped in order
     * @return the encrypted text
     */
    public String encrypt(String plainText, String key) {
        Map<Character, Character> substitutionMap = buildSubstitutionMap(key);
        StringBuilder encrypted = new StringBuilder(plainText.length());

        for (int i = 0; i < plainText.length(); i++) {
            char current = plainText.charAt(i);
            if (Character.isAlphabetic(current)) {
                encrypted.append(substitutionMap.get(current));
            } else {
                encrypted.append(current);
            }
        }

        return encrypted.toString();
    }

    /**
     * Decrypts the given ciphertext using a substitution cipher defined by the key.
     *
     * <p>The key is normalized to:
     * <ul>
     *   <li>lowercase for mapping back to 'a'–'z'</li>
     *   <li>uppercase for mapping back to 'A'–'Z'</li>
     * </ul>
     *
     * @param cipherText the text to decrypt
     * @param key        the substitution key used during encryption
     * @return the decrypted (original) text
     */
    public String decrypt(String cipherText, String key) {
        Map<Character, Character> reverseSubstitutionMap = buildReverseSubstitutionMap(key);
        StringBuilder decrypted = new StringBuilder(cipherText.length());

        for (int i = 0; i < cipherText.length(); i++) {
            char current = cipherText.charAt(i);
            if (Character.isAlphabetic(current)) {
                decrypted.append(reverseSubstitutionMap.get(current));
            } else {
                decrypted.append(current);
            }
        }

        return decrypted.toString();
    }

    /**
     * Builds a character-to-character substitution map from the standard alphabet
     * to the characters of the provided key, preserving case.
     *
     * <p>Mapping:
     * <ul>
     *   <li>'a'–'z' → key in lowercase</li>
     *   <li>'A'–'Z' → key in uppercase</li>
     * </ul>
     *
     * @param key the substitution key
     * @return a map from alphabet characters to key characters
     */
    private Map<Character, Character> buildSubstitutionMap(String key) {
        Map<Character, Character> substitutionMap = new HashMap<>();

        char lowerAlphabetChar = 'a';
        char upperAlphabetChar = 'A';

        String lowerKey = key.toLowerCase();
        String upperKey = lowerKey.toUpperCase();

        for (int i = 0; i < lowerKey.length(); i++) {
            substitutionMap.put(lowerAlphabetChar++, lowerKey.charAt(i));
            substitutionMap.put(upperAlphabetChar++, upperKey.charAt(i));
        }

        return substitutionMap;
    }

    /**
     * Builds a reverse substitution map from the characters of the provided key
     * back to the standard alphabet, preserving case.
     *
     * <p>Mapping:
     * <ul>
     *   <li>key in lowercase → 'a'–'z'</li>
     *   <li>key in uppercase → 'A'–'Z'</li>
     * </ul>
     *
     * @param key the substitution key
     * @return a map from key characters to alphabet characters
     */
    private Map<Character, Character> buildReverseSubstitutionMap(String key) {
        Map<Character, Character> reverseSubstitutionMap = new HashMap<>();

        char lowerAlphabetChar = 'a';
        char upperAlphabetChar = 'A';

        String lowerKey = key.toLowerCase();
        String upperKey = lowerKey.toUpperCase();

        for (int i = 0; i < lowerKey.length(); i++) {
            reverseSubstitutionMap.put(lowerKey.charAt(i), lowerAlphabetChar++);
            reverseSubstitutionMap.put(upperKey.charAt(i), upperAlphabetChar++);
        }

        return reverseSubstitutionMap;
    }
}