package com.thealgorithms.ciphers;

public class HillCipher {

    private static final int ALPHABET_SIZE = 26;
    private static final char PADDING_CHAR = 'X';
    private static final char FIRST_LETTER = 'A';

    public String encrypt(String plaintext, int[][] keyMatrix) {
        return transform(plaintext, keyMatrix);
    }

    public String decrypt(String ciphertext, int[][] inverseKeyMatrix) {
        return transform(ciphertext, inverseKeyMatrix);
    }

    private String transform(String text, int[][] matrix) {
        String sanitizedText = sanitizeInput(text);
        int matrixSize = matrix.length;

        validateKeyMatrix(matrix, matrixSize);

        StringBuilder result = new StringBuilder();
        int[] inputBlock = new int[matrixSize];
        int[] outputBlock = new int[matrixSize];
        int index = 0;

        while (index < sanitizedText.length()) {
            fillBlockFromText(sanitizedText, inputBlock, matrixSize, index);
            index += matrixSize;

            multiplyMatrixVector(matrix, inputBlock, outputBlock, matrixSize);

            appendBlockToResult(result, outputBlock, matrixSize);
        }

        return result.toString();
    }

    private String sanitizeInput(String input) {
        return input.toUpperCase().replaceAll("[^A-Z]", "");
    }

    private void validateKeyMatrix(int[][] matrix, int size) {
        int determinantMod = determinant(matrix, size) % ALPHABET_SIZE;
        if (determinantMod == 0) {
            throw new IllegalArgumentException("Invalid key matrix. Determinant is zero modulo " + ALPHABET_SIZE + ".");
        }
    }

    private int determinant(int[][] matrix, int size) {
        if (size == 1) {
            return matrix[0][0];
        }

        int det = 0;
        int sign = 1;
        int[][] subMatrix = new int[size - 1][size - 1];

        for (int col = 0; col < size; col++) {
            buildSubMatrix(matrix, subMatrix, size, col);
            det += sign * matrix[0][col] * determinant(subMatrix, size - 1);
            sign = -sign;
        }

        return det;
    }

    private void buildSubMatrix(int[][] matrix, int[][] subMatrix, int size, int excludedCol) {
        int subRow = 0;
        for (int row = 1; row < size; row++) {
            int subCol = 0;
            for (int col = 0; col < size; col++) {
                if (col == excludedCol) {
                    continue;
                }
                subMatrix[subRow][subCol++] = matrix[row][col];
            }
            subRow++;
        }
    }

    private void fillBlockFromText(String text, int[] block, int blockSize, int startIndex) {
        for (int i = 0; i < blockSize; i++) {
            int textIndex = startIndex + i;
            char currentChar = textIndex < text.length() ? text.charAt(textIndex) : PADDING_CHAR;
            block[i] = currentChar - FIRST_LETTER;
        }
    }

    private void multiplyMatrixVector(int[][] matrix, int[] vector, int[] result, int size) {
        for (int row = 0; row < size; row++) {
            int value = 0;
            for (int col = 0; col < size; col++) {
                value += matrix[row][col] * vector[col];
            }
            result[row] = value % ALPHABET_SIZE;
        }
    }

    private void appendBlockToResult(StringBuilder result, int[] block, int blockSize) {
        for (int i = 0; i < blockSize; i++) {
            result.append((char) (block[i] + FIRST_LETTER));
        }
    }
}