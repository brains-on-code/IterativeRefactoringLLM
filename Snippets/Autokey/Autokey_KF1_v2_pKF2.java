package com.thealgorithms.ciphers;

/**
 * Autokey cipher implementation.
 *
 * <p>This class provides methods to encrypt and decrypt text using the Autokey cipher.
 * Only alphabetic characters A–Z are used; all other characters are removed and the
 * text is converted to uppercase.</p>
 */
public class AutokeyCipher {

    private static final int ALPHABET_SIZE = 26;
    private static final char FIRST_LETTER = 'A';

    /**
     * Encrypts the given plaintext using the Autokey cipher.
     *
     * @param plaintext the text to encrypt
     * @param key       the initial key
     * @return the encrypted ciphertext (A–Z only, uppercase)
     */
    public String encrypt(String plaintext, String key) {
        String normalizedPlaintext = normalize(plaintext);
        String normalizedKey = normalize(key);

        StringBuilder extendedKey = new StringBuilder(normalizedKey).append(normalizedPlaintext);
        StringBuilder ciphertext = new StringBuilder();

        for (int i = 0; i < normalizedPlaintext.length(); i++) {
            char plainChar = normalizedPlaintext.charAt(i);
            char keyChar = extendedKey.charAt(i);

            int cipherCode = (plainChar - FIRST_LETTER + keyChar - FIRST_LETTER) % ALPHABET_SIZE + FIRST_LETTER;
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
    public String decrypt(String ciphertext, String key) {
        String normalizedCiphertext = normalize(ciphertext);
        String normalizedKey = normalize(key);

        StringBuilder plaintext = new StringBuilder();
        StringBuilder extendedKey = new StringBuilder(normalizedKey);

        for (int i = 0; i < normalizedCiphertext.length(); i++) {
            char cipherChar = normalizedCiphertext.charAt(i);
            char keyChar = extendedKey.charAt(i);

            int plainCode =
                    (cipherChar - FIRST_LETTER - (keyChar - FIRST_LETTER) + ALPHABET_SIZE) % ALPHABET_SIZE
                            + FIRST_LETTER;
            char plainChar = (char) plainCode;

            plaintext.append(plainChar);
            extendedKey.append(plainChar);
        }

        return plaintext.toString();
    }

    /**
     * Normalizes input text by removing non-alphabetic characters and converting to uppercase.
     *
     * @param text the input text
     * @return normalized text containing only A–Z in uppercase
     */
    private String normalize(String text) {
        return text.toUpperCase().replaceAll("[^A-Z]", "");
    }
}