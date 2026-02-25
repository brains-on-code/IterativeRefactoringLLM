package com.thealgorithms.ciphers;

/**
 * Autokey cipher implementation.
 *
 * <p>This class provides methods to encrypt and decrypt text using the Autokey cipher.
 * Only alphabetic characters A–Z are used; all other characters are removed and the
 * text is converted to uppercase.</p>
 */
public class Class1 {

    /**
     * Encrypts the given plaintext using the Autokey cipher.
     *
     * @param plaintext the text to encrypt
     * @param key       the initial key
     * @return the encrypted ciphertext (A–Z only, uppercase)
     */
    public String method1(String plaintext, String key) {
        // Normalize inputs: keep only A–Z and convert to uppercase
        plaintext = plaintext.toUpperCase().replaceAll("[^A-Z]", "");
        key = key.toUpperCase();

        // Build the full key by appending the plaintext to the initial key
        StringBuilder extendedKey = new StringBuilder(key);
        extendedKey.append(plaintext);

        StringBuilder ciphertext = new StringBuilder();

        for (int i = 0; i < plaintext.length(); i++) {
            char plainChar = plaintext.charAt(i);
            char keyChar = extendedKey.charAt(i);

            int cipherCode = (plainChar - 'A' + keyChar - 'A') % 26 + 'A';
            ciphertext.append((char) cipherCode);
        }

        return ciphertext.toString();
    }

    /**
     * Decrypts the given ciphertext using the Autokey cipher.
     *
     * @param ciphertext the text to decrypt
     * @param key        the initial key
     * @return the decrypted plaintext (A–Z only, uppercase)
     */
    public String method2(String ciphertext, String key) {
        // Normalize inputs: keep only A–Z and convert to uppercase
        ciphertext = ciphertext.toUpperCase().replaceAll("[^A-Z]", "");
        key = key.toUpperCase();

        StringBuilder plaintext = new StringBuilder();
        StringBuilder extendedKey = new StringBuilder(key);

        for (int i = 0; i < ciphertext.length(); i++) {
            char cipherChar = ciphertext.charAt(i);
            char keyChar = extendedKey.charAt(i);

            int plainCode = (cipherChar - 'A' - (keyChar - 'A') + 26) % 26 + 'A';
            char plainChar = (char) plainCode;

            plaintext.append(plainChar);
            // In Autokey, recovered plaintext is appended to the key as we go
            extendedKey.append(plainChar);
        }

        return plaintext.toString();
    }
}