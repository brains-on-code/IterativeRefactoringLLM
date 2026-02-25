package com.thealgorithms.ciphers;

import java.util.HashMap;
import java.util.Map;

/**
 * Baconian cipher implementation.
 *
 * @spell shift
 */
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

        // I and J share the same code in the traditional Baconian cipher
        LETTER_TO_BACONIAN_CODE.put('I', LETTER_TO_BACONIAN_CODE.get('J'));
        BACONIAN_CODE_TO_LETTER.put(LETTER_TO_BACONIAN_CODE.get('I'), 'I');
    }

    /**
     * Encodes a plaintext string using the Baconian cipher.
     *
     * @param plaintext the input text to encode
     * @return the encoded Baconian cipher text
     */
    public String encode(String plaintext) {
        StringBuilder encodedTextBuilder = new StringBuilder();
        String normalizedPlaintext = plaintext.toUpperCase().replaceAll("[^A-Z]", "");

        for (char letter : normalizedPlaintext.toCharArray()) {
            encodedTextBuilder.append(LETTER_TO_BACONIAN_CODE.get(letter));
        }

        return encodedTextBuilder.toString();
    }

    /**
     * Decodes a Baconian cipher string back to plaintext.
     *
     * @param cipherText the Baconian cipher text to decode
     * @return the decoded plaintext
     */
    public String decode(String cipherText) {
        StringBuilder decodedTextBuilder = new StringBuilder();

        for (int index = 0; index < cipherText.length(); index += 5) {
            String baconianCodeGroup = cipherText.substring(index, index + 5);
            if (BACONIAN_CODE_TO_LETTER.containsKey(baconianCodeGroup)) {
                decodedTextBuilder.append(BACONIAN_CODE_TO_LETTER.get(baconianCodeGroup));
            } else {
                throw new IllegalArgumentException("Invalid Baconian code: " + baconianCodeGroup);
            }
        }

        return decodedTextBuilder.toString();
    }
}