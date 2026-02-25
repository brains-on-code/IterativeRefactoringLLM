package com.thealgorithms.ciphers;

import java.util.HashMap;
import java.util.Map;

public class BaconianCipher {

    private static final Map<Character, String> LETTER_TO_BACON_CODE = new HashMap<>();
    private static final Map<String, Character> BACON_CODE_TO_LETTER = new HashMap<>();

    static {
        String[] baconianCodeTable = {
            "AAAAA", "AAAAB", "AAABA", "AAABB", "AABAA", "AABAB",
            "AABBA", "AABBB", "ABAAA", "ABAAB", "ABABA", "ABABB",
            "ABBAA", "ABBAB", "ABBBA", "ABBBB", "BAAAA", "BAAAB",
            "BAABA", "BAABB", "BABAA", "BABAB", "BABBA", "BABBB",
            "BBAAA", "BBAAB"
        };

        char currentPlainLetter = 'A';
        for (String baconCode : baconianCodeTable) {
            LETTER_TO_BACON_CODE.put(currentPlainLetter, baconCode);
            BACON_CODE_TO_LETTER.put(baconCode, currentPlainLetter);
            currentPlainLetter++;
        }

        LETTER_TO_BACON_CODE.put('I', LETTER_TO_BACON_CODE.get('J'));
        BACON_CODE_TO_LETTER.put(LETTER_TO_BACON_CODE.get('I'), 'I');
    }

    public String encrypt(String plaintext) {
        StringBuilder encryptedText = new StringBuilder();
        String normalizedPlaintext = plaintext.toUpperCase().replaceAll("[^A-Z]", "");

        for (char plainLetter : normalizedPlaintext.toCharArray()) {
            encryptedText.append(LETTER_TO_BACON_CODE.get(plainLetter));
        }

        return encryptedText.toString();
    }

    public String decrypt(String ciphertext) {
        StringBuilder decryptedText = new StringBuilder();

        for (int position = 0; position < ciphertext.length(); position += 5) {
            String baconCodeGroup = ciphertext.substring(position, position + 5);
            if (BACON_CODE_TO_LETTER.containsKey(baconCodeGroup)) {
                decryptedText.append(BACON_CODE_TO_LETTER.get(baconCodeGroup));
            } else {
                throw new IllegalArgumentException("Invalid Baconian code: " + baconCodeGroup);
            }
        }

        return decryptedText.toString();
    }
}