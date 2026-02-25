package com.thealgorithms.ciphers;

public class HillCipher {

    private static final int ALPHABET_SIZE = 26;
    private static final char PADDING_CHAR = 'X';
    private static final char BASE_CHAR = 'A';

    public String encrypt(String plainText, int[][] keyMatrix) {
        String normalizedText = normalizeText(plainText);
        int matrixSize = keyMatrix.length;
        validateDeterminant(keyMatrix, matrixSize);

        StringBuilder cipherTextBuilder = new StringBuilder();
        int[] textVector = new int[matrixSize];
        int[] resultVector = new int[matrixSize];
        int textIndex = 0;

        while (textIndex < normalizedText.length()) {
            fillVectorFromText(normalizedText, textVector, matrixSize, textIndex);
            textIndex += matrixSize;

            multiplyMatrixVectorModAlphabet(keyMatrix, textVector, resultVector, matrixSize);
            appendVectorAsText(cipherTextBuilder, resultVector, matrixSize);
        }

        return cipherTextBuilder.toString();
    }

    public String decrypt(String cipherText, int[][] inverseKeyMatrix) {
        String normalizedText = normalizeText(cipherText);
        int matrixSize = inverseKeyMatrix.length;
        validateDeterminant(inverseKeyMatrix, matrixSize);

        StringBuilder plainTextBuilder = new StringBuilder();
        int[] textVector = new int[matrixSize];
        int[] resultVector = new int[matrixSize];
        int textIndex = 0;

        while (textIndex < normalizedText.length()) {
            fillVectorFromText(normalizedText, textVector, matrixSize, textIndex);
            textIndex += matrixSize;

            multiplyMatrixVectorModAlphabet(inverseKeyMatrix, textVector, resultVector, matrixSize);
            appendVectorAsText(plainTextBuilder, resultVector, matrixSize);
        }

        return plainTextBuilder.toString();
    }

    private String normalizeText(String text) {
        return text.toUpperCase().replaceAll("[^A-Z]", "");
    }

    private void fillVectorFromText(String text, int[] vector, int vectorSize, int startIndex) {
        for (int i = 0; i < vectorSize; i++) {
            int currentIndex = startIndex + i;
            if (currentIndex < text.length()) {
                vector[i] = text.charAt(currentIndex) - BASE_CHAR;
            } else {
                vector[i] = PADDING_CHAR - BASE_CHAR;
            }
        }
    }

    private void multiplyMatrixVectorModAlphabet(int[][] matrix, int[] inputVector, int[] outputVector, int size) {
        for (int row = 0; row < size; row++) {
            int sum = 0;
            for (int col = 0; col < size; col++) {
                sum += matrix[row][col] * inputVector[col];
            }
            outputVector[row] = sum % ALPHABET_SIZE;
        }
    }

    private void appendVectorAsText(StringBuilder builder, int[] vector, int vectorSize) {
        for (int i = 0; i < vectorSize; i++) {
            builder.append((char) (vector[i] + BASE_CHAR));
        }
    }

    private void validateDeterminant(int[][] keyMatrix, int matrixSize) {
        int determinantValue = computeDeterminant(keyMatrix, matrixSize) % ALPHABET_SIZE;
        if (determinantValue == 0) {
            throw new IllegalArgumentException("Invalid key matrix. Determinant is zero modulo 26.");
        }
    }

    private int computeDeterminant(int[][] matrix, int size) {
        if (size == 1) {
            return matrix[0][0];
        }

        int determinant = 0;
        int sign = 1;
        int[][] subMatrix = new int[size - 1][size - 1];

        for (int column = 0; column < size; column++) {
            buildSubMatrix(matrix, subMatrix, size, column);
            determinant += sign * matrix[0][column] * computeDeterminant(subMatrix, size - 1);
            sign = -sign;
        }

        return determinant;
    }

    private void buildSubMatrix(int[][] originalMatrix, int[][] subMatrix, int size, int excludedColumn) {
        int subRow = 0;
        for (int row = 1; row < size; row++) {
            int subCol = 0;
            for (int col = 0; col < size; col++) {
                if (col == excludedColumn) {
                    continue;
                }
                subMatrix[subRow][subCol++] = originalMatrix[row][col];
            }
            subRow++;
        }
    }
}