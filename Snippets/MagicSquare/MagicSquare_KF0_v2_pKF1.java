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

        int rowIndex = order / 2;
        int columnIndex = order - 1;
        magicSquare[rowIndex][columnIndex] = 1;

        for (int currentValue = 2; currentValue <= order * order; currentValue++) {
            int nextRowIndex = (rowIndex - 1 + order) % order;
            int nextColumnIndex = (columnIndex + 1) % order;

            if (magicSquare[nextRowIndex][nextColumnIndex] == 0) {
                rowIndex = nextRowIndex;
                columnIndex = nextColumnIndex;
            } else {
                columnIndex = (columnIndex - 1 + order) % order;
            }

            magicSquare[rowIndex][columnIndex] = currentValue;
        }

        for (int rowIndexToPrint = 0; rowIndexToPrint < order; rowIndexToPrint++) {
            for (int columnIndexToPrint = 0; columnIndexToPrint < order; columnIndexToPrint++) {
                int valueToPrint = magicSquare[rowIndexToPrint][columnIndexToPrint];
                if (valueToPrint < 10) {
                    System.out.print(" ");
                }
                if (valueToPrint < 100) {
                    System.out.print(" ");
                }
                System.out.print(valueToPrint + " ");
            }
            System.out.println();
        }

        scanner.close();
    }
}