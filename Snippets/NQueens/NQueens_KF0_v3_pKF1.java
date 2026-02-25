package com.thealgorithms.backtracking;

import java.util.ArrayList;
import java.util.List;

public final class NQueens {

    private NQueens() {
    }

    public static List<List<String>> getNQueensArrangements(int boardSize) {
        List<List<String>> allArrangements = new ArrayList<>();
        int[] queenRowForColumn = new int[boardSize];
        solveNQueens(boardSize, allArrangements, queenRowForColumn, 0);
        return allArrangements;
    }

    public static void placeQueens(final int boardSize) {
        List<List<String>> allArrangements = new ArrayList<>();
        int[] queenRowForColumn = new int[boardSize];
        solveNQueens(boardSize, allArrangements, queenRowForColumn, 0);

        if (allArrangements.isEmpty()) {
            System.out.println(
                "There is no way to place " + boardSize + " queens on board of size " + boardSize + "x" + boardSize
            );
        } else {
            System.out.println("Arrangement for placing " + boardSize + " queens");
        }

        for (List<String> arrangement : allArrangements) {
            arrangement.forEach(System.out::println);
            System.out.println();
        }
    }

    /**
     * Backtracking function which tries to place queens recursively.
     *
     * @param boardSize          size of chess board
     * @param allArrangements    holds all possible arrangements
     * @param queenRowForColumn  queenRowForColumn[column] = row where queen is placed in that column
     * @param currentColumnIndex current column in which a queen is being placed
     */
    private static void solveNQueens(
        int boardSize,
        List<List<String>> allArrangements,
        int[] queenRowForColumn,
        int currentColumnIndex
    ) {
        if (currentColumnIndex == boardSize) {
            List<String> boardRepresentation = new ArrayList<>();
            for (int rowIndex = 0; rowIndex < boardSize; rowIndex++) {
                StringBuilder rowBuilder = new StringBuilder();
                for (int columnIndex = 0; columnIndex < boardSize; columnIndex++) {
                    rowBuilder.append(queenRowForColumn[columnIndex] == rowIndex ? "Q" : ".");
                }
                boardRepresentation.add(rowBuilder.toString());
            }
            allArrangements.add(boardRepresentation);
            return;
        }

        for (int rowIndex = 0; rowIndex < boardSize; rowIndex++) {
            queenRowForColumn[currentColumnIndex] = rowIndex;
            if (isSafePosition(queenRowForColumn, rowIndex, currentColumnIndex)) {
                solveNQueens(boardSize, allArrangements, queenRowForColumn, currentColumnIndex + 1);
            }
        }
    }

    /**
     * Checks if a queen can be placed at (rowIndex, columnIndex) safely.
     *
     * @param queenRowForColumn queenRowForColumn[column] = row where queen is placed in that column
     * @param rowIndex          row in which queen has to be placed
     * @param columnIndex       column in which queen is being placed
     * @return true if queen can be placed safely, false otherwise
     */
    private static boolean isSafePosition(int[] queenRowForColumn, int rowIndex, int columnIndex) {
        for (int previousColumnIndex = 0; previousColumnIndex < columnIndex; previousColumnIndex++) {
            int previousRowIndex = queenRowForColumn[previousColumnIndex];

            boolean isSameRow = previousRowIndex == rowIndex;
            boolean isSameDiagonal =
                Math.abs(previousRowIndex - rowIndex) == Math.abs(previousColumnIndex - columnIndex);

            if (isSameRow || isSameDiagonal) {
                return false;
            }
        }
        return true;
    }
}