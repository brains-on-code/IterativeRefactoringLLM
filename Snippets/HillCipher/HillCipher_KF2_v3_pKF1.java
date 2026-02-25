package com.thealgorithms.ciphers;

public class HillCipher {

    private static final int ALPHABET_SIZE = 26;
    private static final char PADDING_CHARACTER = 'X';
    private static final char BASE_CHARACTER = 'A';

    public String encrypt(String plainText, int[][] keyMatrix) {
        String normalizedPlainText = normalizeText(plainText);
        int matrixSize = keyMatrix.length;
        validateDeterminant(keyMatrix, matrixSize);

        StringBuilder cipherTextBuilder = new StringBuilder();
        int[] plainTextVector = new int[matrixSize];
        int[] cipherTextVector = new int[matrixSize];
        int textIndex = 0;

        while (textIndex < normalizedPlainText.length()) {
            for (int rowIndex = 0; rowIndex < matrixSize; rowIndex++) {
                if (textIndex < normalizedPlainText.length()) {
                    plainTextVector[rowIndex] = normalizedPlainText.charAt(textIndex++) - BASE_CHARACTER;
                } else {
                    plainTextVector[rowIndex] = PADDING_CHARACTER - BASE_CHARACTER;
                }
            }

            for (int rowIndex = 0; rowIndex < matrixSize; rowIndex++) {
                cipherTextVector[rowIndex] = 0;
                for (int columnIndex = 0; columnIndex < matrixSize; columnIndex++) {
                    cipherTextVector[rowIndex] += keyMatrix[rowIndex][columnIndex] * plainTextVector[columnIndex];
                }
                cipherTextVector[rowIndex] = cipherTextVector[rowIndex] % ALPHABET_SIZE;
                cipherTextBuilder.append((char) (cipherTextVector[rowIndex] + BASE_CHARACTER));
            }
        }

        return cipherTextBuilder.toString();
    }

    public String decrypt(String cipherText, int[][] inverseKeyMatrix) {
        String normalizedCipherText = normalizeText(cipherText);
        int matrixSize = inverseKeyMatrix.length;
        validateDeterminant(inverseKeyMatrix, matrixSize);

        StringBuilder plainTextBuilder = new StringBuilder();
        int[] cipherTextVector = new int[matrixSize];
        int[] plainTextVector = new int[matrixSize];
        int textIndex = 0;

        while (textIndex < normalizedCipherText.length()) {
            for (int rowIndex = 0; rowIndex < matrixSize; rowIndex++) {
                if (textIndex < normalizedCipherText.length()) {
                    cipherTextVector[rowIndex] = normalizedCipherText.charAt(textIndex++) - BASE_CHARACTER;
                } else {
                    cipherTextVector[rowIndex] = PADDING_CHARACTER - BASE_CHARACTER;
                }
            }

            for (int rowIndex = 0; rowIndex < matrixSize; rowIndex++) {
                plainTextVector[rowIndex] = 0;
                for (int columnIndex = 0; columnIndex < matrixSize; columnIndex++) {
                    plainTextVector[rowIndex] += inverseKeyMatrix[rowIndex][columnIndex] * cipherTextVector[columnIndex];
                }
                plainTextVector[rowIndex] = plainTextVector[rowIndex] % ALPHABET_SIZE;
                plainTextBuilder.append((char) (plainTextVector[rowIndex] + BASE_CHARACTER));
            }
        }

        return plainTextBuilder.toString();
    }

    private String normalizeText(String text) {
        return text.toUpperCase().replaceAll("[^A-Z]", "");
    }

    private void validateDeterminant(int[][] matrix, int matrixSize) {
        int determinantModulo = calculateDeterminant(matrix, matrixSize) % ALPHABET_SIZE;
        if (determinantModulo == 0) {
            throw new IllegalArgumentException("Invalid key matrix. Determinant is zero modulo " + ALPHABET_SIZE + ".");
        }
    }

    private int calculateDeterminant(int[][] matrix, int matrixSize) {
        if (matrixSize == 1) {
            return matrix[0][0];
        }

        int determinantValue = 0;
        int sign = 1;
        int[][] minorMatrix = new int[matrixSize - 1][matrixSize - 1];

        for (int columnIndex = 0; columnIndex < matrixSize; columnIndex++) {
            int minorRowIndex = 0;
            for (int rowIndex = 1; rowIndex < matrixSize; rowIndex++) {
                int minorColumnIndex = 0;
                for (int innerColumnIndex = 0; innerColumnIndex < matrixSize; innerColumnIndex++) {
                    if (innerColumnIndex != columnIndex) {
                        minorMatrix[minorRowIndex][minorColumnIndex++] = matrix[rowIndex][innerColumnIndex];
                    }
                }
                minorRowIndex++;
            }
            determinantValue += sign * matrix[0][columnIndex] * calculateDeterminant(minorMatrix, matrixSize - 1);
            sign = -sign;
        }

        return determinantValue;
    }
}