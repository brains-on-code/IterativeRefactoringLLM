package com.thealgorithms.ciphers;

import java.util.HashMap;
import java.util.Map;

/**
 * Baconian cipher implementation.
 *
 * @spell shift
 */
public class BaconianCipher {

    private static final int CODE_LENGTH = 5;

    private static final Map<Character, String> LETTER_TO_CODE = new HashMap<>();
    private static final Map<String, Character> CODE_TO_LETTER = new HashMap<>();

    static {
        String[] codes = {
            "AAAAA", "AAAAB", "AAABA", "AAABB", "AABAA", "AABAB",
            "AABBA", "AABBB", "ABAAA", "ABAAB", "ABABA", "ABABB",
            "ABBAA", "ABBAB", "ABBBA", "ABBBB", "BAAAA", "BAAAB",
            "BAABA", "BAABB", "BABAA", "BABAB", "BABBA", "BABBB",
            "BBAAA", "BBAAB"
        };

        char letter = 'A';
        for (String code : codes) {
            LETTER_TO_CODE.put(letter, code);
            CODE_TO_LETTER.put(code, letter);
            letter++;
        }

        // I and J share the same code in the traditional Baconian cipher
        String jCode = LETTER_TO_CODE.get('J');
        LETTER_TO_CODE.put('I', jCode);
        CODE_TO_LETTER.put(jCode, 'I');
    }

    /**
     * Encodes a plaintext string using the Baconian cipher.
     *
     * @param plaintext the input text to encode
     * @return the encoded Baconian string
     */
    public String encode(String plaintext) {
        StringBuilder encoded = new StringBuilder();
        String normalized = normalizePlaintext(plaintext);

        for (char ch : normalized.toCharArray()) {
            String code = LETTER_TO_CODE.get(ch);
            if (code != null) {
                encoded.append(code);
            }
        }

        return encoded.toString();
    }

    /**
     * Decodes a Baconian-encoded string back to plaintext.
     *
     * @param cipherText the Baconian-encoded string
     * @return the decoded plaintext
     * @throws IllegalArgumentException if the input length is invalid or contains invalid Baconian codes
     */
    public String decode(String cipherText) {
        validateCipherTextLength(cipherText);

        StringBuilder decoded = new StringBuilder();

        for (int i = 0; i < cipherText.length(); i += CODE_LENGTH) {
            String code = cipherText.substring(i, i + CODE_LENGTH);
            Character letter = CODE_TO_LETTER.get(code);
            if (letter == null) {
                throw new IllegalArgumentException("Invalid Baconian code: " + code);
            }
            decoded.append(letter);
        }

        return decoded.toString();
    }

    private String normalizePlaintext(String plaintext) {
        if (plaintext == null) {
            return "";
        }
        return plaintext.toUpperCase().replaceAll("[^A-Z]", "");
    }

    private void validateCipherTextLength(String cipherText) {
        if (cipherText == null || cipherText.length() % CODE_LENGTH != 0) {
            throw new IllegalArgumentException(
                "Cipher text length must be a multiple of " + CODE_LENGTH
            );
        }
    }
}