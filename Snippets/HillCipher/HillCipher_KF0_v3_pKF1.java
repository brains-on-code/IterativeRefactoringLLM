package com.thealgorithms.ciphers;

public class HillCipher {

    private static final int ALPHABET_SIZE = 26;
    private static final char PADDING_CHARACTER = 'X';
    private static final char ALPHABET_BASE_CHARACTER = 'A';

    public String encrypt(String plainText, int[][] keyMatrix) {
        String normalizedPlainText = normalizeText(plainText);
        int matrixSize = keyMatrix.length;
        validateDeterminant(keyMatrix, matrixSize);

        StringBuilder cipherTextBuilder = new StringBuilder();
        int[] blockVector = new int[matrixSize];
        int[] transformedVector = new int[matrixSize];
        int textIndex = 0;

        while (textIndex < normalizedPlainText.length()) {
            fillVectorFromText(normalizedPlainText, blockVector, matrixSize, textIndex);
            textIndex += matrixSize;

            multiplyMatrixByVectorModuloAlphabet(keyMatrix, blockVector, transformedVector, matrixSize);
            appendVectorAsText(cipherTextBuilder, transformedVector, matrixSize);
        }

        return cipherTextBuilder.toString();
    }

    public String decrypt(String cipherText, int[][] inverseKeyMatrix) {
        String normalizedCipherText = normalizeText(cipherText);
        int matrixSize = inverseKeyMatrix.length;
        validateDeterminant(inverseKeyMatrix, matrixSize);

        StringBuilder plainTextBuilder = new StringBuilder();
        int[] blockVector = new int[matrixSize];
        int[] transformedVector = new int[matrixSize];
        int textIndex = 0;

        while (textIndex < normalizedCipherText.length()) {
            fillVectorFromText(normalizedCipherText, blockVector, matrixSize, textIndex);
            textIndex += matrixSize;

            multiplyMatrixByVectorModuloAlphabet(inverseKeyMatrix, blockVector, transformedVector, matrixSize);
            appendVectorAsText(plainTextBuilder, transformedVector, matrixSize);
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
            int[][] matrix, int[] inputVector, int[] outputVector, int matrixSize) {
        for (int rowIndex = 0; rowIndex < matrixSize; rowIndex++) {
            int rowSum = 0;
            for (int columnIndex = 0; columnIndex < matrixSize; columnIndex++) {
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

    private void validateDeterminant(int[][] matrix, int matrixSize) {
        int determinantModuloAlphabet = computeDeterminant(matrix, matrixSize) % ALPHABET_SIZE;
        if (determinantModuloAlphabet == 0) {
            throw new IllegalArgumentException("Invalid key matrix. Determinant is zero modulo 26.");
        }
    }

    private int computeDeterminant(int[][] matrix, int matrixSize) {
        if (matrixSize == 1) {
            return matrix[0][0];
        }

        int determinant = 0;
        int sign = 1;
        int[][] minorMatrix = new int[matrixSize - 1][matrixSize - 1];

        for (int columnIndex = 0; columnIndex < matrixSize; columnIndex++) {
            buildMinorMatrix(matrix, minorMatrix, matrixSize, columnIndex);
            determinant += sign * matrix[0][columnIndex] * computeDeterminant(minorMatrix, matrixSize - 1);
            sign = -sign;
        }

        return determinant;
    }

    private void buildMinorMatrix(
            int[][] originalMatrix, int[][] minorMatrix, int matrixSize, int excludedColumnIndex) {
        int minorRowIndex = 0;
        for (int rowIndex = 1; rowIndex < matrixSize; rowIndex++) {
            int minorColumnIndex = 0;
            for (int columnIndex = 0; columnIndex < matrixSize; columnIndex++) {
                if (columnIndex == excludedColumnIndex) {
                    continue;
                }
                minorMatrix[minorRowIndex][minorColumnIndex++] = originalMatrix[rowIndex][columnIndex];
            }
            minorRowIndex++;
        }
    }
}