package com.thealgorithms.ciphers;

public class Autokey {

    public String encrypt(String plainText, String keyword) {
        String sanitizedPlainText = plainText.toUpperCase().replaceAll("[^A-Z]", "");
        String sanitizedKeyword = keyword.toUpperCase();

        StringBuilder keyStream = new StringBuilder(sanitizedKeyword);
        keyStream.append(sanitizedPlainText);

        StringBuilder cipherText = new StringBuilder();

        for (int index = 0; index < sanitizedPlainText.length(); index++) {
            char plainCharacter = sanitizedPlainText.charAt(index);
            char keyCharacter = keyStream.charAt(index);

            int encryptedCharacterCode = (plainCharacter - 'A' + keyCharacter - 'A') % 26 + 'A';
            cipherText.append((char) encryptedCharacterCode);
        }

        return cipherText.toString();
    }

    public String decrypt(String cipherText, String keyword) {
        String sanitizedCipherText = cipherText.toUpperCase().replaceAll("[^A-Z]", "");
        String sanitizedKeyword = keyword.toUpperCase();

        StringBuilder decryptedText = new StringBuilder();
        StringBuilder keyStream = new StringBuilder(sanitizedKeyword);

        for (int index = 0; index < sanitizedCipherText.length(); index++) {
            char cipherCharacter = sanitizedCipherText.charAt(index);
            char keyCharacter = keyStream.charAt(index);

            int decryptedCharacterCode = (cipherCharacter - 'A' - (keyCharacter - 'A') + 26) % 26 + 'A';
            char decryptedCharacter = (char) decryptedCharacterCode;

            decryptedText.append(decryptedCharacter);
            keyStream.append(decryptedCharacter);
        }

        return decryptedText.toString();
    }
}