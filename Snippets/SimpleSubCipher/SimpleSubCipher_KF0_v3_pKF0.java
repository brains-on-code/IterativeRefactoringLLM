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
        Map<Character, Character> cipherMap = buildCipherMap(cipherSmall, true);
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
        Map<Character, Character> cipherMap = buildCipherMap(cipherSmall, false);
        return transform(encryptedMessage, cipherMap);
    }

    private Map<Character, Character> buildCipherMap(String cipherSmall, boolean encoding) {
        Map<Character, Character> cipherMap = new HashMap<>();
        String normalizedCipher = cipherSmall.toLowerCase();
        String cipherUpper = normalizedCipher.toUpperCase();

        int limit = Math.min(ALPHABET_LENGTH, normalizedCipher.length());
        for (int i = 0; i < limit; i++) {
            char lowerPlain = (char) ('a' + i);
            char upperPlain = (char) ('A' + i);
            char lowerCipher = normalizedCipher.charAt(i);
            char upperCipher = cipherUpper.charAt(i);

            if (encoding) {
                cipherMap.put(lowerPlain, lowerCipher);
                cipherMap.put(upperPlain, upperCipher);
            } else {
                cipherMap.put(lowerCipher, lowerPlain);
                cipherMap.put(upperCipher, upperPlain);
            }
        }

        return cipherMap;
    }

    private String transform(String text, Map<Character, Character> cipherMap) {
        StringBuilder result = new StringBuilder(text.length());

        for (char ch : text.toCharArray()) {
            result.append(Character.isAlphabetic(ch) ? cipherMap.getOrDefault(ch, ch) : ch);
        }

        return result.toString();
    }
}