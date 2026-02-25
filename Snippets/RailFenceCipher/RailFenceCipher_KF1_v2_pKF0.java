package com.thealgorithms.ciphers;

import java.util.Arrays;

/**
 * Implements Rail Fence Cipher encryption and decryption.
 */
public class RailFenceCipher {

    private static final char PLACEHOLDER = '\n';
    private static final char MARKER = '*';

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

        char[][] fence = initializeFence(rails, text.length(), PLACEHOLDER);

        boolean goingDown = true;
        int row = 0;
        int col = 0;

        for (int index = 0; index < text.length(); index++) {
            if (row == 0) {
                goingDown = true;
            } else if (row == rails - 1) {
                goingDown = false;
            }

            fence[row][col] = text.charAt(index);
            col++;
            row = goingDown ? row + 1 : row - 1;
        }

        return buildStringFromFence(fence, PLACEHOLDER);
    }

    /**
     * Decrypts the given text encrypted with the Rail Fence Cipher.
     *
     * @param cipherText the encrypted text
     * @param rails      the number of rails used during encryption
     * @return the decrypted text
     */
    public String decrypt(String cipherText, int rails) {
        if (rails <= 1 || rails >= cipherText.length()) {
            return cipherText;
        }

        int length = cipherText.length();
        char[][] fence = new char[rails][length];

        // Mark the zigzag pattern with markers
        boolean goingDown = true;
        int row = 0;
        int col = 0;

        while (col < length) {
            if (row == 0) {
                goingDown = true;
            } else if (row == rails - 1) {
                goingDown = false;
            }

            fence[row][col] = MARKER;
            col++;
            row = goingDown ? row + 1 : row - 1;
        }

        // Fill the fence with cipher text characters row by row
        int index = 0;
        for (int r = 0; r < rails; r++) {
            for (int c = 0; c < length; c++) {
                if (fence[r][c] == MARKER) {
                    fence[r][c] = cipherText.charAt(index++);
                }
            }
        }

        // Read the fence in zigzag order to reconstruct the original text
        StringBuilder result = new StringBuilder();
        row = 0;
        col = 0;
        goingDown = true;

        while (col < length) {
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

    private char[][] initializeFence(int rails, int length, char fillChar) {
        char[][] fence = new char[rails][length];
        for (int row = 0; row < rails; row++) {
            Arrays.fill(fence[row], fillChar);
        }
        return fence;
    }

    private String buildStringFromFence(char[][] fence, char skipChar) {
        StringBuilder result = new StringBuilder();
        for (char[] row : fence) {
            for (char ch : row) {
                if (ch != skipChar) {
                    result.append(ch);
                }
            }
        }
        return result.toString();
    }
}