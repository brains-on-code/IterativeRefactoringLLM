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

    private static final char FIRST_UPPERCASE_LETTER = 'A';
    private static final int ALPHABET_SIZE = 26;

    // Encrypts the plaintext using the Autokey cipher
    public String encrypt(String plainText, String keyWord) {
        String normalizedPlainText = normalizeInput(plainText);
        String normalizedKeyWord = keyWord.toUpperCase();

        StringBuilder keyStreamBuilder = new StringBuilder(normalizedKeyWord);
        keyStreamBuilder.append(normalizedPlainText);

        StringBuilder cipherTextBuilder = new StringBuilder();

        for (int index = 0; index < normalizedPlainText.length(); index++) {
            char plainTextCharacter = normalizedPlainText.charAt(index);
            char keyStreamCharacter = keyStreamBuilder.charAt(index);

            int encryptedCharacterCode =
                (plainTextCharacter - FIRST_UPPERCASE_LETTER
                        + keyStreamCharacter - FIRST_UPPERCASE_LETTER)
                    % ALPHABET_SIZE
                    + FIRST_UPPERCASE_LETTER;

            cipherTextBuilder.append((char) encryptedCharacterCode);
        }

        return cipherTextBuilder.toString();
    }

    // Decrypts the ciphertext using the Autokey cipher
    public String decrypt(String cipherText, String keyWord) {
        String normalizedCipherText = normalizeInput(cipherText);
        String normalizedKeyWord = keyWord.toUpperCase();

        StringBuilder plainTextBuilder = new StringBuilder();
        StringBuilder keyStreamBuilder = new StringBuilder(normalizedKeyWord);

        for (int index = 0; index < normalizedCipherText.length(); index++) {
            char cipherTextCharacter = normalizedCipherText.charAt(index);
            char keyStreamCharacter = keyStreamBuilder.charAt(index);

            int decryptedCharacterCode =
                (cipherTextCharacter
                        - FIRST_UPPERCASE_LETTER
                        - (keyStreamCharacter - FIRST_UPPERCASE_LETTER)
                        + ALPHABET_SIZE)
                    % ALPHABET_SIZE
                    + FIRST_UPPERCASE_LETTER;

            char plainTextCharacter = (char) decryptedCharacterCode;
            plainTextBuilder.append(plainTextCharacter);
            keyStreamBuilder.append(plainTextCharacter);
        }

        return plainTextBuilder.toString();
    }

    private String normalizeInput(String input) {
        return input.toUpperCase().replaceAll("[^A-Z]", "");
    }
}