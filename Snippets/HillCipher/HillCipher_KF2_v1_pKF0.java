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
        String normalizedMessage = normalizeMessage(message);
        int matrixSize = matrix.length;
        validateDeterminant(matrix, matrixSize);

        StringBuilder resultText = new StringBuilder();
        int[] messageVector = new int[matrixSize];
        int[] resultVector = new int[matrixSize];
        int index = 0;

        while (index < normalizedMessage.length()) {
            fillMessageVector(normalizedMessage, messageVector, matrixSize, index);
            index += matrixSize;

            multiplyMatrixVector(matrix, messageVector, resultVector, matrixSize);
            appendResult(resultText, resultVector, matrixSize);
        }

        return resultText.toString();
    }

    private String normalizeMessage(String message) {
        return message.toUpperCase().replaceAll("[^A-Z]", "");
    }

    private void fillMessageVector(String message, int[] messageVector, int size, int startIndex) {
        for (int i = 0; i < size; i++) {
            int currentIndex = startIndex + i;
            if (currentIndex < message.length()) {
                messageVector[i] = message.charAt(currentIndex) - BASE_CHAR;
            } else {
                messageVector[i] = PADDING_CHAR - BASE_CHAR;
            }
        }
    }

    private void multiplyMatrixVector(int[][] matrix, int[] vector, int[] result, int size) {
        for (int i = 0; i < size; i++) {
            int value = 0;
            for (int j = 0; j < size; j++) {
                value += matrix[i][j] * vector[j];
            }
            result[i] = value % MODULUS;
        }
    }

    private void appendResult(StringBuilder builder, int[] resultVector, int size) {
        for (int i = 0; i < size; i++) {
            builder.append((char) (resultVector[i] + BASE_CHAR));
        }
    }

    private void validateDeterminant(int[][] keyMatrix, int n) {
        int det = determinant(keyMatrix, n) % MODULUS;
        if (det == 0) {
            throw new IllegalArgumentException("Invalid key matrix. Determinant is zero modulo " + MODULUS + ".");
        }
    }

    private int determinant(int[][] matrix, int n) {
        if (n == 1) {
            return matrix[0][0];
        }

        int det = 0;
        int sign = 1;
        int[][] subMatrix = new int[n - 1][n - 1];

        for (int x = 0; x < n; x++) {
            buildSubMatrix(matrix, subMatrix, n, x);
            det += sign * matrix[0][x] * determinant(subMatrix, n - 1);
            sign = -sign;
        }

        return det;
    }

    private void buildSubMatrix(int[][] matrix, int[][] subMatrix, int n, int excludedColumn) {
        int subI = 0;
        for (int i = 1; i < n; i++) {
            int subJ = 0;
            for (int j = 0; j < n; j++) {
                if (j == excludedColumn) {
                    continue;
                }
                subMatrix[subI][subJ++] = matrix[i][j];
            }
            subI++;
        }
    }
}