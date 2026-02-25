package com.thealgorithms.ciphers;

/**
 * Autokey cipher implementation.
 */
public class AutokeyCipher {

    private static final int ALPHABET_SIZE = 26;
    private static final char ALPHABET_START = 'A';

    /**
     * Encrypts the given plaintext using the provided key with the Autokey cipher.
     *
     * @param plaintext the text to encrypt
     * @param key       the encryption key
     * @return the resulting ciphertext (A–Z only)
     */
    public String encrypt(String plaintext, String key) {
        String sanitizedPlaintext = sanitizeToUppercaseLetters(plaintext);
        String uppercaseKey = key.toUpperCase();

        StringBuilder autokeySequence = new StringBuilder(uppercaseKey).append(sanitizedPlaintext);
        StringBuilder ciphertextBuilder = new StringBuilder();

        for (int index = 0; index < sanitizedPlaintext.length(); index++) {
            char plaintextCharacter = sanitizedPlaintext.charAt(index);
            char keyCharacter = autokeySequence.charAt(index);

            int encryptedCharacterCode =
                    (plaintextCharacter - ALPHABET_START + keyCharacter - ALPHABET_START) % ALPHABET_SIZE
                            + ALPHABET_START;

            ciphertextBuilder.append((char) encryptedCharacterCode);
        }

        return ciphertextBuilder.toString();
    }

    /**
     * Decrypts the given ciphertext using the provided key with the Autokey cipher.
     *
     * @param ciphertext the text to decrypt
     * @param key        the decryption key
     * @return the resulting plaintext (A–Z only)
     */
    public String decrypt(String ciphertext, String key) {
        String sanitizedCiphertext = sanitizeToUppercaseLetters(ciphertext);
        String uppercaseKey = key.toUpperCase();

        StringBuilder plaintextBuilder = new StringBuilder();
        StringBuilder evolvingKeySequence = new StringBuilder(uppercaseKey);

        for (int index = 0; index < sanitizedCiphertext.length(); index++) {
            char ciphertextCharacter = sanitizedCiphertext.charAt(index);
            char keyCharacter = evolvingKeySequence.charAt(index);

            int decryptedCharacterCode =
                    (ciphertextCharacter - ALPHABET_START - (keyCharacter - ALPHABET_START) + ALPHABET_SIZE)
                            % ALPHABET_SIZE
                            + ALPHABET_START;

            char decryptedCharacter = (char) decryptedCharacterCode;

            plaintextBuilder.append(decryptedCharacter);
            evolvingKeySequence.append(decryptedCharacter);
        }

        return plaintextBuilder.toString();
    }

    private String sanitizeToUppercaseLetters(String input) {
        return input.toUpperCase().replaceAll("[^A-Z]", "");
    }
}