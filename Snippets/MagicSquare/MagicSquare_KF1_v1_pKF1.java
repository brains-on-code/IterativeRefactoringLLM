package com.thealgorithms.maths;

import java.util.Scanner;

/**
 * Generates and prints an odd-order magic square using the Siamese method.
 */
public final class MagicSquareGenerator {

    private MagicSquareGenerator() {
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Input a number: ");
        int size = scanner.nextInt();

        if ((size % 2 == 0) || (size <= 0)) {
            System.out.print("Input number must be odd and >0");
            System.exit(0);
        }

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

        for (int r = 0; r < size; r++) {
            for (int c = 0; c < size; c++) {
                if (magicSquare[r][c] < 10) {
                    System.out.print(" ");
                }
                if (magicSquare[r][c] < 100) {
                    System.out.print(" ");
                }
                System.out.print(magicSquare[r][c] + " ");
            }
            System.out.println();
        }

        scanner.close();
    }
}