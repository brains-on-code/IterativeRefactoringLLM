package com.thealgorithms.puzzlesandgames;

/**
 * Utility class for solving Sudoku puzzles of size n x n using backtracking,
 * where n must be a perfect square (e.g., 4, 9, 16).
 */
final class Sudoku {

    private Sudoku() {
        // Prevent instantiation
    }

    /**
     * Checks whether placing {@code num} at ({@code row}, {@code col}) is valid
     * according to Sudoku rules for the given board.
     *
     * @param board the Sudoku board
     * @param row   row index
     * @param col   column index
     * @param num   number to place
     * @return {@code true} if the placement is valid, {@code false} otherwise
     */
    public static boolean isSafe(int[][] board, int row, int col, int num) {
        int size = board.length;

        // Check the row
        for (int c = 0; c < size; c++) {
            if (board[row][c] == num) {
                return false;
            }
        }

        // Check the column
        for (int r = 0; r < size; r++) {
            if (board[r][col] == num) {
                return false;
            }
        }

        // Check the subgrid
        int sqrt = (int) Math.sqrt(size);
        int boxRowStart = row - row % sqrt;
        int boxColStart = col - col % sqrt;

        for (int r = boxRowStart; r < boxRowStart + sqrt; r++) {
            for (int c = boxColStart; c < boxColStart + sqrt; c++) {
                if (board[r][c] == num) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Solves the given Sudoku board using backtracking.
     *
     * @param board the Sudoku board
     * @param n     the size of the board (n x n)
     * @return {@code true} if a solution exists, {@code false} otherwise
     */
    public static boolean solveSudoku(int[][] board, int n) {
        int[] emptyCell = findEmptyCell(board, n);
        int row = emptyCell[0];
        int col = emptyCell[1];

        // No empty cell left: puzzle solved
        if (row == -1) {
            return true;
        }

        // Try all possible numbers for this cell
        for (int num = 1; num <= n; num++) {
            if (isSafe(board, row, col, num)) {
                board[row][col] = num;

                if (solveSudoku(board, n)) {
                    return true;
                }

                // Backtrack
                board[row][col] = 0;
            }
        }

        return false;
    }

    /**
     * Finds the next empty cell (with value 0) in the board.
     *
     * @param board the Sudoku board
     * @param n     the size of the board (n x n)
     * @return an array of size 2: [row, col] of the empty cell, or [-1, -1] if none
     */
    private static int[] findEmptyCell(int[][] board, int n) {
        for (int r = 0; r < n; r++) {
            for (int c = 0; c < n; c++) {
                if (board[r][c] == 0) {
                    return new int[] {r, c};
                }
            }
        }
        return new int[] {-1, -1};
    }

    /**
     * Prints the Sudoku board to standard output.
     *
     * @param board the Sudoku board
     * @param n     the size of the board (n x n)
     */
    public static void print(int[][] board, int n) {
        int sqrt = (int) Math.sqrt(n);

        for (int r = 0; r < n; r++) {
            if (r > 0 && r % sqrt == 0) {
                System.out.println();
            }

            for (int c = 0; c < n; c++) {
                if (c > 0 && c % sqrt == 0) {
                    System.out.print("  ");
                }
                System.out.print(board[r][c]);
                System.out.print(" ");
            }
            System.out.println();
        }
    }

    /**
     * Example driver method that solves a sample 9x9 Sudoku puzzle.
     *
     * @param args command-line arguments (unused)
     */
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
        int n = board.length;

        if (solveSudoku(board, n)) {
            print(board, n);
        } else {
            System.out.println("No solution");
        }
    }
}