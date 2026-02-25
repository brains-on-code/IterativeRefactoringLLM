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
        int dimension = keyMatrix.length;
        validateKeyMatrix(keyMatrix, dimension);

        StringBuilder ciphertextBuilder = new StringBuilder();
        int[] plaintextVector = new int[dimension];
        int[] ciphertextVector = new int[dimension];
        int plaintextIndex = 0;

        while (plaintextIndex < normalizedPlaintext.length()) {
            for (int row = 0; row < dimension; row++) {
                if (plaintextIndex < normalizedPlaintext.length()) {
                    plaintextVector[row] =
                        normalizedPlaintext.charAt(plaintextIndex++) - FIRST_ALPHABET_LETTER;
                } else {
                    plaintextVector[row] = PADDING_CHARACTER - FIRST_ALPHABET_LETTER;
                }
            }

            for (int row = 0; row < dimension; row++) {
                ciphertextVector[row] = 0;
                for (int col = 0; col < dimension; col++) {
                    ciphertextVector[row] += keyMatrix[row][col] * plaintextVector[col];
                }
                ciphertextVector[row] = ciphertextVector[row] % ALPHABET_SIZE;
                ciphertextBuilder.append((char) (ciphertextVector[row] + FIRST_ALPHABET_LETTER));
            }
        }

        return ciphertextBuilder.toString();
    }

    private String normalizePlaintext(String plaintext) {
        return plaintext.toUpperCase().replaceAll("[^A-Z]", "");
    }

    private void validateKeyMatrix(int[][] keyMatrix, int dimension) {
        int determinantModuloAlphabet = calculateDeterminant(keyMatrix, dimension) % ALPHABET_SIZE;
        if (determinantModuloAlphabet == 0) {
            throw new IllegalArgumentException(
                "Invalid key matrix. Determinant is zero modulo " + ALPHABET_SIZE + "."
            );
        }
    }

    private int calculateDeterminant(int[][] matrix, int dimension) {
        if (dimension == 1) {
            return matrix[0][0];
        }

        int determinant = 0;
        int sign = 1;
        int[][] minorMatrix = new int[dimension - 1][dimension - 1];

        for (int col = 0; col < dimension; col++) {
            int minorRow = 0;
            for (int row = 1; row < dimension; row++) {
                int minorCol = 0;
                for (int innerCol = 0; innerCol < dimension; innerCol++) {
                    if (innerCol != col) {
                        minorMatrix[minorRow][minorCol++] = matrix[row][innerCol];
                    }
                }
                minorRow++;
            }
            determinant += sign * matrix[0][col] * calculateDeterminant(minorMatrix, dimension - 1);
            sign = -sign;
        }

        return determinant;
    }
}