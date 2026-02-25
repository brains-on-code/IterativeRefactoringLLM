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
    private KnightsTour() {
    }

    // The size of the chess board (12x12 grid, with 2 extra rows/columns as a buffer around a 8x8 area)
    private static final int BOARD_SIZE_WITH_BORDER = 12;

    // Possible moves for a knight in chess
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

    // Chess grid representing the board
    static int[][] board;

    // Total number of cells the knight needs to visit
    static int totalCellsToVisit;

    /**
     * Resets the chess board to its initial state.
     * Initializes the grid with boundary cells marked as -1 and internal cells as 0.
     * Sets the total number of cells the knight needs to visit.
     */
    public static void resetBoard() {
        board = new int[BOARD_SIZE_WITH_BORDER][BOARD_SIZE_WITH_BORDER];
        totalCellsToVisit = (BOARD_SIZE_WITH_BORDER - 4) * (BOARD_SIZE_WITH_BORDER - 4);

        for (int rowIndex = 0; rowIndex < BOARD_SIZE_WITH_BORDER; rowIndex++) {
            for (int columnIndex = 0; columnIndex < BOARD_SIZE_WITH_BORDER; columnIndex++) {
                if (rowIndex < 2
                    || rowIndex > BOARD_SIZE_WITH_BORDER - 3
                    || columnIndex < 2
                    || columnIndex > BOARD_SIZE_WITH_BORDER - 3) {
                    board[rowIndex][columnIndex] = -1; // Mark boundary cells
                }
            }
        }
    }

    /**
     * Recursive method to solve the Knight's Tour problem.
     *
     * @param rowIndex    The current row of the knight
     * @param columnIndex The current column of the knight
     * @param moveNumber  The current move number
     * @return True if a solution is found, False otherwise
     */
    static boolean solve(int rowIndex, int columnIndex, int moveNumber) {
        if (moveNumber > totalCellsToVisit) {
            return true;
        }

        List<int[]> candidateMoves = getValidMoves(rowIndex, columnIndex);

        if (candidateMoves.isEmpty() && moveNumber != totalCellsToVisit) {
            return false;
        }

        // Sort neighbors by Warnsdorff's rule (fewest onward moves)
        candidateMoves.sort(Comparator.comparingInt(move -> move[2]));

        for (int[] move : candidateMoves) {
            int nextRowIndex = move[0];
            int nextColumnIndex = move[1];
            board[nextRowIndex][nextColumnIndex] = moveNumber;

            if (!createsOrphan(moveNumber, nextRowIndex, nextColumnIndex)
                && solve(nextRowIndex, nextColumnIndex, moveNumber + 1)) {
                return true;
            }

            board[nextRowIndex][nextColumnIndex] = 0; // Backtrack
        }

        return false;
    }

    /**
     * Returns a list of valid neighboring cells where the knight can move.
     *
     * @param rowIndex    The current row of the knight
     * @param columnIndex The current column of the knight
     * @return A list of arrays representing valid moves, where each array contains:
     *         {nextRowIndex, nextColumnIndex, numberOfPossibleNextMoves}
     */
    static List<int[]> getValidMoves(int rowIndex, int columnIndex) {
        List<int[]> validMoves = new ArrayList<>();

        for (int[] moveOffset : KNIGHT_MOVE_OFFSETS) {
            int offsetX = moveOffset[0];
            int offsetY = moveOffset[1];
            int nextRowIndex = rowIndex + offsetY;
            int nextColumnIndex = columnIndex + offsetX;

            if (nextRowIndex >= 0
                && nextRowIndex < BOARD_SIZE_WITH_BORDER
                && nextColumnIndex >= 0
                && nextColumnIndex < BOARD_SIZE_WITH_BORDER
                && board[nextRowIndex][nextColumnIndex] == 0) {

                int onwardMovesCount = countOnwardMoves(nextRowIndex, nextColumnIndex);
                validMoves.add(new int[] {nextRowIndex, nextColumnIndex, onwardMovesCount});
            }
        }
        return validMoves;
    }

    /**
     * Counts the number of possible valid moves for a knight from a given position.
     *
     * @param rowIndex    The row of the current position
     * @param columnIndex The column of the current position
     * @return The number of valid neighboring moves
     */
    static int countOnwardMoves(int rowIndex, int columnIndex) {
        int onwardMovesCount = 0;

        for (int[] moveOffset : KNIGHT_MOVE_OFFSETS) {
            int offsetX = moveOffset[0];
            int offsetY = moveOffset[1];
            int nextRowIndex = rowIndex + offsetY;
            int nextColumnIndex = columnIndex + offsetX;

            if (nextRowIndex >= 0
                && nextRowIndex < BOARD_SIZE_WITH_BORDER
                && nextColumnIndex >= 0
                && nextColumnIndex < BOARD_SIZE_WITH_BORDER
                && board[nextRowIndex][nextColumnIndex] == 0) {

                onwardMovesCount++;
            }
        }
        return onwardMovesCount;
    }

    /**
     * Detects if moving to a given position will create an orphan (a position with no further valid moves).
     *
     * @param moveNumber  The current move number
     * @param rowIndex    The row of the current position
     * @param columnIndex The column of the current position
     * @return True if an orphan is detected, False otherwise
     */
    static boolean createsOrphan(int moveNumber, int rowIndex, int columnIndex) {
        if (moveNumber < totalCellsToVisit - 1) {
            List<int[]> validMoves = getValidMoves(rowIndex, columnIndex);
            for (int[] move : validMoves) {
                int candidateRowIndex = move[0];
                int candidateColumnIndex = move[1];
                if (countOnwardMoves(candidateRowIndex, candidateColumnIndex) == 0) {
                    return true;
                }
            }
        }
        return false;
    }
}