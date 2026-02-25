package com.thealgorithms.ciphers;

public class Class1 {

    private static final int ALPHABET_SIZE = 26;
    private static final char PADDING_CHAR = 'X';
    private static final char FIRST_LETTER = 'A';

    public String method1(String plaintext, int[][] keyMatrix) {
        String sanitizedText = sanitizeInput(plaintext);
        int matrixSize = keyMatrix.length;

        validateKeyMatrix(keyMatrix, matrixSize);

        StringBuilder result = new StringBuilder();
        int[] block = new int[matrixSize];
        int[] transformedBlock = new int[matrixSize];
        int index = 0;

        while (index < sanitizedText.length()) {
            fillBlockFromText(sanitizedText, block, matrixSize, index);
            index += matrixSize;

            multiplyMatrixVector(keyMatrix, block, transformedBlock, matrixSize);

            appendBlockToResult(result, transformedBlock, matrixSize);
        }

        return result.toString();
    }

    public String method2(String ciphertext, int[][] inverseKeyMatrix) {
        String sanitizedText = sanitizeInput(ciphertext);
        int matrixSize = inverseKeyMatrix.length;

        validateKeyMatrix(inverseKeyMatrix, matrixSize);

        StringBuilder result = new StringBuilder();
        int[] block = new int[matrixSize];
        int[] transformedBlock = new int[matrixSize];
        int index = 0;

        while (index < sanitizedText.length()) {
            fillBlockFromText(sanitizedText, block, matrixSize, index);
            index += matrixSize;

            multiplyMatrixVector(inverseKeyMatrix, block, transformedBlock, matrixSize);

            appendBlockToResult(result, transformedBlock, matrixSize);
        }

        return result.toString();
    }

    private String sanitizeInput(String input) {
        return input.toUpperCase().replaceAll("[^A-Z]", "");
    }

    private void validateKeyMatrix(int[][] matrix, int size) {
        int determinantMod = determinant(matrix, size) % ALPHABET_SIZE;
        if (determinantMod == 0) {
            throw new IllegalArgumentException("Invalid key matrix. Determinant is zero modulo 26.");
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
            int subRow = 0;
            for (int row = 1; row < size; row++) {
                int subCol = 0;
                for (int k = 0; k < size; k++) {
                    if (k != col) {
                        subMatrix[subRow][subCol++] = matrix[row][k];
                    }
                }
                subRow++;
            }
            det += sign * matrix[0][col] * determinant(subMatrix, size - 1);
            sign = -sign;
        }

        return det;
    }

    private void fillBlockFromText(String text, int[] block, int blockSize, int startIndex) {
        for (int i = 0; i < blockSize; i++) {
            if (startIndex + i < text.length()) {
                block[i] = text.charAt(startIndex + i) - FIRST_LETTER;
            } else {
                block[i] = PADDING_CHAR - FIRST_LETTER;
            }
        }
    }

    private void multiplyMatrixVector(int[][] matrix, int[] vector, int[] result, int size) {
        for (int row = 0; row < size; row++) {
            result[row] = 0;
            for (int col = 0; col < size; col++) {
                result[row] += matrix[row][col] * vector[col];
            }
            result[row] = result[row] % ALPHABET_SIZE;
        }
    }

    private void appendBlockToResult(StringBuilder result, int[] block, int blockSize) {
        for (int i = 0; i < blockSize; i++) {
            result.append((char) (block[i] + FIRST_LETTER));
        }
    }
}