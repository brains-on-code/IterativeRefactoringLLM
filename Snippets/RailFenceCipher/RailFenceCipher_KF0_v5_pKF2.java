package com.thealgorithms.ciphers;

import java.util.Arrays;

/**
 * Rail fence (zigzag) transposition cipher.
 * https://en.wikipedia.org/wiki/Rail_fence_cipher
 */
public class RailFenceCipher {

    private static final char EMPTY = '\n';
    private static final char MARK = '*';

    public String encrypt(String text, int rails) {
        if (isTrivialCase(text, rails)) {
            return text;
        }

        char[][] railMatrix = createFilledMatrix(rails, text.length(), EMPTY);
        writeZigzag(railMatrix, text);
        return readMatrixRowByRow(railMatrix, EMPTY);
    }

    public String decrypt(String cipherText, int rails) {
        if (isTrivialCase(cipherText, rails)) {
            return cipherText;
        }

        char[][] railMatrix = new char[rails][cipherText.length()];
        markZigzag(railMatrix);
        fillMarkedPositions(railMatrix, cipherText);
        return readZigzag(railMatrix, cipherText.length());
    }

    private boolean isTrivialCase(String text, int rails) {
        return rails <= 1 || rails >= text.length();
    }

    private char[][] createFilledMatrix(int rails, int length, char fillChar) {
        char[][] matrix = new char[rails][length];
        for (char[] row : matrix) {
            Arrays.fill(row, fillChar);
        }
        return matrix;
    }

    private void writeZigzag(char[][] railMatrix, String text) {
        int row = 0;
        int col = 0;
        boolean movingDown = true;

        for (int i = 0; i < text.length(); i++) {
            movingDown = updateDirection(row, railMatrix.length, movingDown);
            railMatrix[row][col++] = text.charAt(i);
            row += movingDown ? 1 : -1;
        }
    }

    private String readMatrixRowByRow(char[][] railMatrix, char skipChar) {
        StringBuilder result = new StringBuilder();
        for (char[] railRow : railMatrix) {
            for (char ch : railRow) {
                if (ch != skipChar) {
                    result.append(ch);
                }
            }
        }
        return result.toString();
    }

    private void markZigzag(char[][] railMatrix) {
        int rails = railMatrix.length;
        int length = railMatrix[0].length;
        int row = 0;
        int col = 0;
        boolean movingDown = true;

        while (col < length) {
            movingDown = updateDirection(row, rails, movingDown);
            railMatrix[row][col++] = MARK;
            row += movingDown ? 1 : -1;
        }
    }

    private void fillMarkedPositions(char[][] railMatrix, String cipherText) {
        int rails = railMatrix.length;
        int length = railMatrix[0].length;
        int index = 0;

        for (int r = 0; r < rails; r++) {
            for (int c = 0; c < length; c++) {
                if (railMatrix[r][c] == MARK) {
                    railMatrix[r][c] = cipherText.charAt(index++);
                }
            }
        }
    }

    private String readZigzag(char[][] railMatrix, int textLength) {
        StringBuilder decrypted = new StringBuilder();
        int rails = railMatrix.length;
        int row = 0;
        int col = 0;
        boolean movingDown = true;

        while (col < textLength) {
            movingDown = updateDirection(row, rails, movingDown);
            decrypted.append(railMatrix[row][col++]);
            row += movingDown ? 1 : -1;
        }

        return decrypted.toString();
    }

    private boolean updateDirection(int currentRow, int totalRails, boolean currentDirectionDown) {
        if (currentRow == 0) {
            return true;
        }
        if (currentRow == totalRails - 1) {
            return false;
        }
        return currentDirectionDown;
    }
}