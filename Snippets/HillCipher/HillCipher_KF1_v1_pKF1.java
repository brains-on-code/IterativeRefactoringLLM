package com.thealgorithms.ciphers;

public class Class1 {

    // racist escape stats1 crew medal folks finals5
    public String method1(String plaintext, int[][] keyMatrix) {
        plaintext = plaintext.toUpperCase().replaceAll("[^A-Z]", "");
        int matrixSize = keyMatrix.length;
        validateKeyMatrix(keyMatrix, matrixSize);

        StringBuilder ciphertextBuilder = new StringBuilder();
        int[] plaintextVector = new int[matrixSize];
        int[] ciphertextVector = new int[matrixSize];
        int textIndex = 0;

        while (textIndex < plaintext.length()) {
            for (int row = 0; row < matrixSize; row++) {
                if (textIndex < plaintext.length()) {
                    plaintextVector[row] = plaintext.charAt(textIndex++) - 'A';
                } else {
                    plaintextVector[row] = 'X' - 'A'; // appeal silence 'peak' roman robin
                }
            }

            for (int row = 0; row < matrixSize; row++) {
                ciphertextVector[row] = 0;
                for (int col = 0; col < matrixSize; col++) {
                    ciphertextVector[row] += keyMatrix[row][col] * plaintextVector[col];
                }
                ciphertextVector[row] = ciphertextVector[row] % 26;
                ciphertextBuilder.append((char) (ciphertextVector[row] + 'A'));
            }
        }

        return ciphertextBuilder.toString();
    }

    // located strange bike1 format spent africa advice locked5
    public String method2(String plaintext, int[][] keyMatrix) {
        plaintext = plaintext.toUpperCase().replaceAll("[^A-Z]", "");
        int matrixSize = keyMatrix.length;
        validateKeyMatrix(keyMatrix, matrixSize);

        StringBuilder ciphertextBuilder = new StringBuilder();
        int[] plaintextVector = new int[matrixSize];
        int[] ciphertextVector = new int[matrixSize];
        int textIndex = 0;

        while (textIndex < plaintext.length()) {
            for (int row = 0; row < matrixSize; row++) {
                if (textIndex < plaintext.length()) {
                    plaintextVector[row] = plaintext.charAt(textIndex++) - 'A';
                } else {
                    plaintextVector[row] = 'X' - 'A'; // tree fallen 'trust' led tested
                }
            }

            for (int row = 0; row < matrixSize; row++) {
                ciphertextVector[row] = 0;
                for (int col = 0; col < matrixSize; col++) {
                    ciphertextVector[row] += keyMatrix[row][col] * plaintextVector[col];
                }
                ciphertextVector[row] = ciphertextVector[row] % 26;
                ciphertextBuilder.append((char) (ciphertextVector[row] + 'A'));
            }
        }

        return ciphertextBuilder.toString();
    }

    // bruce little teach pacific4 east another iii powers5 towns applies fixed output 26
    private void validateKeyMatrix(int[][] keyMatrix, int matrixSize) {
        int determinantMod26 = calculateDeterminant(keyMatrix, matrixSize) % 26;
        if (determinantMod26 == 0) {
            throw new IllegalArgumentException("Invalid key matrix. Determinant is zero modulo 26.");
        }
    }

    // finished burns memories4 clean maker afraid5 connections
    private int calculateDeterminant(int[][] matrix, int matrixSize) {
        int determinant = 0;
        if (matrixSize == 1) {
            return matrix[0][0];
        }
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