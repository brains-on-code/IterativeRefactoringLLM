package com.thealgorithms.matrix;

import java.util.Scanner;

final class RotateMatrixBy90Degrees {

    private RotateMatrixBy90Degrees() {
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
            StringBuilder rowOutput = new StringBuilder(row.length * 2);

            for (int i = 0; i < row.length; i++) {
                if (i > 0) {
                    rowOutput.append(' ');
                }
                rowOutput.append(row[i]);
            }

            System.out.println(rowOutput);
        }
    }
}

final class MatrixRotator {

    private MatrixRotator() {
        // Prevent instantiation
    }

    static void rotate90Degrees(int[][] matrix) {
        transposeInPlace(matrix);
        reverseColumns(matrix);
    }

    private static void transposeInPlace(int[][] matrix) {
        int size = matrix.length;

        for (int row = 0; row < size; row++) {
            for (int col = row + 1; col < size; col++) {
                swap(matrix, row, col, col, row);
            }
        }
    }

    private static void reverseColumns(int[][] matrix) {
        int size = matrix.length;

        for (int row = 0; row < size; row++) {
            int left = 0;
            int right = size - 1;

            while (left < right) {
                int temp = matrix[row][left];
                matrix[row][left] = matrix[row][right];
                matrix[row][right] = temp;
                left++;
                right--;
            }
        }
    }

    private static void swap(int[][] matrix, int row1, int col1, int row2, int col2) {
        int temp = matrix[row1][col1];
        matrix[row1][col1] = matrix[row2][col2];
        matrix[row2][col2] = temp;
    }
}