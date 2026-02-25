package com.thealgorithms.maths;

import java.util.Scanner;

public final class MagicSquare {

    private MagicSquare() {
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Input a number: ");
        int size = scanner.nextInt();

        if (size <= 0 || size % 2 == 0) {
            System.out.print("Input number must be odd and >0");
            System.exit(0);
        }

        int[][] magicSquare = new int[size][size];

        int row = size / 2;
        int column = size - 1;
        magicSquare[row][column] = 1;

        for (int value = 2; value <= size * size; value++) {
            int nextRow = (row - 1 + size) % size;
            int nextColumn = (column + 1) % size;

            if (magicSquare[nextRow][nextColumn] == 0) {
                row = nextRow;
                column = nextColumn;
            } else {
                column = (column - 1 + size) % size;
            }

            magicSquare[row][column] = value;
        }

        for (int rowIndex = 0; rowIndex < size; rowIndex++) {
            for (int columnIndex = 0; columnIndex < size; columnIndex++) {
                int value = magicSquare[rowIndex][columnIndex];
                if (value < 10) {
                    System.out.print(" ");
                }
                if (value < 100) {
                    System.out.print(" ");
                }
                System.out.print(value + " ");
            }
            System.out.println();
        }

        scanner.close();
    }
}