package com.thealgorithms.maths;

import java.util.Scanner;

public final class MagicSquare {

    private MagicSquare() {
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Input a number: ");
        int order = scanner.nextInt();

        if (order <= 0 || order % 2 == 0) {
            System.out.print("Input number must be odd and >0");
            System.exit(0);
        }

        int[][] magicSquare = new int[order][order];

        int currentRow = order / 2;
        int currentColumn = order - 1;
        magicSquare[currentRow][currentColumn] = 1;

        for (int currentNumber = 2; currentNumber <= order * order; currentNumber++) {
            int nextRow = (currentRow - 1 + order) % order;
            int nextColumn = (currentColumn + 1) % order;

            if (magicSquare[nextRow][nextColumn] == 0) {
                currentRow = nextRow;
                currentColumn = nextColumn;
            } else {
                currentColumn = (currentColumn - 1 + order) % order;
            }

            magicSquare[currentRow][currentColumn] = currentNumber;
        }

        for (int rowIndex = 0; rowIndex < order; rowIndex++) {
            for (int columnIndex = 0; columnIndex < order; columnIndex++) {
                int cellValue = magicSquare[rowIndex][columnIndex];
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