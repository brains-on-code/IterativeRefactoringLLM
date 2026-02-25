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

    static boolean solve(int currentRow, int currentColumn, int moveIndex) {
        if (moveIndex > totalInnerSquares) {
            return true;
        }

        List<int[]> candidateMoves = getValidMoves(currentRow, currentColumn);

        if (candidateMoves.isEmpty() && moveIndex != totalInnerSquares) {
            return false;
        }

        candidateMoves.sort(Comparator.comparingInt(move -> move[2]));

        for (int[] move : candidateMoves) {
            int nextRow = move[0];
            int nextColumn = move[1];
            board[nextRow][nextColumn] = moveIndex;

            if (!createsOrphan(moveIndex, nextRow, nextColumn) && solve(nextRow, nextColumn, moveIndex + 1)) {
                return true;
            }

            board[nextRow][nextColumn] = 0;
        }

        return false;
    }

    static List<int[]> getValidMoves(int row, int column) {
        List<int[]> validMoves = new ArrayList<>();

        for (int[] offset : KNIGHT_MOVE_OFFSETS) {
            int deltaColumn = offset[0];
            int deltaRow = offset[1];

            int targetRow = row + deltaRow;
            int targetColumn = column + deltaColumn;

            if (isWithinBoard(targetRow, targetColumn) && board[targetRow][targetColumn] == 0) {
                int onwardMoveCount = countOnwardMoves(targetRow, targetColumn);
                validMoves.add(new int[] {targetRow, targetColumn, onwardMoveCount});
            }
        }

        return validMoves;
    }

    static int countOnwardMoves(int row, int column) {
        int onwardMoveCount = 0;

        for (int[] offset : KNIGHT_MOVE_OFFSETS) {
            int deltaColumn = offset[0];
            int deltaRow = offset[1];

            int targetRow = row + deltaRow;
            int targetColumn = column + deltaColumn;

            if (isWithinBoard(targetRow, targetColumn) && board[targetRow][targetColumn] == 0) {
                onwardMoveCount++;
            }
        }

        return onwardMoveCount;
    }

    static boolean createsOrphan(int moveIndex, int row, int column) {
        if (moveIndex < totalInnerSquares - 1) {
            List<int[]> candidateMoves = getValidMoves(row, column);
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