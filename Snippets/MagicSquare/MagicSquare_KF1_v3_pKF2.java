package com.thealgorithms.maths;

import java.util.Scanner;

/**
 * Generates and prints an odd-order magic square using the Siamese method.
 */
public final class MagicSquareGenerator {

    private MagicSquareGenerator() {
        // Utility class; prevent instantiation
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            int n = readOddPositiveNumber(scanner);
            if (n == -1) {
                return;
            }

            int[][] magicSquare = generateMagicSquare(n);
            printMagicSquare(magicSquare);
        }
    }

    private static int readOddPositiveNumber(Scanner scanner) {
        System.out.print("Input an odd positive number: ");
        int n = scanner.nextInt();

        if (n <= 0 || n % 2 == 0) {
            System.out.println("Input number must be odd and > 0");
            return -1;
        }

        return n;
    }

    /**
     * Generates an n x n magic square using the Siamese method.
     *
     * @param n odd positive size of the magic square
     * @return generated magic square
     */
    private static int[][] generateMagicSquare(int n) {
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

        return magicSquare;
    }

    private static void printMagicSquare(int[][] magicSquare) {
        int n = magicSquare.length;
        int maxNumber = n * n;
        int width = String.valueOf(maxNumber).length() + 1;

        for (int[] row : magicSquare) {
            for (int value : row) {
                System.out.printf("%" + width + "d", value);
            }
            System.out.println();
        }
    }
}