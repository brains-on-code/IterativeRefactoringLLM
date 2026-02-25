package com.thealgorithms.ciphers;

import java.util.HashMap;
import java.util.Map;

/**
 * The simple substitution cipher is a cipher that has been in use for many
 * hundreds of years (an excellent history is given in Simon Singhs 'the Code
 * Book'). It basically consists of substituting every plaintext character for a
 * different ciphertext character. It differs from the Caesar cipher in that the
 * cipher alphabet is not simply the alphabet shifted, it is completely jumbled.
 */
public class SimpleSubCipher {

    private static final int ALPHABET_LENGTH = 26;

    /**
     * Encrypt text by replacing each element with its opposite character.
     *
     * @param message     the plain text message to encode
     * @param cipherSmall the substitution alphabet in lowercase (26 characters)
     * @return Encrypted message
     */
    public String encode(String message, String cipherSmall) {
        Map<Character, Character> cipherMap = buildEncodingMap(cipherSmall);
        return transform(message, cipherMap);
    }

    /**
     * Decrypt message by replacing each element with its opposite character in
     * cipher.
     *
     * @param encryptedMessage the encoded message
     * @param cipherSmall      the substitution alphabet in lowercase (26 characters)
     * @return Decoded plain text message
     */
    public String decode(String encryptedMessage, String cipherSmall) {
        Map<Character, Character> cipherMap = buildDecodingMap(cipherSmall);
        return transform(encryptedMessage, cipherMap);
    }

    private Map<Character, Character> buildEncodingMap(String cipherSmall) {
        Map<Character, Character> cipherMap = new HashMap<>();
        String normalizedCipherSmall = cipherSmall.toLowerCase();
        String cipherCapital = normalizedCipherSmall.toUpperCase();

        char lower = 'a';
        char upper = 'A';

        for (int i = 0; i < ALPHABET_LENGTH && i < normalizedCipherSmall.length(); i++) {
            cipherMap.put(lower++, normalizedCipherSmall.charAt(i));
            cipherMap.put(upper++, cipherCapital.charAt(i));
        }

        return cipherMap;
    }

    private Map<Character, Character> buildDecodingMap(String cipherSmall) {
        Map<Character, Character> cipherMap = new HashMap<>();
        String normalizedCipherSmall = cipherSmall.toLowerCase();
        String cipherCapital = normalizedCipherSmall.toUpperCase();

        char lower = 'a';
        char upper = 'A';

        for (int i = 0; i < ALPHABET_LENGTH && i < normalizedCipherSmall.length(); i++) {
            cipherMap.put(normalizedCipherSmall.charAt(i), lower++);
            cipherMap.put(cipherCapital.charAt(i), upper++);
        }

        return cipherMap;
    }

    private String transform(String text, Map<Character, Character> cipherMap) {
        StringBuilder result = new StringBuilder(text.length());

        for (char ch : text.toCharArray()) {
            if (Character.isAlphabetic(ch)) {
                result.append(cipherMap.getOrDefault(ch, ch));
            } else {
                result.append(ch);
            }
        }

        return result.toString();
    }
}