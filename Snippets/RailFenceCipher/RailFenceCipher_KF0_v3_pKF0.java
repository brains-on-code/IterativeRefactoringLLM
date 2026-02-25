package com.thealgorithms.ciphers;

import java.util.Arrays;

/**
 * The rail fence cipher (also called a zigzag cipher) is a classical type of transposition cipher.
 * It derives its name from the manner in which encryption is performed, in analogy to a fence built with horizontal rails.
 * https://en.wikipedia.org/wiki/Rail_fence_cipher
 * @author https://github.com/Krounosity
 */
public class RailFenceCipher {

    private static final char PLACEHOLDER = '\n';
    private static final char MARKER = '*';

    public String encrypt(String text, int rails) {
        if (isTrivialCase(text, rails)) {
            return text;
        }

        char[][] railMatrix = createFilledMatrix(rails, text.length(), PLACEHOLDER);
        fillRailsForEncryption(text, railMatrix);

        StringBuilder encrypted = new StringBuilder(text.length());
        for (char[] row : railMatrix) {
            for (char ch : row) {
                if (ch != PLACEHOLDER) {
                    encrypted.append(ch);
                }
            }
        }
        return encrypted.toString();
    }

    public String decrypt(String cipherText, int rails) {
        if (isTrivialCase(cipherText, rails)) {
            return cipherText;
        }

        char[][] railMatrix = new char[rails][cipherText.length()];
        markRailPattern(railMatrix);
        fillRailsForDecryption(cipherText, railMatrix);

        StringBuilder decrypted = new StringBuilder(cipherText.length());
        traverseRailsForDecryption(railMatrix, decrypted);
        return decrypted.toString();
    }

    private boolean isTrivialCase(String text, int rails) {
        return text == null || text.isEmpty() || rails <= 1 || rails >= text.length();
    }

    private char[][] createFilledMatrix(int rows, int cols, char fillChar) {
        char[][] matrix = new char[rows][cols];
        for (int row = 0; row < rows; row++) {
            Arrays.fill(matrix[row], fillChar);
        }
        return matrix;
    }

    private void fillRailsForEncryption(String text, char[][] railMatrix) {
        int row = 0;
        int col = 0;
        boolean movingDown = true;
        int totalRows = railMatrix.length;

        for (int i = 0; i < text.length(); i++) {
            railMatrix[row][col++] = text.charAt(i);
            movingDown = updateDirection(row, totalRows, movingDown);
            row += movingDown ? 1 : -1;
        }
    }

    private void markRailPattern(char[][] railMatrix) {
        int rows = railMatrix.length;
        int cols = railMatrix[0].length;

        int row = 0;
        int col = 0;
        boolean movingDown = true;

        while (col < cols) {
            railMatrix[row][col++] = MARKER;
            movingDown = updateDirection(row, rows, movingDown);
            row += movingDown ? 1 : -1;
        }
    }

    private void fillRailsForDecryption(String cipherText, char[][] railMatrix) {
        int index = 0;
        int rows = railMatrix.length;
        int cols = railMatrix[0].length;

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (railMatrix[row][col] == MARKER) {
                    railMatrix[row][col] = cipherText.charAt(index++);
                }
            }
        }
    }

    private void traverseRailsForDecryption(char[][] railMatrix, StringBuilder output) {
        int rows = railMatrix.length;
        int cols = railMatrix[0].length;

        int row = 0;
        int col = 0;
        boolean movingDown = true;

        while (col < cols) {
            output.append(railMatrix[row][col++]);
            movingDown = updateDirection(row, rows, movingDown);
            row += movingDown ? 1 : -1;
        }
    }

    private boolean updateDirection(int currentRow, int totalRows, boolean currentDirection) {
        if (currentRow == 0) {
            return true;
        }
        if (currentRow == totalRows - 1) {
            return false;
        }
        return currentDirection;
    }
}