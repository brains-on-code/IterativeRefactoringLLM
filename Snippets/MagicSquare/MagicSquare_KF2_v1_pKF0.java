package com.thealgorithms.maths;

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