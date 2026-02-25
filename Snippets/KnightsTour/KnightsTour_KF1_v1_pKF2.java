package com.thealgorithms.backtracking;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Knight's Tour on a 12x12 board with a 8x8 playable inner region.
 *
 * The outer border (2 squares wide) is marked as invalid (-1) and never visited.
 * The algorithm uses Warnsdorff's heuristic: always move to the square with the
 * fewest onward moves.
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

    /** Board representation: 0 = unvisited, -1 = invalid, >0 = move index. */
    static int[][] board;

    /** Number of valid squares to visit ((BOARD_SIZE - 4)^2 = 8x8 inner board). */
    static int totalValidSquares;

    /**
     * Initialize the board and mark the invalid border cells.
     */
    public static void method1() {
        board = new int[BOARD_SIZE][BOARD_SIZE];
        totalValidSquares = (BOARD_SIZE - 4) * (BOARD_SIZE - 4);

        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                // Mark a 2-cell-wide border as invalid
                if (row < 2 || row > BOARD_SIZE - 3 || col < 2 || col > BOARD_SIZE - 3) {
                    board[row][col] = -1;
                }
            }
        }
    }

    /**
     * Backtracking search for a Knight's Tour using Warnsdorff's heuristic.
     *
     * @param row   current row
     * @param col   current column
     * @param step  current move index (1-based)
     * @return true if a complete tour is found
     */
    static boolean method2(int row, int col, int step) {
        if (step > totalValidSquares) {
            return true;
        }

        List<int[]> candidates = method3(row, col);

        if (candidates.isEmpty() && step != totalValidSquares) {
            return false;
        }

        // Sort by number of onward moves (Warnsdorff's rule)
        candidates.sort(Comparator.comparingInt(a -> a[2]));

        for (int[] candidate : candidates) {
            int nextRow = candidate[0];
            int nextCol = candidate[1];

            board[nextRow][nextCol] = step;

            if (!method5(step, nextRow, nextCol) && method2(nextRow, nextCol, step + 1)) {
                return true;
            }

            // Backtrack
            board[nextRow][nextCol] = 0;
        }

        return false;
    }

    /**
     * Generate all valid next knight moves from (row, col), each annotated with
     * its onward degree (number of further moves).
     *
     * @param row current row
     * @param col current column
     * @return list of {nextRow, nextCol, degree}
     */
    static List<int[]> method3(int row, int col) {
        List<int[]> result = new ArrayList<>();

        for (int[] move : KNIGHT_MOVES) {
            int dx = move[0];
            int dy = move[1];

            int nextRow = row + dy;
            int nextCol = col + dx;

            if (nextRow >= 0
                && nextRow < BOARD_SIZE
                && nextCol >= 0
                && nextCol < BOARD_SIZE
                && board[nextRow][nextCol] == 0) {

                int degree = method4(nextRow, nextCol);
                result.add(new int[] {nextRow, nextCol, degree});
            }
        }

        return result;
    }

    /**
     * Count the number of valid onward moves from (row, col).
     *
     * @param row row index
     * @param col column index
     * @return number of possible moves from this square
     */
    static int method4(int row, int col) {
        int degree = 0;

        for (int[] move : KNIGHT_MOVES) {
            int dx = move[0];
            int dy = move[1];

            int nextRow = row + dy;
            int nextCol = col + dx;

            if (nextRow >= 0
                && nextRow < BOARD_SIZE
                && nextCol >= 0
                && nextCol < BOARD_SIZE
                && board[nextRow][nextCol] == 0) {

                degree++;
            }
        }

        return degree;
    }

    /**
     * Pruning condition: if we are not near the end of the tour and there exists
     * a move from (row, col) that leads to a dead end (0 onward moves), prune.
     *
     * @param step current move index
     * @param row  current row
     * @param col  current column
     * @return true if the path should be pruned
     */
    static boolean method5(int step, int row, int col) {
        if (step < totalValidSquares - 1) {
            List<int[]> candidates = method3(row, col);
            for (int[] candidate : candidates) {
                if (method4(candidate[0], candidate[1]) == 0) {
                    return true;
                }
            }
        }
        return false;
    }
}