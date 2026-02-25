package com.thealgorithms.backtracking;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public final class KnightsTour {

    private KnightsTour() {}

    private static final int BOARD_SIZE = 12;
    private static final int BORDER_OFFSET = 2;

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
        totalSquares = getInnerBoardSize() * getInnerBoardSize();

        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int column = 0; column < BOARD_SIZE; column++) {
                if (isBorderCell(row, column)) {
                    board[row][column] = -1;
                }
            }
        }
    }

    private static int getInnerBoardSize() {
        return BOARD_SIZE - 2 * BORDER_OFFSET;
    }

    private static boolean isBorderCell(int row, int column) {
        return row < BORDER_OFFSET
            || row >= BOARD_SIZE - BORDER_OFFSET
            || column < BORDER_OFFSET
            || column >= BOARD_SIZE - BORDER_OFFSET;
    }

    static boolean solve(int row, int column, int moveCount) {
        if (moveCount > totalSquares) {
            return true;
        }

        List<int[]> neighbors = getNeighbors(row, column);
        if (neighbors.isEmpty() && moveCount != totalSquares) {
            return false;
        }

        neighbors.sort(Comparator.comparingInt(neighbor -> neighbor[2]));

        for (int[] neighbor : neighbors) {
            int nextRow = neighbor[0];
            int nextColumn = neighbor[1];

            board[nextRow][nextColumn] = moveCount;

            if (!hasOrphan(moveCount, nextRow, nextColumn)
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

            if (isInsideBoard(nextRow, nextColumn) && isUnvisited(nextRow, nextColumn)) {
                int degree = countUnvisitedNeighbors(nextRow, nextColumn);
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

    private static boolean isUnvisited(int row, int column) {
        return board[row][column] == 0;
    }

    static int countUnvisitedNeighbors(int row, int column) {
        int count = 0;

        for (int[] move : KNIGHT_MOVES) {
            int nextColumn = column + move[0];
            int nextRow = row + move[1];

            if (isInsideBoard(nextRow, nextColumn) && isUnvisited(nextRow, nextColumn)) {
                count++;
            }
        }

        return count;
    }

    static boolean hasOrphan(int moveCount, int row, int column) {
        if (moveCount >= totalSquares - 1) {
            return false;
        }

        List<int[]> neighbors = getNeighbors(row, column);

        for (int[] neighbor : neighbors) {
            int neighborRow = neighbor[0];
            int neighborColumn = neighbor[1];

            if (countUnvisitedNeighbors(neighborRow, neighborColumn) == 0) {
                return true;
            }
        }

        return false;
    }
}