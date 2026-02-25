package com.thealgorithms.ciphers;

/**
 * Implements the Autokey cipher, a polyalphabetic substitution cipher that
 * extends the key using the plaintext itself.
 *
 * <p>More details: https://en.wikipedia.org/wiki/Autokey_cipher
 *
 * <p>Example:
 * <pre>
 *     Autokey autokey = new Autokey();
 *     String cipher = autokey.encrypt("ATTACKATDAWN", "QUEEN");
 *     String plain  = autokey.decrypt(cipher, "QUEEN");
 * </pre>
 *
 * <p>Only A–Z characters are processed; all others are removed. Input is
 * converted to uppercase.
 */
public class Autokey {

    private static final int ALPHABET_SIZE = 26;
    private static final char FIRST_LETTER = 'A';

    /**
     * Encrypts the given plaintext using the Autokey cipher.
     *
     * @param plaintext the message to encrypt
     * @param keyword   the initial key used to start the autokey sequence
     * @return the resulting ciphertext (A–Z only, uppercase)
     */
    public String encrypt(String plaintext, String keyword) {
        String sanitizedPlaintext = sanitize(plaintext);
        String sanitizedKeyword = sanitize(keyword);

        StringBuilder extendedKey = new StringBuilder(sanitizedKeyword)
                .append(sanitizedPlaintext);

        StringBuilder ciphertext = new StringBuilder(sanitizedPlaintext.length());

        for (int i = 0; i < sanitizedPlaintext.length(); i++) {
            char plainChar = sanitizedPlaintext.charAt(i);
            char keyChar = extendedKey.charAt(i);

            int encryptedChar = (plainChar - FIRST_LETTER + keyChar - FIRST_LETTER) % ALPHABET_SIZE + FIRST_LETTER;
            ciphertext.append((char) encryptedChar);
        }

        return ciphertext.toString();
    }

    /**
     * Decrypts the given ciphertext using the Autokey cipher.
     *
     * @param ciphertext the message to decrypt
     * @param keyword    the initial key used to start the autokey sequence
     * @return the resulting plaintext (A–Z only, uppercase)
     */
    public String decrypt(String ciphertext, String keyword) {
        String sanitizedCiphertext = sanitize(ciphertext);
        String sanitizedKeyword = sanitize(keyword);

        StringBuilder plaintext = new StringBuilder(sanitizedCiphertext.length());
        StringBuilder extendedKey = new StringBuilder(sanitizedKeyword);

        for (int i = 0; i < sanitizedCiphertext.length(); i++) {
            char cipherChar = sanitizedCiphertext.charAt(i);
            char keyChar = extendedKey.charAt(i);

            int decryptedChar =
                    (cipherChar - FIRST_LETTER - (keyChar - FIRST_LETTER) + ALPHABET_SIZE) % ALPHABET_SIZE
                            + FIRST_LETTER;
            char plainChar = (char) decryptedChar;

            plaintext.append(plainChar);
            extendedKey.append(plainChar);
        }

        return plaintext.toString();
    }

    /**
     * Converts input to uppercase and removes all non A–Z characters.
     *
     * @param input the raw input string
     * @return sanitized string containing only A–Z in uppercase
     */
    private String sanitize(String input) {
        if (input == null) {
            return "";
        }
        return input.toUpperCase().replaceAll("[^A-Z]", "");
    }
}