package com.thealgorithms.ciphers;

public class HillCipher {

    private static final int ALPHABET_SIZE = 26;
    private static final char PADDING_CHAR = 'X';
    private static final char BASE_CHAR = 'A';

    public String encrypt(String message, int[][] keyMatrix) {
        String normalizedMessage = normalizeMessage(message);
        int matrixSize = keyMatrix.length;

        validateDeterminant(keyMatrix, matrixSize);

        StringBuilder cipherText = new StringBuilder();
        int[] messageVector = new int[matrixSize];
        int[] cipherVector = new int[matrixSize];
        int index = 0;

        while (index < normalizedMessage.length()) {
            fillVectorFromMessage(normalizedMessage, messageVector, matrixSize, index);
            index += matrixSize;

            multiplyMatrixVectorMod(keyMatrix, messageVector, cipherVector, ALPHABET_SIZE);
            appendVectorAsText(cipherText, cipherVector);
        }

        return cipherText.toString();
    }

    public String decrypt(String message, int[][] inverseKeyMatrix) {
        String normalizedMessage = normalizeMessage(message);
        int matrixSize = inverseKeyMatrix.length;

        validateDeterminant(inverseKeyMatrix, matrixSize);

        StringBuilder plainText = new StringBuilder();
        int[] messageVector = new int[matrixSize];
        int[] plainVector = new int[matrixSize];
        int index = 0;

        while (index < normalizedMessage.length()) {
            fillVectorFromMessage(normalizedMessage, messageVector, matrixSize, index);
            index += matrixSize;

            multiplyMatrixVectorMod(inverseKeyMatrix, messageVector, plainVector, ALPHABET_SIZE);
            appendVectorAsText(plainText, plainVector);
        }

        return plainText.toString();
    }

    private String normalizeMessage(String message) {
        return message.toUpperCase().replaceAll("[^A-Z]", "");
    }

    private void fillVectorFromMessage(String message, int[] vector, int size, int startIndex) {
        for (int i = 0; i < size; i++) {
            int messageIndex = startIndex + i;
            if (messageIndex < message.length()) {
                vector[i] = message.charAt(messageIndex) - BASE_CHAR;
            } else {
                vector[i] = PADDING_CHAR - BASE_CHAR;
            }
        }
    }

    private void multiplyMatrixVectorMod(int[][] matrix, int[] inputVector, int[] outputVector, int mod) {
        int size = matrix.length;
        for (int i = 0; i < size; i++) {
            int value = 0;
            for (int j = 0; j < size; j++) {
                value += matrix[i][j] * inputVector[j];
            }
            outputVector[i] = Math.floorMod(value, mod);
        }
    }

    private void appendVectorAsText(StringBuilder builder, int[] vector) {
        for (int value : vector) {
            builder.append((char) (value + BASE_CHAR));
        }
    }

    private void validateDeterminant(int[][] keyMatrix, int n) {
        int det = Math.floorMod(determinant(keyMatrix, n), ALPHABET_SIZE);
        if (det == 0) {
            throw new IllegalArgumentException("Invalid key matrix. Determinant is zero modulo " + ALPHABET_SIZE + ".");
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