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
        if (text == null || rails <= 1 || rails >= text.length()) {
            return text;
        }

        int length = text.length();
        char[][] fence = initializeFence(rails, length, PLACEHOLDER);

        int row = 0;
        int col = 0;
        boolean goingDown = true;

        for (int i = 0; i < length; i++) {
            fence[row][col++] = text.charAt(i);
            row = nextRow(row, rails, goingDown);
            goingDown = updateDirection(row, rails, goingDown);
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
        if (cipherText == null || rails <= 1 || rails >= cipherText.length()) {
            return cipherText;
        }

        int length = cipherText.length();
        char[][] fence = new char[rails][length];

        markZigZagPattern(fence, rails, length);
        fillFenceWithCipherText(fence, cipherText, rails, length);

        return readFenceInZigZag(fence, rails, length);
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

    private void markZigZagPattern(char[][] fence, int rails, int length) {
        int row = 0;
        int col = 0;
        boolean goingDown = true;

        while (col < length) {
            fence[row][col++] = MARKER;
            row = nextRow(row, rails, goingDown);
            goingDown = updateDirection(row, rails, goingDown);
        }
    }

    private void fillFenceWithCipherText(char[][] fence, String cipherText, int rails, int length) {
        int index = 0;
        for (int r = 0; r < rails; r++) {
            for (int c = 0; c < length; c++) {
                if (fence[r][c] == MARKER) {
                    fence[r][c] = cipherText.charAt(index++);
                }
            }
        }
    }

    private String readFenceInZigZag(char[][] fence, int rails, int length) {
        StringBuilder result = new StringBuilder();
        int row = 0;
        int col = 0;
        boolean goingDown = true;

        while (col < length) {
            result.append(fence[row][col++]);
            row = nextRow(row, rails, goingDown);
            goingDown = updateDirection(row, rails, goingDown);
        }

        return result.toString();
    }

    private int nextRow(int currentRow, int rails, boolean goingDown) {
        return goingDown ? currentRow + 1 : currentRow - 1;
    }

    private boolean updateDirection(int row, int rails, boolean currentDirection) {
        if (row == 0) {
            return true;
        }
        if (row == rails - 1) {
            return false;
        }
        return currentDirection;
    }
}