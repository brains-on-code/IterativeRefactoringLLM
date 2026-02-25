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
        Map<Character, Character> encodingMap = buildEncodingMap(cipherSmall);
        StringBuilder encoded = new StringBuilder(message.length());

        for (int i = 0; i < message.length(); i++) {
            char ch = message.charAt(i);
            encoded.append(Character.isAlphabetic(ch) ? encodingMap.get(ch) : ch);
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
        Map<Character, Character> decodingMap = buildDecodingMap(cipherSmall);
        StringBuilder decoded = new StringBuilder(encryptedMessage.length());

        for (int i = 0; i < encryptedMessage.length(); i++) {
            char ch = encryptedMessage.charAt(i);
            decoded.append(Character.isAlphabetic(ch) ? decodingMap.get(ch) : ch);
        }

        return decoded.toString();
    }

    /**
     * Builds a character mapping for encoding:
     * plain alphabet characters (lowercase and uppercase) -> cipher characters.
     *
     * @param cipherSmall the 26-letter lowercase substitution alphabet
     * @return map from plain characters to cipher characters
     */
    private Map<Character, Character> buildEncodingMap(String cipherSmall) {
        Map<Character, Character> encodingMap = new HashMap<>(52);

        String cipherLower = cipherSmall.toLowerCase();
        String cipherUpper = cipherLower.toUpperCase();

        char plainLower = 'a';
        char plainUpper = 'A';

        for (int i = 0; i < cipherLower.length(); i++) {
            encodingMap.put(plainLower++, cipherLower.charAt(i));
            encodingMap.put(plainUpper++, cipherUpper.charAt(i));
        }

        return encodingMap;
    }

    /**
     * Builds a character mapping for decoding:
     * cipher characters (lowercase and uppercase) -> plain alphabet characters.
     *
     * @param cipherSmall the 26-letter lowercase substitution alphabet
     * @return map from cipher characters to plain characters
     */
    private Map<Character, Character> buildDecodingMap(String cipherSmall) {
        Map<Character, Character> decodingMap = new HashMap<>(52);

        String cipherLower = cipherSmall.toLowerCase();
        String cipherUpper = cipherLower.toUpperCase();

        char plainLower = 'a';
        char plainUpper = 'A';

        for (int i = 0; i < cipherLower.length(); i++) {
            decodingMap.put(cipherLower.charAt(i), plainLower++);
            decodingMap.put(cipherUpper.charAt(i), plainUpper++);
        }

        return decodingMap;
    }
}