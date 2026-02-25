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
        int rows = matrix.length;
        int cols = rows == 0 ? 0 : matrix[0].length;

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                System.out.print(matrix[row][col] + " ");
            }
            System.out.println();
        }
    }
}

/**
 * Class containing the algorithm to rotate a matrix by 90 degrees.
 */
final class Rotate {

    private Rotate() {
        // Utility class
    }

    static void rotate(int[][] matrix) {
        int n = matrix.length;
        transposeInPlace(matrix, n);
        reverseRows(matrix, n);
    }

    private static void transposeInPlace(int[][] matrix, int n) {
        for (int row = 0; row < n; row++) {
            for (int col = row + 1; col < n; col++) {
                int temp = matrix[row][col];
                matrix[row][col] = matrix[col][row];
                matrix[col][row] = temp;
            }
        }
    }

    private static void reverseRows(int[][] matrix, int n) {
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