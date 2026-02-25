package com.thealgorithms.ciphers;

import java.util.HashMap;
import java.util.Map;

/**
 * Baconian cipher implementation.
 *
 * @spell shift
 */
public class BaconianCipher {

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
        LETTER_TO_CODE.put('I', LETTER_TO_CODE.get('J'));
        CODE_TO_LETTER.put(LETTER_TO_CODE.get('I'), 'I');
    }

    /**
     * Encodes a plaintext string using the Baconian cipher.
     *
     * @param plaintext the input text to encode
     * @return the encoded Baconian string
     */
    public String encode(String plaintext) {
        StringBuilder encoded = new StringBuilder();
        String normalized = plaintext.toUpperCase().replaceAll("[^A-Z]", "");

        for (char ch : normalized.toCharArray()) {
            encoded.append(LETTER_TO_CODE.get(ch));
        }

        return encoded.toString();
    }

    /**
     * Decodes a Baconian-encoded string back to plaintext.
     *
     * @param cipherText the Baconian-encoded string
     * @return the decoded plaintext
     * @throws IllegalArgumentException if the input contains invalid Baconian codes
     */
    public String decode(String cipherText) {
        StringBuilder decoded = new StringBuilder();

        for (int i = 0; i < cipherText.length(); i += 5) {
            String code = cipherText.substring(i, i + 5);
            Character letter = CODE_TO_LETTER.get(code);
            if (letter != null) {
                decoded.append(letter);
            } else {
                throw new IllegalArgumentException("Invalid Baconian code: " + code);
            }
        }

        return decoded.toString();
    }
}