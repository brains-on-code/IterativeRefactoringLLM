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

    public String decrypt(String cipherText, int rails) {
        if (rails <= 1 || rails >= cipherText.length()) {
            return cipherText;
        }

        char[][] railMatrix = new char[rails][cipherText.length()];

        boolean movingDown = true;
        int currentRow = 0;
        int currentCol = 0;

        for (int i = 0; i < cipherText.length(); i++) {
            railMatrix[currentRow][currentCol++] = '*';

            if (currentRow == 0) {
                movingDown = true;
            } else if (currentRow == rails - 1) {
                movingDown = false;
            }

            currentRow += movingDown ? 1 : -1;
        }

        int index = 0;
        for (int row = 0; row < rails; row++) {
            for (int col = 0; col < cipherText.length(); col++) {
                if (railMatrix[row][col] == '*') {
                    railMatrix[row][col] = cipherText.charAt(index++);
                }
            }
        }

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