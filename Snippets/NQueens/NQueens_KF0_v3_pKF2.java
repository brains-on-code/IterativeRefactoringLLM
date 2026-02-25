package com.thealgorithms.backtracking;

import java.util.ArrayList;
import java.util.List;

/**
 * Solves the N-Queens problem: place N queens on an N×N chessboard so that
 * no two queens attack each other. Returns all valid board arrangements.
 *
 * Each arrangement is represented as a list of strings, where:
 * - 'Q' represents a queen
 * - '.' represents an empty square
 */
public final class NQueens {

    private NQueens() {
        // Utility class; prevent instantiation
    }

    /**
     * Returns all valid N-Queens arrangements for a board of the given size.
     *
     * @param boardSize the size of the board (and number of queens)
     * @return list of all valid arrangements
     */
    public static List<List<String>> getNQueensArrangements(int boardSize) {
        List<List<String>> arrangements = new ArrayList<>();
        solve(boardSize, arrangements, new int[boardSize], 0);
        return arrangements;
    }

    /**
     * Prints all valid N-Queens arrangements for a board of the given size.
     *
     * @param boardSize the size of the board (and number of queens)
     */
    public static void placeQueens(final int boardSize) {
        List<List<String>> arrangements = new ArrayList<>();
        solve(boardSize, arrangements, new int[boardSize], 0);

        if (arrangements.isEmpty()) {
            System.out.println(
                "There is no way to place " + boardSize + " queens on a board of size " + boardSize + "x" + boardSize
            );
        } else {
            System.out.println("Arrangements for placing " + boardSize + " queens:");
        }

        for (List<String> arrangement : arrangements) {
            arrangement.forEach(System.out::println);
            System.out.println();
        }
    }

    /**
     * Backtracking solver that tries to place queens column by column.
     *
     * @param boardSize   size of the chessboard (and number of queens)
     * @param solutions   accumulator for all valid arrangements
     * @param queenRows   queenRows[c] = row index where the queen is placed in column c
     * @param columnIndex current column to place a queen in
     */
    private static void solve(int boardSize, List<List<String>> solutions, int[] queenRows, int columnIndex) {
        if (columnIndex == boardSize) {
            solutions.add(buildBoard(queenRows, boardSize));
            return;
        }

        for (int rowIndex = 0; rowIndex < boardSize; rowIndex++) {
            queenRows[columnIndex] = rowIndex;
            if (canPlace(queenRows, rowIndex, columnIndex)) {
                solve(boardSize, solutions, queenRows, columnIndex + 1);
            }
        }
    }

    /**
     * Builds a board representation from the queenRows array.
     *
     * @param queenRows queenRows[c] = row index where the queen is placed in column c
     * @param boardSize size of the board
     * @return list of strings representing the board
     */
    private static List<String> buildBoard(int[] queenRows, int boardSize) {
        List<String> board = new ArrayList<>();
        for (int col = 0; col < boardSize; col++) {
            StringBuilder row = new StringBuilder(boardSize);
            for (int rowIndex = 0; rowIndex < boardSize; rowIndex++) {
                row.append(rowIndex == queenRows[col] ? 'Q' : '.');
            }
            board.add(row.toString());
        }
        return board;
    }

    /**
     * Checks whether a queen can be safely placed at (rowIndex, columnIndex).
     *
     * @param queenRows   queenRows[c] = row index where the queen is placed in column c
     * @param rowIndex    row to place the queen in
     * @param columnIndex column to place the queen in
     * @return true if the queen can be placed safely; false otherwise
     */
    private static boolean canPlace(int[] queenRows, int rowIndex, int columnIndex) {
        for (int col = 0; col < columnIndex; col++) {
            int rowDiff = Math.abs(queenRows[col] - rowIndex);
            int colDiff = columnIndex - col;

            // Conflict if in the same row or on the same diagonal
            if (rowDiff == 0 || rowDiff == colDiff) {
                return false;
            }
        }
        return true;
    }
}