package com.thealgorithms.backtracking;

import java.util.ArrayList;
import java.util.List;

public final class NQueens {

    private NQueens() {
        // Utility class; prevent instantiation
    }

    public static List<List<String>> getNQueensArrangements(int boardSize) {
        List<List<String>> arrangements = new ArrayList<>();
        int[] queenPositionsByColumn = new int[boardSize];
        solve(boardSize, arrangements, queenPositionsByColumn, 0);
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
        List<List<String>> solutions,
        int[] queenPositionsByColumn,
        int currentColumn
    ) {
        if (currentColumn == boardSize) {
            solutions.add(buildBoardRepresentation(boardSize, queenPositionsByColumn));
            return;
        }

        for (int row = 0; row < boardSize; row++) {
            if (isSafe(queenPositionsByColumn, row, currentColumn)) {
                queenPositionsByColumn[currentColumn] = row;
                solve(boardSize, solutions, queenPositionsByColumn, currentColumn + 1);
            }
        }
    }

    private static List<String> buildBoardRepresentation(int boardSize, int[] queenPositionsByColumn) {
        List<String> board = new ArrayList<>(boardSize);

        for (int row = 0; row < boardSize; row++) {
            StringBuilder rowBuilder = new StringBuilder(boardSize);
            int queenColumn = queenPositionsByColumn[row];

            for (int col = 0; col < boardSize; col++) {
                rowBuilder.append(col == queenColumn ? 'Q' : '.');
            }

            board.add(rowBuilder.toString());
        }

        return board;
    }

    private static boolean isSafe(int[] queenPositionsByColumn, int row, int column) {
        for (int previousColumn = 0; previousColumn < column; previousColumn++) {
            int previousRow = queenPositionsByColumn[previousColumn];
            int rowDifference = Math.abs(previousRow - row);
            int columnDifference = column - previousColumn;

            if (rowDifference == 0 || rowDifference == columnDifference) {
                return false;
            }
        }
        return true;
    }
}