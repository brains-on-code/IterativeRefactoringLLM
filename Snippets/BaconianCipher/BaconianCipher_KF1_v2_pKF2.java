package com.thealgorithms.ciphers;

import java.util.HashMap;
import java.util.Map;

/**
 * Baconian cipher implementation.
 *
 * Each letter is encoded as a group of five characters consisting of 'A' and 'B'.
 * In this implementation, I and J share the same encoding.
 */
public class BaconianCipher {

    /** Maps plaintext characters to their 5-letter Baconian codes. */
    private static final Map<Character, String> CHAR_TO_CODE = new HashMap<>();

    /** Maps 5-letter Baconian codes to their corresponding plaintext characters. */
    private static final Map<String, Character> CODE_TO_CHAR = new HashMap<>();

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
            CHAR_TO_CODE.put(letter, code);
            CODE_TO_CHAR.put(code, letter);
            letter++;
        }

        // I and J share the same code; map I to J's code and ensure decoding prefers I.
        CHAR_TO_CODE.put('I', CHAR_TO_CODE.get('J'));
        CODE_TO_CHAR.put(CHAR_TO_CODE.get('I'), 'I');
    }

    /**
     * Encodes a plaintext string using the Baconian cipher.
     *
     * Non-alphabetic characters are removed, and the text is converted to
     * uppercase before encoding.
     *
     * @param plaintext the input text to encode
     * @return the encoded Baconian string consisting of 'A' and 'B'
     */
    public String encode(String plaintext) {
        StringBuilder encoded = new StringBuilder();
        String normalized = plaintext.toUpperCase().replaceAll("[^A-Z]", "");

        for (char c : normalized.toCharArray()) {
            encoded.append(CHAR_TO_CODE.get(c));
        }

        return encoded.toString();
    }

    /**
     * Decodes a Baconian-encoded string back to plaintext.
     *
     * The input length must be a multiple of 5, and each 5-character group
     * must be a valid Baconian code.
     *
     * @param encoded the Baconian-encoded string (only 'A' and 'B')
     * @return the decoded plaintext
     * @throws IllegalArgumentException if an invalid code group is encountered
     */
    public String decode(String encoded) {
        if (encoded.length() % 5 != 0) {
            throw new IllegalArgumentException("Encoded string length must be a multiple of 5.");
        }

        StringBuilder decoded = new StringBuilder();

        for (int i = 0; i < encoded.length(); i += 5) {
            String group = encoded.substring(i, i + 5);
            Character decodedChar = CODE_TO_CHAR.get(group);

            if (decodedChar == null) {
                throw new IllegalArgumentException("Invalid Baconian code: " + group);
            }

            decoded.append(decodedChar);
        }

        return decoded.toString();
    }
}