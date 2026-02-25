package com.thealgorithms.puzzlesandgames;

final class Sudoku {

    private Sudoku() {
    }

    public static boolean isSafe(int[][] board, int row, int column, int number) {
        int size = board.length;

        for (int colIndex = 0; colIndex < size; colIndex++) {
            if (board[row][colIndex] == number) {
                return false;
            }
        }

        for (int rowIndex = 0; rowIndex < size; rowIndex++) {
            if (board[rowIndex][column] == number) {
                return false;
            }
        }

        int boxSize = (int) Math.sqrt(size);
        int boxRowStart = row - row % boxSize;
        int boxColStart = column - column % boxSize;

        for (int rowIndex = boxRowStart; rowIndex < boxRowStart + boxSize; rowIndex++) {
            for (int colIndex = boxColStart; colIndex < boxColStart + boxSize; colIndex++) {
                if (board[rowIndex][colIndex] == number) {
                    return false;
                }
            }
        }

        return true;
    }

    public static boolean solveSudoku(int[][] board, int size) {
        int emptyRow = -1;
        int emptyCol = -1;
        boolean hasEmptyCell = false;

        for (int rowIndex = 0; rowIndex < size; rowIndex++) {
            for (int colIndex = 0; colIndex < size; colIndex++) {
                if (board[rowIndex][colIndex] == 0) {
                    emptyRow = rowIndex;
                    emptyCol = colIndex;
                    hasEmptyCell = true;
                    break;
                }
            }
            if (hasEmptyCell) {
                break;
            }
        }

        if (!hasEmptyCell) {
            return true;
        }

        for (int number = 1; number <= size; number++) {
            if (isSafe(board, emptyRow, emptyCol, number)) {
                board[emptyRow][emptyCol] = number;
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

        for (int rowIndex = 0; rowIndex < size; rowIndex++) {
            for (int colIndex = 0; colIndex < size; colIndex++) {
                System.out.print(board[rowIndex][colIndex]);
                System.out.print(" ");
            }
            System.out.print("\n");

            if ((rowIndex + 1) % boxSize == 0) {
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