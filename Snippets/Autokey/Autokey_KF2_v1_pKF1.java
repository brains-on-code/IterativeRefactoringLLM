package com.thealgorithms.ciphers;

public class Autokey {

    public String encrypt(String plainText, String keyWord) {
        String normalizedPlainText = plainText.toUpperCase().replaceAll("[^A-Z]", "");
        String normalizedKeyWord = keyWord.toUpperCase();

        StringBuilder keyStream = new StringBuilder(normalizedKeyWord);
        keyStream.append(normalizedPlainText);

        StringBuilder cipherText = new StringBuilder();

        for (int index = 0; index < normalizedPlainText.length(); index++) {
            char plainCharacter = normalizedPlainText.charAt(index);
            char keyCharacter = keyStream.charAt(index);

            int encryptedCharacterCode =
                (plainCharacter - 'A' + keyCharacter - 'A') % 26 + 'A';
            cipherText.append((char) encryptedCharacterCode);
        }

        return cipherText.toString();
    }

    public String decrypt(String cipherText, String keyWord) {
        String normalizedCipherText = cipherText.toUpperCase().replaceAll("[^A-Z]", "");
        String normalizedKeyWord = keyWord.toUpperCase();

        StringBuilder plainText = new StringBuilder();
        StringBuilder keyStream = new StringBuilder(normalizedKeyWord);

        for (int index = 0; index < normalizedCipherText.length(); index++) {
            char cipherCharacter = normalizedCipherText.charAt(index);
            char keyCharacter = keyStream.charAt(index);

            int decryptedCharacterCode =
                (cipherCharacter - 'A' - (keyCharacter - 'A') + 26) % 26 + 'A';
            char decryptedCharacter = (char) decryptedCharacterCode;

            plainText.append(decryptedCharacter);
            keyStream.append(decryptedCharacter);
        }

        return plainText.toString();
    }
}