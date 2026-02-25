package com.thealgorithms.ciphers;

public class Autokey {

    private static final int ALPHABET_SIZE = 26;
    private static final char BASE_CHAR = 'A';

    public String encrypt(String plaintext, String keyword) {
        String normalizedPlaintext = normalizeText(plaintext);
        String normalizedKeyword = normalizeText(keyword);

        StringBuilder extendedKey = new StringBuilder(normalizedKeyword).append(normalizedPlaintext);
        StringBuilder ciphertext = new StringBuilder();

        for (int i = 0; i < normalizedPlaintext.length(); i++) {
            char plainChar = normalizedPlaintext.charAt(i);
            char keyChar = extendedKey.charAt(i);

            int encryptedChar = (plainChar - BASE_CHAR + keyChar - BASE_CHAR) % ALPHABET_SIZE + BASE_CHAR;
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

            int decryptedChar =
                    (cipherChar - BASE_CHAR - (keyChar - BASE_CHAR) + ALPHABET_SIZE) % ALPHABET_SIZE + BASE_CHAR;
            char decryptedCharAsChar = (char) decryptedChar;

            plaintext.append(decryptedCharAsChar);
            extendedKey.append(decryptedCharAsChar);
        }

        return plaintext.toString();
    }

    private String normalizeText(String text) {
        return text.toUpperCase().replaceAll("[^A-Z]", "");
    }
}