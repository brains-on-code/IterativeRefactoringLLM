package com.thealgorithms.backtracking;

import java.util.ArrayList;
import java.util.List;

public final class NQueens {
    private NQueens() {
    }

    public static List<List<String>> getNQueensArrangements(int boardSize) {
        List<List<String>> arrangements = new ArrayList<>();
        solveNQueens(boardSize, arrangements, new int[boardSize], 0);
        return arrangements;
    }

    public static void placeQueens(final int boardSize) {
        List<List<String>> arrangements = new ArrayList<>();
        solveNQueens(boardSize, arrangements, new int[boardSize], 0);

        if (arrangements.isEmpty()) {
            System.out.println("There is no way to place " + boardSize + " queens on board of size " + boardSize + "x" + boardSize);
        } else {
            System.out.println("Arrangement for placing " + boardSize + " queens");
        }

        for (List<String> arrangement : arrangements) {
            arrangement.forEach(System.out::println);
            System.out.println();
        }
    }

    /**
     * Backtracking function which tries to place queens recursively.
     *
     * @param boardSize   size of chess board
     * @param arrangements holds all possible arrangements
     * @param queenRows   queenRows[column] = row where queen is placed in that column
     * @param columnIndex current column in which a queen is being placed
     */
    private static void solveNQueens(int boardSize, List<List<String>> arrangements, int[] queenRows, int columnIndex) {
        if (columnIndex == boardSize) {
            List<String> boardRepresentation = new ArrayList<>();
            for (int row = 0; row < boardSize; row++) {
                StringBuilder rowBuilder = new StringBuilder();
                for (int column = 0; column < boardSize; column++) {
                    rowBuilder.append(column == queenRows[row] ? "Q" : ".");
                }
                boardRepresentation.add(rowBuilder.toString());
            }
            arrangements.add(boardRepresentation);
            return;
        }

        for (int rowIndex = 0; rowIndex < boardSize; rowIndex++) {
            queenRows[columnIndex] = rowIndex;
            if (canPlaceQueen(queenRows, rowIndex, columnIndex)) {
                solveNQueens(boardSize, arrangements, queenRows, columnIndex + 1);
            }
        }
    }

    /**
     * Checks if a queen can be placed at (rowIndex, columnIndex) safely.
     *
     * @param queenRows   queenRows[column] = row where queen is placed in that column
     * @param rowIndex    row in which queen has to be placed
     * @param columnIndex column in which queen is being placed
     * @return true if queen can be placed safely, false otherwise
     */
    private static boolean canPlaceQueen(int[] queenRows, int rowIndex, int columnIndex) {
        for (int previousColumn = 0; previousColumn < columnIndex; previousColumn++) {
            int rowDifference = Math.abs(queenRows[previousColumn] - rowIndex);
            int columnDifference = columnIndex - previousColumn;

            if (rowDifference == 0 || columnDifference == rowDifference) {
                return false;
            }
        }
        return true;
    }
}