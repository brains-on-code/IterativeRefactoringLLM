package com.thealgorithms.ciphers;

import java.util.Arrays;

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

    public String decrypt(String cipherText, int rails) {
        if (rails <= 1 || rails >= cipherText.length()) {
            return cipherText;
        }

        char[][] railMatrix = new char[rails][cipherText.length()];

        boolean movingDown = true;
        int row = 0;
        int col = 0;

        // Mark the zigzag pattern positions
        for (int i = 0; i < cipherText.length(); i++) {
            railMatrix[row][col++] = '*';

            if (row == 0) {
                movingDown = true;
            } else if (row == rails - 1) {
                movingDown = false;
            }

            row += movingDown ? 1 : -1;
        }

        // Fill the marked positions with cipher text characters
        int index = 0;
        for (int r = 0; r < rails; r++) {
            for (int c = 0; c < cipherText.length(); c++) {
                if (railMatrix[r][c] == '*') {
                    railMatrix[r][c] = cipherText.charAt(index++);
                }
            }
        }

        // Read the matrix in zigzag order to reconstruct the original text
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