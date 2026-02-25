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
            for (int col = 0; col < BOARD_SIZE_WITH_BORDER; col++) {
                if (row < 2 || row > BOARD_SIZE_WITH_BORDER - 3 || col < 2 || col > BOARD_SIZE_WITH_BORDER - 3) {
                    board[row][col] = -1; // Mark boundary cells
                }
            }
        }
    }

    /**
     * Recursive method to solve the Knight's Tour problem.
     *
     * @param row    The current row of the knight
     * @param column The current column of the knight
     * @param moveNumber The current move number
     * @return True if a solution is found, False otherwise
     */
    static boolean solve(int row, int column, int moveNumber) {
        if (moveNumber > totalCellsToVisit) {
            return true;
        }

        List<int[]> validMoves = getValidMoves(row, column);

        if (validMoves.isEmpty() && moveNumber != totalCellsToVisit) {
            return false;
        }

        // Sort neighbors by Warnsdorff's rule (fewest onward moves)
        validMoves.sort(Comparator.comparingInt(move -> move[2]));

        for (int[] move : validMoves) {
            int nextRow = move[0];
            int nextColumn = move[1];
            board[nextRow][nextColumn] = moveNumber;
            if (!createsOrphan(moveNumber, nextRow, nextColumn) && solve(nextRow, nextColumn, moveNumber + 1)) {
                return true;
            }
            board[nextRow][nextColumn] = 0; // Backtrack
        }

        return false;
    }

    /**
     * Returns a list of valid neighboring cells where the knight can move.
     *
     * @param row    The current row of the knight
     * @param column The current column of the knight
     * @return A list of arrays representing valid moves, where each array contains:
     *         {nextRow, nextColumn, numberOfPossibleNextMoves}
     */
    static List<int[]> getValidMoves(int row, int column) {
        List<int[]> validMoves = new ArrayList<>();

        for (int[] move : KNIGHT_MOVES) {
            int deltaX = move[0];
            int deltaY = move[1];
            int nextRow = row + deltaY;
            int nextColumn = column + deltaX;

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
     * @param row    The row of the current position
     * @param column The column of the current position
     * @return The number of valid neighboring moves
     */
    static int countOnwardMoves(int row, int column) {
        int onwardMovesCount = 0;
        for (int[] move : KNIGHT_MOVES) {
            int deltaX = move[0];
            int deltaY = move[1];
            int nextRow = row + deltaY;
            int nextColumn = column + deltaX;

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
     * @param moveNumber The current move number
     * @param row        The row of the current position
     * @param column     The column of the current position
     * @return True if an orphan is detected, False otherwise
     */
    static boolean createsOrphan(int moveNumber, int row, int column) {
        if (moveNumber < totalCellsToVisit - 1) {
            List<int[]> validMoves = getValidMoves(row, column);
            for (int[] move : validMoves) {
                if (countOnwardMoves(move[0], move[1]) == 0) {
                    return true;
                }
            }
        }
        return false;
    }
}