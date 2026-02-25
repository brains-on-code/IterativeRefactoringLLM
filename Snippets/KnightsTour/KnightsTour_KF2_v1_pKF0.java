package com.thealgorithms.backtracking;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public final class KnightsTour {

    private KnightsTour() {}

    private static final int BOARD_SIZE = 12;

    private static final int[][] KNIGHT_MOVES = {
        {1, -2},
        {2, -1},
        {2, 1},
        {1, 2},
        {-1, 2},
        {-2, 1},
        {-2, -1},
        {-1, -2},
    };

    private static int[][] board;
    private static int totalSquares;

    public static void resetBoard() {
        board = new int[BOARD_SIZE][BOARD_SIZE];
        totalSquares = (BOARD_SIZE - 4) * (BOARD_SIZE - 4);

        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int column = 0; column < BOARD_SIZE; column++) {
                if (isBorderCell(row, column)) {
                    board[row][column] = -1;
                }
            }
        }
    }

    private static boolean isBorderCell(int row, int column) {
        return row < 2
            || row > BOARD_SIZE - 3
            || column < 2
            || column > BOARD_SIZE - 3;
    }

    static boolean solve(int row, int column, int moveCount) {
        if (moveCount > totalSquares) {
            return true;
        }

        List<int[]> neighbors = getNeighbors(row, column);

        if (neighbors.isEmpty() && moveCount != totalSquares) {
            return false;
        }

        neighbors.sort(Comparator.comparingInt(a -> a[2]));

        for (int[] neighbor : neighbors) {
            int nextRow = neighbor[0];
            int nextColumn = neighbor[1];

            board[nextRow][nextColumn] = moveCount;

            if (!orphanDetected(moveCount, nextRow, nextColumn)
                && solve(nextRow, nextColumn, moveCount + 1)) {
                return true;
            }

            board[nextRow][nextColumn] = 0;
        }

        return false;
    }

    static List<int[]> getNeighbors(int row, int column) {
        List<int[]> neighbors = new ArrayList<>();

        for (int[] move : KNIGHT_MOVES) {
            int nextColumn = column + move[0];
            int nextRow = row + move[1];

            if (isInsideBoard(nextRow, nextColumn) && board[nextRow][nextColumn] == 0) {
                int degree = countNeighbors(nextRow, nextColumn);
                neighbors.add(new int[] {nextRow, nextColumn, degree});
            }
        }

        return neighbors;
    }

    private static boolean isInsideBoard(int row, int column) {
        return row >= 0
            && row < BOARD_SIZE
            && column >= 0
            && column < BOARD_SIZE;
    }

    static int countNeighbors(int row, int column) {
        int count = 0;

        for (int[] move : KNIGHT_MOVES) {
            int nextColumn = column + move[0];
            int nextRow = row + move[1];

            if (isInsideBoard(nextRow, nextColumn) && board[nextRow][nextColumn] == 0) {
                count++;
            }
        }

        return count;
    }

    static boolean orphanDetected(int moveCount, int row, int column) {
        if (moveCount >= totalSquares - 1) {
            return false;
        }

        List<int[]> neighbors = getNeighbors(row, column);

        for (int[] neighbor : neighbors) {
            if (countNeighbors(neighbor[0], neighbor[1]) == 0) {
                return true;
            }
        }

        return false;
    }
}