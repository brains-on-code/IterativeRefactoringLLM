package com.thealgorithms.matrix;

import java.util.Scanner;

/**
 * Reads matrices from standard input, rotates each by 90 degrees, and prints the result.
 */
final class MatrixRotationApp {

    private MatrixRotationApp() {
        // Utility class
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

                MatrixRotator.rotate90Degrees(matrix);
                printMatrix(matrix);
            }
        }
    }

    private static void printMatrix(int[][] matrix) {
        for (int[] row : matrix) {
            for (int value : row) {
                System.out.print(value + " ");
            }
            System.out.println();
        }
    }
}

/**
 * Utility class for matrix rotation operations.
 */
final class MatrixRotator {

    private MatrixRotator() {
        // Utility class
    }

    /**
     * Rotates a square matrix 90 degrees clockwise in-place.
     *
     * @param matrix the square matrix to rotate
     */
    static void rotate90Degrees(int[][] matrix) {
        int n = matrix.length;

        // Transpose the matrix
        for (int row = 0; row < n; row++) {
            for (int col = row + 1; col < n; col++) {
                int temp = matrix[row][col];
                matrix[row][col] = matrix[col][row];
                matrix[col][row] = temp;
            }
        }

        // Reverse rows (swap top and bottom)
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