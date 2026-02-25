package com.thealgorithms.ciphers;

import java.util.HashMap;
import java.util.Map;

public class BaconianCipher {

    private static final Map<Character, String> PLAIN_LETTER_TO_CODE = new HashMap<>();
    private static final Map<String, Character> CODE_TO_PLAIN_LETTER = new HashMap<>();

    static {
        String[] baconianCodes = {
            "AAAAA", "AAAAB", "AAABA", "AAABB", "AABAA", "AABAB",
            "AABBA", "AABBB", "ABAAA", "ABAAB", "ABABA", "ABABB",
            "ABBAA", "ABBAB", "ABBBA", "ABBBB", "BAAAA", "BAAAB",
            "BAABA", "BAABB", "BABAA", "BABAB", "BABBA", "BABBB",
            "BBAAA", "BBAAB"
        };

        char currentLetter = 'A';
        for (String code : baconianCodes) {
            PLAIN_LETTER_TO_CODE.put(currentLetter, code);
            CODE_TO_PLAIN_LETTER.put(code, currentLetter);
            currentLetter++;
        }

        PLAIN_LETTER_TO_CODE.put('I', PLAIN_LETTER_TO_CODE.get('J'));
        CODE_TO_PLAIN_LETTER.put(PLAIN_LETTER_TO_CODE.get('I'), 'I');
    }

    public String encrypt(String plaintext) {
        StringBuilder ciphertextBuilder = new StringBuilder();
        String normalizedPlaintext = plaintext.toUpperCase().replaceAll("[^A-Z]", "");

        for (char letter : normalizedPlaintext.toCharArray()) {
            ciphertextBuilder.append(PLAIN_LETTER_TO_CODE.get(letter));
        }

        return ciphertextBuilder.toString();
    }

    public String decrypt(String ciphertext) {
        StringBuilder plaintextBuilder = new StringBuilder();

        for (int index = 0; index < ciphertext.length(); index += 5) {
            String codeGroup = ciphertext.substring(index, index + 5);
            if (CODE_TO_PLAIN_LETTER.containsKey(codeGroup)) {
                plaintextBuilder.append(CODE_TO_PLAIN_LETTER.get(codeGroup));
            } else {
                throw new IllegalArgumentException("Invalid Baconian code: " + codeGroup);
            }
        }

        return plaintextBuilder.toString();
    }
}