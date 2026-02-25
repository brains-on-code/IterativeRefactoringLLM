package com.thealgorithms.ciphers;

import java.util.HashMap;
import java.util.Map;

public class SimpleSubCipher {

    /**
     * Encodes a message using a simple substitution cipher.
     *
     * @param message    the plain text message to encode
     * @param cipherKey  a 26-character string representing the substitution
     *                   for letters 'a' to 'z'
     * @return the encoded message
     */
    public String encode(String message, String cipherKey) {
        Map<Character, Character> encodeMap = buildEncodeMap(cipherKey);
        StringBuilder encoded = new StringBuilder(message.length());

        for (char ch : message.toCharArray()) {
            if (Character.isAlphabetic(ch)) {
                encoded.append(encodeMap.get(ch));
            } else {
                encoded.append(ch);
            }
        }

        return encoded.toString();
    }

    /**
     * Decodes a message that was encoded using the same substitution cipher.
     *
     * @param encryptedMessage the encoded message
     * @param cipherKey        the same 26-character key used for encoding
     * @return the decoded (original) message
     */
    public String decode(String encryptedMessage, String cipherKey) {
        Map<Character, Character> decodeMap = buildDecodeMap(cipherKey);
        StringBuilder decoded = new StringBuilder(encryptedMessage.length());

        for (char ch : encryptedMessage.toCharArray()) {
            if (Character.isAlphabetic(ch)) {
                decoded.append(decodeMap.get(ch));
            } else {
                decoded.append(ch);
            }
        }

        return decoded.toString();
    }

    /**
     * Builds a mapping from plain alphabet characters to cipher characters
     * for both lowercase and uppercase letters.
     */
    private Map<Character, Character> buildEncodeMap(String cipherKey) {
        Map<Character, Character> cipherMap = new HashMap<>(52);

        String lowerKey = cipherKey.toLowerCase();
        String upperKey = lowerKey.toUpperCase();

        char lowerPlain = 'a';
        char upperPlain = 'A';

        for (int i = 0; i < lowerKey.length(); i++) {
            cipherMap.put(lowerPlain++, lowerKey.charAt(i));
            cipherMap.put(upperPlain++, upperKey.charAt(i));
        }

        return cipherMap;
    }

    /**
     * Builds a mapping from cipher characters back to plain alphabet characters
     * for both lowercase and uppercase letters.
     */
    private Map<Character, Character> buildDecodeMap(String cipherKey) {
        Map<Character, Character> cipherMap = new HashMap<>(52);

        String lowerKey = cipherKey.toLowerCase();
        String upperKey = lowerKey.toUpperCase();

        char lowerPlain = 'a';
        char upperPlain = 'A';

        for (int i = 0; i < lowerKey.length(); i++) {
            cipherMap.put(lowerKey.charAt(i), lowerPlain++);
            cipherMap.put(upperKey.charAt(i), upperPlain++);
        }

        return cipherMap;
    }
}