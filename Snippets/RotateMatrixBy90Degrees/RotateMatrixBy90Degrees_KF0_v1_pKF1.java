package com.thealgorithms.matrix;

import java.util.Scanner;

/**
 * Given a matrix of size n x n, rotate this matrix by 90 degrees.
 */
final class RotateMatrixBy90Degrees {
    private RotateMatrixBy90Degrees() {
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int testCasesCount = scanner.nextInt();

        while (testCasesCount-- > 0) {
            int matrixSize = scanner.nextInt();
            int[][] matrix = new int[matrixSize][matrixSize];

            for (int row = 0; row < matrixSize; row++) {
                for (int column = 0; column < matrixSize; column++) {
                    matrix[row][column] = scanner.nextInt();
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
            for (int column = 0; column < columnCount; column++) {
                System.out.print(matrix[row][column] + " ");
            }
            System.out.println();
        }
    }
}

/**
 * Class containing the algorithm to rotate a matrix by 90 degrees.
 */
final class MatrixRotator {
    private MatrixRotator() {
    }

    static void rotate90Degrees(int[][] matrix) {
        int size = matrix.length;

        // Transpose the matrix
        for (int row = 0; row < size; row++) {
            for (int column = 0; column < size; column++) {
                if (row > column) {
                    int temp = matrix[row][column];
                    matrix[row][column] = matrix[column][row];
                    matrix[column][row] = temp;
                }
            }
        }

        // Reverse the rows
        int topRow = 0;
        int bottomRow = size - 1;
        while (topRow < bottomRow) {
            for (int column = 0; column < size; column++) {
                int temp = matrix[topRow][column];
                matrix[topRow][column] = matrix[bottomRow][column];
                matrix[bottomRow][column] = temp;
            }
            topRow++;
            bottomRow--;
        }
    }
}