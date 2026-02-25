package com.thealgorithms.ciphers;

import java.util.Arrays;

/**
 * Implements Rail Fence Cipher encryption and decryption.
 */
public class Class1 {

    /**
     * Encrypts the given text using the Rail Fence Cipher.
     *
     * @param text the input text to encrypt
     * @param rails the number of rails to use
     * @return the encrypted text
     */
    public String method1(String text, int rails) {
        if (rails == 1 || rails >= text.length()) {
            return text;
        }

        boolean goingDown = true;
        char[][] fence = new char[rails][text.length()];

        for (int row = 0; row < rails; row++) {
            Arrays.fill(fence[row], '\n');
        }

        int row = 0;
        int col = 0;
        int index = 0;

        while (col < text.length()) {
            if (row == 0) {
                goingDown = true;
            } else if (row == rails - 1) {
                goingDown = false;
            }

            fence[row][col] = text.charAt(index);
            col++;

            row = goingDown ? row + 1 : row - 1;
            index++;
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
     * @param rails the number of rails used during encryption
     * @return the decrypted (original) text
     */
    public String method2(String cipherText, int rails) {
        if (rails == 1 || rails >= cipherText.length()) {
            return cipherText;
        }

        boolean goingDown = true;
        char[][] fence = new char[rails][cipherText.length()];

        int row = 0;
        int col = 0;

        // Mark the zigzag pattern with placeholders
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

        // Fill the marked positions with the cipher text characters row by row
        int index = 0;
        for (int r = 0; r < rails; r++) {
            for (int c = 0; c < cipherText.length(); c++) {
                if (fence[r][c] == '*') {
                    fence[r][c] = cipherText.charAt(index++);
                }
            }
        }

        // Read the characters in zigzag order to reconstruct the original text
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