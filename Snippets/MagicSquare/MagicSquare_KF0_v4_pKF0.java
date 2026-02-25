package com.thealgorithms.maths;

import java.util.InputMismatchException;
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
        System.out.print("Input an odd positive number: ");
        try {
            int number = scanner.nextInt();
            validateOddPositive(number);
            return number;
        } catch (InputMismatchException e) {
            System.out.println("Input must be an integer.");
            System.exit(1);
            return -1; // Unreachable, but required by compiler
        }
    }

    private static void validateOddPositive(int number) {
        if (!isOddPositive(number)) {
            System.out.println("Input number must be odd and greater than 0.");
            System.exit(1);
        }
    }

    private static boolean isOddPositive(int number) {
        return number > 0 && number % 2 != 0;
    }

    private static int[][] generateMagicSquare(int size) {
        int[][] magicSquare = new int[size][size];

        int row = size / 2;
        int col = size - 1;
        magicSquare[row][col] = 1;

        int maxValue = size * size;
        for (int value = 2; value <= maxValue; value++) {
            int nextRow = getWrappedIndex(row - 1, size);
            int nextCol = getWrappedIndex(col + 1, size);

            if (magicSquare[nextRow][nextCol] == 0) {
                row = nextRow;
                col = nextCol;
            } else {
                col = getWrappedIndex(col - 1, size);
            }

            magicSquare[row][col] = value;
        }

        return magicSquare;
    }

    private static int getWrappedIndex(int index, int size) {
        return (index + size) % size;
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