package com.thealgorithms.ciphers;

/**
 * Autokey cipher implementation.
 */
public class AutokeyCipher {

    /**
     * Encrypts the given plaintext using the provided key with the Autokey cipher.
     *
     * @param plaintext the text to encrypt
     * @param key       the encryption key
     * @return the resulting ciphertext (A–Z only)
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

            int encryptedCharCode = (plainChar - 'A' + keyChar - 'A') % 26 + 'A';
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
        String normalizedCiphertext = ciphertext.toUpperCase().replaceAll("[^A-Z]", "");
        String normalizedKey = key.toUpperCase();

        StringBuilder plaintext = new StringBuilder();
        StringBuilder evolvingKey = new StringBuilder(normalizedKey);

        for (int i = 0; i < normalizedCiphertext.length(); i++) {
            char cipherChar = normalizedCiphertext.charAt(i);
            char keyChar = evolvingKey.charAt(i);

            int decryptedCharCode = (cipherChar - 'A' - (keyChar - 'A') + 26) % 26 + 'A';
            char decryptedChar = (char) decryptedCharCode;

            plaintext.append(decryptedChar);
            evolvingKey.append(decryptedChar);
        }

        return plaintext.toString();
    }
}