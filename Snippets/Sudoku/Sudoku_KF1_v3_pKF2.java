package com.thealgorithms.puzzlesandgames;

/**
 * A simple Sudoku solver using backtracking.
 */
final class SudokuSolver {

    private SudokuSolver() {
        // Prevent instantiation of utility class.
    }

    /**
     * Returns {@code true} if placing {@code num} at ({@code row}, {@code col})
     * is valid according to Sudoku rules.
     *
     * @param board the Sudoku board
     * @param row   the row index
     * @param col   the column index
     * @param num   the number to place
     */
    public static boolean isValidPlacement(int[][] board, int row, int col, int num) {
        int size = board.length;

        // Check row
        for (int j = 0; j < size; j++) {
            if (board[row][j] == num) {
                return false;
            }
        }

        // Check column
        for (int i = 0; i < size; i++) {
            if (board[i][col] == num) {
                return false;
            }
        }

        // Check subgrid
        int subgridSize = (int) Math.sqrt(size);
        int subgridRowStart = row - row % subgridSize;
        int subgridColStart = col - col % subgridSize;

        for (int i = subgridRowStart; i < subgridRowStart + subgridSize; i++) {
            for (int j = subgridColStart; j < subgridColStart + subgridSize; j++) {
                if (board[i][j] == num) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Attempts to solve the given Sudoku board using backtracking.
     *
     * @param board the Sudoku board
     * @param size  the size of the board (e.g., 9 for a 9x9 Sudoku)
     * @return {@code true} if a solution is found, {@code false} otherwise
     */
    public static boolean solveSudoku(int[][] board, int size) {
        int row = -1;
        int col = -1;
        boolean hasEmptyCell = false;

        // Locate next empty cell (denoted by 0)
        for (int i = 0; i < size && !hasEmptyCell; i++) {
            for (int j = 0; j < size; j++) {
                if (board[i][j] == 0) {
                    row = i;
                    col = j;
                    hasEmptyCell = true;
                    break;
                }
            }
        }

        // No empty cells: puzzle solved
        if (!hasEmptyCell) {
            return true;
        }

        // Try all possible numbers in the empty cell
        for (int num = 1; num <= size; num++) {
            if (isValidPlacement(board, row, col, num)) {
                board[row][col] = num;

                if (solveSudoku(board, size)) {
                    return true;
                }

                // Backtrack
                board[row][col] = 0;
            }
        }
        return false;
    }

    /**
     * Prints the Sudoku board to standard output.
     *
     * @param board the Sudoku board
     * @param size  the size of the board
     */
    public static void printBoard(int[][] board, int size) {
        int subgridSize = (int) Math.sqrt(size);

        for (int i = 0; i < size; i++) {
            if (i > 0 && i % subgridSize == 0) {
                System.out.println();
            }

            for (int j = 0; j < size; j++) {
                if (j > 0 && j % subgridSize == 0) {
                    System.out.print("  ");
                }
                System.out.print(board[i][j]);
                System.out.print(' ');
            }
            System.out.println();
        }
    }

    /**
     * Entry point: sets up a sample Sudoku puzzle and attempts to solve it.
     *
     * @param args command-line arguments (unused)
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
        int size = board.length;

        if (solveSudoku(board, size)) {
            printBoard(board, size);
        } else {
            System.out.println("No solution");
        }
    }
}