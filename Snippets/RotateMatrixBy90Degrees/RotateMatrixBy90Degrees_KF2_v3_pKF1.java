package com.thealgorithms.matrix;

import java.util.Scanner;

final class RotateMatrixBy90Degrees {

    private RotateMatrixBy90Degrees() {}

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int testCaseCount = scanner.nextInt();

        while (testCaseCount-- > 0) {
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
        scanner.close();
    }

    static void printMatrix(int[][] matrix) {
        int rowCount = matrix.length;
        int columnCount = matrix[0].length;

        for (int row = 0; row < rowCount; row++) {
            for (int col = 0; col < columnCount; col++) {
                System.out.print(matrix[row][col] + " ");
            }
            System.out.println();
        }
    }
}

final class MatrixRotator {

    private MatrixRotator() {}

    static void rotate90Degrees(int[][] matrix) {
        int size = matrix.length;

        // Transpose the matrix
        for (int row = 0; row < size; row++) {
            for (int col = row + 1; col < size; col++) {
                int temp = matrix[row][col];
                matrix[row][col] = matrix[col][row];
                matrix[col][row] = temp;
            }
        }

        // Reverse rows (top to bottom)
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