package com.thealgorithms.backtracking;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Solves the Knight's Tour problem using backtracking and Warnsdorff's heuristic.
 *
 * Problem:
 * Given an N×N board with a knight placed on the first block, move the knight
 * according to chess rules so that it visits each square exactly once.
 *
 * This implementation uses a 12×12 board with a buffered 8×8 playable area.
 */
public final class KnightsTour {

    private KnightsTour() {}

    /** Board dimension including a 2-cell buffer on each side of the 8×8 area. */
    private static final int BASE = 12;

    /** All possible knight moves (dx, dy). */
    private static final int[][] MOVES = {
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
     * -1 = buffer/border (unusable)
     *  0 = unvisited playable cell
     * >0 = move index (1-based)
     */
    static int[][] grid;

    /** Number of cells in the playable (8×8) area that must be visited. */
    static int total;

    /**
     * Initializes the board.
     *
     * Creates a BASE×BASE grid and marks:
     * - outer 2-cell border as -1 (unusable buffer)
     * - inner (BASE-4)×(BASE-4) area as 0 (unvisited playable cells)
     */
    public static void resetBoard() {
        grid = new int[BASE][BASE];
        total = (BASE - 4) * (BASE - 4);

        for (int row = 0; row < BASE; row++) {
            for (int col = 0; col < BASE; col++) {
                if (isInBuffer(row, col)) {
                    grid[row][col] = -1;
                }
            }
        }
    }

    /**
     * Performs a backtracking search for a full Knight's Tour.
     *
     * @param row    current row
     * @param column current column
     * @param count  current move index (1-based)
     * @return true if a complete tour is found
     */
    static boolean solve(int row, int column, int count) {
        if (count > total) {
            return true;
        }

        List<int[]> neighbors = neighbors(row, column);
        if (neighbors.isEmpty() && count != total) {
            return false;
        }

        // Warnsdorff's rule: try moves with the fewest onward moves first
        neighbors.sort(Comparator.comparingInt(a -> a[2]));

        for (int[] neighbor : neighbors) {
            int nextRow = neighbor[0];
            int nextCol = neighbor[1];

            grid[nextRow][nextCol] = count;

            boolean hasNoOrphan = !orphanDetected(count, nextRow, nextCol);
            if (hasNoOrphan && solve(nextRow, nextCol, count + 1)) {
                return true;
            }

            grid[nextRow][nextCol] = 0;
        }

        return false;
    }

    /**
     * Computes all valid next moves from (row, column).
     *
     * Each entry is {nextRow, nextCol, onwardMoveCount}.
     */
    static List<int[]> neighbors(int row, int column) {
        List<int[]> neighbors = new ArrayList<>();

        for (int[] move : MOVES) {
            int dx = move[0];
            int dy = move[1];
            int nextRow = row + dy;
            int nextCol = column + dx;

            if (isInsideBoard(nextRow, nextCol) && grid[nextRow][nextCol] == 0) {
                int onwardMoves = countNeighbors(nextRow, nextCol);
                neighbors.add(new int[] {nextRow, nextCol, onwardMoves});
            }
        }

        return neighbors;
    }

    /**
     * Counts how many valid moves are available from (row, column).
     *
     * @param row    current row
     * @param column current column
     * @return number of valid onward moves
     */
    static int countNeighbors(int row, int column) {
        int count = 0;

        for (int[] move : MOVES) {
            int dx = move[0];
            int dy = move[1];
            int nextRow = row + dy;
            int nextCol = column + dx;

            if (isInsideBoard(nextRow, nextCol) && grid[nextRow][nextCol] == 0) {
                count++;
            }
        }

        return count;
    }

    /**
     * Checks if moving to (row, column) creates an "orphan" cell:
     * a reachable unvisited cell that has no onward moves.
     *
     * @param count  current move index
     * @param row    current row
     * @param column current column
     * @return true if an orphan cell is detected
     */
    static boolean orphanDetected(int count, int row, int column) {
        if (count >= total - 1) {
            return false;
        }

        List<int[]> neighbors = neighbors(row, column);
        for (int[] neighbor : neighbors) {
            int neighborRow = neighbor[0];
            int neighborCol = neighbor[1];
            if (countNeighbors(neighborRow, neighborCol) == 0) {
                return true;
            }
        }

        return false;
    }

    /**
     * Checks if (row, column) lies within the board bounds.
     *
     * @param row    row index
     * @param column column index
     * @return true if inside the board, false otherwise
     */
    private static boolean isInsideBoard(int row, int column) {
        return row >= 0 && row < BASE && column >= 0 && column < BASE;
    }

    /**
     * Checks if (row, column) lies within the 2-cell buffer region.
     *
     * @param row    row index
     * @param column column index
     * @return true if inside the buffer, false otherwise
     */
    private static boolean isInBuffer(int row, int column) {
        return row < 2 || row > BASE - 3 || column < 2 || column > BASE - 3;
    }
}