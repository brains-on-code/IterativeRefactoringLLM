package com.thealgorithms.puzzlesandgames;

final class Sudoku {

    private Sudoku() {
    }

    public static boolean isSafe(int[][] board, int row, int column, int candidate) {
        int boardSize = board.length;

        for (int currentColumn = 0; currentColumn < boardSize; currentColumn++) {
            if (board[row][currentColumn] == candidate) {
                return false;
            }
        }

        for (int currentRow = 0; currentRow < boardSize; currentRow++) {
            if (board[currentRow][column] == candidate) {
                return false;
            }
        }

        int boxSize = (int) Math.sqrt(boardSize);
        int boxRowStart = row - row % boxSize;
        int boxColumnStart = column - column % boxSize;

        for (int currentRow = boxRowStart; currentRow < boxRowStart + boxSize; currentRow++) {
            for (int currentColumn = boxColumnStart; currentColumn < boxColumnStart + boxSize; currentColumn++) {
                if (board[currentRow][currentColumn] == candidate) {
                    return false;
                }
            }
        }

        return true;
    }

    public static boolean solveSudoku(int[][] board, int boardSize) {
        int emptyRow = -1;
        int emptyColumn = -1;
        boolean hasEmptyCell = false;

        for (int row = 0; row < boardSize; row++) {
            for (int column = 0; column < boardSize; column++) {
                if (board[row][column] == 0) {
                    emptyRow = row;
                    emptyColumn = column;
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

        for (int candidate = 1; candidate <= boardSize; candidate++) {
            if (isSafe(board, emptyRow, emptyColumn, candidate)) {
                board[emptyRow][emptyColumn] = candidate;
                if (solveSudoku(board, boardSize)) {
                    return true;
                } else {
                    board[emptyRow][emptyColumn] = 0;
                }
            }
        }
        return false;
    }

    public static void printBoard(int[][] board, int boardSize) {
        int boxSize = (int) Math.sqrt(boardSize);

        for (int row = 0; row < boardSize; row++) {
            for (int column = 0; column < boardSize; column++) {
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

        int boardSize = board.length;

        if (solveSudoku(board, boardSize)) {
            printBoard(board, boardSize);
        } else {
            System.out.println("No solution");
        }
    }
}