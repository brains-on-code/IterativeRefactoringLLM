package com.thealgorithms.maths;

import java.util.Scanner;

public final class MagicSquare {

    private MagicSquare() {
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Input a number: ");
        int squareSize = scanner.nextInt();

        if (squareSize <= 0 || squareSize % 2 == 0) {
            System.out.print("Input number must be odd and >0");
            System.exit(0);
        }

        int[][] magicSquare = new int[squareSize][squareSize];

        int currentRow = squareSize / 2;
        int currentColumn = squareSize - 1;
        magicSquare[currentRow][currentColumn] = 1;

        for (int currentValue = 2; currentValue <= squareSize * squareSize; currentValue++) {
            int nextRow = (currentRow - 1 + squareSize) % squareSize;
            int nextColumn = (currentColumn + 1) % squareSize;

            if (magicSquare[nextRow][nextColumn] == 0) {
                currentRow = nextRow;
                currentColumn = nextColumn;
            } else {
                currentColumn = (currentColumn - 1 + squareSize) % squareSize;
            }

            magicSquare[currentRow][currentColumn] = currentValue;
        }

        for (int rowIndex = 0; rowIndex < squareSize; rowIndex++) {
            for (int columnIndex = 0; columnIndex < squareSize; columnIndex++) {
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