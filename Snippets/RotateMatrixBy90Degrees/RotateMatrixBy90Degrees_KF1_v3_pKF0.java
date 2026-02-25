package com.thealgorithms.matrix;

import java.util.Scanner;

/**
 * Reads matrices from standard input, rotates each by 90 degrees, and prints the result.
 */
final class MatrixRotationApp {

    private MatrixRotationApp() {
        // Prevent instantiation
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            int testCases = scanner.nextInt();

            for (int t = 0; t < testCases; t++) {
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
            printRow(row);
        }
    }

    private static void printRow(int[] row) {
        if (row.length == 0) {
            System.out.println();
            return;
        }

        StringBuilder builder = new StringBuilder();
        builder.append(row[0]);

        for (int i = 1; i < row.length; i++) {
            builder.append(' ').append(row[i]);
        }

        System.out.println(builder);
    }
}

/**
 * Utility class for matrix rotation operations.
 */
final class MatrixRotator {

    private MatrixRotator() {
        // Prevent instantiation
    }

    /**
     * Rotates a square matrix 90 degrees clockwise in-place.
     *
     * @param matrix the square matrix to rotate
     */
    static void rotate90Degrees(int[][] matrix) {
        int size = matrix.length;

        transpose(matrix, size);
        reverseRows(matrix, size);
    }

    private static void transpose(int[][] matrix, int size) {
        for (int row = 0; row < size; row++) {
            for (int col = row + 1; col < size; col++) {
                swap(matrix, row, col, col, row);
            }
        }
    }

    private static void reverseRows(int[][] matrix, int size) {
        for (int row = 0; row < size; row++) {
            reverseRow(matrix[row]);
        }
    }

    private static void reverseRow(int[] row) {
        int left = 0;
        int right = row.length - 1;

        while (left < right) {
            int temp = row[left];
            row[left] = row[right];
            row[right] = temp;
            left++;
            right--;
        }
    }

    private static void swap(int[][] matrix, int row1, int col1, int row2, int col2) {
        int temp = matrix[row1][col1];
        matrix[row1][col1] = matrix[row2][col2];
        matrix[row2][col2] = temp;
    }
}