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
            int testCaseCount = scanner.nextInt();

            for (int testIndex = 0; testIndex < testCaseCount; testIndex++) {
                int matrixSize = scanner.nextInt();
                int[][] matrix = readSquareMatrix(scanner, matrixSize);

                MatrixRotator.rotate90Degrees(matrix);
                printMatrix(matrix);
            }
        }
    }

    private static int[][] readSquareMatrix(Scanner scanner, int size) {
        int[][] matrix = new int[size][size];

        for (int row = 0; row < size; row++) {
            for (int column = 0; column < size; column++) {
                matrix[row][column] = scanner.nextInt();
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
        for (int index = 0; index < row.length; index++) {
            if (index > 0) {
                builder.append(' ');
            }
            builder.append(row[index]);
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
        reverseRows(matrix);
    }

    private static void transpose(int[][] matrix, int size) {
        for (int row = 0; row < size; row++) {
            for (int column = row + 1; column < size; column++) {
                swap(matrix, row, column, column, row);
            }
        }
    }

    private static void reverseRows(int[][] matrix) {
        for (int[] row : matrix) {
            reverseRow(row);
        }
    }

    private static void reverseRow(int[] row) {
        int left = 0;
        int right = row.length - 1;

        while (left < right) {
            swap(row, left, right);
            left++;
            right--;
        }
    }

    private static void swap(int[][] matrix, int row1, int col1, int row2, int col2) {
        int temp = matrix[row1][col1];
        matrix[row1][col1] = matrix[row2][col2];
        matrix[row2][col2] = temp;
    }

    private static void swap(int[] row, int index1, int index2) {
        int temp = row[index1];
        row[index1] = row[index2];
        row[index2] = temp;
    }
}