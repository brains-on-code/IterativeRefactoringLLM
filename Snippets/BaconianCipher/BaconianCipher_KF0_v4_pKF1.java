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

    private static final Map<Character, String> LETTER_TO_BACONIAN_CODE = new HashMap<>();
    private static final Map<String, Character> BACONIAN_CODE_TO_LETTER = new HashMap<>();

    private static final String[] BACONIAN_CODE_TABLE = {
        "AAAAA", "AAAAB", "AAABA", "AAABB", "AABAA", "AABAB",
        "AABBA", "AABBB", "ABAAA", "ABAAB", "ABABA", "ABABB",
        "ABBAA", "ABBAB", "ABBBA", "ABBBB", "BAAAA", "BAAAB",
        "BAABA", "BAABB", "BABAA", "BABAB", "BABBA", "BABBB",
        "BBAAA", "BBAAB"
    };

    static {
        char currentLetter = 'A';
        for (String baconianCode : BACONIAN_CODE_TABLE) {
            LETTER_TO_BACONIAN_CODE.put(currentLetter, baconianCode);
            BACONIAN_CODE_TO_LETTER.put(baconianCode, currentLetter);
            currentLetter++;
        }

        LETTER_TO_BACONIAN_CODE.put('I', LETTER_TO_BACONIAN_CODE.get('J'));
        BACONIAN_CODE_TO_LETTER.put(LETTER_TO_BACONIAN_CODE.get('I'), 'I');
    }

    /**
     * Encrypts the given plaintext using the Baconian cipher.
     *
     * @param plaintext The plaintext message to encrypt.
     * @return The ciphertext as a binary (A/B) sequence.
     */
    public String encrypt(String plaintext) {
        StringBuilder encryptedTextBuilder = new StringBuilder();
        String normalizedPlaintext = plaintext.toUpperCase().replaceAll("[^A-Z]", "");

        for (char letter : normalizedPlaintext.toCharArray()) {
            encryptedTextBuilder.append(LETTER_TO_BACONIAN_CODE.get(letter));
        }

        return encryptedTextBuilder.toString();
    }

    /**
     * Decrypts the given ciphertext encoded in binary (A/B) format using the Baconian cipher.
     *
     * @param ciphertext The ciphertext to decrypt.
     * @return The decrypted plaintext message.
     */
    public String decrypt(String ciphertext) {
        StringBuilder decryptedTextBuilder = new StringBuilder();

        for (int index = 0; index < ciphertext.length(); index += 5) {
            String baconianCodeChunk = ciphertext.substring(index, index + 5);
            Character decodedLetter = BACONIAN_CODE_TO_LETTER.get(baconianCodeChunk);

            if (decodedLetter != null) {
                decryptedTextBuilder.append(decodedLetter);
            } else {
                throw new IllegalArgumentException("Invalid Baconian code: " + baconianCodeChunk);
            }
        }

        return decryptedTextBuilder.toString();
    }
}