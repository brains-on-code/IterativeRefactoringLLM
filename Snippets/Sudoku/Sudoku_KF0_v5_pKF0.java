package com.thealgorithms.puzzlesandgames;

/**
 * A class that provides methods to solve Sudoku puzzles of any n x n size
 * using a backtracking approach, where n must be a perfect square.
 * The algorithm checks for safe number placements in rows, columns,
 * and subgrids (which are sqrt(n) x sqrt(n) in size) and recursively solves the puzzle.
 * Though commonly used for 9x9 grids, it is adaptable to other valid Sudoku dimensions.
 */
final class Sudoku {

    private Sudoku() {
        // Utility class; prevent instantiation
    }

    /**
     * Checks if placing a number in a specific position on the Sudoku board is safe.
     *
     * @param board The current state of the Sudoku board.
     * @param row   The row index where the number is to be placed.
     * @param col   The column index where the number is to be placed.
     * @param num   The number to be placed on the board.
     * @return True if the placement is safe, otherwise false.
     */
    public static boolean isSafe(int[][] board, int row, int col, int num) {
        return isRowSafe(board, row, num)
            && isColumnSafe(board, col, num)
            && isBoxSafe(board, row, col, num);
    }

    private static boolean isRowSafe(int[][] board, int row, int num) {
        for (int value : board[row]) {
            if (value == num) {
                return false;
            }
        }
        return true;
    }

    private static boolean isColumnSafe(int[][] board, int col, int num) {
        for (int[] currentRow : board) {
            if (currentRow[col] == num) {
                return false;
            }
        }
        return true;
    }

    private static boolean isBoxSafe(int[][] board, int row, int col, int num) {
        int size = board.length;
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

    /**
     * Finds the next empty cell (with value 0) on the board.
     *
     * @param board The current state of the Sudoku board.
     * @return An array {row, col} of the empty cell, or null if no empty cell exists.
     */
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

    /**
     * Solves the Sudoku puzzle using backtracking.
     *
     * @param board The current state of the Sudoku board.
     * @param n     The size of the Sudoku board (typically 9 for a standard puzzle).
     * @return True if the Sudoku puzzle is solvable, false otherwise.
     */
    public static boolean solveSudoku(int[][] board, int n) {
        int[] emptyCell = findEmptyCell(board);

        if (emptyCell == null) {
            return true;
        }

        int row = emptyCell[0];
        int col = emptyCell[1];

        for (int num = 1; num <= n; num++) {
            if (isSafe(board, row, col, num)) {
                board[row][col] = num;

                if (solveSudoku(board, n)) {
                    return true;
                }

                board[row][col] = 0;
            }
        }
        return false;
    }

    /**
     * Prints the current state of the Sudoku board in a readable format.
     *
     * @param board The current state of the Sudoku board.
     * @param n     The size of the Sudoku board (typically 9 for a standard puzzle).
     */
    public static void print(int[][] board, int n) {
        int boxSize = (int) Math.sqrt(n);

        for (int row = 0; row < n; row++) {
            if (row > 0 && row % boxSize == 0) {
                System.out.println();
            }

            for (int col = 0; col < n; col++) {
                if (col > 0 && col % boxSize == 0) {
                    System.out.print("  ");
                }
                System.out.print(board[row][col] + " ");
            }
            System.out.println();
        }
    }

    /**
     * The driver method to demonstrate solving a Sudoku puzzle.
     *
     * @param args Command-line arguments (not used in this program).
     */
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
        int n = board.length;

        if (solveSudoku(board, n)) {
            print(board, n);
        } else {
            System.out.println("No solution");
        }
    }
}