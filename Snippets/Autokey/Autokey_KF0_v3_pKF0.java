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
    private static final String NON_ALPHABETIC_REGEX = "[^A-Z]";

    public String encrypt(String plaintext, String keyword) {
        String sanitizedPlaintext = sanitize(plaintext);
        String sanitizedKeyword = sanitize(keyword);

        StringBuilder extendedKey = buildExtendedKeyForEncryption(sanitizedKeyword, sanitizedPlaintext);
        StringBuilder ciphertext = new StringBuilder(sanitizedPlaintext.length());

        for (int i = 0; i < sanitizedPlaintext.length(); i++) {
            char plainChar = sanitizedPlaintext.charAt(i);
            char keyChar = extendedKey.charAt(i);
            ciphertext.append(encryptChar(plainChar, keyChar));
        }

        return ciphertext.toString();
    }

    public String decrypt(String ciphertext, String keyword) {
        String sanitizedCiphertext = sanitize(ciphertext);
        String sanitizedKeyword = sanitize(keyword);

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

    private String sanitize(String input) {
        if (input == null) {
            return "";
        }
        return input.toUpperCase().replaceAll(NON_ALPHABETIC_REGEX, "");
    }

    private StringBuilder buildExtendedKeyForEncryption(String keyword, String plaintext) {
        return new StringBuilder(keyword).append(plaintext);
    }

    private char encryptChar(char plainChar, char keyChar) {
        int plainIndex = toAlphabetIndex(plainChar);
        int keyIndex = toAlphabetIndex(keyChar);
        int encryptedIndex = (plainIndex + keyIndex) % ALPHABET_SIZE;
        return toAlphabetChar(encryptedIndex);
    }

    private char decryptChar(char cipherChar, char keyChar) {
        int cipherIndex = toAlphabetIndex(cipherChar);
        int keyIndex = toAlphabetIndex(keyChar);
        int decryptedIndex = (cipherIndex - keyIndex + ALPHABET_SIZE) % ALPHABET_SIZE;
        return toAlphabetChar(decryptedIndex);
    }

    private int toAlphabetIndex(char c) {
        return c - FIRST_UPPERCASE_LETTER;
    }

    private char toAlphabetChar(int index) {
        return (char) (index + FIRST_UPPERCASE_LETTER);
    }
}