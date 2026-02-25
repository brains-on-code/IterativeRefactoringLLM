package com.thealgorithms.ciphers;

public class Autokey {

    private static final int ALPHABET_SIZE = 26;
    private static final char FIRST_UPPERCASE_LETTER = 'A';

    public String encrypt(String plainText, String keyword) {
        String normalizedPlainText = normalizeText(plainText);
        String normalizedKeyword = keyword.toUpperCase();

        StringBuilder keyStream = new StringBuilder(normalizedKeyword).append(normalizedPlainText);
        StringBuilder encryptedText = new StringBuilder();

        for (int position = 0; position < normalizedPlainText.length(); position++) {
            char plainChar = normalizedPlainText.charAt(position);
            char keyChar = keyStream.charAt(position);

            int encryptedCharCode =
                (plainChar - FIRST_UPPERCASE_LETTER + keyChar - FIRST_UPPERCASE_LETTER) % ALPHABET_SIZE
                    + FIRST_UPPERCASE_LETTER;

            encryptedText.append((char) encryptedCharCode);
        }

        return encryptedText.toString();
    }

    public String decrypt(String cipherText, String keyword) {
        String normalizedCipherText = normalizeText(cipherText);
        String normalizedKeyword = keyword.toUpperCase();

        StringBuilder decryptedText = new StringBuilder();
        StringBuilder keyStream = new StringBuilder(normalizedKeyword);

        for (int position = 0; position < normalizedCipherText.length(); position++) {
            char cipherChar = normalizedCipherText.charAt(position);
            char keyChar = keyStream.charAt(position);

            int decryptedCharCode =
                (cipherChar - FIRST_UPPERCASE_LETTER - (keyChar - FIRST_UPPERCASE_LETTER) + ALPHABET_SIZE)
                    % ALPHABET_SIZE
                    + FIRST_UPPERCASE_LETTER;

            char decryptedChar = (char) decryptedCharCode;

            decryptedText.append(decryptedChar);
            keyStream.append(decryptedChar);
        }

        return decryptedText.toString();
    }

    private String normalizeText(String text) {
        return text.toUpperCase().replaceAll("[^A-Z]", "");
    }
}