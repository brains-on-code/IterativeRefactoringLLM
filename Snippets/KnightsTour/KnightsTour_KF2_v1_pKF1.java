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

    static int[][] board;
    static int totalSquaresToVisit;

    public static void resetBoard() {
        board = new int[BOARD_SIZE][BOARD_SIZE];
        totalSquaresToVisit = (BOARD_SIZE - 4) * (BOARD_SIZE - 4);

        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int column = 0; column < BOARD_SIZE; column++) {
                if (row < 2 || row > BOARD_SIZE - 3 || column < 2 || column > BOARD_SIZE - 3) {
                    board[row][column] = -1;
                }
            }
        }
    }

    static boolean solve(int row, int column, int moveCount) {
        if (moveCount > totalSquaresToVisit) {
            return true;
        }

        List<int[]> possibleMoves = getValidMoves(row, column);

        if (possibleMoves.isEmpty() && moveCount != totalSquaresToVisit) {
            return false;
        }

        possibleMoves.sort(Comparator.comparingInt(move -> move[2]));

        for (int[] move : possibleMoves) {
            int nextRow = move[0];
            int nextColumn = move[1];
            board[nextRow][nextColumn] = moveCount;

            if (!isOrphanDetected(moveCount, nextRow, nextColumn) && solve(nextRow, nextColumn, moveCount + 1)) {
                return true;
            }

            board[nextRow][nextColumn] = 0;
        }

        return false;
    }

    static List<int[]> getValidMoves(int row, int column) {
        List<int[]> validMoves = new ArrayList<>();

        for (int[] move : KNIGHT_MOVES) {
            int deltaColumn = move[0];
            int deltaRow = move[1];

            int newRow = row + deltaRow;
            int newColumn = column + deltaColumn;

            if (isWithinBoard(newRow, newColumn) && board[newRow][newColumn] == 0) {
                int onwardMoves = countOnwardMoves(newRow, newColumn);
                validMoves.add(new int[] {newRow, newColumn, onwardMoves});
            }
        }

        return validMoves;
    }

    static int countOnwardMoves(int row, int column) {
        int onwardMoveCount = 0;

        for (int[] move : KNIGHT_MOVES) {
            int deltaColumn = move[0];
            int deltaRow = move[1];

            int newRow = row + deltaRow;
            int newColumn = column + deltaColumn;

            if (isWithinBoard(newRow, newColumn) && board[newRow][newColumn] == 0) {
                onwardMoveCount++;
            }
        }

        return onwardMoveCount;
    }

    static boolean isOrphanDetected(int moveCount, int row, int column) {
        if (moveCount < totalSquaresToVisit - 1) {
            List<int[]> possibleMoves = getValidMoves(row, column);
            for (int[] move : possibleMoves) {
                if (countOnwardMoves(move[0], move[1]) == 0) {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean isWithinBoard(int row, int column) {
        return row >= 0 && row < BOARD_SIZE && column >= 0 && column < BOARD_SIZE;
    }
}