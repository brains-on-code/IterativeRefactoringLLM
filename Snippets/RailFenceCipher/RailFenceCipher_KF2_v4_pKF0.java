package com.thealgorithms.ciphers;

import java.util.Arrays;

public class RailFenceCipher {

    private static final char PLACEHOLDER = '\n';
    private static final char MARKER = '*';

    public String encrypt(String text, int rails) {
        if (isTrivialCase(text, rails)) {
            return text;
        }

        char[][] railMatrix = createInitializedRailMatrix(rails, text.length(), PLACEHOLDER);
        fillRails(text, rails, railMatrix, false);
        return buildStringFromMatrix(railMatrix, PLACEHOLDER);
    }

    public String decrypt(String cipher, int rails) {
        if (isTrivialCase(cipher, rails)) {
            return cipher;
        }

        char[][] railMatrix = new char[rails][cipher.length()];
        markRailPattern(cipher.length(), rails, railMatrix);
        fillRailsWithCipher(cipher, rails, railMatrix);
        return readRails(cipher.length(), rails, railMatrix);
    }

    private void fillRails(String text, int rails, char[][] railMatrix, boolean useMarker) {
        boolean movingDown = false;
        int currentRow = 0;

        for (int col = 0; col < text.length(); col++) {
            if (isFirstOrLastRow(currentRow, rails)) {
                movingDown = !movingDown;
            }
            railMatrix[currentRow][col] = useMarker ? MARKER : text.charAt(col);
            currentRow += movingDown ? 1 : -1;
        }
    }

    private void markRailPattern(int length, int rails, char[][] railMatrix) {
        boolean movingDown = false;
        int currentRow = 0;

        for (int col = 0; col < length; col++) {
            if (isFirstOrLastRow(currentRow, rails)) {
                movingDown = !movingDown;
            }
            railMatrix[currentRow][col] = MARKER;
            currentRow += movingDown ? 1 : -1;
        }
    }

    private void fillRailsWithCipher(String cipher, int rails, char[][] railMatrix) {
        int index = 0;
        int cipherLength = cipher.length();

        for (int row = 0; row < rails; row++) {
            for (int col = 0; col < cipherLength; col++) {
                if (railMatrix[row][col] == MARKER && index < cipherLength) {
                    railMatrix[row][col] = cipher.charAt(index++);
                }
            }
        }
    }

    private String readRails(int length, int rails, char[][] railMatrix) {
        StringBuilder decrypted = new StringBuilder(length);
        boolean movingDown = false;
        int currentRow = 0;

        for (int col = 0; col < length; col++) {
            if (isFirstOrLastRow(currentRow, rails)) {
                movingDown = !movingDown;
            }
            decrypted.append(railMatrix[currentRow][col]);
            currentRow += movingDown ? 1 : -1;
        }

        return decrypted.toString();
    }

    private boolean isTrivialCase(String text, int rails) {
        return text == null || text.isEmpty() || rails <= 1 || rails >= text.length();
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
        StringBuilder result = new StringBuilder(matrix.length * matrix[0].length);
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