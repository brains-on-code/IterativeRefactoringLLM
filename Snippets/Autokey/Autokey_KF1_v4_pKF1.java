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
        String normalizedPlaintext = normalizeToUppercaseLetters(plaintext);
        String normalizedKey = key.toUpperCase();

        StringBuilder fullKey = new StringBuilder(normalizedKey).append(normalizedPlaintext);
        StringBuilder ciphertext = new StringBuilder();

        for (int i = 0; i < normalizedPlaintext.length(); i++) {
            char plaintextChar = normalizedPlaintext.charAt(i);
            char keyChar = fullKey.charAt(i);

            int encryptedCharCode =
                    (plaintextChar - ALPHABET_START + keyChar - ALPHABET_START) % ALPHABET_SIZE
                            + ALPHABET_START;

            ciphertext.append((char) encryptedCharCode);
        }

        return ciphertext.toString();
    }

    /**
     * Decrypts the given ciphertext using the provided key with the Autokey cipher.
     *
     * @param ciphertext the text to decrypt
     * @param key        the decryption key
     * @return the resulting plaintext (A–Z only)
     */
    public String decrypt(String ciphertext, String key) {
        String normalizedCiphertext = normalizeToUppercaseLetters(ciphertext);
        String normalizedKey = key.toUpperCase();

        StringBuilder plaintext = new StringBuilder();
        StringBuilder evolvingKey = new StringBuilder(normalizedKey);

        for (int i = 0; i < normalizedCiphertext.length(); i++) {
            char ciphertextChar = normalizedCiphertext.charAt(i);
            char keyChar = evolvingKey.charAt(i);

            int decryptedCharCode =
                    (ciphertextChar - ALPHABET_START - (keyChar - ALPHABET_START) + ALPHABET_SIZE)
                            % ALPHABET_SIZE
                            + ALPHABET_START;

            char decryptedChar = (char) decryptedCharCode;

            plaintext.append(decryptedChar);
            evolvingKey.append(decryptedChar);
        }

        return plaintext.toString();
    }

    private String normalizeToUppercaseLetters(String input) {
        return input.toUpperCase().replaceAll("[^A-Z]", "");
    }
}