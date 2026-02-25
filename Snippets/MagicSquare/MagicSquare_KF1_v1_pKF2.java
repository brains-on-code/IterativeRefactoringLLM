package com.thealgorithms.maths;

import java.util.Scanner;

/**
 * Generates and prints an odd-order magic square using the Siamese method.
 */
public final class Class1 {

    private Class1() {
        // Utility class; prevent instantiation.
    }

    public static void method1(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Input an odd positive number: ");
            int n = scanner.nextInt();

            if (n <= 0 || n % 2 == 0) {
                System.out.print("Input number must be odd and > 0");
                return;
            }

            int[][] magicSquare = new int[n][n];

            // Start position: middle row, last column
            int row = n / 2;
            int col = n - 1;
            magicSquare[row][col] = 1;

            // Fill the magic square using the Siamese method
            for (int num = 2; num <= n * n; num++) {
                int nextRow = (row - 1 + n) % n;
                int nextCol = (col + 1) % n;

                if (magicSquare[nextRow][nextCol] == 0) {
                    row = nextRow;
                    col = nextCol;
                } else {
                    col = (col - 1 + n) % n;
                }

                magicSquare[row][col] = num;
            }

            // Print the magic square with basic alignment
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (magicSquare[i][j] < 10) {
                        System.out.print(" ");
                    }
                    if (magicSquare[i][j] < 100) {
                        System.out.print(" ");
                    }
                    System.out.print(magicSquare[i][j] + " ");
                }
                System.out.println();
            }
        }
    }
}