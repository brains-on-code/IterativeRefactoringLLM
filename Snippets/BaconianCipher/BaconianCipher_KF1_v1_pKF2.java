package com.thealgorithms.ciphers;

import java.util.HashMap;
import java.util.Map;

/**
 * Baconian cipher implementation.
 *
 * This cipher encodes each letter as a group of five characters consisting of
 * 'A' and 'B'. In this implementation, I and J share the same encoding.
 */
public class Class1 {

    /** Mapping from plaintext characters to their Baconian 5-letter codes. */
    private static final Map<Character, String> charToCode = new HashMap<>();

    /** Mapping from Baconian 5-letter codes to their plaintext characters. */
    private static final Map<String, Character> codeToChar = new HashMap<>();

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
            charToCode.put(letter, code);
            codeToChar.put(code, letter);
            letter++;
        }

        // I and J share the same code; map I to J's code.
        charToCode.put('I', charToCode.get('J'));
        codeToChar.put(charToCode.get('I'), 'I');
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
    public String method1(String plaintext) {
        StringBuilder encoded = new StringBuilder();
        String normalized = plaintext.toUpperCase().replaceAll("[^A-Z]", "");

        for (char c : normalized.toCharArray()) {
            encoded.append(charToCode.get(c));
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
    public String method2(String encoded) {
        StringBuilder decoded = new StringBuilder();

        for (int i = 0; i < encoded.length(); i += 5) {
            String group = encoded.substring(i, i + 5);
            if (codeToChar.containsKey(group)) {
                decoded.append(codeToChar.get(group));
            } else {
                throw new IllegalArgumentException("Invalid Baconian code: " + group);
            }
        }

        return decoded.toString();
    }
}