package com.thealgorithms.maths;

import java.util.Scanner;

/*
 * A magic square of order n is an arrangement of distinct n^2 integers in a square,
 * such that the n numbers in all rows, all columns, and both diagonals sum to the same constant.
 * A magic square contains the integers from 1 to n^2.
 */
public final class MagicSquare {

    private MagicSquare() {
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Input a number: ");
        int order = scanner.nextInt();

        if (order <= 0 || order % 2 == 0) {
            System.out.print("Input number must be odd and > 0");
            System.exit(0);
        }

        int[][] magicSquare = new int[order][order];

        int currentRow = order / 2;
        int currentColumn = order - 1;
        magicSquare[currentRow][currentColumn] = 1;

        for (int currentValue = 2; currentValue <= order * order; currentValue++) {
            int nextRow = (currentRow - 1 + order) % order;
            int nextColumn = (currentColumn + 1) % order;

            if (magicSquare[nextRow][nextColumn] == 0) {
                currentRow = nextRow;
                currentColumn = nextColumn;
            } else {
                currentColumn = (currentColumn - 1 + order) % order;
            }

            magicSquare[currentRow][currentColumn] = currentValue;
        }

        for (int row = 0; row < order; row++) {
            for (int column = 0; column < order; column++) {
                int cellValue = magicSquare[row][column];
                if (cellValue < 10) {
                    System.out.print(" ");
                }
                if (cellValue < 100) {
                    System.out.print(" ");
                }
                System.out.print(cellValue + " ");
            }
            System.out.println();
        }

        scanner.close();
    }
}