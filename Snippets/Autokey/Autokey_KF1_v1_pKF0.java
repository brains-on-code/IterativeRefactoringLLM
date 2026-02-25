package com.thealgorithms.ciphers;

/**
 * Implements an autokey-style cipher with encryption and decryption methods.
 */
public class Class1 {

    /**
     * Encrypts the given plaintext using the provided key.
     *
     * @param plaintext the text to encrypt
     * @param key       the encryption key
     * @return the encrypted text (ciphertext)
     */
    public String encrypt(String plaintext, String key) {
        String normalizedPlaintext = plaintext.toUpperCase().replaceAll("[^A-Z]", "");
        String normalizedKey = key.toUpperCase();

        StringBuilder extendedKey = new StringBuilder(normalizedKey);
        extendedKey.append(normalizedPlaintext);

        StringBuilder ciphertext = new StringBuilder();

        for (int i = 0; i < normalizedPlaintext.length(); i++) {
            char plainChar = normalizedPlaintext.charAt(i);
            char keyChar = extendedKey.charAt(i);

            int encryptedChar = (plainChar - 'A' + keyChar - 'A') % 26 + 'A';
            ciphertext.append((char) encryptedChar);
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
        String normalizedCiphertext = ciphertext.toUpperCase().replaceAll("[^A-Z]", "");
        String normalizedKey = key.toUpperCase();

        StringBuilder plaintext = new StringBuilder();
        StringBuilder extendedKey = new StringBuilder(normalizedKey);

        for (int i = 0; i < normalizedCiphertext.length(); i++) {
            char cipherChar = normalizedCiphertext.charAt(i);
            char keyChar = extendedKey.charAt(i);

            int decryptedChar = (cipherChar - 'A' - (keyChar - 'A') + 26) % 26 + 'A';
            plaintext.append((char) decryptedChar);

            extendedKey.append((char) decryptedChar);
        }

        return plaintext.toString();
    }
}