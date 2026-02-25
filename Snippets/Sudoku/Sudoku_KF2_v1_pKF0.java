package com.thealgorithms.puzzlesandgames;

final class Sudoku {

    private Sudoku() {
        // Utility class
    }

    public static boolean isSafe(int[][] board, int row, int col, int num) {
        int size = board.length;

        // Check row
        for (int c = 0; c < size; c++) {
            if (board[row][c] == num) {
                return false;
            }
        }

        // Check column
        for (int r = 0; r < size; r++) {
            if (board[r][col] == num) {
                return false;
            }
        }

        // Check subgrid
        int boxSize = (int) Math.sqrt(size);
        int boxRowStart = row - row % boxSize;
        int boxColStart = col - col % boxSize;

        for (int r = boxRowStart; r < boxRowStart + boxSize; r++) {
            for (int c = boxColStart; c < boxColStart + boxSize; c++) {
                if (board[r][c] == num) {
                    return false;
                }
            }
        }

        return true;
    }

    public static boolean solveSudoku(int[][] board, int size) {
        int[] emptyCell = findEmptyCell(board, size);
        if (emptyCell == null) {
            return true; // No empty cells left, puzzle solved
        }

        int row = emptyCell[0];
        int col = emptyCell[1];

        for (int num = 1; num <= size; num++) {
            if (isSafe(board, row, col, num)) {
                board[row][col] = num;

                if (solveSudoku(board, size)) {
                    return true;
                }

                board[row][col] = 0; // Backtrack
            }
        }

        return false;
    }

    private static int[] findEmptyCell(int[][] board, int size) {
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                if (board[row][col] == 0) {
                    return new int[] {row, col};
                }
            }
        }
        return null;
    }

    public static void print(int[][] board, int size) {
        int boxSize = (int) Math.sqrt(size);

        for (int row = 0; row < size; row++) {
            if (row > 0 && row % boxSize == 0) {
                System.out.println();
            }

            for (int col = 0; col < size; col++) {
                if (col > 0 && col % boxSize == 0) {
                    System.out.print("  ");
                }
                System.out.print(board[row][col] + " ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        int[][] board = new int[][] {
            {3, 0, 6, 5, 0, 8, 4, 0, 0},
            {5, 2, 0, 0, 0, 0, 0, 0, 0},
            {0, 8, 7, 0, 0, 0, 0, 3, 1},
            {0, 0, 3, 0, 1, 0, 0, 8, 0},
            {9, 0, 0, 8, 6, 3, 0, 0, 5},
            {0, 5, 0, 0, 9, 0, 6, 0, 0},
            {1, 3, 0, 0, 0, 0, 2, 5, 0},
            {0, 0, 0, 0, 0, 0, 0, 7, 4},
            {0, 0, 5, 2, 0, 6, 3, 0, 0},
        };

        int size = board.length;

        if (solveSudoku(board, size)) {
            print(board, size);
        } else {
            System.out.println("No solution");
        }
    }
}