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

        char[][] fence = initFence(rails, text.length(), PLACEHOLDER);
        fillFenceWithPlainText(fence, text, rails);

        StringBuilder result = new StringBuilder();
        for (char[] fenceRow : fence) {
            for (char c : fenceRow) {
                if (c != PLACEHOLDER) {
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
        markZigZagPattern(fence, rails, cipherText.length());
        fillFenceWithCipherText(fence, cipherText, rails);

        return readZigZag(fence, rails, cipherText.length());
    }

    private char[][] initFence(int rails, int length, char fillChar) {
        char[][] fence = new char[rails][length];
        for (char[] row : fence) {
            Arrays.fill(row, fillChar);
        }
        return fence;
    }

    private void fillFenceWithPlainText(char[][] fence, String text, int rails) {
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

    private void markZigZagPattern(char[][] fence, int rails, int length) {
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
    }

    private void fillFenceWithCipherText(char[][] fence, String cipherText, int rails) {
        int index = 0;
        int length = cipherText.length();

        for (int r = 0; r < rails; r++) {
            for (int c = 0; c < length; c++) {
                if (fence[r][c] == MARKER) {
                    fence[r][c] = cipherText.charAt(index++);
                }
            }
        }
    }

    private String readZigZag(char[][] fence, int rails, int length) {
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