package com.thealgorithms.ciphers;

public class Class1 {

    private static final int ALPHABET_SIZE = 26;
    private static final char PADDING_CHAR = 'X';
    private static final char FIRST_LETTER = 'A';

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
        return transformText(plaintext, keyMatrix);
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
        return transformText(text, matrix);
    }

    private String transformText(String text, int[][] matrix) {
        String normalizedText = normalizeText(text);
        int size = matrix.length;
        validateKeyMatrix(matrix, size);

        StringBuilder result = new StringBuilder();
        int[] block = new int[size];
        int[] resultBlock = new int[size];
        int index = 0;

        while (index < normalizedText.length()) {
            fillBlock(normalizedText, block, size, index);
            index += size;

            multiplyMatrixByBlock(matrix, block, resultBlock, size);
            appendBlockToResult(result, resultBlock, size);
        }

        return result.toString();
    }

    private String normalizeText(String text) {
        return text.toUpperCase().replaceAll("[^A-Z]", "");
    }

    private void fillBlock(String text, int[] block, int size, int startIndex) {
        for (int i = 0; i < size; i++) {
            int currentIndex = startIndex + i;
            block[i] = (currentIndex < text.length())
                ? text.charAt(currentIndex) - FIRST_LETTER
                : PADDING_CHAR - FIRST_LETTER;
        }
    }

    private void multiplyMatrixByBlock(int[][] matrix, int[] block, int[] resultBlock, int size) {
        for (int row = 0; row < size; row++) {
            int sum = 0;
            for (int col = 0; col < size; col++) {
                sum += matrix[row][col] * block[col];
            }
            resultBlock[row] = sum % ALPHABET_SIZE;
        }
    }

    private void appendBlockToResult(StringBuilder result, int[] resultBlock, int size) {
        for (int i = 0; i < size; i++) {
            result.append((char) (resultBlock[i] + FIRST_LETTER));
        }
    }

    /**
     * Validates that the key matrix has a non-zero determinant modulo 26.
     * A zero determinant (mod 26) would make the matrix non-invertible and thus invalid.
     *
     * @param matrix the key matrix
     * @param size   the dimension of the square matrix
     */
    private void validateKeyMatrix(int[][] matrix, int size) {
        int determinantMod26 = determinant(matrix, size) % ALPHABET_SIZE;
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
        if (size == 1) {
            return matrix[0][0];
        }

        int det = 0;
        int sign = 1;
        int[][] subMatrix = new int[size - 1][size - 1];

        for (int col = 0; col < size; col++) {
            buildSubMatrix(matrix, subMatrix, size, col);
            det += sign * matrix[0][col] * determinant(subMatrix, size - 1);
            sign = -sign;
        }
        return det;
    }

    private void buildSubMatrix(int[][] matrix, int[][] subMatrix, int size, int excludedCol) {
        int subRow = 0;
        for (int row = 1; row < size; row++) {
            int subCol = 0;
            for (int col = 0; col < size; col++) {
                if (col == excludedCol) {
                    continue;
                }
                subMatrix[subRow][subCol++] = matrix[row][col];
            }
            subRow++;
        }
    }
}