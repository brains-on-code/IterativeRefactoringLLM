package com.thealgorithms.maths;

import java.util.Scanner;

/**
 * Generates and prints an odd-order magic square using the Siamese method.
 *
 * <p>A magic square of order n is an arrangement of distinct n^2 integers in an n×n grid such that
 * the numbers in all rows, all columns, and both main diagonals sum to the same constant.</p>
 */
public final class MagicSquare {

    private MagicSquare() {
        // Utility class; prevent instantiation
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Input an odd positive number: ");
            int size = scanner.nextInt();

            if (!isValidSize(size)) {
                System.out.println("Input number must be an odd integer greater than 0.");
                return;
            }

            int[][] magicSquare = generateMagicSquare(size);
            printMagicSquare(magicSquare);
        }
    }

    /**
     * Checks whether the given size is a positive odd integer.
     *
     * @param size the size of the magic square
     * @return {@code true} if size is positive and odd; {@code false} otherwise
     */
    private static boolean isValidSize(int size) {
        return size > 0 && size % 2 != 0;
    }

    /**
     * Generates an odd-order magic square using the Siamese method.
     *
     * <p>Algorithm (Siamese method):</p>
     * <ol>
     *   <li>Start with 1 in the middle of the last column.</li>
     *   <li>For each next number, move one row up and one column right (wrapping around edges).</li>
     *   <li>If the target cell is already filled, move one column left from the current position instead.</li>
     * </ol>
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
            int nextRow = (row - 1 + size) % size; // move up (with wrap-around)
            int nextCol = (col + 1) % size;        // move right (with wrap-around)

            if (magicSquare[nextRow][nextCol] == 0) {
                row = nextRow;
                col = nextCol;
            } else {
                col = (col - 1 + size) % size;     // move left if cell is already occupied
            }

            magicSquare[row][col] = value;
        }

        return magicSquare;
    }

    /**
     * Prints the magic square with basic alignment.
     *
     * @param magicSquare the 2D array representing the magic square
     */
    private static void printMagicSquare(int[][] magicSquare) {
        int size = magicSquare.length;

        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                int value = magicSquare[row][col];

                if (value < 10) {
                    System.out.print("  ");
                } else if (value < 100) {
                    System.out.print(" ");
                }

                System.out.print(value + " ");
            }
            System.out.println();
        }
    }
}