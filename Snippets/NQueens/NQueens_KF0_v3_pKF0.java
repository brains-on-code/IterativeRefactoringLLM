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
        placeQueensInColumn(boardSize, arrangements, queenRowsByColumn, 0);
        return arrangements;
    }

    public static void placeQueens(final int boardSize) {
        List<List<String>> arrangements = getNQueensArrangements(boardSize);

        if (arrangements.isEmpty()) {
            System.out.println(
                "There is no way to place " + boardSize + " queens on a board of size " + boardSize + "x" + boardSize
            );
            return;
        }

        System.out.println("Arrangements for placing " + boardSize + " queens:");

        for (List<String> arrangement : arrangements) {
            arrangement.forEach(System.out::println);
            System.out.println();
        }
    }

    /**
     * Backtracking function which tries to place queens column by column.
     *
     * @param boardSize         size of chess board (N)
     * @param solutions         list to collect all valid arrangements
     * @param queenRowsByColumn queenRowsByColumn[c] = row index where queen is placed in column c
     * @param columnIndex       current column to place a queen
     */
    private static void placeQueensInColumn(
        int boardSize,
        List<List<String>> solutions,
        int[] queenRowsByColumn,
        int columnIndex
    ) {
        if (columnIndex == boardSize) {
            solutions.add(buildBoard(boardSize, queenRowsByColumn));
            return;
        }

        for (int rowIndex = 0; rowIndex < boardSize; rowIndex++) {
            if (isSafePosition(queenRowsByColumn, rowIndex, columnIndex)) {
                queenRowsByColumn[columnIndex] = rowIndex;
                placeQueensInColumn(boardSize, solutions, queenRowsByColumn, columnIndex + 1);
            }
        }
    }

    /**
     * Builds a board representation from the queenRowsByColumn array.
     */
    private static List<String> buildBoard(int boardSize, int[] queenRowsByColumn) {
        List<String> board = new ArrayList<>(boardSize);

        for (int row = 0; row < boardSize; row++) {
            StringBuilder rowBuilder = new StringBuilder(boardSize);
            for (int col = 0; col < boardSize; col++) {
                rowBuilder.append(queenRowsByColumn[col] == row ? 'Q' : '.');
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

            boolean sameRow = placedRow == rowIndex;
            boolean sameDiagonal = Math.abs(placedRow - rowIndex) == columnIndex - col;

            if (sameRow || sameDiagonal) {
                return false;
            }
        }
        return true;
    }
}