package com.thealgorithms.backtracking;

import java.util.ArrayList;
import java.util.List;

/**
 * Problem statement: Given an N x N chess board, return all arrangements in
 * which N queens can be placed on the board such that no two queens attack each
 * other.
 */
public final class NQueens {

    private NQueens() {
        // Utility class
    }

    public static List<List<String>> getNQueensArrangements(int boardSize) {
        List<List<String>> arrangements = new ArrayList<>();
        int[] queenRowsByColumn = new int[boardSize];
        solveNQueens(boardSize, 0, queenRowsByColumn, arrangements);
        return arrangements;
    }

    public static void placeQueens(final int boardSize) {
        List<List<String>> arrangements = getNQueensArrangements(boardSize);

        if (arrangements.isEmpty()) {
            System.out.printf(
                "There is no way to place %d queens on a board of size %dx%d%n",
                boardSize,
                boardSize,
                boardSize
            );
            return;
        }

        System.out.printf("Arrangements for placing %d queens:%n", boardSize);

        for (List<String> arrangement : arrangements) {
            arrangement.forEach(System.out::println);
            System.out.println();
        }
    }

    /**
     * Backtracking function which tries to place queens column by column.
     *
     * @param boardSize         size of chess board (N)
     * @param columnIndex       current column to place a queen
     * @param queenRowsByColumn queenRowsByColumn[c] = row index where queen is placed in column c
     * @param solutions         list to collect all valid arrangements
     */
    private static void solveNQueens(
        int boardSize,
        int columnIndex,
        int[] queenRowsByColumn,
        List<List<String>> solutions
    ) {
        if (columnIndex == boardSize) {
            solutions.add(buildBoard(boardSize, queenRowsByColumn));
            return;
        }

        for (int rowIndex = 0; rowIndex < boardSize; rowIndex++) {
            if (isSafePosition(queenRowsByColumn, rowIndex, columnIndex)) {
                queenRowsByColumn[columnIndex] = rowIndex;
                solveNQueens(boardSize, columnIndex + 1, queenRowsByColumn, solutions);
            }
        }
    }

    /**
     * Builds a board representation from the queenRowsByColumn array.
     */
    private static List<String> buildBoard(int boardSize, int[] queenRowsByColumn) {
        List<String> board = new ArrayList<>(boardSize);

        for (int rowIndex = 0; rowIndex < boardSize; rowIndex++) {
            StringBuilder rowBuilder = new StringBuilder(boardSize);
            for (int columnIndex = 0; columnIndex < boardSize; columnIndex++) {
                rowBuilder.append(queenRowsByColumn[columnIndex] == rowIndex ? 'Q' : '.');
            }
            board.add(rowBuilder.toString());
        }

        return board;
    }

    /**
     * Checks if a queen can be placed at (rowIndex, columnIndex) safely.
     *
     * @param queenRowsByColumn queenRowsByColumn[c] = row index where queen is placed in column c
     * @param rowIndex          row in which queen has to be placed
     * @param columnIndex       column in which queen is being placed
     * @return true if queen can be placed safely, false otherwise
     */
    private static boolean isSafePosition(int[] queenRowsByColumn, int rowIndex, int columnIndex) {
        for (int col = 0; col < columnIndex; col++) {
            int placedRow = queenRowsByColumn[col];

            if (placedRow == rowIndex) {
                return false;
            }

            int columnDistance = columnIndex - col;
            int rowDistance = Math.abs(placedRow - rowIndex);

            if (rowDistance == columnDistance) {
                return false;
            }
        }
        return true;
    }
}