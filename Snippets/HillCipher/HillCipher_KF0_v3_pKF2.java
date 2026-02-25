package com.thealgorithms.ciphers;

public class HillCipher {

    private static final int ALPHABET_SIZE = 26;
    private static final char PADDING_CHAR = 'X';
    private static final char FIRST_LETTER = 'A';

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

            multiplyMatrixVector(keyMatrix, messageVector, cipherVector, matrixSize);

            appendVectorAsText(cipherText, cipherVector, matrixSize);
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

            multiplyMatrixVector(inverseKeyMatrix, messageVector, plainVector, matrixSize);

            appendVectorAsText(plainText, plainVector, matrixSize);
        }

        return plainText.toString();
    }

    private String normalizeMessage(String message) {
        return message.toUpperCase().replaceAll("[^A-Z]", "");
    }

    private void fillVectorFromMessage(String message, int[] vector, int size, int startIndex) {
        for (int i = 0; i < size; i++) {
            int currentIndex = startIndex + i;
            vector[i] = (currentIndex < message.length())
                ? message.charAt(currentIndex) - FIRST_LETTER
                : PADDING_CHAR - FIRST_LETTER;
        }
    }

    private void multiplyMatrixVector(int[][] matrix, int[] inputVector, int[] outputVector, int size) {
        for (int row = 0; row < size; row++) {
            int value = 0;
            for (int col = 0; col < size; col++) {
                value += matrix[row][col] * inputVector[col];
            }
            outputVector[row] = value % ALPHABET_SIZE;
        }
    }

    private void appendVectorAsText(StringBuilder builder, int[] vector, int size) {
        for (int i = 0; i < size; i++) {
            builder.append((char) (vector[i] + FIRST_LETTER));
        }
    }

    private void validateDeterminant(int[][] keyMatrix, int size) {
        int det = determinant(keyMatrix, size) % ALPHABET_SIZE;
        if (det == 0) {
            throw new IllegalArgumentException(
                "Invalid key matrix: determinant is zero modulo " + ALPHABET_SIZE + "."
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
                subMatrix[subRow][subCol++] = matrix[row][col];
            }
            subRow++;
        }
    }
}