package com.thealgorithms.puzzlesandgames;

final class Sudoku {

    private Sudoku() {
        // Utility class; prevent instantiation
    }

    /**
     * Checks whether placing {@code num} at position ({@code row}, {@code col})
     * in the given {@code board} is valid according to Sudoku rules.
     *
     * @param board the Sudoku board
     * @param row   the row index
     * @param col   the column index
     * @param num   the number to place
     * @return {@code true} if it is safe to place the number, {@code false} otherwise
     */
    public static boolean isSafe(int[][] board, int row, int col, int num) {
        int size = board.length;

        // Check if num is already present in the given row
        for (int c = 0; c < size; c++) {
            if (board[row][c] == num) {
                return false;
            }
        }

        // Check if num is already present in the given column
        for (int r = 0; r < size; r++) {
            if (board[r][col] == num) {
                return false;
            }
        }

        // Check if num is already present in the corresponding subgrid
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
     * Attempts to solve the given Sudoku board using backtracking.
     *
     * @param board the Sudoku board
     * @param size  the dimension of the board (e.g., 9 for a 9x9 board)
     * @return {@code true} if the board can be solved, {@code false} otherwise
     */
    public static boolean solveSudoku(int[][] board, int size) {
        int[] emptyCell = findEmptyCell(board, size);
        if (emptyCell == null) {
            // No empty cells left; puzzle solved
            return true;
        }

        int row = emptyCell[0];
        int col = emptyCell[1];

        // Try placing numbers 1 through size in the empty cell
        for (int num = 1; num <= size; num++) {
            if (isSafe(board, row, col, num)) {
                board[row][col] = num;

                if (solveSudoku(board, size)) {
                    return true;
                }

                // Backtrack if placing num doesn't lead to a solution
                board[row][col] = 0;
            }
        }

        // Trigger backtracking
        return false;
    }

    /**
     * Finds the next empty cell (denoted by 0) in the board.
     *
     * @param board the Sudoku board
     * @param size  the dimension of the board
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
     * @param size  the dimension of the board
     */
    public static void print(int[][] board, int size) {
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