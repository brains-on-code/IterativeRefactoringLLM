package com.thealgorithms.ciphers;

import java.util.Arrays;

public class RailFenceCipher {

    public String encrypt(String text, int rails) {
        if (isTrivialCase(text, rails)) {
            return text;
        }

        char[][] railMatrix = createInitializedRailMatrix(rails, text.length(), '\n');

        boolean movingDown = false;
        int currentRow = 0;

        for (int col = 0; col < text.length(); col++) {
            if (isFirstOrLastRow(currentRow, rails)) {
                movingDown = !movingDown;
            }

            railMatrix[currentRow][col] = text.charAt(col);
            currentRow += movingDown ? 1 : -1;
        }

        return buildStringFromMatrix(railMatrix, '\n');
    }

    public String decrypt(String cipher, int rails) {
        if (isTrivialCase(cipher, rails)) {
            return cipher;
        }

        char[][] railMatrix = new char[rails][cipher.length()];

        boolean movingDown = false;
        int currentRow = 0;

        for (int col = 0; col < cipher.length(); col++) {
            if (isFirstOrLastRow(currentRow, rails)) {
                movingDown = !movingDown;
            }

            railMatrix[currentRow][col] = '*';
            currentRow += movingDown ? 1 : -1;
        }

        int index = 0;
        for (int row = 0; row < rails; row++) {
            for (int col = 0; col < cipher.length(); col++) {
                if (railMatrix[row][col] == '*' && index < cipher.length()) {
                    railMatrix[row][col] = cipher.charAt(index++);
                }
            }
        }

        StringBuilder decrypted = new StringBuilder();
        movingDown = false;
        currentRow = 0;

        for (int col = 0; col < cipher.length(); col++) {
            if (isFirstOrLastRow(currentRow, rails)) {
                movingDown = !movingDown;
            }

            decrypted.append(railMatrix[currentRow][col]);
            currentRow += movingDown ? 1 : -1;
        }

        return decrypted.toString();
    }

    private boolean isTrivialCase(String text, int rails) {
        return rails <= 1 || rails >= text.length();
    }

    private boolean isFirstOrLastRow(int row, int totalRails) {
        return row == 0 || row == totalRails - 1;
    }

    private char[][] createInitializedRailMatrix(int rails, int length, char fillChar) {
        char[][] matrix = new char[rails][length];
        for (char[] row : matrix) {
            Arrays.fill(row, fillChar);
        }
        return matrix;
    }

    private String buildStringFromMatrix(char[][] matrix, char skipChar) {
        StringBuilder result = new StringBuilder();
        for (char[] row : matrix) {
            for (char ch : row) {
                if (ch != skipChar) {
                    result.append(ch);
                }
            }
        }
        return result.toString();
    }
}