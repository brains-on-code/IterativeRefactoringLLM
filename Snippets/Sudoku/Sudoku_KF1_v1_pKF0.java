package com.thealgorithms.puzzlesandgames;

final class Class1 {

    private Class1() {
    }

    public static boolean isSafe(int[][] board, int row, int col, int num) {
        int size = board.length;

        for (int j = 0; j < size; j++) {
            if (board[row][j] == num) {
                return false;
            }
        }

        for (int i = 0; i < size; i++) {
            if (board[i][col] == num) {
                return false;
            }
        }

        int boxSize = (int) Math.sqrt(size);
        int boxRowStart = row - row % boxSize;
        int boxColStart = col - col % boxSize;

        for (int i = boxRowStart; i < boxRowStart + boxSize; i++) {
            for (int j = boxColStart; j < boxColStart + boxSize; j++) {
                if (board[i][j] == num) {
                    return false;
                }
            }
        }

        return true;
    }

    public static boolean solveSudoku(int[][] board, int size) {
        int row = -1;
        int col = -1;
        boolean isComplete = true;

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (board[i][j] == 0) {
                    row = i;
                    col = j;
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
            if (isSafe(board, row, col, num)) {
                board[row][col] = num;
                if (solveSudoku(board, size)) {
                    return true;
                } else {
                    board[row][col] = 0;
                }
            }
        }
        return false;
    }

    public static void printBoard(int[][] board, int size) {
        int boxSize = (int) Math.sqrt(size);

        for (int i = 0; i < size; i++) {
            if (i > 0 && i % boxSize == 0) {
                System.out.println();
            }
            for (int j = 0; j < size; j++) {
                if (j > 0 && j % boxSize == 0) {
                    System.out.print(" ");
                }
                System.out.print(board[i][j] + " ");
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
            printBoard(board, size);
        } else {
            System.out.println("No solution");
        }
    }
}