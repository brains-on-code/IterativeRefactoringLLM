package com.thealgorithms.ciphers;

public class Autokey {

    private static final int ALPHABET_SIZE = 26;
    private static final char FIRST_UPPERCASE_LETTER = 'A';

    public String encrypt(String plaintext, String keyword) {
        String normalizedPlaintext = normalizeText(plaintext);
        String normalizedKeyword = normalizeText(keyword);

        StringBuilder extendedKey = new StringBuilder(normalizedKeyword)
            .append(normalizedPlaintext);

        StringBuilder ciphertext = new StringBuilder();

        for (int i = 0; i < normalizedPlaintext.length(); i++) {
            char plainChar = normalizedPlaintext.charAt(i);
            char keyChar = extendedKey.charAt(i);
            ciphertext.append(encryptChar(plainChar, keyChar));
        }

        return ciphertext.toString();
    }

    public String decrypt(String ciphertext, String keyword) {
        String normalizedCiphertext = normalizeText(ciphertext);
        String normalizedKeyword = normalizeText(keyword);

        StringBuilder plaintext = new StringBuilder();
        StringBuilder extendedKey = new StringBuilder(normalizedKeyword);

        for (int i = 0; i < normalizedCiphertext.length(); i++) {
            char cipherChar = normalizedCiphertext.charAt(i);
            char keyChar = extendedKey.charAt(i);

            char plainChar = decryptChar(cipherChar, keyChar);
            plaintext.append(plainChar);
            extendedKey.append(plainChar);
        }

        return plaintext.toString();
    }

    private char encryptChar(char plainChar, char keyChar) {
        int plainOffset = plainChar - FIRST_UPPERCASE_LETTER;
        int keyOffset = keyChar - FIRST_UPPERCASE_LETTER;
        int encryptedOffset = (plainOffset + keyOffset) % ALPHABET_SIZE;
        return (char) (encryptedOffset + FIRST_UPPERCASE_LETTER);
    }

    private char decryptChar(char cipherChar, char keyChar) {
        int cipherOffset = cipherChar - FIRST_UPPERCASE_LETTER;
        int keyOffset = keyChar - FIRST_UPPERCASE_LETTER;
        int decryptedOffset = (cipherOffset - keyOffset + ALPHABET_SIZE) % ALPHABET_SIZE;
        return (char) (decryptedOffset + FIRST_UPPERCASE_LETTER);
    }

    private String normalizeText(String text) {
        if (text == null) {
            return "";
        }
        return text.toUpperCase().replaceAll("[^A-Z]", "");
    }
}