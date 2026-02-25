package com.thealgorithms.backtracking;

import java.util.ArrayList;
import java.util.List;

/**
 * Solves the N-Queens problem using backtracking.
 * Each solution is represented as a list of strings, where:
 * - 'Q' represents a queen
 * - '.' represents an empty square
 */
public final class NQueens {

    private NQueens() {
        // Utility class; prevent instantiation
    }

    /**
     * Returns all valid arrangements for placing {@code queens} queens
     * on a {@code queens x queens} chessboard.
     *
     * @param queens the number of queens and the size of the board
     * @return list of all valid board arrangements
     */
    public static List<List<String>> getNQueensArrangements(int queens) {
        List<List<String>> arrangements = new ArrayList<>();
        solve(queens, arrangements, new int[queens], 0);
        return arrangements;
    }

    /**
     * Prints all valid arrangements for placing {@code queens} queens
     * on a {@code queens x queens} chessboard.
     *
     * @param queens the number of queens and the size of the board
     */
    public static void placeQueens(final int queens) {
        List<List<String>> arrangements = new ArrayList<>();
        solve(queens, arrangements, new int[queens], 0);

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
     * Recursively attempts to place queens column by column.
     *
     * @param boardSize  size of the board (and number of queens)
     * @param solutions  accumulator for all valid solutions
     * @param columns    columns[c] = row index of the queen placed in column c
     * @param columnIndex current column to place a queen in
     */
    private static void solve(
        int boardSize,
        List<List<String>> solutions,
        int[] columns,
        int columnIndex
    ) {
        // All queens are placed; construct and store the board representation
        if (columnIndex == boardSize) {
            solutions.add(buildBoard(boardSize, columns));
            return;
        }

        // Try placing a queen in each row of the current column
        for (int rowIndex = 0; rowIndex < boardSize; rowIndex++) {
            columns[columnIndex] = rowIndex;
            if (isSafe(columns, rowIndex, columnIndex)) {
                solve(boardSize, solutions, columns, columnIndex + 1);
            }
        }
    }

    /**
     * Builds a visual representation of the board from the columns array.
     *
     * @param boardSize size of the board
     * @param columns   columns[c] = row index of the queen in column c
     * @return list of strings representing the board
     */
    private static List<String> buildBoard(int boardSize, int[] columns) {
        List<String> board = new ArrayList<>(boardSize);
        for (int row = 0; row < boardSize; row++) {
            StringBuilder rowBuilder = new StringBuilder(boardSize);
            for (int col = 0; col < boardSize; col++) {
                rowBuilder.append(col == columns[row] ? 'Q' : '.');
            }
            board.add(rowBuilder.toString());
        }
        return board;
    }

    /**
     * Checks whether placing a queen at (rowIndex, columnIndex) is safe
     * given the current partial placement in {@code columns}.
     *
     * @param columns     columns[c] = row index of the queen in column c
     * @param rowIndex    row of the queen to place
     * @param columnIndex column of the queen to place
     * @return {@code true} if the queen can be placed safely; {@code false} otherwise
     */
    private static boolean isSafe(int[] columns, int rowIndex, int columnIndex) {
        for (int col = 0; col < columnIndex; col++) {
            int existingRow = columns[col];
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