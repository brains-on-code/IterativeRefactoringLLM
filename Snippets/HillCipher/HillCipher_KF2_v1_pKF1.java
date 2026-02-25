package com.thealgorithms.ciphers;

public class HillCipher {

    public String encrypt(String plainText, int[][] keyMatrix) {
        String normalizedText = plainText.toUpperCase().replaceAll("[^A-Z]", "");
        int matrixSize = keyMatrix.length;
        validateDeterminant(keyMatrix, matrixSize);

        StringBuilder cipherTextBuilder = new StringBuilder();
        int[] plainTextVector = new int[matrixSize];
        int[] cipherTextVector = new int[matrixSize];
        int textIndex = 0;

        while (textIndex < normalizedText.length()) {
            for (int rowIndex = 0; rowIndex < matrixSize; rowIndex++) {
                if (textIndex < normalizedText.length()) {
                    plainTextVector[rowIndex] = normalizedText.charAt(textIndex++) - 'A';
                } else {
                    plainTextVector[rowIndex] = 'X' - 'A';
                }
            }

            for (int rowIndex = 0; rowIndex < matrixSize; rowIndex++) {
                cipherTextVector[rowIndex] = 0;
                for (int columnIndex = 0; columnIndex < matrixSize; columnIndex++) {
                    cipherTextVector[rowIndex] += keyMatrix[rowIndex][columnIndex] * plainTextVector[columnIndex];
                }
                cipherTextVector[rowIndex] = cipherTextVector[rowIndex] % 26;
                cipherTextBuilder.append((char) (cipherTextVector[rowIndex] + 'A'));
            }
        }

        return cipherTextBuilder.toString();
    }

    public String decrypt(String cipherText, int[][] inverseKeyMatrix) {
        String normalizedText = cipherText.toUpperCase().replaceAll("[^A-Z]", "");
        int matrixSize = inverseKeyMatrix.length;
        validateDeterminant(inverseKeyMatrix, matrixSize);

        StringBuilder plainTextBuilder = new StringBuilder();
        int[] cipherTextVector = new int[matrixSize];
        int[] plainTextVector = new int[matrixSize];
        int textIndex = 0;

        while (textIndex < normalizedText.length()) {
            for (int rowIndex = 0; rowIndex < matrixSize; rowIndex++) {
                if (textIndex < normalizedText.length()) {
                    cipherTextVector[rowIndex] = normalizedText.charAt(textIndex++) - 'A';
                } else {
                    cipherTextVector[rowIndex] = 'X' - 'A';
                }
            }

            for (int rowIndex = 0; rowIndex < matrixSize; rowIndex++) {
                plainTextVector[rowIndex] = 0;
                for (int columnIndex = 0; columnIndex < matrixSize; columnIndex++) {
                    plainTextVector[rowIndex] += inverseKeyMatrix[rowIndex][columnIndex] * cipherTextVector[columnIndex];
                }
                plainTextVector[rowIndex] = plainTextVector[rowIndex] % 26;
                plainTextBuilder.append((char) (plainTextVector[rowIndex] + 'A'));
            }
        }

        return plainTextBuilder.toString();
    }

    private void validateDeterminant(int[][] keyMatrix, int matrixSize) {
        int determinantModulo = determinant(keyMatrix, matrixSize) % 26;
        if (determinantModulo == 0) {
            throw new IllegalArgumentException("Invalid key matrix. Determinant is zero modulo 26.");
        }
    }

    private int determinant(int[][] matrix, int matrixSize) {
        if (matrixSize == 1) {
            return matrix[0][0];
        }

        int determinantValue = 0;
        int sign = 1;
        int[][] subMatrix = new int[matrixSize - 1][matrixSize - 1];

        for (int columnIndex = 0; columnIndex < matrixSize; columnIndex++) {
            int subMatrixRow = 0;
            for (int rowIndex = 1; rowIndex < matrixSize; rowIndex++) {
                int subMatrixColumn = 0;
                for (int innerColumnIndex = 0; innerColumnIndex < matrixSize; innerColumnIndex++) {
                    if (innerColumnIndex != columnIndex) {
                        subMatrix[subMatrixRow][subMatrixColumn++] = matrix[rowIndex][innerColumnIndex];
                    }
                }
                subMatrixRow++;
            }
            determinantValue += sign * matrix[0][columnIndex] * determinant(subMatrix, matrixSize - 1);
            sign = -sign;
        }

        return determinantValue;
    }
}