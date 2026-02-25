package com.thealgorithms.puzzlesandgames;

final class SudokuSolver {

    private SudokuSolver() {
    }

    public static boolean isSafeToPlace(int[][] board, int row, int col, int num) {
        for (int currentCol = 0; currentCol < board.length; currentCol++) {
            if (board[row][currentCol] == num) {
                return false;
            }
        }

        for (int currentRow = 0; currentRow < board.length; currentRow++) {
            if (board[currentRow][col] == num) {
                return false;
            }
        }

        int boxSize = (int) Math.sqrt(board.length);
        int boxStartRow = row - row % boxSize;
        int boxStartCol = col - col % boxSize;

        for (int currentRow = boxStartRow; currentRow < boxStartRow + boxSize; currentRow++) {
            for (int currentCol = boxStartCol; currentCol < boxStartCol + boxSize; currentCol++) {
                if (board[currentRow][currentCol] == num) {
                    return false;
                }
            }
        }

        return true;
    }

    public static boolean solveSudoku(int[][] board, int size) {
        int emptyRow = -1;
        int emptyCol = -1;
        boolean isComplete = true;

        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                if (board[row][col] == 0) {
                    emptyRow = row;
                    emptyCol = col;
                    isComplete = false;
                    break;
                }
            }
            if (!isComplete) {
                break;
            }
        }

        if (isComplete) {
            return true;
        }

        for (int num = 1; num <= size; num++) {
            if (isSafeToPlace(board, emptyRow, emptyCol, num)) {
                board[emptyRow][emptyCol] = num;
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
        int boxSize = (int) Math.sqrt(size);

        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                System.out.print(board[row][col]);
                System.out.print(" ");
            }
            System.out.print("\n");

            if ((row + 1) % boxSize == 0) {
                System.out.print("");
            }
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
            printBoard(board, size);
        } else {
            System.out.println("No solution");
        }
    }
}