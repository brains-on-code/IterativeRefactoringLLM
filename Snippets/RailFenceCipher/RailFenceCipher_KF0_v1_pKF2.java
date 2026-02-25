package com.thealgorithms.ciphers;

import java.util.Arrays;

/**
 * Rail fence (zigzag) transposition cipher.
 * https://en.wikipedia.org/wiki/Rail_fence_cipher
 */
public class RailFenceCipher {

    public String encrypt(String text, int rails) {
        if (rails <= 1 || rails >= text.length()) {
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
            if (row == 0) {
                movingDown = true;
            } else if (row == rails - 1) {
                movingDown = false;
            }

            railMatrix[row][col++] = text.charAt(i);
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

    public String decrypt(String cipherText, int rails) {
        if (rails <= 1 || rails >= cipherText.length()) {
            return cipherText;
        }

        char[][] railMatrix = new char[rails][cipherText.length()];

        boolean movingDown = true;
        int row = 0;
        int col = 0;

        // Mark zigzag pattern
        while (col < cipherText.length()) {
            if (row == 0) {
                movingDown = true;
            } else if (row == rails - 1) {
                movingDown = false;
            }

            railMatrix[row][col++] = '*';
            row += movingDown ? 1 : -1;
        }

        // Fill marked positions with cipher text
        int index = 0;
        for (int r = 0; r < rails; r++) {
            for (int c = 0; c < cipherText.length(); c++) {
                if (railMatrix[r][c] == '*') {
                    railMatrix[r][c] = cipherText.charAt(index++);
                }
            }
        }

        // Read in zigzag order
        StringBuilder decrypted = new StringBuilder();
        row = 0;
        col = 0;
        movingDown = true;

        while (col < cipherText.length()) {
            if (row == 0) {
                movingDown = true;
            } else if (row == rails - 1) {
                movingDown = false;
            }

            decrypted.append(railMatrix[row][col++]);
            row += movingDown ? 1 : -1;
        }

        return decrypted.toString();
    }
}