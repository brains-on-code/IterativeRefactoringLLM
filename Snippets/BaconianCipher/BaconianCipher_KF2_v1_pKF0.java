package com.thealgorithms.ciphers;

import java.util.HashMap;
import java.util.Map;

public class BaconianCipher {

    private static final Map<Character, String> BACONIAN_MAP = new HashMap<>();
    private static final Map<String, Character> REVERSE_BACONIAN_MAP = new HashMap<>();
    private static final int CODE_LENGTH = 5;
    private static final String[] BACONIAN_ALPHABET = {
        "AAAAA", "AAAAB", "AAABA", "AAABB", "AABAA", "AABAB",
        "AABBA", "AABBB", "ABAAA", "ABAAB", "ABABA", "ABABB",
        "ABBAA", "ABBAB", "ABBBA", "ABBBB", "BAAAA", "BAAAB",
        "BAABA", "BAABB", "BABAA", "BABAB", "BABBA", "BABBB",
        "BBAAA", "BBAAB"
    };

    static {
        initializeMappings();
    }

    private static void initializeMappings() {
        char letter = 'A';
        for (String code : BACONIAN_ALPHABET) {
            BACONIAN_MAP.put(letter, code);
            REVERSE_BACONIAN_MAP.put(code, letter);
            letter++;
        }

        // Map I and J to the same code
        BACONIAN_MAP.put('I', BACONIAN_MAP.get('J'));
        REVERSE_BACONIAN_MAP.put(BACONIAN_MAP.get('I'), 'I');
    }

    public String encrypt(String plaintext) {
        StringBuilder ciphertext = new StringBuilder();
        String normalizedText = normalizePlaintext(plaintext);

        for (char letter : normalizedText.toCharArray()) {
            String code = BACONIAN_MAP.get(letter);
            if (code != null) {
                ciphertext.append(code);
            }
        }

        return ciphertext.toString();
    }

    public String decrypt(String ciphertext) {
        validateCiphertext(ciphertext);

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
        if (plaintext == null) {
            return "";
        }
        return plaintext.toUpperCase().replaceAll("[^A-Z]", "");
    }

    private void validateCiphertext(String ciphertext) {
        if (ciphertext == null || ciphertext.isEmpty()) {
            throw new IllegalArgumentException("Ciphertext cannot be null or empty.");
        }
        if (ciphertext.length() % CODE_LENGTH != 0) {
            throw new IllegalArgumentException("Ciphertext length must be a multiple of " + CODE_LENGTH + ".");
        }
    }
}