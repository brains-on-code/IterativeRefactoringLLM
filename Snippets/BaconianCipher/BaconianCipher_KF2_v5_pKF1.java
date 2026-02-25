package com.thealgorithms.ciphers;

import java.util.HashMap;
import java.util.Map;

public class BaconianCipher {

    private static final Map<Character, String> LETTER_TO_CODE = new HashMap<>();
    private static final Map<String, Character> CODE_TO_LETTER = new HashMap<>();

    static {
        String[] baconianCodes = {
            "AAAAA", "AAAAB", "AAABA", "AAABB", "AABAA", "AABAB",
            "AABBA", "AABBB", "ABAAA", "ABAAB", "ABABA", "ABABB",
            "ABBAA", "ABBAB", "ABBBA", "ABBBB", "BAAAA", "BAAAB",
            "BAABA", "BAABB", "BABAA", "BABAB", "BABBA", "BABBB",
            "BBAAA", "BBAAB"
        };

        char letter = 'A';
        for (String code : baconianCodes) {
            LETTER_TO_CODE.put(letter, code);
            CODE_TO_LETTER.put(code, letter);
            letter++;
        }

        LETTER_TO_CODE.put('I', LETTER_TO_CODE.get('J'));
        CODE_TO_LETTER.put(LETTER_TO_CODE.get('I'), 'I');
    }

    public String encrypt(String plaintext) {
        StringBuilder ciphertextBuilder = new StringBuilder();
        String normalizedPlaintext = plaintext.toUpperCase().replaceAll("[^A-Z]", "");

        for (char letter : normalizedPlaintext.toCharArray()) {
            ciphertextBuilder.append(LETTER_TO_CODE.get(letter));
        }

        return ciphertextBuilder.toString();
    }

    public String decrypt(String ciphertext) {
        StringBuilder plaintextBuilder = new StringBuilder();

        for (int index = 0; index < ciphertext.length(); index += 5) {
            String codeGroup = ciphertext.substring(index, index + 5);
            if (CODE_TO_LETTER.containsKey(codeGroup)) {
                plaintextBuilder.append(CODE_TO_LETTER.get(codeGroup));
            } else {
                throw new IllegalArgumentException("Invalid Baconian code: " + codeGroup);
            }
        }

        return plaintextBuilder.toString();
    }
}