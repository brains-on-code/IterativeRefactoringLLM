package com.thealgorithms.matrix;

import java.util.Scanner;

/**
 * Utility class for rotating square matrices by 90 degrees clockwise.
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
                int[][] matrix = readMatrix(scanner, size);
                Rotate.rotate(matrix);
                printMatrix(matrix);
            }
        }
    }

    private static int[][] readMatrix(Scanner scanner, int size) {
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
            for (int value : row) {
                System.out.print(value + " ");
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
        int size = matrix.length;
        transpose(matrix, size);
        reverseRows(matrix, size);
    }

    /**
     * Transposes the matrix in-place.
     * After this operation, matrix[row][col] becomes matrix[col][row].
     */
    private static void transpose(int[][] matrix, int size) {
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < row; col++) {
                int temp = matrix[row][col];
                matrix[row][col] = matrix[col][row];
                matrix[col][row] = temp;
            }
        }
    }

    /**
     * Reverses the order of rows in the matrix in-place.
     * The first row is swapped with the last, the second with the second-last, and so on.
     */
    private static void reverseRows(int[][] matrix, int size) {
        int top = 0;
        int bottom = size - 1;

        while (top < bottom) {
            for (int col = 0; col < size; col++) {
                int temp = matrix[top][col];
                matrix[top][col] = matrix[bottom][col];
                matrix[bottom][col] = temp;
            }
            top++;
            bottom--;
        }
    }
}