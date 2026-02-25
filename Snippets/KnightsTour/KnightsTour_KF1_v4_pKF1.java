package com.thealgorithms.backtracking;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public final class KnightTourSolver {
    private KnightTourSolver() {
    }

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

    static int innerBoardCellCount;

    public static void initializeBoard() {
        board = new int[BOARD_SIZE][BOARD_SIZE];
        innerBoardCellCount = (BOARD_SIZE - 4) * (BOARD_SIZE - 4);

        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int column = 0; column < BOARD_SIZE; column++) {
                if (row < 2
                    || row > BOARD_SIZE - 3
                    || column < 2
                    || column > BOARD_SIZE - 3) {
                    board[row][column] = -1;
                }
            }
        }
    }

    static boolean solveKnightTour(int currentRow, int currentColumn, int moveNumber) {
        if (moveNumber > innerBoardCellCount) {
            return true;
        }

        List<int[]> candidateMoves = getCandidateMoves(currentRow, currentColumn);

        if (candidateMoves.isEmpty() && moveNumber != innerBoardCellCount) {
            return false;
        }

        candidateMoves.sort(Comparator.comparingInt(move -> move[2]));

        for (int[] move : candidateMoves) {
            int nextRow = move[0];
            int nextColumn = move[1];
            board[nextRow][nextColumn] = moveNumber;

            if (!createsDeadEnd(moveNumber, nextRow, nextColumn)
                && solveKnightTour(nextRow, nextColumn, moveNumber + 1)) {
                return true;
            }

            board[nextRow][nextColumn] = 0;
        }

        return false;
    }

    static List<int[]> getCandidateMoves(int currentRow, int currentColumn) {
        List<int[]> movesWithOnwardDegree = new ArrayList<>();

        for (int[] offset : KNIGHT_MOVE_OFFSETS) {
            int columnOffset = offset[0];
            int rowOffset = offset[1];
            int newRow = currentRow + rowOffset;
            int newColumn = currentColumn + columnOffset;

            if (newRow >= 0
                && newRow < BOARD_SIZE
                && newColumn >= 0
                && newColumn < BOARD_SIZE
                && board[newRow][newColumn] == 0) {

                int onwardDegree = countOnwardMoves(newRow, newColumn);
                movesWithOnwardDegree.add(new int[] {newRow, newColumn, onwardDegree});
            }
        }
        return movesWithOnwardDegree;
    }

    static int countOnwardMoves(int currentRow, int currentColumn) {
        int onwardMoveCount = 0;

        for (int[] offset : KNIGHT_MOVE_OFFSETS) {
            int columnOffset = offset[0];
            int rowOffset = offset[1];
            int newRow = currentRow + rowOffset;
            int newColumn = currentColumn + columnOffset;

            if (newRow >= 0
                && newRow < BOARD_SIZE
                && newColumn >= 0
                && newColumn < BOARD_SIZE
                && board[newRow][newColumn] == 0) {

                onwardMoveCount++;
            }
        }
        return onwardMoveCount;
    }

    static boolean createsDeadEnd(int moveNumber, int currentRow, int currentColumn) {
        if (moveNumber < innerBoardCellCount - 1) {
            List<int[]> nextMoves = getCandidateMoves(currentRow, currentColumn);
            for (int[] move : nextMoves) {
                int nextRow = move[0];
                int nextColumn = move[1];
                if (countOnwardMoves(nextRow, nextColumn) == 0) {
                    return true;
                }
            }
        }
        return false;
    }
}