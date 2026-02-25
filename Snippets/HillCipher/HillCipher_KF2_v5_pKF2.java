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
            vector[i] = (messageIndex < message.length())
                ? message.charAt(messageIndex) - BASE_CHAR
                : PADDING_CHAR - BASE_CHAR;
        }
    }

    private void multiplyMatrixVectorMod(int[][] matrix, int[] inputVector, int[] outputVector, int mod) {
        int size = matrix.length;
        for (int row = 0; row < size; row++) {
            int value = 0;
            for (int col = 0; col < size; col++) {
                value += matrix[row][col] * inputVector[col];
            }
            outputVector[row] = Math.floorMod(value, mod);
        }
    }

    private void appendVectorAsText(StringBuilder builder, int[] vector) {
        for (int value : vector) {
            builder.append((char) (value + BASE_CHAR));
        }
    }

    private void validateDeterminant(int[][] keyMatrix, int size) {
        int determinantMod = Math.floorMod(determinant(keyMatrix, size), ALPHABET_SIZE);
        if (determinantMod == 0) {
            throw new IllegalArgumentException(
                "Invalid key matrix. Determinant is zero modulo " + ALPHABET_SIZE + "."
            );
        }
    }

    private int determinant(int[][] matrix, int size) {
        if (size == 1) {
            return matrix[0][0];
        }

        int determinant = 0;
        int sign = 1;
        int[][] subMatrix = new int[size - 1][size - 1];

        for (int col = 0; col < size; col++) {
            buildSubMatrix(matrix, subMatrix, size, col);
            determinant += sign * matrix[0][col] * determinant(subMatrix, size - 1);
            sign = -sign;
        }

        return determinant;
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