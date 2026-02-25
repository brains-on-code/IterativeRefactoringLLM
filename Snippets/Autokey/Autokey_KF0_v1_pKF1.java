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
        String sanitizedPlainText = sanitizeInput(plainText);
        String sanitizedKeyWord = keyWord.toUpperCase();

        StringBuilder keyStream = new StringBuilder(sanitizedKeyWord);
        keyStream.append(sanitizedPlainText);

        StringBuilder cipherText = new StringBuilder();

        for (int index = 0; index < sanitizedPlainText.length(); index++) {
            char plainCharacter = sanitizedPlainText.charAt(index);
            char keyCharacter = keyStream.charAt(index);

            int encryptedCharacter =
                (plainCharacter - FIRST_UPPERCASE_LETTER + keyCharacter - FIRST_UPPERCASE_LETTER)
                    % ALPHABET_SIZE
                    + FIRST_UPPERCASE_LETTER;

            cipherText.append((char) encryptedCharacter);
        }

        return cipherText.toString();
    }

    // Decrypts the ciphertext using the Autokey cipher
    public String decrypt(String cipherText, String keyWord) {
        String sanitizedCipherText = sanitizeInput(cipherText);
        String sanitizedKeyWord = keyWord.toUpperCase();

        StringBuilder plainText = new StringBuilder();
        StringBuilder keyStream = new StringBuilder(sanitizedKeyWord);

        for (int index = 0; index < sanitizedCipherText.length(); index++) {
            char cipherCharacter = sanitizedCipherText.charAt(index);
            char keyCharacter = keyStream.charAt(index);

            int decryptedCharacter =
                (cipherCharacter
                        - FIRST_UPPERCASE_LETTER
                        - (keyCharacter - FIRST_UPPERCASE_LETTER)
                        + ALPHABET_SIZE)
                    % ALPHABET_SIZE
                    + FIRST_UPPERCASE_LETTER;

            char plainCharacter = (char) decryptedCharacter;
            plainText.append(plainCharacter);
            keyStream.append(plainCharacter);
        }

        return plainText.toString();
    }

    private String sanitizeInput(String input) {
        return input.toUpperCase().replaceAll("[^A-Z]", "");
    }
}