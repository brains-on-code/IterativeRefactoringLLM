package com.thealgorithms.ciphers;

public class Autokey {

    private static final int ALPHABET_SIZE = 26;
    private static final char FIRST_UPPERCASE_LETTER = 'A';

    public String encrypt(String plainText, String keyword) {
        String normalizedPlainText = normalizeText(plainText);
        String normalizedKeyword = keyword.toUpperCase();

        StringBuilder keyStream = new StringBuilder(normalizedKeyword).append(normalizedPlainText);
        StringBuilder cipherTextBuilder = new StringBuilder();

        for (int index = 0; index < normalizedPlainText.length(); index++) {
            char plainCharacter = normalizedPlainText.charAt(index);
            char keyCharacter = keyStream.charAt(index);

            int encryptedCharacterCode =
                (plainCharacter - FIRST_UPPERCASE_LETTER + keyCharacter - FIRST_UPPERCASE_LETTER) % ALPHABET_SIZE
                    + FIRST_UPPERCASE_LETTER;

            cipherTextBuilder.append((char) encryptedCharacterCode);
        }

        return cipherTextBuilder.toString();
    }

    public String decrypt(String cipherText, String keyword) {
        String normalizedCipherText = normalizeText(cipherText);
        String normalizedKeyword = keyword.toUpperCase();

        StringBuilder plainTextBuilder = new StringBuilder();
        StringBuilder keyStream = new StringBuilder(normalizedKeyword);

        for (int index = 0; index < normalizedCipherText.length(); index++) {
            char cipherCharacter = normalizedCipherText.charAt(index);
            char keyCharacter = keyStream.charAt(index);

            int decryptedCharacterCode =
                (cipherCharacter - FIRST_UPPERCASE_LETTER - (keyCharacter - FIRST_UPPERCASE_LETTER) + ALPHABET_SIZE)
                    % ALPHABET_SIZE
                    + FIRST_UPPERCASE_LETTER;

            char decryptedCharacter = (char) decryptedCharacterCode;

            plainTextBuilder.append(decryptedCharacter);
            keyStream.append(decryptedCharacter);
        }

        return plainTextBuilder.toString();
    }

    private String normalizeText(String text) {
        return text.toUpperCase().replaceAll("[^A-Z]", "");
    }
}