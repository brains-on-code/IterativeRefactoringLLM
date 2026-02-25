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

            int encryptedChar = encryptChar(plainChar, keyChar);
            ciphertext.append((char) encryptedChar);
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

            int decryptedChar = decryptChar(cipherChar, keyChar);
            char plainChar = (char) decryptedChar;

            plaintext.append(plainChar);
            extendedKey.append(plainChar);
        }

        return plaintext.toString();
    }

    private int encryptChar(char plainChar, char keyChar) {
        int plainOffset = plainChar - FIRST_UPPERCASE_LETTER;
        int keyOffset = keyChar - FIRST_UPPERCASE_LETTER;

        return (plainOffset + keyOffset) % ALPHABET_SIZE + FIRST_UPPERCASE_LETTER;
    }

    private int decryptChar(char cipherChar, char keyChar) {
        int cipherOffset = cipherChar - FIRST_UPPERCASE_LETTER;
        int keyOffset = keyChar - FIRST_UPPERCASE_LETTER;

        return (cipherOffset - keyOffset + ALPHABET_SIZE) % ALPHABET_SIZE + FIRST_UPPERCASE_LETTER;
    }

    private String normalizeText(String text) {
        return text.toUpperCase().replaceAll("[^A-Z]", "");
    }
}