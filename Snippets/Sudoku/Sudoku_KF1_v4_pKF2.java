package com.thealgorithms.puzzlesandgames;

/**
 * A simple Sudoku solver using backtracking.
 */
final class SudokuSolver {

    private SudokuSolver() {
        // Utility class; prevent instantiation.
    }

    /**
     * Checks whether placing {@code num} at position ({@code row}, {@code col})
     * is valid according to Sudoku rules.
     *
     * @param board the Sudoku board
     * @param row   the row index
     * @param col   the column index
     * @param num   the number to place
     * @return {@code true} if the placement is valid; {@code false} otherwise
     */
    public static boolean isValidPlacement(int[][] board, int row, int col, int num) {
        int size = board.length;

        // Check the row.
        for (int j = 0; j < size; j++) {
            if (board[row][j] == num) {
                return false;
            }
        }

        // Check the column.
        for (int i = 0; i < size; i++) {
            if (board[i][col] == num) {
                return false;
            }
        }

        // Check the subgrid.
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
     * @return {@code true} if a solution is found; {@code false} otherwise
     */
    public static boolean solveSudoku(int[][] board, int size) {
        int[] emptyCell = findEmptyCell(board, size);

        // No empty cells: puzzle solved.
        if (emptyCell == null) {
            return true;
        }

        int row = emptyCell[0];
        int col = emptyCell[1];

        // Try all possible numbers in the empty cell.
        for (int num = 1; num <= size; num++) {
            if (isValidPlacement(board, row, col, num)) {
                board[row][col] = num;

                if (solveSudoku(board, size)) {
                    return true;
                }

                // Backtrack.
                board[row][col] = 0;
            }
        }
        return false;
    }

    /**
     * Finds the next empty cell (denoted by 0) in the board.
     *
     * @param board the Sudoku board
     * @param size  the size of the board
     * @return an array {@code [row, col]} of the empty cell position,
     *         or {@code null} if no empty cell exists
     */
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

    /**
     * Prints the Sudoku board to standard output.
     *
     * @param board the Sudoku board
     * @param size  the size of the board
     */
    public static void printBoard(int[][] board, int size) {
        int subgridSize = (int) Math.sqrt(size);

        for (int row = 0; row < size; row++) {
            if (row > 0 && row % subgridSize == 0) {
                System.out.println();
            }

            for (int col = 0; col < size; col++) {
                if (col > 0 && col % subgridSize == 0) {
                    System.out.print("  ");
                }
                System.out.print(board[row][col]);
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