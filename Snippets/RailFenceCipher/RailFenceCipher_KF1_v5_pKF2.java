package com.thealgorithms.ciphers;

import java.util.Arrays;

/**
 * Implements Rail Fence Cipher encryption and decryption.
 */
public class RailFenceCipher {

    private static final char EMPTY_CELL = '\n';
    private static final char PATH_MARKER = '*';

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

        char[][] fence = createFilledFence(rails, text.length(), EMPTY_CELL);
        writePlainTextInZigZag(fence, text, rails);

        StringBuilder result = new StringBuilder();
        for (char[] row : fence) {
            for (char c : row) {
                if (c != EMPTY_CELL) {
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

        int length = cipherText.length();
        char[][] fence = new char[rails][length];

        markZigZagPath(fence, rails, length);
        placeCipherTextOnPath(fence, cipherText, rails);
        return readZigZagPath(fence, rails, length);
    }

    private char[][] createFilledFence(int rails, int length, char fillChar) {
        char[][] fence = new char[rails][length];
        for (char[] row : fence) {
            Arrays.fill(row, fillChar);
        }
        return fence;
    }

    private void writePlainTextInZigZag(char[][] fence, String text, int rails) {
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
    }

    private void markZigZagPath(char[][] fence, int rails, int length) {
        boolean goingDown = true;
        int row = 0;
        int col = 0;

        while (col < length) {
            if (row == 0) {
                goingDown = true;
            } else if (row == rails - 1) {
                goingDown = false;
            }

            fence[row][col] = PATH_MARKER;
            col++;
            row = goingDown ? row + 1 : row - 1;
        }
    }

    private void placeCipherTextOnPath(char[][] fence, String cipherText, int rails) {
        int index = 0;
        int length = cipherText.length();

        for (int r = 0; r < rails; r++) {
            for (int c = 0; c < length; c++) {
                if (fence[r][c] == PATH_MARKER) {
                    fence[r][c] = cipherText.charAt(index++);
                }
            }
        }
    }

    private String readZigZagPath(char[][] fence, int rails, int length) {
        StringBuilder result = new StringBuilder();
        boolean goingDown = true;
        int row = 0;
        int col = 0;

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
}