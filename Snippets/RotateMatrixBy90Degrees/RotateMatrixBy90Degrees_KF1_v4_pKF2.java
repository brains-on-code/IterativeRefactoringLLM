package com.thealgorithms.matrix;

import java.util.Scanner;

/**
 * Reads square matrices from standard input, rotates each 90 degrees clockwise,
 * and prints the result.
 *
 * <p>Input format:
 * <pre>
 * t
 * n
 * a11 a12 ... a1n
 * ...
 * an1 an2 ... ann
 * (repeated t times)
 * </pre>
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
                int[][] matrix = readSquareMatrix(scanner, size);
                MatrixRotator.rotate90Clockwise(matrix);
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

        transposeInPlace(matrix);
        reverseRowOrder(matrix);
    }

    /**
     * Transposes a square matrix in place.
     */
    private static void transposeInPlace(int[][] matrix) {
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
    private static void reverseRowOrder(int[][] matrix) {
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