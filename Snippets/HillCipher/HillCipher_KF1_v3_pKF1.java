package com.thealgorithms.ciphers;

public class HillCipher {

    private static final int ALPHABET_SIZE = 26;
    private static final char PADDING_CHARACTER = 'X';
    private static final char FIRST_ALPHABET_LETTER = 'A';

    public String encrypt(String plaintext, int[][] keyMatrix) {
        String sanitizedPlaintext = sanitizePlaintext(plaintext);
        int matrixDimension = keyMatrix.length;
        validateKeyMatrix(keyMatrix, matrixDimension);

        StringBuilder ciphertext = new StringBuilder();
        int[] plaintextBlock = new int[matrixDimension];
        int[] ciphertextBlock = new int[matrixDimension];
        int plaintextIndex = 0;

        while (plaintextIndex < sanitizedPlaintext.length()) {
            for (int row = 0; row < matrixDimension; row++) {
                if (plaintextIndex < sanitizedPlaintext.length()) {
                    plaintextBlock[row] = sanitizedPlaintext.charAt(plaintextIndex++) - FIRST_ALPHABET_LETTER;
                } else {
                    plaintextBlock[row] = PADDING_CHARACTER - FIRST_ALPHABET_LETTER;
                }
            }

            for (int row = 0; row < matrixDimension; row++) {
                ciphertextBlock[row] = 0;
                for (int col = 0; col < matrixDimension; col++) {
                    ciphertextBlock[row] += keyMatrix[row][col] * plaintextBlock[col];
                }
                ciphertextBlock[row] = ciphertextBlock[row] % ALPHABET_SIZE;
                ciphertext.append((char) (ciphertextBlock[row] + FIRST_ALPHABET_LETTER));
            }
        }

        return ciphertext.toString();
    }

    public String encryptAlternative(String plaintext, int[][] keyMatrix) {
        String sanitizedPlaintext = sanitizePlaintext(plaintext);
        int matrixDimension = keyMatrix.length;
        validateKeyMatrix(keyMatrix, matrixDimension);

        StringBuilder ciphertext = new StringBuilder();
        int[] plaintextBlock = new int[matrixDimension];
        int[] ciphertextBlock = new int[matrixDimension];
        int plaintextIndex = 0;

        while (plaintextIndex < sanitizedPlaintext.length()) {
            for (int row = 0; row < matrixDimension; row++) {
                if (plaintextIndex < sanitizedPlaintext.length()) {
                    plaintextBlock[row] = sanitizedPlaintext.charAt(plaintextIndex++) - FIRST_ALPHABET_LETTER;
                } else {
                    plaintextBlock[row] = PADDING_CHARACTER - FIRST_ALPHABET_LETTER;
                }
            }

            for (int row = 0; row < matrixDimension; row++) {
                ciphertextBlock[row] = 0;
                for (int col = 0; col < matrixDimension; col++) {
                    ciphertextBlock[row] += keyMatrix[row][col] * plaintextBlock[col];
                }
                ciphertextBlock[row] = ciphertextBlock[row] % ALPHABET_SIZE;
                ciphertext.append((char) (ciphertextBlock[row] + FIRST_ALPHABET_LETTER));
            }
        }

        return ciphertext.toString();
    }

    private String sanitizePlaintext(String plaintext) {
        return plaintext.toUpperCase().replaceAll("[^A-Z]", "");
    }

    private void validateKeyMatrix(int[][] keyMatrix, int matrixDimension) {
        int determinantModuloAlphabet = calculateDeterminant(keyMatrix, matrixDimension) % ALPHABET_SIZE;
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

        for (int col = 0; col < matrixDimension; col++) {
            int minorRow = 0;
            for (int row = 1; row < matrixDimension; row++) {
                int minorCol = 0;
                for (int innerCol = 0; innerCol < matrixDimension; innerCol++) {
                    if (innerCol != col) {
                        minorMatrix[minorRow][minorCol++] = matrix[row][innerCol];
                    }
                }
                minorRow++;
            }
            determinant += sign * matrix[0][col] * calculateDeterminant(minorMatrix, matrixDimension - 1);
            sign = -sign;
        }

        return determinant;
    }
}