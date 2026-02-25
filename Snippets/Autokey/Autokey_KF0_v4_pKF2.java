package com.thealgorithms.ciphers;

/**
 * Autokey cipher implementation (polyalphabetic substitution).
 *
 * <p>Details: https://en.wikipedia.org/wiki/Autokey_cipher
 *
 * <p>Usage example:
 * <pre>
 *     Autokey autokey = new Autokey();
 *     String cipher = autokey.encrypt("ATTACKATDAWN", "QUEEN");
 *     String plain  = autokey.decrypt(cipher, "QUEEN");
 * </pre>
 *
 * <p>Behavior:
 * <ul>
 *   <li>Only A–Z characters are processed; all others are removed.</li>
 *   <li>Input is converted to uppercase.</li>
 * </ul>
 */
public class Autokey {

    private static final int ALPHABET_SIZE = 26;
    private static final char FIRST_LETTER = 'A';

    /**
     * Encrypts plaintext using the Autokey cipher.
     *
     * @param plaintext the message to encrypt
     * @param keyword   the initial key used to start the autokey sequence
     * @return ciphertext (A–Z only, uppercase)
     */
    public String encrypt(String plaintext, String keyword) {
        String sanitizedPlaintext = sanitize(plaintext);
        String sanitizedKeyword = sanitize(keyword);

        StringBuilder extendedKey = buildExtendedKeyForEncryption(sanitizedPlaintext, sanitizedKeyword);
        StringBuilder ciphertext = new StringBuilder(sanitizedPlaintext.length());

        for (int i = 0; i < sanitizedPlaintext.length(); i++) {
            char plainChar = sanitizedPlaintext.charAt(i);
            char keyChar = extendedKey.charAt(i);
            ciphertext.append(encryptChar(plainChar, keyChar));
        }

        return ciphertext.toString();
    }

    /**
     * Decrypts ciphertext using the Autokey cipher.
     *
     * @param ciphertext the message to decrypt
     * @param keyword    the initial key used to start the autokey sequence
     * @return plaintext (A–Z only, uppercase)
     */
    public String decrypt(String ciphertext, String keyword) {
        String sanitizedCiphertext = sanitize(ciphertext);
        String sanitizedKeyword = sanitize(keyword);

        StringBuilder plaintext = new StringBuilder(sanitizedCiphertext.length());
        StringBuilder extendedKey = new StringBuilder(sanitizedKeyword);

        for (int i = 0; i < sanitizedCiphertext.length(); i++) {
            char cipherChar = sanitizedCiphertext.charAt(i);
            char keyChar = extendedKey.charAt(i);

            char plainChar = decryptChar(cipherChar, keyChar);
            plaintext.append(plainChar);
            extendedKey.append(plainChar);
        }

        return plaintext.toString();
    }

    /**
     * Converts input to uppercase and removes all non A–Z characters.
     *
     * @param input raw input string
     * @return sanitized string containing only A–Z in uppercase
     */
    private String sanitize(String input) {
        if (input == null) {
            return "";
        }
        return input.toUpperCase().replaceAll("[^A-Z]", "");
    }

    /**
     * Builds the extended key for encryption by appending the plaintext to the keyword.
     */
    private StringBuilder buildExtendedKeyForEncryption(String plaintext, String keyword) {
        return new StringBuilder(keyword).append(plaintext);
    }

    /**
     * Encrypts a single character using the given key character.
     */
    private char encryptChar(char plainChar, char keyChar) {
        int encryptedChar =
                (plainChar - FIRST_LETTER + keyChar - FIRST_LETTER) % ALPHABET_SIZE + FIRST_LETTER;
        return (char) encryptedChar;
    }

    /**
     * Decrypts a single character using the given key character.
     */
    private char decryptChar(char cipherChar, char keyChar) {
        int decryptedChar =
                (cipherChar - FIRST_LETTER - (keyChar - FIRST_LETTER) + ALPHABET_SIZE) % ALPHABET_SIZE
                        + FIRST_LETTER;
        return (char) decryptedChar;
    }
}