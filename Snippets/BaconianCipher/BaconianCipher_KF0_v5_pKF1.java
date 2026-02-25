package com.thealgorithms.ciphers;

import java.util.HashMap;
import java.util.Map;

/**
 * The Baconian Cipher is a substitution cipher where each letter is represented
 * by a group of five binary digits (A's and B's). It can also be used to hide
 * messages within other texts, making it a simple form of steganography.
 * https://en.wikipedia.org/wiki/Bacon%27s_cipher
 *
 * @author Bennybebo
 */
public class BaconianCipher {

    private static final Map<Character, String> LETTER_TO_CODE_MAP = new HashMap<>();
    private static final Map<String, Character> CODE_TO_LETTER_MAP = new HashMap<>();

    private static final String[] BACONIAN_CODES = {
        "AAAAA", "AAAAB", "AAABA", "AAABB", "AABAA", "AABAB",
        "AABBA", "AABBB", "ABAAA", "ABAAB", "ABABA", "ABABB",
        "ABBAA", "ABBAB", "ABBBA", "ABBBB", "BAAAA", "BAAAB",
        "BAABA", "BAABB", "BABAA", "BABAB", "BABBA", "BABBB",
        "BBAAA", "BBAAB"
    };

    static {
        char letter = 'A';
        for (String code : BACONIAN_CODES) {
            LETTER_TO_CODE_MAP.put(letter, code);
            CODE_TO_LETTER_MAP.put(code, letter);
            letter++;
        }

        LETTER_TO_CODE_MAP.put('I', LETTER_TO_CODE_MAP.get('J'));
        CODE_TO_LETTER_MAP.put(LETTER_TO_CODE_MAP.get('I'), 'I');
    }

    /**
     * Encrypts the given plaintext using the Baconian cipher.
     *
     * @param plaintext The plaintext message to encrypt.
     * @return The ciphertext as a binary (A/B) sequence.
     */
    public String encrypt(String plaintext) {
        StringBuilder ciphertextBuilder = new StringBuilder();
        String normalizedPlaintext = plaintext.toUpperCase().replaceAll("[^A-Z]", "");

        for (char letter : normalizedPlaintext.toCharArray()) {
            ciphertextBuilder.append(LETTER_TO_CODE_MAP.get(letter));
        }

        return ciphertextBuilder.toString();
    }

    /**
     * Decrypts the given ciphertext encoded in binary (A/B) format using the Baconian cipher.
     *
     * @param ciphertext The ciphertext to decrypt.
     * @return The decrypted plaintext message.
     */
    public String decrypt(String ciphertext) {
        StringBuilder plaintextBuilder = new StringBuilder();

        for (int index = 0; index < ciphertext.length(); index += 5) {
            String codeChunk = ciphertext.substring(index, index + 5);
            Character decodedLetter = CODE_TO_LETTER_MAP.get(codeChunk);

            if (decodedLetter != null) {
                plaintextBuilder.append(decodedLetter);
            } else {
                throw new IllegalArgumentException("Invalid Baconian code: " + codeChunk);
            }
        }

        return plaintextBuilder.toString();
    }
}