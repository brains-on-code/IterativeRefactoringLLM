package com.thealgorithms.ciphers;

public class HillCipher {

    private static final int MODULUS = 26;
    private static final char PADDING_CHAR = 'X';
    private static final char BASE_CHAR = 'A';

    public String encrypt(String message, int[][] keyMatrix) {
        return process(message, keyMatrix);
    }

    public String decrypt(String message, int[][] inverseKeyMatrix) {
        return process(message, inverseKeyMatrix);
    }

    private String process(String message, int[][] matrix) {
        String sanitizedMessage = sanitizeMessage(message);
        int matrixSize = matrix.length;
        validateDeterminant(matrix, matrixSize);

        StringBuilder result = new StringBuilder();
        int[] inputVector = new int[matrixSize];
        int[] outputVector = new int[matrixSize];
        int index = 0;

        while (index < sanitizedMessage.length()) {
            fillVectorFromMessage(sanitizedMessage, inputVector, matrixSize, index);
            index += matrixSize;

            multiplyMatrixVector(matrix, inputVector, outputVector, matrixSize);
            appendVectorAsText(result, outputVector, matrixSize);
        }

        return result.toString();
    }

    private String sanitizeMessage(String message) {
        return message.toUpperCase().replaceAll("[^A-Z]", "");
    }

    private void fillVectorFromMessage(String message, int[] vector, int size, int startIndex) {
        for (int i = 0; i < size; i++) {
            int messageIndex = startIndex + i;
            char ch = messageIndex < message.length() ? message.charAt(messageIndex) : PADDING_CHAR;
            vector[i] = ch - BASE_CHAR;
        }
    }

    private void multiplyMatrixVector(int[][] matrix, int[] inputVector, int[] outputVector, int size) {
        for (int i = 0; i < size; i++) {
            int value = 0;
            for (int j = 0; j < size; j++) {
                value += matrix[i][j] * inputVector[j];
            }
            outputVector[i] = Math.floorMod(value, MODULUS);
        }
    }

    private void appendVectorAsText(StringBuilder builder, int[] vector, int size) {
        for (int i = 0; i < size; i++) {
            builder.append((char) (vector[i] + BASE_CHAR));
        }
    }

    private void validateDeterminant(int[][] keyMatrix, int n) {
        int det = Math.floorMod(determinant(keyMatrix, n), MODULUS);
        if (det == 0) {
            throw new IllegalArgumentException("Invalid key matrix. Determinant is zero modulo 26.");
        }
    }

    private int determinant(int[][] matrix, int n) {
        if (n == 1) {
            return matrix[0][0];
        }

        int det = 0;
        int sign = 1;
        int[][] subMatrix = new int[n - 1][n - 1];

        for (int col = 0; col < n; col++) {
            buildSubMatrix(matrix, subMatrix, n, col);
            det += sign * matrix[0][col] * determinant(subMatrix, n - 1);
            sign = -sign;
        }

        return det;
    }

    private void buildSubMatrix(int[][] matrix, int[][] subMatrix, int n, int excludedCol) {
        int subI = 0;
        for (int i = 1; i < n; i++) {
            int subJ = 0;
            for (int j = 0; j < n; j++) {
                if (j == excludedCol) {
                    continue;
                }
                subMatrix[subI][subJ++] = matrix[i][j];
            }
            subI++;
        }
    }
}