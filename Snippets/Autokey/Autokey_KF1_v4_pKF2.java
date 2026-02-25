package com.thealgorithms.ciphers;

/**
 * Autokey cipher implementation.
 *
 * <p>Encrypts and decrypts text using the Autokey cipher. Input is normalized to
 * uppercase A–Z and all non-alphabetic characters are removed.</p>
 */
public class AutokeyCipher {

    private static final int ALPHABET_SIZE = 26;
    private static final char FIRST_LETTER = 'A';

    /**
     * Encrypts plaintext using the Autokey cipher.
     *
     * @param plaintext the text to encrypt
     * @param key       the initial key
     * @return ciphertext in uppercase A–Z
     */
    public String encrypt(String plaintext, String key) {
        String normalizedPlaintext = normalize(plaintext);
        String normalizedKey = normalize(key);

        StringBuilder extendedKey = new StringBuilder(normalizedKey).append(normalizedPlaintext);
        StringBuilder ciphertext = new StringBuilder(normalizedPlaintext.length());

        for (int i = 0; i < normalizedPlaintext.length(); i++) {
            char plainChar = normalizedPlaintext.charAt(i);
            char keyChar = extendedKey.charAt(i);

            int cipherCode = encodeCharacter(plainChar, keyChar);
            ciphertext.append((char) cipherCode);
        }

        return ciphertext.toString();
    }

    /**
     * Decrypts ciphertext using the Autokey cipher.
     *
     * @param ciphertext the text to decrypt
     * @param key        the initial key
     * @return plaintext in uppercase A–Z
     */
    public String decrypt(String ciphertext, String key) {
        String normalizedCiphertext = normalize(ciphertext);
        String normalizedKey = normalize(key);

        StringBuilder plaintext = new StringBuilder(normalizedCiphertext.length());
        StringBuilder extendedKey = new StringBuilder(normalizedKey);

        for (int i = 0; i < normalizedCiphertext.length(); i++) {
            char cipherChar = normalizedCiphertext.charAt(i);
            char keyChar = extendedKey.charAt(i);

            int plainCode = decodeCharacter(cipherChar, keyChar);
            char plainChar = (char) plainCode;

            plaintext.append(plainChar);
            extendedKey.append(plainChar);
        }

        return plaintext.toString();
    }

    /**
     * Normalizes text by removing non-alphabetic characters and converting to uppercase A–Z.
     *
     * @param text input text
     * @return normalized text
     */
    private String normalize(String text) {
        return text.toUpperCase().replaceAll("[^A-Z]", "");
    }

    /**
     * Encodes a single character using the Autokey cipher formula.
     *
     * @param plainChar the plaintext character
     * @param keyChar   the key character
     * @return encoded character code
     */
    private int encodeCharacter(char plainChar, char keyChar) {
        return (plainChar - FIRST_LETTER + keyChar - FIRST_LETTER) % ALPHABET_SIZE + FIRST_LETTER;
    }

    /**
     * Decodes a single character using the Autokey cipher formula.
     *
     * @param cipherChar the ciphertext character
     * @param keyChar    the key character
     * @return decoded character code
     */
    private int decodeCharacter(char cipherChar, char keyChar) {
        return (cipherChar - FIRST_LETTER - (keyChar - FIRST_LETTER) + ALPHABET_SIZE) % ALPHABET_SIZE + FIRST_LETTER;
    }
}