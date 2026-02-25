package com.thealgorithms.ciphers;

import java.util.HashMap;
import java.util.Map;

/**
 * Baconian cipher implementation.
 *
 * @spell shift
 */
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

        char currentLetter = 'A';
        for (String code : baconianCodes) {
            LETTER_TO_CODE.put(currentLetter, code);
            CODE_TO_LETTER.put(code, currentLetter);
            currentLetter++;
        }

        // I and J share the same code in the traditional Baconian cipher
        LETTER_TO_CODE.put('I', LETTER_TO_CODE.get('J'));
        CODE_TO_LETTER.put(LETTER_TO_CODE.get('I'), 'I');
    }

    /**
     * Encodes a plaintext string using the Baconian cipher.
     *
     * @param plaintext the input text to encode
     * @return the encoded Baconian cipher text
     */
    public String encode(String plaintext) {
        StringBuilder encodedText = new StringBuilder();
        String normalizedPlaintext = plaintext.toUpperCase().replaceAll("[^A-Z]", "");

        for (char letter : normalizedPlaintext.toCharArray()) {
            encodedText.append(LETTER_TO_CODE.get(letter));
        }

        return encodedText.toString();
    }

    /**
     * Decodes a Baconian cipher string back to plaintext.
     *
     * @param cipherText the Baconian cipher text to decode
     * @return the decoded plaintext
     */
    public String decode(String cipherText) {
        StringBuilder decodedText = new StringBuilder();

        for (int index = 0; index < cipherText.length(); index += 5) {
            String codeGroup = cipherText.substring(index, index + 5);
            if (CODE_TO_LETTER.containsKey(codeGroup)) {
                decodedText.append(CODE_TO_LETTER.get(codeGroup));
            } else {
                throw new IllegalArgumentException("Invalid Baconian code: " + codeGroup);
            }
        }

        return decodedText.toString();
    }
}