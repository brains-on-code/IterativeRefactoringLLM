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
        String normalizedPlainText = normalizeToUppercaseLetters(plainText);
        String normalizedKeyWord = keyWord.toUpperCase();

        StringBuilder keyStream = new StringBuilder(normalizedKeyWord);
        keyStream.append(normalizedPlainText);

        StringBuilder cipherText = new StringBuilder();

        for (int position = 0; position < normalizedPlainText.length(); position++) {
            char plainChar = normalizedPlainText.charAt(position);
            char keyChar = keyStream.charAt(position);

            int encryptedCharCode =
                (plainChar - FIRST_UPPERCASE_LETTER
                        + keyChar - FIRST_UPPERCASE_LETTER)
                    % ALPHABET_SIZE
                    + FIRST_UPPERCASE_LETTER;

            cipherText.append((char) encryptedCharCode);
        }

        return cipherText.toString();
    }

    // Decrypts the ciphertext using the Autokey cipher
    public String decrypt(String cipherText, String keyWord) {
        String normalizedCipherText = normalizeToUppercaseLetters(cipherText);
        String normalizedKeyWord = keyWord.toUpperCase();

        StringBuilder plainText = new StringBuilder();
        StringBuilder keyStream = new StringBuilder(normalizedKeyWord);

        for (int position = 0; position < normalizedCipherText.length(); position++) {
            char cipherChar = normalizedCipherText.charAt(position);
            char keyChar = keyStream.charAt(position);

            int decryptedCharCode =
                (cipherChar
                        - FIRST_UPPERCASE_LETTER
                        - (keyChar - FIRST_UPPERCASE_LETTER)
                        + ALPHABET_SIZE)
                    % ALPHABET_SIZE
                    + FIRST_UPPERCASE_LETTER;

            char plainChar = (char) decryptedCharCode;
            plainText.append(plainChar);
            keyStream.append(plainChar);
        }

        return plainText.toString();
    }

    private String normalizeToUppercaseLetters(String input) {
        return input.toUpperCase().replaceAll("[^A-Z]", "");
    }
}