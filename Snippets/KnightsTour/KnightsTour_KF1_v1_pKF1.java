package com.thealgorithms.backtracking;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public final class Class1 {
    private Class1() {
    }

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

    static int innerBoardCells;

    public static void initializeBoard() {
        board = new int[BOARD_SIZE][BOARD_SIZE];
        innerBoardCells = (BOARD_SIZE - 4) * (BOARD_SIZE - 4);
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                if (row < 2 || row > BOARD_SIZE - 3 || col < 2 || col > BOARD_SIZE - 3) {
                    board[row][col] = -1;
                }
            }
        }
    }

    static boolean solveKnightTour(int row, int col, int moveNumber) {
        if (moveNumber > innerBoardCells) {
            return true;
        }

        List<int[]> nextMoves = getNextMoves(row, col);

        if (nextMoves.isEmpty() && moveNumber != innerBoardCells) {
            return false;
        }

        nextMoves.sort(Comparator.comparingInt(a -> a[2]));

        for (int[] move : nextMoves) {
            int nextRow = move[0];
            int nextCol = move[1];
            board[nextRow][nextCol] = moveNumber;
            if (!createsDeadEnd(moveNumber, nextRow, nextCol) && solveKnightTour(nextRow, nextCol, moveNumber + 1)) {
                return true;
            }
            board[nextRow][nextCol] = 0;
        }

        return false;
    }

    static List<int[]> getNextMoves(int row, int col) {
        List<int[]> movesWithDegree = new ArrayList<>();

        for (int[] move : KNIGHT_MOVES) {
            int dx = move[0];
            int dy = move[1];
            int newRow = row + dy;
            int newCol = col + dx;

            if (newRow >= 0
                && newRow < BOARD_SIZE
                && newCol >= 0
                && newCol < BOARD_SIZE
                && board[newRow][newCol] == 0) {

                int degree = countOnwardMoves(newRow, newCol);
                movesWithDegree.add(new int[] {newRow, newCol, degree});
            }
        }
        return movesWithDegree;
    }

    static int countOnwardMoves(int row, int col) {
        int degree = 0;
        for (int[] move : KNIGHT_MOVES) {
            int dx = move[0];
            int dy = move[1];
            int newRow = row + dy;
            int newCol = col + dx;

            if (newRow >= 0
                && newRow < BOARD_SIZE
                && newCol >= 0
                && newCol < BOARD_SIZE
                && board[newRow][newCol] == 0) {

                degree++;
            }
        }
        return degree;
    }

    static boolean createsDeadEnd(int moveNumber, int row, int col) {
        if (moveNumber < innerBoardCells - 1) {
            List<int[]> nextMoves = getNextMoves(row, col);
            for (int[] move : nextMoves) {
                if (countOnwardMoves(move[0], move[1]) == 0) {
                    return true;
                }
            }
        }
        return false;
    }
}