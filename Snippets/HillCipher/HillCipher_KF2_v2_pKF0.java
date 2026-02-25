package com.thealgorithms.ciphers;

public class HillCipher {

    private static final int MODULUS = 26;
    private static final char PADDING_CHAR = 'X';
    private static final char BASE_CHAR = 'A';

    public String encrypt(String message, int[][] keyMatrix) {
        return transform(message, keyMatrix);
    }

    public String decrypt(String message, int[][] inverseKeyMatrix) {
        return transform(message, inverseKeyMatrix);
    }

    private String transform(String message, int[][] matrix) {
        String normalizedMessage = normalizeMessage(message);
        int matrixSize = matrix.length;

        validateDeterminant(matrix, matrixSize);

        StringBuilder result = new StringBuilder();
        int[] messageVector = new int[matrixSize];
        int[] resultVector = new int[matrixSize];

        for (int index = 0; index < normalizedMessage.length(); index += matrixSize) {
            fillMessageVector(normalizedMessage, messageVector, matrixSize, index);
            multiplyMatrixVector(matrix, messageVector, resultVector, matrixSize);
            appendVectorAsText(result, resultVector, matrixSize);
        }

        return result.toString();
    }

    private String normalizeMessage(String message) {
        return message.toUpperCase().replaceAll("[^A-Z]", "");
    }

    private void fillMessageVector(String message, int[] messageVector, int size, int startIndex) {
        for (int i = 0; i < size; i++) {
            int currentIndex = startIndex + i;
            char ch = currentIndex < message.length() ? message.charAt(currentIndex) : PADDING_CHAR;
            messageVector[i] = ch - BASE_CHAR;
        }
    }

    private void multiplyMatrixVector(int[][] matrix, int[] vector, int[] result, int size) {
        for (int row = 0; row < size; row++) {
            int value = 0;
            for (int col = 0; col < size; col++) {
                value += matrix[row][col] * vector[col];
            }
            result[row] = value % MODULUS;
        }
    }

    private void appendVectorAsText(StringBuilder builder, int[] resultVector, int size) {
        for (int i = 0; i < size; i++) {
            builder.append((char) (resultVector[i] + BASE_CHAR));
        }
    }

    private void validateDeterminant(int[][] keyMatrix, int size) {
        int det = determinant(keyMatrix, size) % MODULUS;
        if (det == 0) {
            throw new IllegalArgumentException(
                "Invalid key matrix. Determinant is zero modulo " + MODULUS + "."
            );
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

    private void buildSubMatrix(int[][] matrix, int[][] subMatrix, int size, int excludedColumn) {
        int subRow = 0;
        for (int row = 1; row < size; row++) {
            int subCol = 0;
            for (int col = 0; col < size; col++) {
                if (col == excludedColumn) {
                    continue;
                }
                subMatrix[subRow][subCol++] = matrix[row][col];
            }
            subRow++;
        }
    }
}