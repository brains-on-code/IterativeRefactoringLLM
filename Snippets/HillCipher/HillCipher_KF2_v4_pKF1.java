package com.thealgorithms.ciphers;

public class HillCipher {

    private static final int ALPHABET_SIZE = 26;
    private static final char PADDING_CHAR = 'X';
    private static final char BASE_CHAR = 'A';

    public String encrypt(String plainText, int[][] keyMatrix) {
        String normalizedPlainText = normalizeText(plainText);
        int dimension = keyMatrix.length;
        validateDeterminant(keyMatrix, dimension);

        StringBuilder cipherTextBuilder = new StringBuilder();
        int[] plainVector = new int[dimension];
        int[] cipherVector = new int[dimension];
        int textPosition = 0;

        while (textPosition < normalizedPlainText.length()) {
            for (int row = 0; row < dimension; row++) {
                if (textPosition < normalizedPlainText.length()) {
                    plainVector[row] = normalizedPlainText.charAt(textPosition++) - BASE_CHAR;
                } else {
                    plainVector[row] = PADDING_CHAR - BASE_CHAR;
                }
            }

            for (int row = 0; row < dimension; row++) {
                cipherVector[row] = 0;
                for (int col = 0; col < dimension; col++) {
                    cipherVector[row] += keyMatrix[row][col] * plainVector[col];
                }
                cipherVector[row] = cipherVector[row] % ALPHABET_SIZE;
                cipherTextBuilder.append((char) (cipherVector[row] + BASE_CHAR));
            }
        }

        return cipherTextBuilder.toString();
    }

    public String decrypt(String cipherText, int[][] inverseKeyMatrix) {
        String normalizedCipherText = normalizeText(cipherText);
        int dimension = inverseKeyMatrix.length;
        validateDeterminant(inverseKeyMatrix, dimension);

        StringBuilder plainTextBuilder = new StringBuilder();
        int[] cipherVector = new int[dimension];
        int[] plainVector = new int[dimension];
        int textPosition = 0;

        while (textPosition < normalizedCipherText.length()) {
            for (int row = 0; row < dimension; row++) {
                if (textPosition < normalizedCipherText.length()) {
                    cipherVector[row] = normalizedCipherText.charAt(textPosition++) - BASE_CHAR;
                } else {
                    cipherVector[row] = PADDING_CHAR - BASE_CHAR;
                }
            }

            for (int row = 0; row < dimension; row++) {
                plainVector[row] = 0;
                for (int col = 0; col < dimension; col++) {
                    plainVector[row] += inverseKeyMatrix[row][col] * cipherVector[col];
                }
                plainVector[row] = plainVector[row] % ALPHABET_SIZE;
                plainTextBuilder.append((char) (plainVector[row] + BASE_CHAR));
            }
        }

        return plainTextBuilder.toString();
    }

    private String normalizeText(String text) {
        return text.toUpperCase().replaceAll("[^A-Z]", "");
    }

    private void validateDeterminant(int[][] matrix, int dimension) {
        int determinantModulo = calculateDeterminant(matrix, dimension) % ALPHABET_SIZE;
        if (determinantModulo == 0) {
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
        int[][] minor = new int[dimension - 1][dimension - 1];

        for (int col = 0; col < dimension; col++) {
            int minorRow = 0;
            for (int row = 1; row < dimension; row++) {
                int minorCol = 0;
                for (int innerCol = 0; innerCol < dimension; innerCol++) {
                    if (innerCol != col) {
                        minor[minorRow][minorCol++] = matrix[row][innerCol];
                    }
                }
                minorRow++;
            }
            determinant += sign * matrix[0][col] * calculateDeterminant(minor, dimension - 1);
            sign = -sign;
        }

        return determinant;
    }
}