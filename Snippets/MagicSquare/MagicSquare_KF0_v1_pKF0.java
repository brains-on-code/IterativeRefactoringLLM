package com.thealgorithms.maths;

import java.util.Scanner;

/**
 * A magic square of order n is an arrangement of distinct n^2 integers in a
 * square, such that the n numbers in all rows, all columns, and both diagonals
 * sum to the same constant. A magic square contains the integers from 1 to n^2.
 */
public final class MagicSquare {

    private MagicSquare() {
        // Utility class
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            int size = readOddPositiveInteger(scanner);
            int[][] magicSquare = generateMagicSquare(size);
            printMagicSquare(magicSquare);
        }
    }

    private static int readOddPositiveInteger(Scanner scanner) {
        System.out.print("Input a number: ");
        int number = scanner.nextInt();

        if (number <= 0 || number % 2 == 0) {
            System.out.println("Input number must be odd and > 0");
            System.exit(0);
        }

        return number;
    }

    private static int[][] generateMagicSquare(int size) {
        int[][] magicSquare = new int[size][size];

        int row = size / 2;
        int col = size - 1;
        magicSquare[row][col] = 1;

        for (int value = 2; value <= size * size; value++) {
            int nextRow = (row - 1 + size) % size;
            int nextCol = (col + 1) % size;

            if (magicSquare[nextRow][nextCol] == 0) {
                row = nextRow;
                col = nextCol;
            } else {
                col = (col - 1 + size) % size;
            }

            magicSquare[row][col] = value;
        }

        return magicSquare;
    }

    private static void printMagicSquare(int[][] magicSquare) {
        for (int[] row : magicSquare) {
            for (int value : row) {
                System.out.printf("%3d ", value);
            }
            System.out.println();
        }
    }
}