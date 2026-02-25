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
            if (currentIndex < message.length()) {
                vector[i] = message.charAt(currentIndex) - FIRST_LETTER;
            } else {
                vector[i] = PADDING_CHAR - FIRST_LETTER;
            }
        }
    }

    private void multiplyMatrixVector(int[][] matrix, int[] inputVector, int[] outputVector, int size) {
        for (int i = 0; i < size; i++) {
            outputVector[i] = 0;
            for (int j = 0; j < size; j++) {
                outputVector[i] += matrix[i][j] * inputVector[j];
            }
            outputVector[i] = outputVector[i] % ALPHABET_SIZE;
        }
    }

    private void appendVectorAsText(StringBuilder builder, int[] vector, int size) {
        for (int i = 0; i < size; i++) {
            builder.append((char) (vector[i] + FIRST_LETTER));
        }
    }

    private void validateDeterminant(int[][] keyMatrix, int n) {
        int det = determinant(keyMatrix, n) % ALPHABET_SIZE;
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

        for (int x = 0; x < n; x++) {
            int subI = 0;
            for (int i = 1; i < n; i++) {
                int subJ = 0;
                for (int j = 0; j < n; j++) {
                    if (j != x) {
                        subMatrix[subI][subJ++] = matrix[i][j];
                    }
                }
                subI++;
            }
            det += sign * matrix[0][x] * determinant(subMatrix, n - 1);
            sign = -sign;
        }

        return det;
    }
}