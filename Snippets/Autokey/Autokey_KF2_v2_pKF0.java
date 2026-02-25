package com.thealgorithms.ciphers;

public class Autokey {

    private static final int ALPHABET_SIZE = 26;
    private static final char BASE_CHAR = 'A';

    public String encrypt(String plaintext, String keyword) {
        String normalizedPlaintext = normalizeText(plaintext);
        String normalizedKeyword = normalizeText(keyword);

        StringBuilder extendedKey = new StringBuilder(normalizedKeyword)
                .append(normalizedPlaintext);
        StringBuilder ciphertext = new StringBuilder(normalizedPlaintext.length());

        for (int i = 0; i < normalizedPlaintext.length(); i++) {
            char plainChar = normalizedPlaintext.charAt(i);
            char keyChar = extendedKey.charAt(i);

            char encryptedChar = shiftChar(plainChar, keyChar);
            ciphertext.append(encryptedChar);
        }

        return ciphertext.toString();
    }

    public String decrypt(String ciphertext, String keyword) {
        String normalizedCiphertext = normalizeText(ciphertext);
        String normalizedKeyword = normalizeText(keyword);

        StringBuilder plaintext = new StringBuilder(normalizedCiphertext.length());
        StringBuilder extendedKey = new StringBuilder(normalizedKeyword);

        for (int i = 0; i < normalizedCiphertext.length(); i++) {
            char cipherChar = normalizedCiphertext.charAt(i);
            char keyChar = extendedKey.charAt(i);

            char decryptedChar = unshiftChar(cipherChar, keyChar);
            plaintext.append(decryptedChar);
            extendedKey.append(decryptedChar);
        }

        return plaintext.toString();
    }

    private String normalizeText(String text) {
        if (text == null) {
            return "";
        }
        return text.toUpperCase().replaceAll("[^A-Z]", "");
    }

    private char shiftChar(char plainChar, char keyChar) {
        int shifted = (plainChar - BASE_CHAR + keyChar - BASE_CHAR) % ALPHABET_SIZE + BASE_CHAR;
        return (char) shifted;
    }

    private char unshiftChar(char cipherChar, char keyChar) {
        int shifted = (cipherChar - BASE_CHAR - (keyChar - BASE_CHAR) + ALPHABET_SIZE) % ALPHABET_SIZE + BASE_CHAR;
        return (char) shifted;
    }
}