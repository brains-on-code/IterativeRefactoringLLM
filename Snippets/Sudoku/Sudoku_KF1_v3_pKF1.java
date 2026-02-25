package com.thealgorithms.puzzlesandgames;

final class SudokuSolver {

    private SudokuSolver() {
    }

    public static boolean isSafeToPlace(int[][] board, int row, int column, int number) {
        int boardSize = board.length;

        for (int currentColumn = 0; currentColumn < boardSize; currentColumn++) {
            if (board[row][currentColumn] == number) {
                return false;
            }
        }

        for (int currentRow = 0; currentRow < boardSize; currentRow++) {
            if (board[currentRow][column] == number) {
                return false;
            }
        }

        int boxSize = (int) Math.sqrt(boardSize);
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

        for (int candidateNumber = 1; candidateNumber <= boardSize; candidateNumber++) {
            if (isSafeToPlace(board, emptyRow, emptyColumn, candidateNumber)) {
                board[emptyRow][emptyColumn] = candidateNumber;
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