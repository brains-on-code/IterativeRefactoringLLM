package com.thealgorithms.ciphers;

/**
 * Implements an autokey-style cipher with encryption and decryption methods.
 */
public class Class1 {

    private static final int ALPHABET_SIZE = 26;
    private static final char FIRST_UPPERCASE_LETTER = 'A';

    /**
     * Encrypts the given plaintext using the provided key.
     *
     * @param plaintext the text to encrypt
     * @param key       the encryption key
     * @return the encrypted text (ciphertext)
     */
    public String encrypt(String plaintext, String key) {
        String normalizedPlaintext = normalizeText(plaintext);
        String normalizedKey = key.toUpperCase();

        StringBuilder extendedKey = new StringBuilder(normalizedKey).append(normalizedPlaintext);
        StringBuilder ciphertext = new StringBuilder(normalizedPlaintext.length());

        for (int i = 0; i < normalizedPlaintext.length(); i++) {
            char plainChar = normalizedPlaintext.charAt(i);
            char keyChar = extendedKey.charAt(i);
            ciphertext.append(encryptChar(plainChar, keyChar));
        }

        return ciphertext.toString();
    }

    /**
     * Decrypts the given ciphertext using the provided key.
     *
     * @param ciphertext the text to decrypt
     * @param key        the decryption key
     * @return the decrypted text (plaintext)
     */
    public String decrypt(String ciphertext, String key) {
        String normalizedCiphertext = normalizeText(ciphertext);
        String normalizedKey = key.toUpperCase();

        StringBuilder plaintext = new StringBuilder(normalizedCiphertext.length());
        StringBuilder extendedKey = new StringBuilder(normalizedKey);

        for (int i = 0; i < normalizedCiphertext.length(); i++) {
            char cipherChar = normalizedCiphertext.charAt(i);
            char keyChar = extendedKey.charAt(i);

            char decryptedChar = decryptChar(cipherChar, keyChar);
            plaintext.append(decryptedChar);
            extendedKey.append(decryptedChar);
        }

        return plaintext.toString();
    }

    private String normalizeText(String text) {
        return text.toUpperCase().replaceAll("[^A-Z]", "");
    }

    private char encryptChar(char plainChar, char keyChar) {
        int offset = (plainChar - FIRST_UPPERCASE_LETTER + keyChar - FIRST_UPPERCASE_LETTER) % ALPHABET_SIZE;
        return (char) (FIRST_UPPERCASE_LETTER + offset);
    }

    private char decryptChar(char cipherChar, char keyChar) {
        int offset = (cipherChar - FIRST_UPPERCASE_LETTER - (keyChar - FIRST_UPPERCASE_LETTER) + ALPHABET_SIZE)
                % ALPHABET_SIZE;
        return (char) (FIRST_UPPERCASE_LETTER + offset);
    }
}