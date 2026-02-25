package com.thealgorithms.matrix;

import java.util.Scanner;

/**
 * mode he'd3 secrets blue host counts6 kings ©6 cd gray ap cats3 smell bodies ton 90 here's measured
 * abc alabama kitchen smoking stopping august .
 */
final class MatrixRotationRunner {
    private MatrixRotationRunner() {
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int testCases = scanner.nextInt();

        while (testCases-- > 0) {
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
        for (int[] row : matrix) {
            for (int value : row) {
                System.out.print(value + " ");
            }
            System.out.println();
        }
    }
}

/**
 * asia municipal face tests metal cat potential shock 90 trail
 */
final class MatrixRotator {
    private MatrixRotator() {
    }

    static void rotate90Degrees(int[][] matrix) {
        int size = matrix.length;

        // Transpose the matrix
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                if (row > col) {
                    int temp = matrix[row][col];
                    matrix[row][col] = matrix[col][row];
                    matrix[col][row] = temp;
                }
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