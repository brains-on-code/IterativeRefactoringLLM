package com.thealgorithms.backtracking;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public final class KnightsTour {

    private KnightsTour() {}

    /** Board dimension including a 2-cell-wide invalid border. */
    private static final int BOARD_SIZE = 12;

    /** All 8 possible knight moves (dx, dy). */
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

    /**
     * Board representation:
     * -1 = invalid border
     *  0 = unvisited valid square
     * >0 = move index in the tour
     */
    static int[][] board;

    /** Number of valid (non-border) squares to visit. */
    static int totalSquaresToVisit;

    /**
     * Initializes the board with a 2-cell-wide invalid border.
     * The inner (BOARD_SIZE - 4) x (BOARD_SIZE - 4) region is valid.
     */
    public static void resetBoard() {
        board = new int[BOARD_SIZE][BOARD_SIZE];
        totalSquaresToVisit = (BOARD_SIZE - 4) * (BOARD_SIZE - 4);

        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                boolean isBorderRow = row < 2 || row > BOARD_SIZE - 3;
                boolean isBorderCol = col < 2 || col > BOARD_SIZE - 3;
                if (isBorderRow || isBorderCol) {
                    board[row][col] = -1;
                }
            }
        }
    }

    /**
     * Attempts to complete a knight's tour using backtracking and Warnsdorff's heuristic.
     *
     * @param row  current row
     * @param col  current column
     * @param move current move index (1-based)
     * @return true if a complete tour is found
     */
    static boolean solve(int row, int col, int move) {
        if (move > totalSquaresToVisit) {
            return true;
        }

        List<int[]> neighbors = getNeighbors(row, col);

        if (neighbors.isEmpty() && move != totalSquaresToVisit) {
            return false;
        }

        // Apply Warnsdorff's heuristic: visit squares with the fewest onward moves first.
        neighbors.sort(Comparator.comparingInt(a -> a[2]));

        for (int[] neighbor : neighbors) {
            int nextRow = neighbor[0];
            int nextCol = neighbor[1];

            board[nextRow][nextCol] = move;

            boolean hasOrphan = orphanDetected(move, nextRow, nextCol);
            if (!hasOrphan && solve(nextRow, nextCol, move + 1)) {
                return true;
            }

            // Undo move (backtrack).
            board[nextRow][nextCol] = 0;
        }

        return false;
    }

    /**
     * Returns all valid, unvisited knight moves from (row, col),
     * each annotated with its onward-move count.
     *
     * @param row current row
     * @param col current column
     * @return list of {row, col, onwardMoveCount}
     */
    static List<int[]> getNeighbors(int row, int col) {
        List<int[]> neighbors = new ArrayList<>();

        for (int[] move : KNIGHT_MOVES) {
            int dx = move[0];
            int dy = move[1];
            int newRow = row + dy;
            int newCol = col + dx;

            if (isInsideBoard(newRow, newCol) && board[newRow][newCol] == 0) {
                int onwardMoves = countUnvisitedNeighbors(newRow, newCol);
                neighbors.add(new int[] {newRow, newCol, onwardMoves});
            }
        }

        return neighbors;
    }

    /**
     * Counts how many valid, unvisited knight moves exist from (row, col).
     *
     * @param row current row
     * @param col current column
     * @return number of valid, unvisited neighbors
     */
    static int countUnvisitedNeighbors(int row, int col) {
        int count = 0;

        for (int[] move : KNIGHT_MOVES) {
            int dx = move[0];
            int dy = move[1];
            int newRow = row + dy;
            int newCol = col + dx;

            if (isInsideBoard(newRow, newCol) && board[newRow][newCol] == 0) {
                count++;
            }
        }

        return count;
    }

    /**
     * Detects "orphan" squares: unvisited squares that will have no onward moves later,
     * which would make completing the tour impossible.
     *
     * @param move current move index
     * @param row  current row
     * @param col  current column
     * @return true if an orphan is detected
     */
    static boolean orphanDetected(int move, int row, int col) {
        if (move >= totalSquaresToVisit - 1) {
            return false;
        }

        List<int[]> neighbors = getNeighbors(row, col);
        for (int[] neighbor : neighbors) {
            int neighborRow = neighbor[0];
            int neighborCol = neighbor[1];
            if (countUnvisitedNeighbors(neighborRow, neighborCol) == 0) {
                return true;
            }
        }

        return false;
    }

    /**
     * Checks if (row, col) lies within the board bounds.
     *
     * @param row row index
     * @param col column index
     * @return true if inside the board, false otherwise
     */
    private static boolean isInsideBoard(int row, int col) {
        return row >= 0 && row < BOARD_SIZE && col >= 0 && col < BOARD_SIZE;
    }
}