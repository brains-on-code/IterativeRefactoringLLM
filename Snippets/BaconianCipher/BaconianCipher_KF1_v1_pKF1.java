package com.thealgorithms.ciphers;

import java.util.HashMap;
import java.util.Map;

/**
 * Baconian cipher implementation.
 *
 * @spell shift
 */
public class BaconianCipher {

    private static final Map<Character, String> letterToCodeMap = new HashMap<>();
    private static final Map<String, Character> codeToLetterMap = new HashMap<>();

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
            letterToCodeMap.put(currentLetter, code);
            codeToLetterMap.put(code, currentLetter);
            currentLetter++;
        }

        // I and J share the same code in the traditional Baconian cipher
        letterToCodeMap.put('I', letterToCodeMap.get('J'));
        codeToLetterMap.put(letterToCodeMap.get('I'), 'I');
    }

    /**
     * Encodes a plaintext string using the Baconian cipher.
     *
     * @param plaintext the input text to encode
     * @return the encoded Baconian cipher text
     */
    public String encode(String plaintext) {
        StringBuilder encoded = new StringBuilder();
        String normalized = plaintext.toUpperCase().replaceAll("[^A-Z]", "");

        for (char letter : normalized.toCharArray()) {
            encoded.append(letterToCodeMap.get(letter));
        }

        return encoded.toString();
    }

    /**
     * Decodes a Baconian cipher string back to plaintext.
     *
     * @param cipherText the Baconian cipher text to decode
     * @return the decoded plaintext
     */
    public String decode(String cipherText) {
        StringBuilder decoded = new StringBuilder();

        for (int i = 0; i < cipherText.length(); i += 5) {
            String codeGroup = cipherText.substring(i, i + 5);
            if (codeToLetterMap.containsKey(codeGroup)) {
                decoded.append(codeToLetterMap.get(codeGroup));
            } else {
                throw new IllegalArgumentException("Invalid Baconian code: " + codeGroup);
            }
        }

        return decoded.toString();
    }
}