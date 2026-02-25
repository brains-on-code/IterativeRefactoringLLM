package com.thealgorithms.maths;

import java.util.Scanner;

/**
 * Generates and prints an odd-order magic square using the Siamese method.
 */
public final class MagicSquareGenerator {

    private static final String INPUT_PROMPT = "Input a number: ";
    private static final String INVALID_INPUT_MESSAGE = "Input number must be odd and >0";

    private MagicSquareGenerator() {
        // Utility class; prevent instantiation
    }

    public static void main(String[] args) {
        generateMagicSquareFromInput();
    }

    private static void generateMagicSquareFromInput() {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print(INPUT_PROMPT);
            int size = scanner.nextInt();

            if (!isValidOddSize(size)) {
                System.out.print(INVALID_INPUT_MESSAGE);
                return;
            }

            int[][] magicSquare = generateMagicSquare(size);
            printMagicSquare(magicSquare);
        }
    }

    private static boolean isValidOddSize(int size) {
        return size > 0 && size % 2 != 0;
    }

    private static int[][] generateMagicSquare(int size) {
        int[][] square = new int[size][size];

        int currentRow = size / 2;
        int currentCol = size - 1;
        square[currentRow][currentCol] = 1;

        int maxValue = size * size;
        for (int value = 2; value <= maxValue; value++) {
            int nextRow = wrapIndex(currentRow - 1, size);
            int nextCol = wrapIndex(currentCol + 1, size);

            if (square[nextRow][nextCol] == 0) {
                currentRow = nextRow;
                currentCol = nextCol;
            } else {
                currentCol = wrapIndex(currentCol - 1, size);
            }

            square[currentRow][currentCol] = value;
        }

        return square;
    }

    private static int wrapIndex(int index, int size) {
        return (index + size) % size;
    }

    private static void printMagicSquare(int[][] square) {
        for (int[] row : square) {
            for (int value : row) {
                System.out.printf("%3d ", value);
            }
            System.out.println();
        }
    }
}