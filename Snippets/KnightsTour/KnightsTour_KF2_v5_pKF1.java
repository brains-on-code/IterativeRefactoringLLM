package com.thealgorithms.backtracking;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public final class KnightsTour {
    private KnightsTour() {}

    private static final int BOARD_SIZE = 12;

    private static final int[][] KNIGHT_MOVE_OFFSETS = {
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
    static int totalInnerSquares;

    public static void resetBoard() {
        board = new int[BOARD_SIZE][BOARD_SIZE];
        totalInnerSquares = (BOARD_SIZE - 4) * (BOARD_SIZE - 4);

        for (int rowIndex = 0; rowIndex < BOARD_SIZE; rowIndex++) {
            for (int columnIndex = 0; columnIndex < BOARD_SIZE; columnIndex++) {
                if (rowIndex < 2
                        || rowIndex > BOARD_SIZE - 3
                        || columnIndex < 2
                        || columnIndex > BOARD_SIZE - 3) {
                    board[rowIndex][columnIndex] = -1;
                }
            }
        }
    }

    static boolean solve(int currentRow, int currentColumn, int moveNumber) {
        if (moveNumber > totalInnerSquares) {
            return true;
        }

        List<int[]> candidateMoves = getValidMoves(currentRow, currentColumn);

        if (candidateMoves.isEmpty() && moveNumber != totalInnerSquares) {
            return false;
        }

        candidateMoves.sort(Comparator.comparingInt(move -> move[2]));

        for (int[] move : candidateMoves) {
            int nextRow = move[0];
            int nextColumn = move[1];
            board[nextRow][nextColumn] = moveNumber;

            if (!createsOrphan(moveNumber, nextRow, nextColumn)
                    && solve(nextRow, nextColumn, moveNumber + 1)) {
                return true;
            }

            board[nextRow][nextColumn] = 0;
        }

        return false;
    }

    static List<int[]> getValidMoves(int currentRow, int currentColumn) {
        List<int[]> validMoves = new ArrayList<>();

        for (int[] offset : KNIGHT_MOVE_OFFSETS) {
            int columnOffset = offset[0];
            int rowOffset = offset[1];

            int targetRow = currentRow + rowOffset;
            int targetColumn = currentColumn + columnOffset;

            if (isWithinBoard(targetRow, targetColumn) && board[targetRow][targetColumn] == 0) {
                int onwardMoveCount = countOnwardMoves(targetRow, targetColumn);
                validMoves.add(new int[] {targetRow, targetColumn, onwardMoveCount});
            }
        }

        return validMoves;
    }

    static int countOnwardMoves(int currentRow, int currentColumn) {
        int onwardMoveCount = 0;

        for (int[] offset : KNIGHT_MOVE_OFFSETS) {
            int columnOffset = offset[0];
            int rowOffset = offset[1];

            int targetRow = currentRow + rowOffset;
            int targetColumn = currentColumn + columnOffset;

            if (isWithinBoard(targetRow, targetColumn) && board[targetRow][targetColumn] == 0) {
                onwardMoveCount++;
            }
        }

        return onwardMoveCount;
    }

    static boolean createsOrphan(int moveNumber, int currentRow, int currentColumn) {
        if (moveNumber < totalInnerSquares - 1) {
            List<int[]> candidateMoves = getValidMoves(currentRow, currentColumn);
            for (int[] move : candidateMoves) {
                int targetRow = move[0];
                int targetColumn = move[1];
                if (countOnwardMoves(targetRow, targetColumn) == 0) {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean isWithinBoard(int rowIndex, int columnIndex) {
        return rowIndex >= 0
                && rowIndex < BOARD_SIZE
                && columnIndex >= 0
                && columnIndex < BOARD_SIZE;
    }
}