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

        char[][] railMatrix = initRailMatrix(rails, text.length(), EMPTY);

        fillZigzagPattern(railMatrix, text);

        return readMatrixRowByRow(railMatrix, EMPTY);
    }

    public String decrypt(String cipherText, int rails) {
        if (isTrivialCase(cipherText, rails)) {
            return cipherText;
        }

        char[][] railMatrix = new char[rails][cipherText.length()];

        markZigzagPattern(railMatrix);

        fillMarkedPositions(railMatrix, cipherText);

        return readZigzag(railMatrix, cipherText.length());
    }

    private boolean isTrivialCase(String text, int rails) {
        return rails <= 1 || rails >= text.length();
    }

    private char[][] initRailMatrix(int rails, int length, char fillChar) {
        char[][] matrix = new char[rails][length];
        for (char[] row : matrix) {
            Arrays.fill(row, fillChar);
        }
        return matrix;
    }

    private void fillZigzagPattern(char[][] railMatrix, String text) {
        boolean movingDown = true;
        int row = 0;
        int col = 0;

        for (int i = 0; i < text.length(); i++) {
            if (row == 0) {
                movingDown = true;
            } else if (row == railMatrix.length - 1) {
                movingDown = false;
            }

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

    private void markZigzagPattern(char[][] railMatrix) {
        boolean movingDown = true;
        int row = 0;
        int col = 0;
        int rails = railMatrix.length;
        int length = railMatrix[0].length;

        while (col < length) {
            if (row == 0) {
                movingDown = true;
            } else if (row == rails - 1) {
                movingDown = false;
            }

            railMatrix[row][col++] = MARK;
            row += movingDown ? 1 : -1;
        }
    }

    private void fillMarkedPositions(char[][] railMatrix, String cipherText) {
        int index = 0;
        int rails = railMatrix.length;
        int length = railMatrix[0].length;

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
        boolean movingDown = true;
        int row = 0;
        int col = 0;
        int rails = railMatrix.length;

        while (col < textLength) {
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