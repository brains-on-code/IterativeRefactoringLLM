package com.thealgorithms.ciphers;

/**
 * The Autokey Cipher is an interesting and historically significant encryption method,
 * as it improves upon the classic Vigenère Cipher by using the plaintext itself to
 * extend the key. This makes it harder to break using frequency analysis, as it
 * doesn’t rely solely on a repeated key.
 * https://en.wikipedia.org/wiki/Autokey_cipher
 *
 * @author bennybebo
 */
public class Autokey {

    private static final int ALPHABET_SIZE = 26;
    private static final char FIRST_UPPERCASE_LETTER = 'A';

    public String encrypt(String plaintext, String keyword) {
        String sanitizedPlaintext = sanitizeInput(plaintext);
        String sanitizedKeyword = keyword.toUpperCase();

        StringBuilder extendedKey = new StringBuilder(sanitizedKeyword).append(sanitizedPlaintext);
        StringBuilder ciphertext = new StringBuilder(sanitizedPlaintext.length());

        for (int i = 0; i < sanitizedPlaintext.length(); i++) {
            char plainChar = sanitizedPlaintext.charAt(i);
            char keyChar = extendedKey.charAt(i);
            ciphertext.append(encryptChar(plainChar, keyChar));
        }

        return ciphertext.toString();
    }

    public String decrypt(String ciphertext, String keyword) {
        String sanitizedCiphertext = sanitizeInput(ciphertext);
        String sanitizedKeyword = keyword.toUpperCase();

        StringBuilder plaintext = new StringBuilder(sanitizedCiphertext.length());
        StringBuilder extendedKey = new StringBuilder(sanitizedKeyword);

        for (int i = 0; i < sanitizedCiphertext.length(); i++) {
            char cipherChar = sanitizedCiphertext.charAt(i);
            char keyChar = extendedKey.charAt(i);
            char decryptedChar = decryptChar(cipherChar, keyChar);

            plaintext.append(decryptedChar);
            extendedKey.append(decryptedChar);
        }

        return plaintext.toString();
    }

    private String sanitizeInput(String input) {
        return input.toUpperCase().replaceAll("[^A-Z]", "");
    }

    private char encryptChar(char plainChar, char keyChar) {
        int plainIndex = plainChar - FIRST_UPPERCASE_LETTER;
        int keyIndex = keyChar - FIRST_UPPERCASE_LETTER;
        int encryptedIndex = (plainIndex + keyIndex) % ALPHABET_SIZE;
        return (char) (encryptedIndex + FIRST_UPPERCASE_LETTER);
    }

    private char decryptChar(char cipherChar, char keyChar) {
        int cipherIndex = cipherChar - FIRST_UPPERCASE_LETTER;
        int keyIndex = keyChar - FIRST_UPPERCASE_LETTER;
        int decryptedIndex = (cipherIndex - keyIndex + ALPHABET_SIZE) % ALPHABET_SIZE;
        return (char) (decryptedIndex + FIRST_UPPERCASE_LETTER);
    }
}