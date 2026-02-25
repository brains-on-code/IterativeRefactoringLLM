package com.thealgorithms.backtracking;

import java.util.ArrayList;
import java.util.List;

public final class NQueens {

    private NQueens() {
        // Utility class; prevent instantiation
    }

    public static List<List<String>> getNQueensArrangements(int boardSize) {
        List<List<String>> arrangements = new ArrayList<>();
        int[] queenRowByColumn = new int[boardSize];
        solve(boardSize, 0, queenRowByColumn, arrangements);
        return arrangements;
    }

    public static void placeQueens(final int boardSize) {
        List<List<String>> arrangements = getNQueensArrangements(boardSize);

        if (arrangements.isEmpty()) {
            System.out.printf(
                "There is no way to place %d queens on a board of size %dx%d%n",
                boardSize, boardSize, boardSize
            );
            return;
        }

        System.out.printf("Arrangements for placing %d queens:%n%n", boardSize);

        for (List<String> arrangement : arrangements) {
            arrangement.forEach(System.out::println);
            System.out.println();
        }
    }

    private static void solve(
        int boardSize,
        int currentColumn,
        int[] queenRowByColumn,
        List<List<String>> solutions
    ) {
        if (currentColumn == boardSize) {
            solutions.add(buildBoardRepresentation(boardSize, queenRowByColumn));
            return;
        }

        for (int row = 0; row < boardSize; row++) {
            if (isSafe(queenRowByColumn, row, currentColumn)) {
                queenRowByColumn[currentColumn] = row;
                solve(boardSize, currentColumn + 1, queenRowByColumn, solutions);
            }
        }
    }

    private static List<String> buildBoardRepresentation(int boardSize, int[] queenRowByColumn) {
        List<String> board = new ArrayList<>(boardSize);

        for (int row = 0; row < boardSize; row++) {
            StringBuilder rowBuilder = new StringBuilder(boardSize);
            int queenRow = queenRowByColumn[row];

            for (int col = 0; col < boardSize; col++) {
                rowBuilder.append(col == queenRow ? 'Q' : '.');
            }

            board.add(rowBuilder.toString());
        }

        return board;
    }

    private static boolean isSafe(int[] queenRowByColumn, int row, int column) {
        for (int previousColumn = 0; previousColumn < column; previousColumn++) {
            int previousRow = queenRowByColumn[previousColumn];

            if (previousRow == row) {
                return false;
            }

            int rowDifference = Math.abs(previousRow - row);
            int columnDifference = column - previousColumn;

            if (rowDifference == columnDifference) {
                return false;
            }
        }
        return true;
    }
}