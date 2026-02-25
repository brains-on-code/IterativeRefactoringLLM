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

        for (int rowIndex = 0; rowIndex < size; rowIndex++) {
            for (int colIndex = 0; colIndex < size; colIndex++) {
                matrix[rowIndex][colIndex] = scanner.nextInt();
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

        for (int index = 1; index < row.length; index++) {
            builder.append(' ').append(row[index]);
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
        for (int rowIndex = 0; rowIndex < size; rowIndex++) {
            for (int colIndex = rowIndex + 1; colIndex < size; colIndex++) {
                swap(matrix, rowIndex, colIndex, colIndex, rowIndex);
            }
        }
    }

    private static void reverseRows(int[][] matrix, int size) {
        for (int rowIndex = 0; rowIndex < size; rowIndex++) {
            reverseRow(matrix[rowIndex]);
        }
    }

    private static void reverseRow(int[] row) {
        int leftIndex = 0;
        int rightIndex = row.length - 1;

        while (leftIndex < rightIndex) {
            swap(row, leftIndex, rightIndex);
            leftIndex++;
            rightIndex--;
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