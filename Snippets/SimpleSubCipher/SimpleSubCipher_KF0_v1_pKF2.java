package com.thealgorithms.ciphers;

import java.util.HashMap;
import java.util.Map;

/**
 * Simple substitution cipher.
 *
 * <p>Each plaintext character is replaced with a corresponding ciphertext
 * character from a provided substitution alphabet. The substitution alphabet
 * must be a permutation of the 26 lowercase letters. Uppercase letters are
 * handled by applying the same substitution in uppercase form.
 */
public class SimpleSubCipher {

    /**
     * Encrypts a message using the provided substitution alphabet.
     *
     * @param message     the plaintext message to encrypt
     * @param cipherSmall the 26-letter lowercase substitution alphabet
     * @return the encrypted message
     */
    public String encode(String message, String cipherSmall) {
        Map<Character, Character> cipherMap = buildEncodingMap(cipherSmall);
        StringBuilder encoded = new StringBuilder(message.length());

        for (int i = 0; i < message.length(); i++) {
            char ch = message.charAt(i);
            encoded.append(Character.isAlphabetic(ch) ? cipherMap.get(ch) : ch);
        }

        return encoded.toString();
    }

    /**
     * Decrypts a message using the provided substitution alphabet.
     *
     * @param encryptedMessage the ciphertext message to decrypt
     * @param cipherSmall      the 26-letter lowercase substitution alphabet
     * @return the decrypted plaintext message
     */
    public String decode(String encryptedMessage, String cipherSmall) {
        Map<Character, Character> cipherMap = buildDecodingMap(cipherSmall);
        StringBuilder decoded = new StringBuilder(encryptedMessage.length());

        for (int i = 0; i < encryptedMessage.length(); i++) {
            char ch = encryptedMessage.charAt(i);
            decoded.append(Character.isAlphabetic(ch) ? cipherMap.get(ch) : ch);
        }

        return decoded.toString();
    }

    /**
     * Builds a map from plain alphabet characters to cipher characters
     * (both lowercase and uppercase).
     */
    private Map<Character, Character> buildEncodingMap(String cipherSmall) {
        Map<Character, Character> cipherMap = new HashMap<>(52);

        cipherSmall = cipherSmall.toLowerCase();
        String cipherCapital = cipherSmall.toUpperCase();

        char plainLower = 'a';
        char plainUpper = 'A';

        for (int i = 0; i < cipherSmall.length(); i++) {
            cipherMap.put(plainLower++, cipherSmall.charAt(i));
            cipherMap.put(plainUpper++, cipherCapital.charAt(i));
        }

        return cipherMap;
    }

    /**
     * Builds a map from cipher characters back to plain alphabet characters
     * (both lowercase and uppercase).
     */
    private Map<Character, Character> buildDecodingMap(String cipherSmall) {
        Map<Character, Character> cipherMap = new HashMap<>(52);

        cipherSmall = cipherSmall.toLowerCase();
        String cipherCapital = cipherSmall.toUpperCase();

        char plainLower = 'a';
        char plainUpper = 'A';

        for (int i = 0; i < cipherSmall.length(); i++) {
            cipherMap.put(cipherSmall.charAt(i), plainLower++);
            cipherMap.put(cipherCapital.charAt(i), plainUpper++);
        }

        return cipherMap;
    }
}