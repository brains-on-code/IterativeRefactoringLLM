package com.thealgorithms.backtracking;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Knight's Tour on a 12x12 board with a 8x8 inner playable area.
 *
 * The outer 2-cell-wide border is marked as invalid (-1). The algorithm
 * searches for a tour within the inner (var7) cells using Warnsdorff-like
 * heuristics (sorting by onward moves).
 */
public final class Class1 {

    private Class1() {
    }

    /** Board size (12x12). */
    private static final int BOARD_SIZE = 12;

    /** Knight move offsets: (dx, dy). */
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

    /** Board representation. -1 = invalid, 0 = unvisited, >0 = move index. */
    static int[][] board;

    /** Number of valid (inner) cells to visit. */
    static int targetMoveCount;

    /** Initialize the board and mark the outer border as invalid. */
    public static void initializeBoard() {
        board = new int[BOARD_SIZE][BOARD_SIZE];
        targetMoveCount = (BOARD_SIZE - 4) * (BOARD_SIZE - 4);

        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                if (row < 2 || row > BOARD_SIZE - 3 || col < 2 || col > BOARD_SIZE - 3) {
                    board[row][col] = -1;
                }
            }
        }
    }

    /**
     * Backtracking step for Knight's Tour.
     *
     * @param row   current row
     * @param col   current column
     * @param move  current move index (1-based)
     * @return true if a complete tour is found
     */
    static boolean solveKnightTour(int row, int col, int move) {
        if (move > targetMoveCount) {
            return true;
        }

        List<int[]> nextMoves = getNextMoves(row, col);

        if (nextMoves.isEmpty() && move != targetMoveCount) {
            return false;
        }

        // Warnsdorff heuristic: sort by number of onward moves (ascending)
        nextMoves.sort(Comparator.comparingInt(a -> a[2]));

        for (int[] candidate : nextMoves) {
            int nextRow = candidate[0];
            int nextCol = candidate[1];

            board[nextRow][nextCol] = move;

            if (!createsDeadEnd(move, nextRow, nextCol) && solveKnightTour(nextRow, nextCol, move + 1)) {
                return true;
            }

            board[nextRow][nextCol] = 0;
        }

        return false;
    }

    /**
     * Generate all valid next moves from (row, col), each annotated with
     * the number of onward moves from that position.
     *
     * @param row current row
     * @param col current column
     * @return list of {nextRow, nextCol, onwardMovesCount}
     */
    static List<int[]> getNextMoves(int row, int col) {
        List<int[]> moves = new ArrayList<>();

        for (int[] offset : KNIGHT_MOVES) {
            int dx = offset[0];
            int dy = offset[1];

            int nextRow = row + dy;
            int nextCol = col + dx;

            if (isInsideBoard(nextRow, nextCol) && board[nextRow][nextCol] == 0) {
                int onwardMoves = countOnwardMoves(nextRow, nextCol);
                moves.add(new int[] {nextRow, nextCol, onwardMoves});
            }
        }

        return moves;
    }

    /**
     * Count how many valid moves are available from (row, col).
     *
     * @param row row index
     * @param col column index
     * @return number of onward moves
     */
    static int countOnwardMoves(int row, int col) {
        int count = 0;

        for (int[] offset : KNIGHT_MOVES) {
            int dx = offset[0];
            int dy = offset[1];

            int nextRow = row + dy;
            int nextCol = col + dx;

            if (isInsideBoard(nextRow, nextCol) && board[nextRow][nextCol] == 0) {
                count++;
            }
        }

        return count;
    }

    /**
     * Check if placing the current move at (row, col) creates a dead end
     * too early (i.e., before the last two moves).
     *
     * @param move current move index
     * @param row  current row
     * @param col  current column
     * @return true if a dead end is created
     */
    static boolean createsDeadEnd(int move, int row, int col) {
        if (move < targetMoveCount - 1) {
            List<int[]> nextMoves = getNextMoves(row, col);
            for (int[] candidate : nextMoves) {
                if (countOnwardMoves(candidate[0], candidate[1]) == 0) {
                    return true;
                }
            }
        }
        return false;
    }

    /** Check if (row, col) is within the board bounds. */
    private static boolean isInsideBoard(int row, int col) {
        return row >= 0 && row < BOARD_SIZE && col >= 0 && col < BOARD_SIZE;
    }
}