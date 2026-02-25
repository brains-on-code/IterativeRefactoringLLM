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
        generateMagicSquareFromInput();
    }

    private static void generateMagicSquareFromInput() {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Input a number: ");
            int size = scanner.nextInt();

            if (!isValidSize(size)) {
                System.out.print("Input number must be odd and >0");
                return;
            }

            int[][] magicSquare = generateMagicSquare(size);
            printMagicSquare(magicSquare);
        }
    }

    private static boolean isValidSize(int size) {
        return size > 0 && size % 2 != 0;
    }

    private static int[][] generateMagicSquare(int size) {
        int[][] square = new int[size][size];

        int row = size / 2;
        int col = size - 1;
        square[row][col] = 1;

        for (int value = 2; value <= size * size; value++) {
            int nextRow = (row - 1 + size) % size;
            int nextCol = (col + 1) % size;

            if (square[nextRow][nextCol] == 0) {
                row = nextRow;
                col = nextCol;
            } else {
                col = (col - 1 + size) % size;
            }

            square[row][col] = value;
        }

        return square;
    }

    private static void printMagicSquare(int[][] square) {
        int size = square.length;

        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                System.out.printf("%3d ", square[row][col]);
            }
            System.out.println();
        }
    }
}