package com.thealgorithms.backtracking;

import java.util.ArrayList;
import java.util.List;

/**
 * Backtracking solution to the N-Queens problem.
 *
 * Each solution is represented as a list of strings, where:
 * - 'Q' is a queen
 * - '.' is an empty square
 */
public final class NQueens {

    private NQueens() {
        // Utility class; prevent instantiation
    }

    /**
     * Returns all valid ways to place {@code queens} queens
     * on a {@code queens x queens} chessboard.
     *
     * @param queens number of queens and board dimension
     * @return list of all valid board configurations
     */
    public static List<List<String>> getNQueensArrangements(int queens) {
        List<List<String>> arrangements = new ArrayList<>();
        int[] queenRowByColumn = new int[queens];
        solve(queens, arrangements, queenRowByColumn, 0);
        return arrangements;
    }

    /**
     * Prints all valid ways to place {@code queens} queens
     * on a {@code queens x queens} chessboard.
     *
     * @param queens number of queens and board dimension
     */
    public static void placeQueens(final int queens) {
        List<List<String>> arrangements = new ArrayList<>();
        int[] queenRowByColumn = new int[queens];
        solve(queens, arrangements, queenRowByColumn, 0);

        if (arrangements.isEmpty()) {
            System.out.printf(
                "There is no way to place %d queens on a board of size %dx%d%n",
                queens, queens, queens
            );
        } else {
            System.out.printf("Arrangements for placing %d queens:%n", queens);
        }

        for (List<String> arrangement : arrangements) {
            arrangement.forEach(System.out::println);
            System.out.println();
        }
    }

    /**
     * Recursively places queens column by column.
     *
     * @param boardSize       board dimension (and number of queens)
     * @param solutions       accumulator for all valid solutions
     * @param queenRowByColumn queenRowByColumn[c] = row index of the queen in column c
     * @param columnIndex     column currently being filled
     */
    private static void solve(
        int boardSize,
        List<List<String>> solutions,
        int[] queenRowByColumn,
        int columnIndex
    ) {
        if (columnIndex == boardSize) {
            solutions.add(buildBoard(boardSize, queenRowByColumn));
            return;
        }

        for (int rowIndex = 0; rowIndex < boardSize; rowIndex++) {
            queenRowByColumn[columnIndex] = rowIndex;
            if (isSafe(queenRowByColumn, rowIndex, columnIndex)) {
                solve(boardSize, solutions, queenRowByColumn, columnIndex + 1);
            }
        }
    }

    /**
     * Builds a board representation from the queen positions.
     *
     * @param boardSize       board dimension
     * @param queenRowByColumn queenRowByColumn[c] = row index of the queen in column c
     * @return list of strings representing the board
     */
    private static List<String> buildBoard(int boardSize, int[] queenRowByColumn) {
        List<String> board = new ArrayList<>(boardSize);
        for (int row = 0; row < boardSize; row++) {
            StringBuilder rowBuilder = new StringBuilder(boardSize);
            for (int col = 0; col < boardSize; col++) {
                rowBuilder.append(queenRowByColumn[col] == row ? 'Q' : '.');
            }
            board.add(rowBuilder.toString());
        }
        return board;
    }

    /**
     * Returns whether a queen at (rowIndex, columnIndex) is safe
     * given the current partial placement.
     *
     * @param queenRowByColumn queenRowByColumn[c] = row index of the queen in column c
     * @param rowIndex         candidate row
     * @param columnIndex      candidate column
     * @return {@code true} if the queen can be placed safely; {@code false} otherwise
     */
    private static boolean isSafe(int[] queenRowByColumn, int rowIndex, int columnIndex) {
        for (int col = 0; col < columnIndex; col++) {
            int existingRow = queenRowByColumn[col];
            int rowDiff = Math.abs(existingRow - rowIndex);
            int colDiff = columnIndex - col;

            // Same row or same diagonal
            if (rowDiff == 0 || rowDiff == colDiff) {
                return false;
            }
        }
        return true;
    }
}