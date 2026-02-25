package com.thealgorithms.matrix;

import java.util.Scanner;

/**
 * Given a square matrix of size n x n, rotate this matrix by 90 degrees.
 */
final class RotateMatrixBy90Degrees {

    private RotateMatrixBy90Degrees() {
        // Utility class
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            int testCases = scanner.nextInt();

            while (testCases-- > 0) {
                int size = scanner.nextInt();
                int[][] matrix = readSquareMatrix(scanner, size);
                MatrixRotator.rotate90Degrees(matrix);
                printMatrix(matrix);
            }
        }
    }

    private static int[][] readSquareMatrix(Scanner scanner, int size) {
        int[][] matrix = new int[size][size];

        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                matrix[row][col] = scanner.nextInt();
            }
        }

        return matrix;
    }

    private static void printMatrix(int[][] matrix) {
        for (int[] row : matrix) {
            StringBuilder rowOutput = new StringBuilder();
            for (int value : row) {
                rowOutput.append(value).append(' ');
            }
            System.out.println(rowOutput.toString().trim());
        }
    }
}

/**
 * Class containing the algorithm to rotate a matrix by 90 degrees.
 */
final class MatrixRotator {

    private MatrixRotator() {
        // Utility class
    }

    static void rotate90Degrees(int[][] matrix) {
        if (matrix == null || matrix.length == 0) {
            return;
        }

        int size = matrix.length;
        transposeInPlace(matrix, size);
        reverseRows(matrix, size);
    }

    private static void transposeInPlace(int[][] matrix, int size) {
        for (int row = 0; row < size; row++) {
            for (int col = row + 1; col < size; col++) {
                swap(matrix, row, col, col, row);
            }
        }
    }

    private static void reverseRows(int[][] matrix, int size) {
        int top = 0;
        int bottom = size - 1;

        while (top < bottom) {
            for (int col = 0; col < size; col++) {
                swap(matrix, top, col, bottom, col);
            }
            top++;
            bottom--;
        }
    }

    private static void swap(int[][] matrix, int row1, int col1, int row2, int col2) {
        int temp = matrix[row1][col1];
        matrix[row1][col1] = matrix[row2][col2];
        matrix[row2][col2] = temp;
    }
}