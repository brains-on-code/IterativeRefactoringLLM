package com.thealgorithms.ciphers;

import java.util.HashMap;
import java.util.Map;

/**
 * The Baconian Cipher is a substitution cipher where each letter is represented
 * by a group of five binary digits (A's and B's). It can also be used to hide
 * messages within other texts, making it a simple form of steganography.
 * https://en.wikipedia.org/wiki/Bacon%27s_cipher
 *
 * @author Bennybebo
 */
public class BaconianCipher {

    private static final int CODE_LENGTH = 5;

    private static final Map<Character, String> BACONIAN_MAP = new HashMap<>();
    private static final Map<String, Character> REVERSE_BACONIAN_MAP = new HashMap<>();

    static {
        initializeMappings();
    }

    private static void initializeMappings() {
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

        // Handle I/J as the same letter
        BACONIAN_MAP.put('I', BACONIAN_MAP.get('J'));
        REVERSE_BACONIAN_MAP.put(BACONIAN_MAP.get('I'), 'I');
    }

    /**
     * Encrypts the given plaintext using the Baconian cipher.
     *
     * @param plaintext The plaintext message to encrypt.
     * @return The ciphertext as a binary (A/B) sequence.
     */
    public String encrypt(String plaintext) {
        if (plaintext == null || plaintext.isEmpty()) {
            return "";
        }

        StringBuilder ciphertext = new StringBuilder();
        String normalized = normalizePlaintext(plaintext);

        for (char letter : normalized.toCharArray()) {
            String code = BACONIAN_MAP.get(letter);
            if (code != null) {
                ciphertext.append(code);
            }
        }

        return ciphertext.toString();
    }

    /**
     * Decrypts the given ciphertext encoded in binary (A/B) format using the Baconian cipher.
     *
     * @param ciphertext The ciphertext to decrypt.
     * @return The decrypted plaintext message.
     */
    public String decrypt(String ciphertext) {
        if (ciphertext == null || ciphertext.isEmpty()) {
            return "";
        }

        if (ciphertext.length() % CODE_LENGTH != 0) {
            throw new IllegalArgumentException("Ciphertext length must be a multiple of " + CODE_LENGTH);
        }

        StringBuilder plaintext = new StringBuilder();

        for (int i = 0; i < ciphertext.length(); i += CODE_LENGTH) {
            String code = ciphertext.substring(i, i + CODE_LENGTH);
            Character letter = REVERSE_BACONIAN_MAP.get(code);

            if (letter == null) {
                throw new IllegalArgumentException("Invalid Baconian code: " + code);
            }

            plaintext.append(letter);
        }

        return plaintext.toString();
    }

    private String normalizePlaintext(String plaintext) {
        return plaintext.toUpperCase().replaceAll("[^A-Z]", "");
    }
}