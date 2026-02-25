package com.thealgorithms.ciphers;

import java.util.Arrays;

/**
 * Implementation of the Rail Fence Cipher.
 *
 * <p>The Rail Fence Cipher is a form of transposition cipher that writes the
 * plaintext in a zigzag pattern across a given number of "rails" and then
 * reads off each rail to produce the ciphertext.
 */
public class RailFenceCipher {

    /**
     * Encrypts the given text using the Rail Fence Cipher.
     *
     * @param text  the plaintext to encrypt
     * @param rails the number of rails to use
     * @return the encrypted text
     */
    public String encrypt(String text, int rails) {
        if (rails <= 1 || rails >= text.length()) {
            return text;
        }

        char[][] railMatrix = new char[rails][text.length()];
        for (char[] row : railMatrix) {
            Arrays.fill(row, '\n');
        }

        boolean movingDown = true;
        int currentRow = 0;
        int currentCol = 0;

        for (int i = 0; i < text.length(); i++) {
            railMatrix[currentRow][currentCol++] = text.charAt(i);

            if (currentRow == 0) {
                movingDown = true;
            } else if (currentRow == rails - 1) {
                movingDown = false;
            }

            currentRow += movingDown ? 1 : -1;
        }

        StringBuilder encrypted = new StringBuilder();
        for (char[] railRow : railMatrix) {
            for (char ch : railRow) {
                if (ch != '\n') {
                    encrypted.append(ch);
                }
            }
        }

        return encrypted.toString();
    }

    /**
     * Decrypts the given ciphertext using the Rail Fence Cipher.
     *
     * @param cipherText the text to decrypt
     * @param rails      the number of rails used during encryption
     * @return the decrypted (original) text
     */
    public String decrypt(String cipherText, int rails) {
        if (rails <= 1 || rails >= cipherText.length()) {
            return cipherText;
        }

        char[][] railMatrix = new char[rails][cipherText.length()];

        boolean movingDown = true;
        int currentRow = 0;
        int currentCol = 0;

        // Mark the zigzag pattern with placeholders
        for (int i = 0; i < cipherText.length(); i++) {
            railMatrix[currentRow][currentCol++] = '*';

            if (currentRow == 0) {
                movingDown = true;
            } else if (currentRow == rails - 1) {
                movingDown = false;
            }

            currentRow += movingDown ? 1 : -1;
        }

        // Fill the marked positions with the ciphertext characters
        int index = 0;
        for (int row = 0; row < rails; row++) {
            for (int col = 0; col < cipherText.length(); col++) {
                if (railMatrix[row][col] == '*') {
                    railMatrix[row][col] = cipherText.charAt(index++);
                }
            }
        }

        // Read the matrix in zigzag order to reconstruct the plaintext
        StringBuilder decrypted = new StringBuilder();
        currentRow = 0;
        currentCol = 0;
        movingDown = true;

        for (int i = 0; i < cipherText.length(); i++) {
            decrypted.append(railMatrix[currentRow][currentCol++]);

            if (currentRow == 0) {
                movingDown = true;
            } else if (currentRow == rails - 1) {
                movingDown = false;
            }

            currentRow += movingDown ? 1 : -1;
        }

        return decrypted.toString();
    }
}