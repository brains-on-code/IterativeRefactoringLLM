package com.thealgorithms.maths;

import java.util.InputMismatchException;
import java.util.Scanner;

public final class MagicSquare {

    private MagicSquare() {
        // Utility class
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            int size = readOddPositiveNumber(scanner);
            int[][] magicSquare = generateMagicSquare(size);
            printMagicSquare(magicSquare);
        }
    }

    private static int readOddPositiveNumber(Scanner scanner) {
        System.out.print("Input an odd positive number: ");
        try {
            int number = scanner.nextInt();
            validateOddPositive(number);
            return number;
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter an integer.");
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

        for (int value = 2; value <= size * size; value++) {
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
        int size = magicSquare.length;
        int maxNumber = size * size;
        int width = String.valueOf(maxNumber).length() + 1;

        for (int[] row : magicSquare) {
            printRow(row, width);
        }
    }

    private static void printRow(int[] row, int width) {
        for (int value : row) {
            System.out.printf("%" + width + "d", value);
        }
        System.out.println();
    }
}