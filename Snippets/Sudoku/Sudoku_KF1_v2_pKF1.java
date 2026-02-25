package com.thealgorithms.puzzlesandgames;

final class SudokuSolver {

    private SudokuSolver() {
    }

    public static boolean isSafeToPlace(int[][] board, int row, int column, int number) {
        int size = board.length;

        for (int currentColumn = 0; currentColumn < size; currentColumn++) {
            if (board[row][currentColumn] == number) {
                return false;
            }
        }

        for (int currentRow = 0; currentRow < size; currentRow++) {
            if (board[currentRow][column] == number) {
                return false;
            }
        }

        int boxSize = (int) Math.sqrt(size);
        int boxStartRow = row - row % boxSize;
        int boxStartColumn = column - column % boxSize;

        for (int currentRow = boxStartRow; currentRow < boxStartRow + boxSize; currentRow++) {
            for (int currentColumn = boxStartColumn; currentColumn < boxStartColumn + boxSize; currentColumn++) {
                if (board[currentRow][currentColumn] == number) {
                    return false;
                }
            }
        }

        return true;
    }

    public static boolean solveSudoku(int[][] board, int size) {
        int emptyCellRow = -1;
        int emptyCellColumn = -1;
        boolean boardIsComplete = true;

        for (int row = 0; row < size; row++) {
            for (int column = 0; column < size; column++) {
                if (board[row][column] == 0) {
                    emptyCellRow = row;
                    emptyCellColumn = column;
                    boardIsComplete = false;
                    break;
                }
            }
            if (!boardIsComplete) {
                break;
            }
        }

        if (boardIsComplete) {
            return true;
        }

        for (int candidateNumber = 1; candidateNumber <= size; candidateNumber++) {
            if (isSafeToPlace(board, emptyCellRow, emptyCellColumn, candidateNumber)) {
                board[emptyCellRow][emptyCellColumn] = candidateNumber;
                if (solveSudoku(board, size)) {
                    return true;
                } else {
                    board[emptyCellRow][emptyCellColumn] = 0;
                }
            }
        }
        return false;
    }

    public static void printBoard(int[][] board, int size) {
        int boxSize = (int) Math.sqrt(size);

        for (int row = 0; row < size; row++) {
            for (int column = 0; column < size; column++) {
                System.out.print(board[row][column]);
                System.out.print(" ");
            }
            System.out.print("\n");

            if ((row + 1) % boxSize == 0) {
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