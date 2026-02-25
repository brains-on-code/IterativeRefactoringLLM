package com.thealgorithms.backtracking;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * The KnightsTour class solves the Knight's Tour problem using backtracking.
 *
 * Problem Statement:
 * Given an N*N board with a knight placed on the first block, the knight must
 * move according to chess rules and visit each square on the board exactly once.
 * The class outputs the sequence of moves for the knight.
 *
 * Example:
 * Input: N = 8 (8x8 chess board)
 * Output: The sequence of numbers representing the order in which the knight visits each square.
 */
public final class KnightsTour {

    private KnightsTour() {}

    /** The size of the chess board (12x12 grid, with 2 extra rows/columns as a buffer around an 8x8 area). */
    private static final int BASE = 12;

    /** Possible moves for a knight in chess. */
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

    /** Chess grid representing the board. */
    static int[][] grid;

    /** Total number of cells the knight needs to visit. */
    static int totalCellsToVisit;

    /**
     * Resets the chess board to its initial state.
     * Initializes the grid with boundary cells marked as -1 and internal cells as 0.
     * Sets the total number of cells the knight needs to visit.
     */
    public static void resetBoard() {
        grid = new int[BASE][BASE];
        totalCellsToVisit = (BASE - 4) * (BASE - 4);

        for (int row = 0; row < BASE; row++) {
            for (int col = 0; col < BASE; col++) {
                if (isBoundaryCell(row, col)) {
                    grid[row][col] = -1;
                }
            }
        }
    }

    private static boolean isBoundaryCell(int row, int col) {
        return row < 2 || row > BASE - 3 || col < 2 || col > BASE - 3;
    }

    /**
     * Recursive method to solve the Knight's Tour problem.
     *
     * @param row        The current row of the knight
     * @param column     The current column of the knight
     * @param moveNumber The current move number
     * @return True if a solution is found, False otherwise
     */
    static boolean solve(int row, int column, int moveNumber) {
        if (moveNumber > totalCellsToVisit) {
            return true;
        }

        List<int[]> neighbors = getNeighbors(row, column);

        if (neighbors.isEmpty() && moveNumber != totalCellsToVisit) {
            return false;
        }

        // Sort neighbors by Warnsdorff's rule (fewest onward moves)
        neighbors.sort(Comparator.comparingInt(move -> move[2]));

        for (int[] move : neighbors) {
            int nextRow = move[0];
            int nextCol = move[1];

            grid[nextRow][nextCol] = moveNumber;

            boolean hasNoOrphan = !orphanDetected(moveNumber, nextRow, nextCol);
            if (hasNoOrphan && solve(nextRow, nextCol, moveNumber + 1)) {
                return true;
            }

            grid[nextRow][nextCol] = 0; // Backtrack
        }

        return false;
    }

    /**
     * Returns a list of valid neighboring cells where the knight can move.
     *
     * @param row    The current row of the knight
     * @param column The current column of the knight
     * @return A list of arrays representing valid moves, where each array contains:
     *         {nextRow, nextCol, numberOfPossibleNextMoves}
     */
    static List<int[]> getNeighbors(int row, int column) {
        List<int[]> neighbors = new ArrayList<>();

        for (int[] move : KNIGHT_MOVES) {
            int deltaX = move[0];
            int deltaY = move[1];
            int nextRow = row + deltaY;
            int nextCol = column + deltaX;

            if (isInsideBoard(nextRow, nextCol) && isUnvisitedCell(nextRow, nextCol)) {
                int onwardMoves = countNeighbors(nextRow, nextCol);
                neighbors.add(new int[] {nextRow, nextCol, onwardMoves});
            }
        }

        return neighbors;
    }

    private static boolean isInsideBoard(int row, int col) {
        return row >= 0 && row < BASE && col >= 0 && col < BASE;
    }

    private static boolean isUnvisitedCell(int row, int col) {
        return grid[row][col] == 0;
    }

    /**
     * Counts the number of possible valid moves for a knight from a given position.
     *
     * @param row    The row of the current position
     * @param column The column of the current position
     * @return The number of valid neighboring moves
     */
    static int countNeighbors(int row, int column) {
        int validMoveCount = 0;

        for (int[] move : KNIGHT_MOVES) {
            int deltaX = move[0];
            int deltaY = move[1];
            int nextRow = row + deltaY;
            int nextCol = column + deltaX;

            if (isInsideBoard(nextRow, nextCol) && isUnvisitedCell(nextRow, nextCol)) {
                validMoveCount++;
            }
        }

        return validMoveCount;
    }

    /**
     * Detects if moving to a given position will create an orphan
     * (a position with no further valid moves).
     *
     * @param moveNumber The current move number
     * @param row        The row of the current position
     * @param column     The column of the current position
     * @return True if an orphan is detected, False otherwise
     */
    static boolean orphanDetected(int moveNumber, int row, int column) {
        if (moveNumber >= totalCellsToVisit - 1) {
            return false;
        }

        List<int[]> neighbors = getNeighbors(row, column);
        for (int[] move : neighbors) {
            int neighborRow = move[0];
            int neighborCol = move[1];
            if (countNeighbors(neighborRow, neighborCol) == 0) {
                return true;
            }
        }

        return false;
    }
}