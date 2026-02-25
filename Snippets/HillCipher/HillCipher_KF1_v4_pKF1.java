package com.thealgorithms.ciphers;

public class HillCipher {

    private static final int ALPHABET_SIZE = 26;
    private static final char PADDING_CHARACTER = 'X';
    private static final char FIRST_ALPHABET_LETTER = 'A';

    public String encrypt(String plaintext, int[][] keyMatrix) {
        return encryptInternal(plaintext, keyMatrix);
    }

    public String encryptAlternative(String plaintext, int[][] keyMatrix) {
        return encryptInternal(plaintext, keyMatrix);
    }

    private String encryptInternal(String plaintext, int[][] keyMatrix) {
        String normalizedPlaintext = normalizePlaintext(plaintext);
        int matrixSize = keyMatrix.length;
        validateKeyMatrix(keyMatrix, matrixSize);

        StringBuilder ciphertextBuilder = new StringBuilder();
        int[] plaintextVector = new int[matrixSize];
        int[] ciphertextVector = new int[matrixSize];
        int plaintextPosition = 0;

        while (plaintextPosition < normalizedPlaintext.length()) {
            for (int rowIndex = 0; rowIndex < matrixSize; rowIndex++) {
                if (plaintextPosition < normalizedPlaintext.length()) {
                    plaintextVector[rowIndex] =
                        normalizedPlaintext.charAt(plaintextPosition++) - FIRST_ALPHABET_LETTER;
                } else {
                    plaintextVector[rowIndex] = PADDING_CHARACTER - FIRST_ALPHABET_LETTER;
                }
            }

            for (int rowIndex = 0; rowIndex < matrixSize; rowIndex++) {
                ciphertextVector[rowIndex] = 0;
                for (int columnIndex = 0; columnIndex < matrixSize; columnIndex++) {
                    ciphertextVector[rowIndex] += keyMatrix[rowIndex][columnIndex] * plaintextVector[columnIndex];
                }
                ciphertextVector[rowIndex] = ciphertextVector[rowIndex] % ALPHABET_SIZE;
                ciphertextBuilder.append((char) (ciphertextVector[rowIndex] + FIRST_ALPHABET_LETTER));
            }
        }

        return ciphertextBuilder.toString();
    }

    private String normalizePlaintext(String plaintext) {
        return plaintext.toUpperCase().replaceAll("[^A-Z]", "");
    }

    private void validateKeyMatrix(int[][] keyMatrix, int matrixSize) {
        int determinantModuloAlphabet = calculateDeterminant(keyMatrix, matrixSize) % ALPHABET_SIZE;
        if (determinantModuloAlphabet == 0) {
            throw new IllegalArgumentException(
                "Invalid key matrix. Determinant is zero modulo " + ALPHABET_SIZE + "."
            );
        }
    }

    private int calculateDeterminant(int[][] matrix, int matrixSize) {
        if (matrixSize == 1) {
            return matrix[0][0];
        }

        int determinant = 0;
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
            determinant += sign * matrix[0][columnIndex] * calculateDeterminant(minorMatrix, matrixSize - 1);
            sign = -sign;
        }

        return determinant;
    }
}