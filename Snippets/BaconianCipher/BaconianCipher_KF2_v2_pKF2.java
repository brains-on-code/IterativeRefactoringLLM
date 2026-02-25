package com.thealgorithms.ciphers;

import java.util.HashMap;
import java.util.Map;

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

        BACONIAN_MAP.put('I', BACONIAN_MAP.get('J'));
        REVERSE_BACONIAN_MAP.put(BACONIAN_MAP.get('I'), 'I');
    }

    public String encrypt(String plaintext) {
        StringBuilder ciphertext = new StringBuilder();
        String normalized = plaintext.toUpperCase().replaceAll("[^A-Z]", "");

        for (char letter : normalized.toCharArray()) {
            String code = BACONIAN_MAP.get(letter);
            if (code == null) {
                throw new IllegalArgumentException(
                    "Unsupported character for Baconian cipher: " + letter
                );
            }
            ciphertext.append(code);
        }

        return ciphertext.toString();
    }

    public String decrypt(String ciphertext) {
        if (ciphertext.length() % 5 != 0) {
            throw new IllegalArgumentException(
                "Ciphertext length must be a multiple of 5."
            );
        }

        StringBuilder plaintext = new StringBuilder();

        for (int i = 0; i < ciphertext.length(); i += 5) {
            String code = ciphertext.substring(i, i + 5);
            Character letter = REVERSE_BACONIAN_MAP.get(code);
            if (letter == null) {
                throw new IllegalArgumentException(
                    "Invalid Baconian code: " + code
                );
            }
            plaintext.append(letter);
        }

        return plaintext.toString();
    }
}