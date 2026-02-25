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
            System.out.println(
                "There is no way to place " + boardSize + " queens on board of size " + boardSize + "x" + boardSize
            );
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
     * @param boardSize     size of chess board
     * @param arrangements  holds all possible arrangements
     * @param queenPositions queenPositions[column] = row where queen is placed in that column
     * @param currentColumn current column in which a queen is being placed
     */
    private static void solveNQueens(
        int boardSize,
        List<List<String>> arrangements,
        int[] queenPositions,
        int currentColumn
    ) {
        if (currentColumn == boardSize) {
            List<String> boardRepresentation = new ArrayList<>();
            for (int row = 0; row < boardSize; row++) {
                StringBuilder rowBuilder = new StringBuilder();
                for (int column = 0; column < boardSize; column++) {
                    rowBuilder.append(column == queenPositions[row] ? "Q" : ".");
                }
                boardRepresentation.add(rowBuilder.toString());
            }
            arrangements.add(boardRepresentation);
            return;
        }

        for (int row = 0; row < boardSize; row++) {
            queenPositions[currentColumn] = row;
            if (isSafePosition(queenPositions, row, currentColumn)) {
                solveNQueens(boardSize, arrangements, queenPositions, currentColumn + 1);
            }
        }
    }

    /**
     * Checks if a queen can be placed at (row, column) safely.
     *
     * @param queenPositions queenPositions[column] = row where queen is placed in that column
     * @param row            row in which queen has to be placed
     * @param column         column in which queen is being placed
     * @return true if queen can be placed safely, false otherwise
     */
    private static boolean isSafePosition(int[] queenPositions, int row, int column) {
        for (int previousColumn = 0; previousColumn < column; previousColumn++) {
            int rowDifference = Math.abs(queenPositions[previousColumn] - row);
            int columnDifference = column - previousColumn;

            if (rowDifference == 0 || columnDifference == rowDifference) {
                return false;
            }
        }
        return true;
    }
}