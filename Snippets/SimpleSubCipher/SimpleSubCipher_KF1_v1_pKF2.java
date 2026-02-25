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
public class Class1 {

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
    public String method1(String plainText, String key) {
        StringBuilder encrypted = new StringBuilder();

        // Map from standard alphabet characters to key characters (case-sensitive)
        Map<Character, Character> substitutionMap = new HashMap<>();

        char lowerAlphabetChar = 'a';
        char upperAlphabetChar = 'A';

        key = key.toLowerCase();
        String upperKey = key.toUpperCase();

        // Build mapping: a-z -> key (lowercase), A-Z -> key (uppercase)
        for (int i = 0; i < key.length(); i++) {
            substitutionMap.put(lowerAlphabetChar++, key.charAt(i));
            substitutionMap.put(upperAlphabetChar++, upperKey.charAt(i));
        }

        // Apply substitution to each character in the plaintext
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
    public String method2(String cipherText, String key) {
        StringBuilder decrypted = new StringBuilder();

        // Map from key characters back to standard alphabet characters (case-sensitive)
        Map<Character, Character> reverseSubstitutionMap = new HashMap<>();

        char lowerAlphabetChar = 'a';
        char upperAlphabetChar = 'A';

        key = key.toLowerCase();
        String upperKey = key.toUpperCase();

        // Build reverse mapping: key (lowercase) -> a-z, key (uppercase) -> A-Z
        for (int i = 0; i < key.length(); i++) {
            reverseSubstitutionMap.put(key.charAt(i), lowerAlphabetChar++);
            reverseSubstitutionMap.put(upperKey.charAt(i), upperAlphabetChar++);
        }

        // Apply reverse substitution to each character in the ciphertext
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
}