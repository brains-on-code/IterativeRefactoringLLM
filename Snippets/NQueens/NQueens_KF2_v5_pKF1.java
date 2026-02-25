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

    private static void solveNQueens(
        int boardSize,
        List<List<String>> solutions,
        int[] queenRowByColumn,
        int currentColumn
    ) {
        if (currentColumn == boardSize) {
            solutions.add(buildBoardRepresentation(boardSize, queenRowByColumn));
            return;
        }

        for (int candidateRow = 0; candidateRow < boardSize; candidateRow++) {
            queenRowByColumn[currentColumn] = candidateRow;
            if (isSafePlacement(queenRowByColumn, candidateRow, currentColumn)) {
                solveNQueens(boardSize, solutions, queenRowByColumn, currentColumn + 1);
            }
        }
    }

    private static List<String> buildBoardRepresentation(int boardSize, int[] queenRowByColumn) {
        List<String> boardRepresentation = new ArrayList<>();
        for (int columnIndex = 0; columnIndex < boardSize; columnIndex++) {
            StringBuilder rowBuilder = new StringBuilder();
            for (int rowIndex = 0; rowIndex < boardSize; rowIndex++) {
                rowBuilder.append(rowIndex == queenRowByColumn[columnIndex] ? "Q" : ".");
            }
            boardRepresentation.add(rowBuilder.toString());
        }
        return boardRepresentation;
    }

    private static boolean isSafePlacement(
        int[] queenRowByColumn,
        int candidateRow,
        int candidateColumn
    ) {
        for (int previousColumn = 0; previousColumn < candidateColumn; previousColumn++) {
            int previousRow = queenRowByColumn[previousColumn];
            int rowDifference = Math.abs(previousRow - candidateRow);
            int columnDifference = candidateColumn - previousColumn;

            if (rowDifference == 0 || rowDifference == columnDifference) {
                return false;
            }
        }
        return true;
    }
}