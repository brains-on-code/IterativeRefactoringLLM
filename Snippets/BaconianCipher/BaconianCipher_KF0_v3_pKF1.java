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

    private static final Map<Character, String> LETTER_TO_CODE = new HashMap<>();
    private static final Map<String, Character> CODE_TO_LETTER = new HashMap<>();

    private static final String[] BACONIAN_CODES = {
        "AAAAA", "AAAAB", "AAABA", "AAABB", "AABAA", "AABAB",
        "AABBA", "AABBB", "ABAAA", "ABAAB", "ABABA", "ABABB",
        "ABBAA", "ABBAB", "ABBBA", "ABBBB", "BAAAA", "BAAAB",
        "BAABA", "BAABB", "BABAA", "BABAB", "BABBA", "BABBB",
        "BBAAA", "BBAAB"
    };

    static {
        char currentLetter = 'A';
        for (String code : BACONIAN_CODES) {
            LETTER_TO_CODE.put(currentLetter, code);
            CODE_TO_LETTER.put(code, currentLetter);
            currentLetter++;
        }

        LETTER_TO_CODE.put('I', LETTER_TO_CODE.get('J'));
        CODE_TO_LETTER.put(LETTER_TO_CODE.get('I'), 'I');
    }

    /**
     * Encrypts the given plaintext using the Baconian cipher.
     *
     * @param plaintext The plaintext message to encrypt.
     * @return The ciphertext as a binary (A/B) sequence.
     */
    public String encrypt(String plaintext) {
        StringBuilder ciphertext = new StringBuilder();
        String normalizedPlaintext = plaintext.toUpperCase().replaceAll("[^A-Z]", "");

        for (char letter : normalizedPlaintext.toCharArray()) {
            ciphertext.append(LETTER_TO_CODE.get(letter));
        }

        return ciphertext.toString();
    }

    /**
     * Decrypts the given ciphertext encoded in binary (A/B) format using the Baconian cipher.
     *
     * @param ciphertext The ciphertext to decrypt.
     * @return The decrypted plaintext message.
     */
    public String decrypt(String ciphertext) {
        StringBuilder plaintext = new StringBuilder();

        for (int index = 0; index < ciphertext.length(); index += 5) {
            String codeChunk = ciphertext.substring(index, index + 5);
            Character decodedLetter = CODE_TO_LETTER.get(codeChunk);

            if (decodedLetter != null) {
                plaintext.append(decodedLetter);
            } else {
                throw new IllegalArgumentException("Invalid Baconian code: " + codeChunk);
            }
        }

        return plaintext.toString();
    }
}