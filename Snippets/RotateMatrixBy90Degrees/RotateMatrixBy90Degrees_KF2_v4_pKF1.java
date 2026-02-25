package com.thealgorithms.matrix;

import java.util.Scanner;

final class RotateMatrixBy90Degrees {

    private RotateMatrixBy90Degrees() {}

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int numberOfTestCases = scanner.nextInt();

        while (numberOfTestCases-- > 0) {
            int matrixSize = scanner.nextInt();
            int[][] matrix = new int[matrixSize][matrixSize];

            for (int rowIndex = 0; rowIndex < matrixSize; rowIndex++) {
                for (int columnIndex = 0; columnIndex < matrixSize; columnIndex++) {
                    matrix[rowIndex][columnIndex] = scanner.nextInt();
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

        for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
            for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
                System.out.print(matrix[rowIndex][columnIndex] + " ");
            }
            System.out.println();
        }
    }
}

final class MatrixRotator {

    private MatrixRotator() {}

    static void rotate90Degrees(int[][] matrix) {
        int matrixSize = matrix.length;

        // Transpose the matrix
        for (int rowIndex = 0; rowIndex < matrixSize; rowIndex++) {
            for (int columnIndex = rowIndex + 1; columnIndex < matrixSize; columnIndex++) {
                int temporaryValue = matrix[rowIndex][columnIndex];
                matrix[rowIndex][columnIndex] = matrix[columnIndex][rowIndex];
                matrix[columnIndex][rowIndex] = temporaryValue;
            }
        }

        // Reverse rows (top to bottom)
        int topRowIndex = 0;
        int bottomRowIndex = matrixSize - 1;
        while (topRowIndex < bottomRowIndex) {
            for (int columnIndex = 0; columnIndex < matrixSize; columnIndex++) {
                int temporaryValue = matrix[topRowIndex][columnIndex];
                matrix[topRowIndex][columnIndex] = matrix[bottomRowIndex][columnIndex];
                matrix[bottomRowIndex][columnIndex] = temporaryValue;
            }
            topRowIndex++;
            bottomRowIndex--;
        }
    }
}