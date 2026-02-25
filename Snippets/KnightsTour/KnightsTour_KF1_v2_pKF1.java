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
            for (int col = 0; col < BOARD_SIZE; col++) {
                if (row < 2 || row > BOARD_SIZE - 3 || col < 2 || col > BOARD_SIZE - 3) {
                    board[row][col] = -1;
                }
            }
        }
    }

    static boolean solveKnightTour(int row, int col, int moveNumber) {
        if (moveNumber > innerBoardCellCount) {
            return true;
        }

        List<int[]> candidateMoves = getCandidateMoves(row, col);

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

    static List<int[]> getCandidateMoves(int row, int col) {
        List<int[]> movesWithOnwardDegree = new ArrayList<>();

        for (int[] offset : KNIGHT_MOVE_OFFSETS) {
            int offsetX = offset[0];
            int offsetY = offset[1];
            int newRow = row + offsetY;
            int newCol = col + offsetX;

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

    static int countOnwardMoves(int row, int col) {
        int onwardMoveCount = 0;

        for (int[] offset : KNIGHT_MOVE_OFFSETS) {
            int offsetX = offset[0];
            int offsetY = offset[1];
            int newRow = row + offsetY;
            int newCol = col + offsetX;

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

    static boolean createsDeadEnd(int moveNumber, int row, int col) {
        if (moveNumber < innerBoardCellCount - 1) {
            List<int[]> nextMoves = getCandidateMoves(row, col);
            for (int[] move : nextMoves) {
                if (countOnwardMoves(move[0], move[1]) == 0) {
                    return true;
                }
            }
        }
        return false;
    }
}