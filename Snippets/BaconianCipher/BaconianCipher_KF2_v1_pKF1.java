package com.thealgorithms.ciphers;

import java.util.HashMap;
import java.util.Map;

public class BaconianCipher {

    private static final Map<Character, String> LETTER_TO_BACONIAN_CODE = new HashMap<>();
    private static final Map<String, Character> BACONIAN_CODE_TO_LETTER = new HashMap<>();

    static {
        String[] baconianCodes = {
            "AAAAA", "AAAAB", "AAABA", "AAABB", "AABAA", "AABAB",
            "AABBA", "AABBB", "ABAAA", "ABAAB", "ABABA", "ABABB",
            "ABBAA", "ABBAB", "ABBBA", "ABBBB", "BAAAA", "BAAAB",
            "BAABA", "BAABB", "BABAA", "BABAB", "BABBA", "BABBB",
            "BBAAA", "BBAAB"
        };

        char currentLetter = 'A';
        for (String baconianCode : baconianCodes) {
            LETTER_TO_BACONIAN_CODE.put(currentLetter, baconianCode);
            BACONIAN_CODE_TO_LETTER.put(baconianCode, currentLetter);
            currentLetter++;
        }

        LETTER_TO_BACONIAN_CODE.put('I', LETTER_TO_BACONIAN_CODE.get('J'));
        BACONIAN_CODE_TO_LETTER.put(LETTER_TO_BACONIAN_CODE.get('I'), 'I');
    }

    public String encrypt(String plaintext) {
        StringBuilder encryptedText = new StringBuilder();
        String normalizedPlaintext = plaintext.toUpperCase().replaceAll("[^A-Z]", "");

        for (char letter : normalizedPlaintext.toCharArray()) {
            encryptedText.append(LETTER_TO_BACONIAN_CODE.get(letter));
        }

        return encryptedText.toString();
    }

    public String decrypt(String ciphertext) {
        StringBuilder decryptedText = new StringBuilder();

        for (int index = 0; index < ciphertext.length(); index += 5) {
            String baconianCode = ciphertext.substring(index, index + 5);
            if (BACONIAN_CODE_TO_LETTER.containsKey(baconianCode)) {
                decryptedText.append(BACONIAN_CODE_TO_LETTER.get(baconianCode));
            } else {
                throw new IllegalArgumentException("Invalid Baconian code: " + baconianCode);
            }
        }

        return decryptedText.toString();
    }
}