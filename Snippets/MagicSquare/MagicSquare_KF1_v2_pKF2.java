package com.thealgorithms.maths;

import java.util.Scanner;

/**
 * Generates and prints an odd-order magic square using the Siamese method.
 */
public final class Class1 {

    private Class1() {
        // Prevent instantiation
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

            int row = n / 2;
            int col = n - 1;
            magicSquare[row][col] = 1;

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

            int maxNumber = n * n;
            int width = String.valueOf(maxNumber).length() + 1;

            for (int[] magicSquareRow : magicSquare) {
                for (int value : magicSquareRow) {
                    System.out.printf("%" + width + "d", value);
                }
                System.out.println();
            }
        }
    }
}