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

        int row = order / 2;
        int col = order - 1;
        magicSquare[row][col] = 1;

        for (int number = 2; number <= order * order; number++) {
            int nextRow = (row - 1 + order) % order;
            int nextCol = (col + 1) % order;

            if (magicSquare[nextRow][nextCol] == 0) {
                row = nextRow;
                col = nextCol;
            } else {
                col = (col - 1 + order) % order;
            }

            magicSquare[row][col] = number;
        }

        for (int currentRow = 0; currentRow < order; currentRow++) {
            for (int currentCol = 0; currentCol < order; currentCol++) {
                int value = magicSquare[currentRow][currentCol];
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