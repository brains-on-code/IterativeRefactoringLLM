package com.thealgorithms.ciphers;

public class HillCipher {

    private static final int ALPHABET_SIZE = 26;
    private static final char PADDING_CHAR = 'X';
    private static final char FIRST_LETTER = 'A';

    public String encrypt(String plaintext, int[][] keyMatrix) {
        String normalizedPlaintext = normalizePlaintext(plaintext);
        int matrixSize = keyMatrix.length;
        validateKeyMatrix(keyMatrix, matrixSize);

        StringBuilder ciphertextBuilder = new StringBuilder();
        int[] plaintextVector = new int[matrixSize];
        int[] ciphertextVector = new int[matrixSize];
        int textIndex = 0;

        while (textIndex < normalizedPlaintext.length()) {
            for (int row = 0; row < matrixSize; row++) {
                if (textIndex < normalizedPlaintext.length()) {
                    plaintextVector[row] = normalizedPlaintext.charAt(textIndex++) - FIRST_LETTER;
                } else {
                    plaintextVector[row] = PADDING_CHAR - FIRST_LETTER;
                }
            }

            for (int row = 0; row < matrixSize; row++) {
                ciphertextVector[row] = 0;
                for (int col = 0; col < matrixSize; col++) {
                    ciphertextVector[row] += keyMatrix[row][col] * plaintextVector[col];
                }
                ciphertextVector[row] = ciphertextVector[row] % ALPHABET_SIZE;
                ciphertextBuilder.append((char) (ciphertextVector[row] + FIRST_LETTER));
            }
        }

        return ciphertextBuilder.toString();
    }

    public String encryptAlternative(String plaintext, int[][] keyMatrix) {
        String normalizedPlaintext = normalizePlaintext(plaintext);
        int matrixSize = keyMatrix.length;
        validateKeyMatrix(keyMatrix, matrixSize);

        StringBuilder ciphertextBuilder = new StringBuilder();
        int[] plaintextVector = new int[matrixSize];
        int[] ciphertextVector = new int[matrixSize];
        int textIndex = 0;

        while (textIndex < normalizedPlaintext.length()) {
            for (int row = 0; row < matrixSize; row++) {
                if (textIndex < normalizedPlaintext.length()) {
                    plaintextVector[row] = normalizedPlaintext.charAt(textIndex++) - FIRST_LETTER;
                } else {
                    plaintextVector[row] = PADDING_CHAR - FIRST_LETTER;
                }
            }

            for (int row = 0; row < matrixSize; row++) {
                ciphertextVector[row] = 0;
                for (int col = 0; col < matrixSize; col++) {
                    ciphertextVector[row] += keyMatrix[row][col] * plaintextVector[col];
                }
                ciphertextVector[row] = ciphertextVector[row] % ALPHABET_SIZE;
                ciphertextBuilder.append((char) (ciphertextVector[row] + FIRST_LETTER));
            }
        }

        return ciphertextBuilder.toString();
    }

    private String normalizePlaintext(String plaintext) {
        return plaintext.toUpperCase().replaceAll("[^A-Z]", "");
    }

    private void validateKeyMatrix(int[][] keyMatrix, int matrixSize) {
        int determinantModAlphabet = calculateDeterminant(keyMatrix, matrixSize) % ALPHABET_SIZE;
        if (determinantModAlphabet == 0) {
            throw new IllegalArgumentException("Invalid key matrix. Determinant is zero modulo " + ALPHABET_SIZE + ".");
        }
    }

    private int calculateDeterminant(int[][] matrix, int matrixSize) {
        if (matrixSize == 1) {
            return matrix[0][0];
        }

        int determinant = 0;
        int sign = 1;
        int[][] subMatrix = new int[matrixSize - 1][matrixSize - 1];

        for (int col = 0; col < matrixSize; col++) {
            int subRow = 0;
            for (int row = 1; row < matrixSize; row++) {
                int subCol = 0;
                for (int innerCol = 0; innerCol < matrixSize; innerCol++) {
                    if (innerCol != col) {
                        subMatrix[subRow][subCol++] = matrix[row][innerCol];
                    }
                }
                subRow++;
            }
            determinant += sign * matrix[0][col] * calculateDeterminant(subMatrix, matrixSize - 1);
            sign = -sign;
        }

        return determinant;
    }
}