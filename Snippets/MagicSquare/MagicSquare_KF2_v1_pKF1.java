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

        int currentRow = size / 2;
        int currentCol = size - 1;
        magicSquare[currentRow][currentCol] = 1;

        for (int value = 2; value <= size * size; value++) {
            int nextRow = (currentRow - 1 + size) % size;
            int nextCol = (currentCol + 1) % size;

            if (magicSquare[nextRow][nextCol] == 0) {
                currentRow = nextRow;
                currentCol = nextCol;
            } else {
                currentCol = (currentCol - 1 + size) % size;
            }

            magicSquare[currentRow][currentCol] = value;
        }

        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                if (magicSquare[row][col] < 10) {
                    System.out.print(" ");
                }
                if (magicSquare[row][col] < 100) {
                    System.out.print(" ");
                }
                System.out.print(magicSquare[row][col] + " ");
            }
            System.out.println();
        }

        scanner.close();
    }
}