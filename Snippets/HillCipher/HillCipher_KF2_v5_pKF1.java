package com.thealgorithms.ciphers;

public class HillCipher {

    private static final int ALPHABET_SIZE = 26;
    private static final char PADDING_CHARACTER = 'X';
    private static final char BASE_CHARACTER = 'A';

    public String encrypt(String plainText, int[][] keyMatrix) {
        String normalizedPlainText = normalizeText(plainText);
        int matrixDimension = keyMatrix.length;
        validateDeterminant(keyMatrix, matrixDimension);

        StringBuilder cipherTextBuilder = new StringBuilder();
        int[] plainTextVector = new int[matrixDimension];
        int[] cipherTextVector = new int[matrixDimension];
        int textIndex = 0;

        while (textIndex < normalizedPlainText.length()) {
            for (int rowIndex = 0; rowIndex < matrixDimension; rowIndex++) {
                if (textIndex < normalizedPlainText.length()) {
                    plainTextVector[rowIndex] = normalizedPlainText.charAt(textIndex++) - BASE_CHARACTER;
                } else {
                    plainTextVector[rowIndex] = PADDING_CHARACTER - BASE_CHARACTER;
                }
            }

            for (int rowIndex = 0; rowIndex < matrixDimension; rowIndex++) {
                cipherTextVector[rowIndex] = 0;
                for (int columnIndex = 0; columnIndex < matrixDimension; columnIndex++) {
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
        int matrixDimension = inverseKeyMatrix.length;
        validateDeterminant(inverseKeyMatrix, matrixDimension);

        StringBuilder plainTextBuilder = new StringBuilder();
        int[] cipherTextVector = new int[matrixDimension];
        int[] plainTextVector = new int[matrixDimension];
        int textIndex = 0;

        while (textIndex < normalizedCipherText.length()) {
            for (int rowIndex = 0; rowIndex < matrixDimension; rowIndex++) {
                if (textIndex < normalizedCipherText.length()) {
                    cipherTextVector[rowIndex] = normalizedCipherText.charAt(textIndex++) - BASE_CHARACTER;
                } else {
                    cipherTextVector[rowIndex] = PADDING_CHARACTER - BASE_CHARACTER;
                }
            }

            for (int rowIndex = 0; rowIndex < matrixDimension; rowIndex++) {
                plainTextVector[rowIndex] = 0;
                for (int columnIndex = 0; columnIndex < matrixDimension; columnIndex++) {
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

    private void validateDeterminant(int[][] matrix, int matrixDimension) {
        int determinantModuloAlphabet = calculateDeterminant(matrix, matrixDimension) % ALPHABET_SIZE;
        if (determinantModuloAlphabet == 0) {
            throw new IllegalArgumentException(
                "Invalid key matrix. Determinant is zero modulo " + ALPHABET_SIZE + "."
            );
        }
    }

    private int calculateDeterminant(int[][] matrix, int matrixDimension) {
        if (matrixDimension == 1) {
            return matrix[0][0];
        }

        int determinant = 0;
        int sign = 1;
        int[][] minorMatrix = new int[matrixDimension - 1][matrixDimension - 1];

        for (int columnIndex = 0; columnIndex < matrixDimension; columnIndex++) {
            int minorRowIndex = 0;
            for (int rowIndex = 1; rowIndex < matrixDimension; rowIndex++) {
                int minorColumnIndex = 0;
                for (int innerColumnIndex = 0; innerColumnIndex < matrixDimension; innerColumnIndex++) {
                    if (innerColumnIndex != columnIndex) {
                        minorMatrix[minorRowIndex][minorColumnIndex++] = matrix[rowIndex][innerColumnIndex];
                    }
                }
                minorRowIndex++;
            }
            determinant += sign * matrix[0][columnIndex] * calculateDeterminant(minorMatrix, matrixDimension - 1);
            sign = -sign;
        }

        return determinant;
    }
}