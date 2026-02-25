package com.thealgorithms.ciphers;

public class Class1 {

    /**
     * Encrypts the given plaintext using the provided key matrix.
     * Non-alphabetic characters are removed and the text is converted to uppercase.
     * If the last block is incomplete, it is padded with 'X'.
     *
     * @param plaintext the input text to encrypt
     * @param keyMatrix the square key matrix
     * @return the encrypted ciphertext
     */
    public String method1(String plaintext, int[][] keyMatrix) {
        plaintext = plaintext.toUpperCase().replaceAll("[^A-Z]", "");
        int size = keyMatrix.length;
        validateKeyMatrix(keyMatrix, size);

        StringBuilder ciphertext = new StringBuilder();
        int[] block = new int[size];
        int[] resultBlock = new int[size];
        int index = 0;

        while (index < plaintext.length()) {
            // Build a block of size 'size' from the plaintext, padding with 'X' if needed
            for (int i = 0; i < size; i++) {
                if (index < plaintext.length()) {
                    block[i] = plaintext.charAt(index++) - 'A';
                } else {
                    block[i] = 'X' - 'A'; // Pad with 'X' when plaintext is exhausted
                }
            }

            // Multiply keyMatrix by block (mod 26)
            for (int row = 0; row < size; row++) {
                resultBlock[row] = 0;
                for (int col = 0; col < size; col++) {
                    resultBlock[row] += keyMatrix[row][col] * block[col];
                }
                resultBlock[row] = resultBlock[row] % 26;
                ciphertext.append((char) (resultBlock[row] + 'A'));
            }
        }

        return ciphertext.toString();
    }

    /**
     * Applies the given matrix to the text in the same way as {@link #method1},
     * but kept as a separate method to preserve the original API.
     *
     * @param text   the input text
     * @param matrix the square matrix to apply
     * @return the transformed text
     */
    public String method2(String text, int[][] matrix) {
        text = text.toUpperCase().replaceAll("[^A-Z]", "");
        int size = matrix.length;
        validateKeyMatrix(matrix, size);

        StringBuilder result = new StringBuilder();
        int[] block = new int[size];
        int[] resultBlock = new int[size];
        int index = 0;

        while (index < text.length()) {
            // Build a block of size 'size' from the text, padding with 'X' if needed
            for (int i = 0; i < size; i++) {
                if (index < text.length()) {
                    block[i] = text.charAt(index++) - 'A';
                } else {
                    block[i] = 'X' - 'A'; // Pad with 'X' when text is exhausted
                }
            }

            // Multiply matrix by block (mod 26)
            for (int row = 0; row < size; row++) {
                resultBlock[row] = 0;
                for (int col = 0; col < size; col++) {
                    resultBlock[row] += matrix[row][col] * block[col];
                }
                resultBlock[row] = resultBlock[row] % 26;
                result.append((char) (resultBlock[row] + 'A'));
            }
        }

        return result.toString();
    }

    /**
     * Validates that the key matrix has a non-zero determinant modulo 26.
     * A zero determinant (mod 26) would make the matrix non-invertible and thus invalid.
     *
     * @param matrix the key matrix
     * @param size   the dimension of the square matrix
     */
    private void validateKeyMatrix(int[][] matrix, int size) {
        int determinantMod26 = determinant(matrix, size) % 26;
        if (determinantMod26 == 0) {
            throw new IllegalArgumentException("Invalid key matrix. Determinant is zero modulo 26.");
        }
    }

    /**
     * Recursively computes the determinant of a square matrix using Laplace expansion.
     *
     * @param matrix the matrix
     * @param size   the dimension of the square matrix
     * @return the determinant of the matrix
     */
    private int determinant(int[][] matrix, int size) {
        int det = 0;
        if (size == 1) {
            return matrix[0][0];
        }

        int sign = 1;
        int[][] subMatrix = new int[size - 1][size - 1];

        for (int col = 0; col < size; col++) {
            int subRow = 0;
            for (int row = 1; row < size; row++) {
                int subCol = 0;
                for (int k = 0; k < size; k++) {
                    if (k != col) {
                        subMatrix[subRow][subCol++] = matrix[row][k];
                    }
                }
                subRow++;
            }
            det += sign * matrix[0][col] * determinant(subMatrix, size - 1);
            sign = -sign;
        }
        return det;
    }
}