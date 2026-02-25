package com.thealgorithms.matrix;

import java.util.Scanner;

/**
 * Reads square matrices from standard input, rotates each 90 degrees clockwise,
 * and prints the result.
 *
 * <p>Input format:
 * <ul>
 *   <li>First line: integer t, the number of test cases</li>
 *   <li>For each test case:
 *     <ul>
 *       <li>Integer n, the size of the n x n matrix</li>
 *       <li>n^2 integers representing the matrix elements row by row</li>
 *     </ul>
 *   </li>
 * </ul>
 */
final class MatrixRotationRunner {

    private MatrixRotationRunner() {
        // Utility class; prevent instantiation.
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            int testCases = scanner.nextInt();

            while (testCases-- > 0) {
                int size = scanner.nextInt();
                int[][] matrix = readMatrix(scanner, size);
                MatrixRotator.rotate90Clockwise(matrix);
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
 * Matrix rotation utilities.
 */
final class MatrixRotator {

    private MatrixRotator() {
        // Utility class; prevent instantiation.
    }

    /**
     * Rotates a square matrix 90 degrees clockwise in place.
     *
     * @param matrix the square matrix to rotate
     * @throws IllegalArgumentException if the matrix is empty or not square
     */
    static void rotate90Clockwise(int[][] matrix) {
        int n = matrix.length;
        if (n == 0 || matrix[0].length != n) {
            throw new IllegalArgumentException("Matrix must be non-empty and square.");
        }

        transpose(matrix);
        reverseRows(matrix);
    }

    /**
     * In-place transpose of a square matrix.
     */
    private static void transpose(int[][] matrix) {
        int n = matrix.length;
        for (int row = 0; row < n; row++) {
            for (int col = row + 1; col < n; col++) {
                int temp = matrix[row][col];
                matrix[row][col] = matrix[col][row];
                matrix[col][row] = temp;
            }
        }
    }

    /**
     * Reverses the order of the rows of the matrix in place.
     */
    private static void reverseRows(int[][] matrix) {
        int top = 0;
        int bottom = matrix.length - 1;

        while (top < bottom) {
            int[] temp = matrix[top];
            matrix[top] = matrix[bottom];
            matrix[bottom] = temp;
            top++;
            bottom--;
        }
    }
}