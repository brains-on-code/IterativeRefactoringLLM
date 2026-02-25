package com.thealgorithms.ciphers;

import java.util.Arrays;

/**
 * Rail Fence Cipher implementation.
 *
 * <p>Writes text in a zigzag pattern across a given number of rails, then reads
 * row by row to produce the ciphertext.
 */
public class RailFenceCipher {

    /**
     * Encrypts text using the Rail Fence Cipher.
     *
     * @param text  plaintext
     * @param rails number of rails
     * @return ciphertext
     */
    public String encrypt(String text, int rails) {
        if (text == null || rails <= 1 || rails >= text.length()) {
            return text;
        }

        char[][] railMatrix = new char[rails][text.length()];
        for (char[] row : railMatrix) {
            Arrays.fill(row, '\n');
        }

        boolean movingDown = true;
        int row = 0;
        int col = 0;

        for (int i = 0; i < text.length(); i++) {
            railMatrix[row][col++] = text.charAt(i);

            if (row == 0) {
                movingDown = true;
            } else if (row == rails - 1) {
                movingDown = false;
            }

            row += movingDown ? 1 : -1;
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
     * Decrypts text encrypted with the Rail Fence Cipher.
     *
     * @param cipherText ciphertext
     * @param rails      number of rails used during encryption
     * @return plaintext
     */
    public String decrypt(String cipherText, int rails) {
        if (cipherText == null || rails <= 1 || rails >= cipherText.length()) {
            return cipherText;
        }

        char[][] railMatrix = new char[rails][cipherText.length()];

        // Mark the zigzag path.
        boolean movingDown = true;
        int row = 0;
        int col = 0;

        for (int i = 0; i < cipherText.length(); i++) {
            railMatrix[row][col++] = '*';

            if (row == 0) {
                movingDown = true;
            } else if (row == rails - 1) {
                movingDown = false;
            }

            row += movingDown ? 1 : -1;
        }

        // Fill the marked positions with ciphertext characters.
        int index = 0;
        for (int r = 0; r < rails; r++) {
            for (int c = 0; c < cipherText.length(); c++) {
                if (railMatrix[r][c] == '*') {
                    railMatrix[r][c] = cipherText.charAt(index++);
                }
            }
        }

        // Traverse the zigzag path to reconstruct the plaintext.
        StringBuilder decrypted = new StringBuilder();
        row = 0;
        col = 0;
        movingDown = true;

        for (int i = 0; i < cipherText.length(); i++) {
            decrypted.append(railMatrix[row][col++]);

            if (row == 0) {
                movingDown = true;
            } else if (row == rails - 1) {
                movingDown = false;
            }

            row += movingDown ? 1 : -1;
        }

        return decrypted.toString();
    }
}