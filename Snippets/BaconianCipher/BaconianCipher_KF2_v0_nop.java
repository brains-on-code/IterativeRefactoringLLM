package com.thealgorithms.ciphers;

import java.util.HashMap;
import java.util.Map;


public class BaconianCipher {

    private static final Map<Character, String> BACONIAN_MAP = new HashMap<>();
    private static final Map<String, Character> REVERSE_BACONIAN_MAP = new HashMap<>();

    static {
        String[] baconianAlphabet = {"AAAAA", "AAAAB", "AAABA", "AAABB", "AABAA", "AABAB", "AABBA", "AABBB", "ABAAA", "ABAAB", "ABABA", "ABABB", "ABBAA", "ABBAB", "ABBBA", "ABBBB", "BAAAA", "BAAAB", "BAABA", "BAABB", "BABAA", "BABAB", "BABBA", "BABBB", "BBAAA", "BBAAB"};
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
        plaintext = plaintext.toUpperCase().replaceAll("[^A-Z]", "");

        for (char letter : plaintext.toCharArray()) {
            ciphertext.append(BACONIAN_MAP.get(letter));
        }

        return ciphertext.toString();
    }


    public String decrypt(String ciphertext) {
        StringBuilder plaintext = new StringBuilder();

        for (int i = 0; i < ciphertext.length(); i += 5) {
            String code = ciphertext.substring(i, i + 5);
            if (REVERSE_BACONIAN_MAP.containsKey(code)) {
                plaintext.append(REVERSE_BACONIAN_MAP.get(code));
            } else {
                throw new IllegalArgumentException("Invalid Baconian code: " + code);
            }
        }

        return plaintext.toString();
    }
}
