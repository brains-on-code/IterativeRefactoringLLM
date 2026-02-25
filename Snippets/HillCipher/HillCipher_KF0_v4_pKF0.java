package com.thealgorithms.ciphers;

public class HillCipher {

    private static final int MODULUS = 26;
    private static final char PADDING_CHAR = 'X';
    private static final char BASE_CHAR = 'A';
    private static final String NON_ALPHABETIC_REGEX = "[^A-Z]";

    public String encrypt(String message, int[][] keyMatrix) {
        return transform(message, keyMatrix);
    }

    public String decrypt(String message, int[][] inverseKeyMatrix) {
        return transform(message, inverseKeyMatrix);
    }

    private String transform(String message, int[][] matrix) {
        validateKeyMatrix(matrix);

        String sanitizedMessage = sanitizeMessage(message);
        int matrixSize = matrix.length;

        StringBuilder result = new StringBuilder();
        int[] inputVector = new int[matrixSize];
        int[] outputVector = new int[matrixSize];

        for (int index = 0; index < sanitizedMessage.length(); index += matrixSize) {
            fillVectorFromMessage(sanitizedMessage, inputVector, index);
            multiplyMatrixVector(matrix, inputVector, outputVector);
            appendVectorAsText(result, outputVector);
        }

        return result.toString();
    }

    private String sanitizeMessage(String message) {
        if (message == null) {
            return "";
        }
        return message.toUpperCase().replaceAll(NON_ALPHABETIC_REGEX, "");
    }

    private void fillVectorFromMessage(String message, int[] vector, int startIndex) {
        int size = vector.length;
        int messageLength = message.length();

        for (int i = 0; i < size; i++) {
            int messageIndex = startIndex + i;
            char ch = messageIndex < messageLength ? message.charAt(messageIndex) : PADDING_CHAR;
            vector[i] = ch - BASE_CHAR;
        }
    }

    private void multiplyMatrixVector(int[][] matrix, int[] inputVector, int[] outputVector) {
        int size = inputVector.length;

        for (int row = 0; row < size; row++) {
            int value = 0;
            for (int col = 0; col < size; col++) {
                value += matrix[row][col] * inputVector[col];
            }
            outputVector[row] = Math.floorMod(value, MODULUS);
        }
    }

    private void appendVectorAsText(StringBuilder builder, int[] vector) {
        for (int value : vector) {
            builder.append((char) (value + BASE_CHAR));
        }
    }

    private void validateKeyMatrix(int[][] keyMatrix) {
        int size = keyMatrix.length;
        int det = Math.floorMod(determinant(keyMatrix, size), MODULUS);
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

    private void buildSubMatrix(int[][] matrix, int[][] subMatrix, int size, int excludedCol) {
        int subRow = 0;

        for (int row = 1; row < size; row++) {
            int subCol = 0;
            for (int col = 0; col < size; col++) {
                if (col == excludedCol) {
                    continue;
                }
                subMatrix[subRow][subCol] = matrix[row][col];
                subCol++;
            }
            subRow++;
        }
    }
}