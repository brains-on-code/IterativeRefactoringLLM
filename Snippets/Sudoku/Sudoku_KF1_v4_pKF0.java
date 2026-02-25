package com.thealgorithms.puzzlesandgames;

final class SudokuSolver {

    private SudokuSolver() {
        // Utility class; prevent instantiation
    }

    public static boolean isSafe(int[][] board, int row, int col, int num) {
        int size = board.length;
        return !isInRow(board, row, num)
            && !isInColumn(board, col, num)
            && !isInBox(board, row, col, num, size);
    }

    private static boolean isInRow(int[][] board, int row, int num) {
        for (int value : board[row]) {
            if (value == num) {
                return true;
            }
        }
        return false;
    }

    private static boolean isInColumn(int[][] board, int col, int num) {
        for (int[] row : board) {
            if (row[col] == num) {
                return true;
            }
        }
        return false;
    }

    private static boolean isInBox(int[][] board, int row, int col, int num, int size) {
        int boxSize = (int) Math.sqrt(size);
        int boxRowStart = row - row % boxSize;
        int boxColStart = col - col % boxSize;

        for (int r = boxRowStart; r < boxRowStart + boxSize; r++) {
            for (int c = boxColStart; c < boxColStart + boxSize; c++) {
                if (board[r][c] == num) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean solveSudoku(int[][] board) {
        int size = board.length;
        int[] emptyCell = findEmptyCell(board);

        if (emptyCell == null) {
            return true; // Puzzle solved
        }

        int row = emptyCell[0];
        int col = emptyCell[1];

        for (int num = 1; num <= size; num++) {
            if (isSafe(board, row, col, num)) {
                board[row][col] = num;

                if (solveSudoku(board)) {
                    return true;
                }

                board[row][col] = 0; // Backtrack
            }
        }
        return false;
    }

    private static int[] findEmptyCell(int[][] board) {
        int size = board.length;

        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                if (board[row][col] == 0) {
                    return new int[] {row, col};
                }
            }
        }
        return null;
    }

    public static void printBoard(int[][] board) {
        int size = board.length;
        int boxSize = (int) Math.sqrt(size);

        for (int row = 0; row < size; row++) {
            if (row > 0 && row % boxSize == 0) {
                System.out.println();
            }
            for (int col = 0; col < size; col++) {
                if (col > 0 && col % boxSize == 0) {
                    System.out.print(" ");
                }
                System.out.print(board[row][col] + " ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        int[][] board = {
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

        if (solveSudoku(board)) {
            printBoard(board);
        } else {
            System.out.println("No solution");
        }
    }
}