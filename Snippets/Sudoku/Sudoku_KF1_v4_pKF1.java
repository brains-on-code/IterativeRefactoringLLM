package com.thealgorithms.puzzlesandgames;

final class SudokuSolver {

    private SudokuSolver() {
    }

    public static boolean isSafeToPlace(int[][] board, int row, int col, int value) {
        int size = board.length;

        // Check row
        for (int currentCol = 0; currentCol < size; currentCol++) {
            if (board[row][currentCol] == value) {
                return false;
            }
        }

        // Check column
        for (int currentRow = 0; currentRow < size; currentRow++) {
            if (board[currentRow][col] == value) {
                return false;
            }
        }

        // Check subgrid
        int subgridSize = (int) Math.sqrt(size);
        int subgridStartRow = row - row % subgridSize;
        int subgridStartCol = col - col % subgridSize;

        for (int currentRow = subgridStartRow; currentRow < subgridStartRow + subgridSize; currentRow++) {
            for (int currentCol = subgridStartCol; currentCol < subgridStartCol + subgridSize; currentCol++) {
                if (board[currentRow][currentCol] == value) {
                    return false;
                }
            }
        }

        return true;
    }

    public static boolean solveSudoku(int[][] board, int size) {
        int emptyRow = -1;
        int emptyCol = -1;
        boolean foundEmptyCell = false;

        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                if (board[row][col] == 0) {
                    emptyRow = row;
                    emptyCol = col;
                    foundEmptyCell = true;
                    break;
                }
            }
            if (foundEmptyCell) {
                break;
            }
        }

        if (!foundEmptyCell) {
            return true;
        }

        for (int candidate = 1; candidate <= size; candidate++) {
            if (isSafeToPlace(board, emptyRow, emptyCol, candidate)) {
                board[emptyRow][emptyCol] = candidate;
                if (solveSudoku(board, size)) {
                    return true;
                } else {
                    board[emptyRow][emptyCol] = 0;
                }
            }
        }
        return false;
    }

    public static void printBoard(int[][] board, int size) {
        int subgridSize = (int) Math.sqrt(size);

        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                System.out.print(board[row][col]);
                System.out.print(" ");
            }
            System.out.print("\n");

            if ((row + 1) % subgridSize == 0) {
                System.out.print("");
            }
        }
    }

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
        int size = board.length;

        if (solveSudoku(board, size)) {
            printBoard(board, size);
        } else {
            System.out.println("No solution");
        }
    }
}