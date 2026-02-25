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

    // The size of the chess board (12x12 grid, with 2 extra rows/columns as a buffer around an 8x8 area)
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

        for (int row = 0; row < BOARD_SIZE_WITH_BORDER; row++) {
            for (int column = 0; column < BOARD_SIZE_WITH_BORDER; column++) {
                if (row < 2
                    || row > BOARD_SIZE_WITH_BORDER - 3
                    || column < 2
                    || column > BOARD_SIZE_WITH_BORDER - 3) {
                    board[row][column] = -1; // Mark boundary cells
                }
            }
        }
    }

    /**
     * Recursive method to solve the Knight's Tour problem.
     *
     * @param currentRow        The current row of the knight
     * @param currentColumn     The current column of the knight
     * @param currentMoveNumber The current move number
     * @return True if a solution is found, False otherwise
     */
    static boolean solve(int currentRow, int currentColumn, int currentMoveNumber) {
        if (currentMoveNumber > totalCellsToVisit) {
            return true;
        }

        List<int[]> candidateMoves = getValidMoves(currentRow, currentColumn);

        if (candidateMoves.isEmpty() && currentMoveNumber != totalCellsToVisit) {
            return false;
        }

        // Sort neighbors by Warnsdorff's rule (fewest onward moves)
        candidateMoves.sort(Comparator.comparingInt(move -> move[2]));

        for (int[] move : candidateMoves) {
            int nextRow = move[0];
            int nextColumn = move[1];
            board[nextRow][nextColumn] = currentMoveNumber;

            if (!createsOrphan(currentMoveNumber, nextRow, nextColumn)
                && solve(nextRow, nextColumn, currentMoveNumber + 1)) {
                return true;
            }

            board[nextRow][nextColumn] = 0; // Backtrack
        }

        return false;
    }

    /**
     * Returns a list of valid neighboring cells where the knight can move.
     *
     * @param currentRow    The current row of the knight
     * @param currentColumn The current column of the knight
     * @return A list of arrays representing valid moves, where each array contains:
     *         {nextRow, nextColumn, numberOfPossibleNextMoves}
     */
    static List<int[]> getValidMoves(int currentRow, int currentColumn) {
        List<int[]> validMoves = new ArrayList<>();

        for (int[] moveOffset : KNIGHT_MOVE_OFFSETS) {
            int columnOffset = moveOffset[0];
            int rowOffset = moveOffset[1];
            int nextRow = currentRow + rowOffset;
            int nextColumn = currentColumn + columnOffset;

            if (nextRow >= 0
                && nextRow < BOARD_SIZE_WITH_BORDER
                && nextColumn >= 0
                && nextColumn < BOARD_SIZE_WITH_BORDER
                && board[nextRow][nextColumn] == 0) {

                int onwardMovesCount = countOnwardMoves(nextRow, nextColumn);
                validMoves.add(new int[] {nextRow, nextColumn, onwardMovesCount});
            }
        }
        return validMoves;
    }

    /**
     * Counts the number of possible valid moves for a knight from a given position.
     *
     * @param currentRow    The row of the current position
     * @param currentColumn The column of the current position
     * @return The number of valid neighboring moves
     */
    static int countOnwardMoves(int currentRow, int currentColumn) {
        int onwardMovesCount = 0;

        for (int[] moveOffset : KNIGHT_MOVE_OFFSETS) {
            int columnOffset = moveOffset[0];
            int rowOffset = moveOffset[1];
            int nextRow = currentRow + rowOffset;
            int nextColumn = currentColumn + columnOffset;

            if (nextRow >= 0
                && nextRow < BOARD_SIZE_WITH_BORDER
                && nextColumn >= 0
                && nextColumn < BOARD_SIZE_WITH_BORDER
                && board[nextRow][nextColumn] == 0) {

                onwardMovesCount++;
            }
        }
        return onwardMovesCount;
    }

    /**
     * Detects if moving to a given position will create an orphan (a position with no further valid moves).
     *
     * @param currentMoveNumber The current move number
     * @param currentRow        The row of the current position
     * @param currentColumn     The column of the current position
     * @return True if an orphan is detected, False otherwise
     */
    static boolean createsOrphan(int currentMoveNumber, int currentRow, int currentColumn) {
        if (currentMoveNumber < totalCellsToVisit - 1) {
            List<int[]> validMoves = getValidMoves(currentRow, currentColumn);
            for (int[] move : validMoves) {
                int candidateRow = move[0];
                int candidateColumn = move[1];
                if (countOnwardMoves(candidateRow, candidateColumn) == 0) {
                    return true;
                }
            }
        }
        return false;
    }
}