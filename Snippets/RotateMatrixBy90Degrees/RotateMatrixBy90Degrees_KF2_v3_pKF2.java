package com.thealgorithms.matrix;

import java.util.Scanner;

/**
 * Reads square matrices from standard input, rotates each by 90 degrees
 * clockwise, and prints the result.
 */
final class RotateMatrixBy90Degrees {

    private RotateMatrixBy90Degrees() {
        // Utility class; prevent instantiation.
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            int testCases = scanner.nextInt();

            while (testCases-- > 0) {
                int size = scanner.nextInt();
                int[][] matrix = readSquareMatrix(scanner, size);

                MatrixRotator.rotate90DegreesClockwise(matrix);

                printMatrix(matrix);
            }
        }
    }

    /**
     * Reads a square matrix of the given size from the provided scanner.
     *
     * @param scanner the input source
     * @param size    the dimension of the square matrix
     * @return a size x size matrix filled with input values
     */
    private static int[][] readSquareMatrix(Scanner scanner, int size) {
        int[][] matrix = new int[size][size];

        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                matrix[row][col] = scanner.nextInt();
            }
        }

        return matrix;
    }

    /**
     * Prints the given matrix to standard output, one row per line.
     *
     * @param matrix the matrix to print
     */
    private static void printMatrix(int[][] matrix) {
        for (int[] row : matrix) {
            StringBuilder line = new StringBuilder();

            for (int value : row) {
                line.append(value).append(' ');
            }

            System.out.println(line.toString().trim());
        }
    }
}

/**
 * Utility class for in-place matrix rotation operations.
 */
final class MatrixRotator {

    private MatrixRotator() {
        // Utility class; prevent instantiation.
    }

    /**
     * Rotates the given square matrix 90 degrees clockwise in place.
     *
     * @param matrix the matrix to rotate; must be square
     */
    static void rotate90DegreesClockwise(int[][] matrix) {
        transposeInPlace(matrix);
        reverseRows(matrix);
    }

    /**
     * Transposes the given square matrix in place.
     *
     * @param matrix the matrix to transpose
     */
    private static void transposeInPlace(int[][] matrix) {
        int size = matrix.length;

        for (int row = 0; row < size; row++) {
            for (int col = row + 1; col < size; col++) {
                int temp = matrix[row][col];
                matrix[row][col] = matrix[col][row];
                matrix[col][row] = temp;
            }
        }
    }

    /**
     * Reverses the order of the rows in the given matrix in place.
     *
     * @param matrix the matrix whose rows will be reversed
     */
    private static void reverseRows(int[][] matrix) {
        int top = 0;
        int bottom = matrix.length - 1;

        while (top < bottom) {
            int[] tempRow = matrix[top];
            matrix[top] = matrix[bottom];
            matrix[bottom] = tempRow;

            top++;
            bottom--;
        }
    }
}