package com.thealgorithms.ciphers;

public class HillCipher {

    private static final int ALPHABET_SIZE = 26;
    private static final char PADDING_CHARACTER = 'X';
    private static final char ALPHABET_BASE_CHARACTER = 'A';

    public String encrypt(String plainText, int[][] keyMatrix) {
        String normalizedPlainText = normalizeText(plainText);
        int dimension = keyMatrix.length;
        validateDeterminant(keyMatrix, dimension);

        StringBuilder cipherTextBuilder = new StringBuilder();
        int[] inputVector = new int[dimension];
        int[] outputVector = new int[dimension];
        int currentIndex = 0;

        while (currentIndex < normalizedPlainText.length()) {
            fillVectorFromText(normalizedPlainText, inputVector, dimension, currentIndex);
            currentIndex += dimension;

            multiplyMatrixByVectorModuloAlphabet(keyMatrix, inputVector, outputVector, dimension);
            appendVectorAsText(cipherTextBuilder, outputVector, dimension);
        }

        return cipherTextBuilder.toString();
    }

    public String decrypt(String cipherText, int[][] inverseKeyMatrix) {
        String normalizedCipherText = normalizeText(cipherText);
        int dimension = inverseKeyMatrix.length;
        validateDeterminant(inverseKeyMatrix, dimension);

        StringBuilder plainTextBuilder = new StringBuilder();
        int[] inputVector = new int[dimension];
        int[] outputVector = new int[dimension];
        int currentIndex = 0;

        while (currentIndex < normalizedCipherText.length()) {
            fillVectorFromText(normalizedCipherText, inputVector, dimension, currentIndex);
            currentIndex += dimension;

            multiplyMatrixByVectorModuloAlphabet(inverseKeyMatrix, inputVector, outputVector, dimension);
            appendVectorAsText(plainTextBuilder, outputVector, dimension);
        }

        return plainTextBuilder.toString();
    }

    private String normalizeText(String text) {
        return text.toUpperCase().replaceAll("[^A-Z]", "");
    }

    private void fillVectorFromText(String text, int[] vector, int vectorLength, int startIndex) {
        for (int offset = 0; offset < vectorLength; offset++) {
            int textIndex = startIndex + offset;
            if (textIndex < text.length()) {
                vector[offset] = text.charAt(textIndex) - ALPHABET_BASE_CHARACTER;
            } else {
                vector[offset] = PADDING_CHARACTER - ALPHABET_BASE_CHARACTER;
            }
        }
    }

    private void multiplyMatrixByVectorModuloAlphabet(
            int[][] matrix, int[] inputVector, int[] outputVector, int dimension) {
        for (int rowIndex = 0; rowIndex < dimension; rowIndex++) {
            int rowSum = 0;
            for (int columnIndex = 0; columnIndex < dimension; columnIndex++) {
                rowSum += matrix[rowIndex][columnIndex] * inputVector[columnIndex];
            }
            outputVector[rowIndex] = rowSum % ALPHABET_SIZE;
        }
    }

    private void appendVectorAsText(StringBuilder textBuilder, int[] vector, int vectorLength) {
        for (int index = 0; index < vectorLength; index++) {
            textBuilder.append((char) (vector[index] + ALPHABET_BASE_CHARACTER));
        }
    }

    private void validateDeterminant(int[][] matrix, int dimension) {
        int determinantModuloAlphabet = computeDeterminant(matrix, dimension) % ALPHABET_SIZE;
        if (determinantModuloAlphabet == 0) {
            throw new IllegalArgumentException("Invalid key matrix. Determinant is zero modulo 26.");
        }
    }

    private int computeDeterminant(int[][] matrix, int dimension) {
        if (dimension == 1) {
            return matrix[0][0];
        }

        int determinant = 0;
        int sign = 1;
        int[][] minorMatrix = new int[dimension - 1][dimension - 1];

        for (int columnIndex = 0; columnIndex < dimension; columnIndex++) {
            buildMinorMatrix(matrix, minorMatrix, dimension, columnIndex);
            determinant += sign * matrix[0][columnIndex] * computeDeterminant(minorMatrix, dimension - 1);
            sign = -sign;
        }

        return determinant;
    }

    private void buildMinorMatrix(
            int[][] originalMatrix, int[][] minorMatrix, int dimension, int excludedColumnIndex) {
        int minorRowIndex = 0;
        for (int rowIndex = 1; rowIndex < dimension; rowIndex++) {
            int minorColumnIndex = 0;
            for (int columnIndex = 0; columnIndex < dimension; columnIndex++) {
                if (columnIndex == excludedColumnIndex) {
                    continue;
                }
                minorMatrix[minorRowIndex][minorColumnIndex++] = originalMatrix[rowIndex][columnIndex];
            }
            minorRowIndex++;
        }
    }
}