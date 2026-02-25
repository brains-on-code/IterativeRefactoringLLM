package com.thealgorithms.ciphers;

/**
 * Implements an autokey-style cipher with encryption and decryption methods.
 */
public class Class1 {

    private static final int ALPHABET_SIZE = 26;
    private static final char FIRST_UPPERCASE_LETTER = 'A';
    private static final String NON_ALPHABETIC_REGEX = "[^A-Z]";

    /**
     * Encrypts the given plaintext using the provided key.
     *
     * @param plaintext the text to encrypt
     * @param key       the encryption key
     * @return the encrypted text (ciphertext)
     */
    public String encrypt(String plaintext, String key) {
        String normalizedPlaintext = normalize(plaintext);
        String normalizedKey = normalize(key);

        if (normalizedPlaintext.isEmpty() || normalizedKey.isEmpty()) {
            return "";
        }

        StringBuilder extendedKey = new StringBuilder(normalizedKey).append(normalizedPlaintext);
        StringBuilder ciphertext = new StringBuilder(normalizedPlaintext.length());

        for (int i = 0; i < normalizedPlaintext.length(); i++) {
            ciphertext.append(
                encryptChar(
                    normalizedPlaintext.charAt(i),
                    extendedKey.charAt(i)
                )
            );
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
        String normalizedCiphertext = normalize(ciphertext);
        String normalizedKey = normalize(key);

        if (normalizedCiphertext.isEmpty() || normalizedKey.isEmpty()) {
            return "";
        }

        StringBuilder plaintext = new StringBuilder(normalizedCiphertext.length());
        StringBuilder extendedKey = new StringBuilder(normalizedKey);

        for (int i = 0; i < normalizedCiphertext.length(); i++) {
            char decryptedChar = decryptChar(
                normalizedCiphertext.charAt(i),
                extendedKey.charAt(i)
            );
            plaintext.append(decryptedChar);
            extendedKey.append(decryptedChar);
        }

        return plaintext.toString();
    }

    private String normalize(String input) {
        if (input == null || input.isEmpty()) {
            return "";
        }
        return input
            .toUpperCase()
            .replaceAll(NON_ALPHABETIC_REGEX, "");
    }

    private char encryptChar(char plainChar, char keyChar) {
        int plainOffset = plainChar - FIRST_UPPERCASE_LETTER;
        int keyOffset = keyChar - FIRST_UPPERCASE_LETTER;
        int cipherOffset = (plainOffset + keyOffset) % ALPHABET_SIZE;
        return (char) (FIRST_UPPERCASE_LETTER + cipherOffset);
    }

    private char decryptChar(char cipherChar, char keyChar) {
        int cipherOffset = cipherChar - FIRST_UPPERCASE_LETTER;
        int keyOffset = keyChar - FIRST_UPPERCASE_LETTER;
        int plainOffset = (cipherOffset - keyOffset + ALPHABET_SIZE) % ALPHABET_SIZE;
        return (char) (FIRST_UPPERCASE_LETTER + plainOffset);
    }
}