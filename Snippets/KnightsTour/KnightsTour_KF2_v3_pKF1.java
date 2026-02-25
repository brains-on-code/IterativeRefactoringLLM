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

        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int column = 0; column < BOARD_SIZE; column++) {
                if (row < 2 || row > BOARD_SIZE - 3 || column < 2 || column > BOARD_SIZE - 3) {
                    board[row][column] = -1;
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

            if (!createsOrphan(moveNumber, nextRow, nextColumn) && solve(nextRow, nextColumn, moveNumber + 1)) {
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