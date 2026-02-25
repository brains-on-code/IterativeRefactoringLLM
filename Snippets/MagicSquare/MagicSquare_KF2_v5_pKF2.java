package com.thealgorithms.maths;

import java.util.Scanner;

public final class MagicSquare {

    private MagicSquare() {
        // Utility class; prevent instantiation
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            int size = readOddPositiveSize(scanner);
            int[][] magicSquare = generateMagicSquare(size);
            printMagicSquare(magicSquare);
        }
    }

    /**
     * Reads the size of the magic square from input.
     * The size must be a positive odd integer.
     *
     * @param scanner input source
     * @return validated odd positive size
     */
    private static int readOddPositiveSize(Scanner scanner) {
        System.out.print("Input a number: ");
        int size = scanner.nextInt();

        if (size <= 0 || size % 2 == 0) {
            System.out.println("Input number must be odd and > 0");
            System.exit(0);
        }

        return size;
    }

    /**
     * Generates an odd-order magic square using the Siamese method.
     *
     * @param size the order of the magic square (must be odd and > 0)
     * @return a 2D array representing the magic square
     */
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

    /**
     * Prints the magic square with aligned columns.
     *
     * @param magicSquare the magic square to print
     */
    private static void printMagicSquare(int[][] magicSquare) {
        int size = magicSquare.length;

        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                printAlignedValue(magicSquare[row][col]);
            }
            System.out.println();
        }
    }

    /**
     * Prints a single value with spacing so that columns align.
     *
     * @param value the value to print
     */
    private static void printAlignedValue(int value) {
        if (value < 10) {
            System.out.print("  ");
        } else if (value < 100) {
            System.out.print(" ");
        }
        System.out.print(value + " ");
    }
}