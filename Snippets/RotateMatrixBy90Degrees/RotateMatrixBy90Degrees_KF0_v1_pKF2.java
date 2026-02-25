package com.thealgorithms.matrix;

import java.util.Scanner;

/**
 * Utility class for rotating square matrices by 90 degrees.
 */
final class RotateMatrixBy90Degrees {

    private RotateMatrixBy90Degrees() {
        // Prevent instantiation
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            int testCases = scanner.nextInt();

            while (testCases-- > 0) {
                int size = scanner.nextInt();
                int[][] matrix = new int[size][size];

                for (int row = 0; row < size; row++) {
                    for (int col = 0; col < size; col++) {
                        matrix[row][col] = scanner.nextInt();
                    }
                }

                Rotate.rotate(matrix);
                printMatrix(matrix);
            }
        }
    }

    private static void printMatrix(int[][] matrix) {
        int rows = matrix.length;
        int cols = matrix[0].length;

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                System.out.print(matrix[row][col] + " ");
            }
            System.out.println();
        }
    }
}

/**
 * Contains the algorithm to rotate a square matrix by 90 degrees clockwise.
 */
final class Rotate {

    private Rotate() {
        // Prevent instantiation
    }

    /**
     * Rotates the given square matrix by 90 degrees clockwise in-place.
     *
     * @param matrix the square matrix to rotate
     */
    static void rotate(int[][] matrix) {
        int n = matrix.length;

        // Transpose the matrix (swap matrix[i][j] with matrix[j][i] for i > j)
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < row; col++) {
                int temp = matrix[row][col];
                matrix[row][col] = matrix[col][row];
                matrix[col][row] = temp;
            }
        }

        // Reverse the rows (swap top row with bottom row, moving inward)
        int top = 0;
        int bottom = n - 1;
        while (top < bottom) {
            for (int col = 0; col < n; col++) {
                int temp = matrix[top][col];
                matrix[top][col] = matrix[bottom][col];
                matrix[bottom][col] = temp;
            }
            top++;
            bottom--;
        }
    }
}