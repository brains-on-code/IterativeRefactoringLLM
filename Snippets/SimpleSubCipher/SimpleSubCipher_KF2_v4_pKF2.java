package com.thealgorithms.ciphers;

import java.util.HashMap;
import java.util.Map;

public class SimpleSubCipher {

    private static final int ALPHABET_SIZE = 26;
    private static final int CASE_VARIANTS = 2;
    private static final int MAP_CAPACITY = ALPHABET_SIZE * CASE_VARIANTS;

    /**
     * Encodes a message using a simple substitution cipher.
     *
     * @param message   plain text to encode
     * @param cipherKey 26-character substitution for 'a'–'z'
     * @return encoded message
     */
    public String encode(String message, String cipherKey) {
        validateCipherKey(cipherKey);

        Map<Character, Character> encodeMap = buildEncodeMap(cipherKey);
        StringBuilder encoded = new StringBuilder(message.length());

        for (char ch : message.toCharArray()) {
            encoded.append(Character.isAlphabetic(ch) ? encodeMap.getOrDefault(ch, ch) : ch);
        }

        return encoded.toString();
    }

    /**
     * Decodes a message encoded with the same substitution cipher.
     *
     * @param encryptedMessage encoded message
     * @param cipherKey        26-character key used for encoding
     * @return decoded message
     */
    public String decode(String encryptedMessage, String cipherKey) {
        validateCipherKey(cipherKey);

        Map<Character, Character> decodeMap = buildDecodeMap(cipherKey);
        StringBuilder decoded = new StringBuilder(encryptedMessage.length());

        for (char ch : encryptedMessage.toCharArray()) {
            decoded.append(Character.isAlphabetic(ch) ? decodeMap.getOrDefault(ch, ch) : ch);
        }

        return decoded.toString();
    }

    /**
     * Builds a map from plain alphabet characters to cipher characters
     * for both lowercase and uppercase letters.
     */
    private Map<Character, Character> buildEncodeMap(String cipherKey) {
        Map<Character, Character> cipherMap = new HashMap<>(MAP_CAPACITY);

        String lowerKey = cipherKey.toLowerCase();
        String upperKey = lowerKey.toUpperCase();

        for (int i = 0; i < ALPHABET_SIZE; i++) {
            char lowerPlain = (char) ('a' + i);
            char upperPlain = (char) ('A' + i);

            cipherMap.put(lowerPlain, lowerKey.charAt(i));
            cipherMap.put(upperPlain, upperKey.charAt(i));
        }

        return cipherMap;
    }

    /**
     * Builds a map from cipher characters back to plain alphabet characters
     * for both lowercase and uppercase letters.
     */
    private Map<Character, Character> buildDecodeMap(String cipherKey) {
        Map<Character, Character> cipherMap = new HashMap<>(MAP_CAPACITY);

        String lowerKey = cipherKey.toLowerCase();
        String upperKey = lowerKey.toUpperCase();

        for (int i = 0; i < ALPHABET_SIZE; i++) {
            char lowerPlain = (char) ('a' + i);
            char upperPlain = (char) ('A' + i);

            cipherMap.put(lowerKey.charAt(i), lowerPlain);
            cipherMap.put(upperKey.charAt(i), upperPlain);
        }

        return cipherMap;
    }

    /**
     * Ensures the cipher key is non-null and has the expected length.
     *
     * @param cipherKey key to validate
     */
    private void validateCipherKey(String cipherKey) {
        if (cipherKey == null || cipherKey.length() != ALPHABET_SIZE) {
            throw new IllegalArgumentException(
                "Cipher key must be non-null and exactly " + ALPHABET_SIZE + " characters long."
            );
        }
    }
}