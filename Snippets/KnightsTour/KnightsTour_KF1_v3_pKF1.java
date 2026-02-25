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

        for (int rowIndex = 0; rowIndex < BOARD_SIZE; rowIndex++) {
            for (int colIndex = 0; colIndex < BOARD_SIZE; colIndex++) {
                if (rowIndex < 2
                    || rowIndex > BOARD_SIZE - 3
                    || colIndex < 2
                    || colIndex > BOARD_SIZE - 3) {
                    board[rowIndex][colIndex] = -1;
                }
            }
        }
    }

    static boolean solveKnightTour(int currentRow, int currentCol, int moveNumber) {
        if (moveNumber > innerBoardCellCount) {
            return true;
        }

        List<int[]> candidateMoves = getCandidateMoves(currentRow, currentCol);

        if (candidateMoves.isEmpty() && moveNumber != innerBoardCellCount) {
            return false;
        }

        candidateMoves.sort(Comparator.comparingInt(move -> move[2]));

        for (int[] move : candidateMoves) {
            int nextRow = move[0];
            int nextCol = move[1];
            board[nextRow][nextCol] = moveNumber;

            if (!createsDeadEnd(moveNumber, nextRow, nextCol)
                && solveKnightTour(nextRow, nextCol, moveNumber + 1)) {
                return true;
            }

            board[nextRow][nextCol] = 0;
        }

        return false;
    }

    static List<int[]> getCandidateMoves(int currentRow, int currentCol) {
        List<int[]> movesWithOnwardDegree = new ArrayList<>();

        for (int[] offset : KNIGHT_MOVE_OFFSETS) {
            int colOffset = offset[0];
            int rowOffset = offset[1];
            int newRow = currentRow + rowOffset;
            int newCol = currentCol + colOffset;

            if (newRow >= 0
                && newRow < BOARD_SIZE
                && newCol >= 0
                && newCol < BOARD_SIZE
                && board[newRow][newCol] == 0) {

                int onwardDegree = countOnwardMoves(newRow, newCol);
                movesWithOnwardDegree.add(new int[] {newRow, newCol, onwardDegree});
            }
        }
        return movesWithOnwardDegree;
    }

    static int countOnwardMoves(int currentRow, int currentCol) {
        int onwardMoveCount = 0;

        for (int[] offset : KNIGHT_MOVE_OFFSETS) {
            int colOffset = offset[0];
            int rowOffset = offset[1];
            int newRow = currentRow + rowOffset;
            int newCol = currentCol + colOffset;

            if (newRow >= 0
                && newRow < BOARD_SIZE
                && newCol >= 0
                && newCol < BOARD_SIZE
                && board[newRow][newCol] == 0) {

                onwardMoveCount++;
            }
        }
        return onwardMoveCount;
    }

    static boolean createsDeadEnd(int moveNumber, int currentRow, int currentCol) {
        if (moveNumber < innerBoardCellCount - 1) {
            List<int[]> nextMoves = getCandidateMoves(currentRow, currentCol);
            for (int[] move : nextMoves) {
                int nextRow = move[0];
                int nextCol = move[1];
                if (countOnwardMoves(nextRow, nextCol) == 0) {
                    return true;
                }
            }
        }
        return false;
    }
}