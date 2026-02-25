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

        if (sanitizedPlaintext.isEmpty() || sanitizedKeyword.isEmpty()) {
            return "";
        }

        StringBuilder extendedKey = buildExtendedKeyForEncryption(sanitizedKeyword, sanitizedPlaintext);
        StringBuilder ciphertext = new StringBuilder(sanitizedPlaintext.length());

        for (int i = 0; i < sanitizedPlaintext.length(); i++) {
            ciphertext.append(encryptChar(sanitizedPlaintext.charAt(i), extendedKey.charAt(i)));
        }

        return ciphertext.toString();
    }

    public String decrypt(String ciphertext, String keyword) {
        String sanitizedCiphertext = sanitize(ciphertext);
        String sanitizedKeyword = sanitize(keyword);

        if (sanitizedCiphertext.isEmpty() || sanitizedKeyword.isEmpty()) {
            return "";
        }

        StringBuilder plaintext = new StringBuilder(sanitizedCiphertext.length());
        StringBuilder extendedKey = new StringBuilder(sanitizedKeyword);

        for (int i = 0; i < sanitizedCiphertext.length(); i++) {
            char decryptedChar = decryptChar(sanitizedCiphertext.charAt(i), extendedKey.charAt(i));
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
        int encryptedIndex = (toAlphabetIndex(plainChar) + toAlphabetIndex(keyChar)) % ALPHABET_SIZE;
        return toAlphabetChar(encryptedIndex);
    }

    private char decryptChar(char cipherChar, char keyChar) {
        int decryptedIndex =
                (toAlphabetIndex(cipherChar) - toAlphabetIndex(keyChar) + ALPHABET_SIZE) % ALPHABET_SIZE;
        return toAlphabetChar(decryptedIndex);
    }

    private int toAlphabetIndex(char c) {
        return c - FIRST_UPPERCASE_LETTER;
    }

    private char toAlphabetChar(int index) {
        return (char) (index + FIRST_UPPERCASE_LETTER);
    }
}