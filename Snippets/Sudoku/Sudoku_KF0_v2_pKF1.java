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
    }

    /**
     * Checks if placing a number in a specific position on the Sudoku board is safe.
     * The number is considered safe if it does not violate any of the Sudoku rules:
     * - It should not be present in the same row.
     * - It should not be present in the same column.
     * - It should not be present in the corresponding subgrid, which is sqrt(n) x sqrt(n) in size.
     *
     * @param board The current state of the Sudoku board.
     * @param row   The row index where the number is to be placed.
     * @param col   The column index where the number is to be placed.
     * @param value The number to be placed on the board.
     * @return True if the placement is safe, otherwise false.
     */
    public static boolean isSafe(int[][] board, int row, int col, int value) {
        int boardSize = board.length;

        // Check the row for duplicates
        for (int column = 0; column < boardSize; column++) {
            if (board[row][column] == value) {
                return false;
            }
        }

        // Check the column for duplicates
        for (int currentRow = 0; currentRow < boardSize; currentRow++) {
            if (board[currentRow][col] == value) {
                return false;
            }
        }

        // Check the corresponding subgrid for duplicates
        int subgridSize = (int) Math.sqrt(boardSize);
        int subgridRowStart = row - row % subgridSize;
        int subgridColStart = col - col % subgridSize;

        for (int currentRow = subgridRowStart; currentRow < subgridRowStart + subgridSize; currentRow++) {
            for (int currentCol = subgridColStart; currentCol < subgridColStart + subgridSize; currentCol++) {
                if (board[currentRow][currentCol] == value) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Solves the Sudoku puzzle using backtracking.
     *
     * @param board     The current state of the Sudoku board.
     * @param boardSize The size of the Sudoku board (typically 9 for a standard puzzle).
     * @return True if the Sudoku puzzle is solvable, false otherwise.
     */
    public static boolean solveSudoku(int[][] board, int boardSize) {
        int emptyCellRow = -1;
        int emptyCellCol = -1;
        boolean foundEmptyCell = false;

        // Find the next empty cell
        for (int row = 0; row < boardSize; row++) {
            for (int col = 0; col < boardSize; col++) {
                if (board[row][col] == 0) {
                    emptyCellRow = row;
                    emptyCellCol = col;
                    foundEmptyCell = true;
                    break;
                }
            }
            if (foundEmptyCell) {
                break;
            }
        }

        // No empty space left
        if (!foundEmptyCell) {
            return true;
        }

        // Try placing numbers 1 to boardSize in the empty cell
        for (int candidateValue = 1; candidateValue <= boardSize; candidateValue++) {
            if (isSafe(board, emptyCellRow, emptyCellCol, candidateValue)) {
                board[emptyCellRow][emptyCellCol] = candidateValue;
                if (solveSudoku(board, boardSize)) {
                    return true;
                } else {
                    // backtrack
                    board[emptyCellRow][emptyCellCol] = 0;
                }
            }
        }
        return false;
    }

    /**
     * Prints the current state of the Sudoku board in a readable format.
     * Each row is printed on a new line, with numbers separated by spaces.
     *
     * @param board     The current state of the Sudoku board.
     * @param boardSize The size of the Sudoku board (typically 9 for a standard puzzle).
     */
    public static void print(int[][] board, int boardSize) {
        int subgridSize = (int) Math.sqrt(boardSize);

        for (int row = 0; row < boardSize; row++) {
            for (int col = 0; col < boardSize; col++) {
                System.out.print(board[row][col]);
                System.out.print(" ");
            }
            System.out.print("\n");

            if ((row + 1) % subgridSize == 0) {
                System.out.print("");
            }
        }
    }

    /**
     * The driver method to demonstrate solving a Sudoku puzzle.
     *
     * @param args Command-line arguments (not used in this program).
     */
    public static void main(String[] args) {
        int[][] board =
            new int[][] {
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
        int boardSize = board.length;

        if (solveSudoku(board, boardSize)) {
            print(board, boardSize);
        } else {
            System.out.println("No solution");
        }
    }
}