package com.thealgorithms.ciphers;

public class Autokey {

    public String encrypt(String plainText, String keyword) {
        String normalizedPlainText = plainText.toUpperCase().replaceAll("[^A-Z]", "");
        String normalizedKeyword = keyword.toUpperCase();

        StringBuilder keyStreamBuilder = new StringBuilder(normalizedKeyword);
        keyStreamBuilder.append(normalizedPlainText);

        StringBuilder encryptedTextBuilder = new StringBuilder();

        for (int position = 0; position < normalizedPlainText.length(); position++) {
            char plainChar = normalizedPlainText.charAt(position);
            char keyChar = keyStreamBuilder.charAt(position);

            int encryptedCharCode = (plainChar - 'A' + keyChar - 'A') % 26 + 'A';
            encryptedTextBuilder.append((char) encryptedCharCode);
        }

        return encryptedTextBuilder.toString();
    }

    public String decrypt(String cipherText, String keyword) {
        String normalizedCipherText = cipherText.toUpperCase().replaceAll("[^A-Z]", "");
        String normalizedKeyword = keyword.toUpperCase();

        StringBuilder decryptedTextBuilder = new StringBuilder();
        StringBuilder keyStreamBuilder = new StringBuilder(normalizedKeyword);

        for (int position = 0; position < normalizedCipherText.length(); position++) {
            char cipherChar = normalizedCipherText.charAt(position);
            char keyChar = keyStreamBuilder.charAt(position);

            int decryptedCharCode = (cipherChar - 'A' - (keyChar - 'A') + 26) % 26 + 'A';
            char decryptedChar = (char) decryptedCharCode;

            decryptedTextBuilder.append(decryptedChar);
            keyStreamBuilder.append(decryptedChar);
        }

        return decryptedTextBuilder.toString();
    }
}