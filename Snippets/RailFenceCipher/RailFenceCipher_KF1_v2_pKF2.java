package com.thealgorithms.ciphers;

import java.util.Arrays;

/**
 * Implements Rail Fence Cipher encryption and decryption.
 */
public class RailFenceCipher {

    /**
     * Encrypts the given text using the Rail Fence Cipher.
     *
     * @param text  the input text to encrypt
     * @param rails the number of rails to use
     * @return the encrypted text
     */
    public String encrypt(String text, int rails) {
        if (rails <= 1 || rails >= text.length()) {
            return text;
        }

        char[][] fence = new char[rails][text.length()];
        for (char[] row : fence) {
            Arrays.fill(row, '\n');
        }

        boolean goingDown = true;
        int row = 0;
        int col = 0;

        while (col < text.length()) {
            if (row == 0) {
                goingDown = true;
            } else if (row == rails - 1) {
                goingDown = false;
            }

            fence[row][col] = text.charAt(col);
            col++;
            row = goingDown ? row + 1 : row - 1;
        }

        StringBuilder result = new StringBuilder();
        for (char[] fenceRow : fence) {
            for (char c : fenceRow) {
                if (c != '\n') {
                    result.append(c);
                }
            }
        }
        return result.toString();
    }

    /**
     * Decrypts the given text encrypted with the Rail Fence Cipher.
     *
     * @param cipherText the encrypted text
     * @param rails      the number of rails used during encryption
     * @return the decrypted (original) text
     */
    public String decrypt(String cipherText, int rails) {
        if (rails <= 1 || rails >= cipherText.length()) {
            return cipherText;
        }

        char[][] fence = new char[rails][cipherText.length()];
        boolean goingDown = true;
        int row = 0;
        int col = 0;

        // First pass: mark the zigzag pattern positions
        while (col < cipherText.length()) {
            if (row == 0) {
                goingDown = true;
            } else if (row == rails - 1) {
                goingDown = false;
            }

            fence[row][col] = '*';
            col++;
            row = goingDown ? row + 1 : row - 1;
        }

        // Second pass: fill the marked positions with cipher text characters
        int index = 0;
        for (int r = 0; r < rails; r++) {
            for (int c = 0; c < cipherText.length(); c++) {
                if (fence[r][c] == '*') {
                    fence[r][c] = cipherText.charAt(index++);
                }
            }
        }

        // Third pass: read characters in zigzag order to reconstruct original text
        StringBuilder result = new StringBuilder();
        row = 0;
        col = 0;
        goingDown = true;

        while (col < cipherText.length()) {
            if (row == 0) {
                goingDown = true;
            } else if (row == rails - 1) {
                goingDown = false;
            }

            result.append(fence[row][col]);
            col++;
            row = goingDown ? row + 1 : row - 1;
        }

        return result.toString();
    }
}