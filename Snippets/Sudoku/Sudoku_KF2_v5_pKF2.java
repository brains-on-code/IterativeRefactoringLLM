package com.thealgorithms.puzzlesandgames;

final class Sudoku {

    private Sudoku() {
        // Utility class; prevent instantiation
    }

    /**
     * Returns {@code true} if placing {@code num} at ({@code row}, {@code col})
     * in {@code board} is valid according to Sudoku rules.
     *
     * @param board the Sudoku board
     * @param row   row index
     * @param col   column index
     * @param num   number to place
     */
    public static boolean isSafe(int[][] board, int row, int col, int num) {
        int size = board.length;
        return !isInRow(board, row, num, size)
            && !isInColumn(board, col, num, size)
            && !isInBox(board, row, col, num, size);
    }

    /**
     * Returns {@code true} if {@code num} exists in {@code row}.
     */
    private static boolean isInRow(int[][] board, int row, int num, int size) {
        for (int col = 0; col < size; col++) {
            if (board[row][col] == num) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns {@code true} if {@code num} exists in {@code col}.
     */
    private static boolean isInColumn(int[][] board, int col, int num, int size) {
        for (int row = 0; row < size; row++) {
            if (board[row][col] == num) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns {@code true} if {@code num} exists in the sub-box containing
     * ({@code row}, {@code col}).
     */
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

    /**
     * Solves {@code board} using backtracking.
     *
     * @param board the Sudoku board
     * @param size  board dimension (e.g., 9 for a 9x9 board)
     * @return {@code true} if a solution exists; {@code false} otherwise
     */
    public static boolean solveSudoku(int[][] board, int size) {
        int[] emptyCell = findEmptyCell(board, size);
        if (emptyCell == null) {
            // No empty cells left: puzzle solved
            return true;
        }

        int row = emptyCell[0];
        int col = emptyCell[1];

        for (int num = 1; num <= size; num++) {
            if (isSafe(board, row, col, num)) {
                board[row][col] = num;

                if (solveSudoku(board, size)) {
                    return true;
                }

                // Undo the move (backtrack)
                board[row][col] = 0;
            }
        }

        return false;
    }

    /**
     * Returns the next empty cell (value {@code 0}) in {@code board}.
     *
     * @param board the Sudoku board
     * @param size  board dimension
     * @return an array {@code [row, col]} for the next empty cell,
     *         or {@code null} if the board is full
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
     * Prints {@code board} to standard output, grouping cells by sub-box.
     *
     * @param board the Sudoku board
     * @param size  board dimension
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