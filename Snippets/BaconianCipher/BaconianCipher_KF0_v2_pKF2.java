package com.thealgorithms.ciphers;

import java.util.HashMap;
import java.util.Map;

/**
 * Baconian cipher implementation.
 *
 * Each letter is represented by a group of five characters consisting of 'A' and 'B'.
 * See: https://en.wikipedia.org/wiki/Bacon%27s_cipher
 */
public class BaconianCipher {

    private static final Map<Character, String> BACONIAN_MAP = new HashMap<>();
    private static final Map<String, Character> REVERSE_BACONIAN_MAP = new HashMap<>();

    static {
        String[] baconianAlphabet = {
            "AAAAA", "AAAAB", "AAABA", "AAABB", "AABAA", "AABAB",
            "AABBA", "AABBB", "ABAAA", "ABAAB", "ABABA", "ABABB",
            "ABBAA", "ABBAB", "ABBBA", "ABBBB", "BAAAA", "BAAAB",
            "BAABA", "BAABB", "BABAA", "BABAB", "BABBA", "BABBB",
            "BBAAA", "BBAAB"
        };

        char letter = 'A';
        for (String code : baconianAlphabet) {
            BACONIAN_MAP.put(letter, code);
            REVERSE_BACONIAN_MAP.put(code, letter);
            letter++;
        }

        // In the classical Baconian cipher, I and J share the same encoding.
        BACONIAN_MAP.put('I', BACONIAN_MAP.get('J'));
        REVERSE_BACONIAN_MAP.put(BACONIAN_MAP.get('I'), 'I');
    }

    /**
     * Encrypts plaintext using the Baconian cipher.
     *
     * Non-alphabetic characters are removed and letters are converted to uppercase.
     *
     * @param plaintext input text
     * @return sequence of 'A' and 'B' characters
     */
    public String encrypt(String plaintext) {
        StringBuilder ciphertext = new StringBuilder();
        String normalized = plaintext.toUpperCase().replaceAll("[^A-Z]", "");

        for (char letter : normalized.toCharArray()) {
            ciphertext.append(BACONIAN_MAP.get(letter));
        }

        return ciphertext.toString();
    }

    /**
     * Decrypts a Baconian-encoded string.
     *
     * The input must be a sequence of 'A' and 'B' characters whose length
     * is a multiple of 5.
     *
     * @param ciphertext sequence of 'A' and 'B' characters
     * @return decrypted text
     * @throws IllegalArgumentException if an invalid 5-character group is encountered
     */
    public String decrypt(String ciphertext) {
        StringBuilder plaintext = new StringBuilder();

        for (int i = 0; i <= ciphertext.length() - 5; i += 5) {
            String code = ciphertext.substring(i, i + 5);
            Character decodedChar = REVERSE_BACONIAN_MAP.get(code);

            if (decodedChar == null) {
                throw new IllegalArgumentException("Invalid Baconian code: " + code);
            }

            plaintext.append(decodedChar);
        }

        return plaintext.toString();
    }
}