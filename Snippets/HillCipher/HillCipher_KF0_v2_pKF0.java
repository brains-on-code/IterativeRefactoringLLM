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
        String sanitizedMessage = sanitizeMessage(message);
        int matrixSize = matrix.length;

        validateKeyMatrix(matrix, matrixSize);

        StringBuilder result = new StringBuilder();
        int[] inputVector = new int[matrixSize];
        int[] outputVector = new int[matrixSize];

        for (int index = 0; index < sanitizedMessage.length(); index += matrixSize) {
            fillVectorFromMessage(sanitizedMessage, inputVector, matrixSize, index);
            multiplyMatrixVector(matrix, inputVector, outputVector, matrixSize);
            appendVectorAsText(result, outputVector, matrixSize);
        }

        return result.toString();
    }

    private String sanitizeMessage(String message) {
        if (message == null) {
            return "";
        }
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
        for (int row = 0; row < size; row++) {
            int value = 0;
            for (int col = 0; col < size; col++) {
                value += matrix[row][col] * inputVector[col];
            }
            outputVector[row] = Math.floorMod(value, MODULUS);
        }
    }

    private void appendVectorAsText(StringBuilder builder, int[] vector, int size) {
        for (int i = 0; i < size; i++) {
            builder.append((char) (vector[i] + BASE_CHAR));
        }
    }

    private void validateKeyMatrix(int[][] keyMatrix, int size) {
        int det = Math.floorMod(determinant(keyMatrix, size), MODULUS);
        if (det == 0) {
            throw new IllegalArgumentException("Invalid key matrix. Determinant is zero modulo " + MODULUS + ".");
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
}