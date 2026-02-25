package com.thealgorithms.backtracking;

import java.util.ArrayList;
import java.util.List;

public final class NQueens {

    private NQueens() {
        // Utility class; prevent instantiation
    }

    public static List<List<String>> getNQueensArrangements(int boardSize) {
        List<List<String>> arrangements = new ArrayList<>();
        int[] queenPositions = new int[boardSize];
        solve(boardSize, arrangements, queenPositions, 0);
        return arrangements;
    }

    public static void placeQueens(final int boardSize) {
        List<List<String>> arrangements = getNQueensArrangements(boardSize);

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

    private static void solve(
        int boardSize,
        List<List<String>> solutions,
        int[] queenPositions,
        int columnIndex
    ) {
        if (columnIndex == boardSize) {
            solutions.add(buildBoardRepresentation(boardSize, queenPositions));
            return;
        }

        for (int rowIndex = 0; rowIndex < boardSize; rowIndex++) {
            queenPositions[columnIndex] = rowIndex;
            if (isSafe(queenPositions, rowIndex, columnIndex)) {
                solve(boardSize, solutions, queenPositions, columnIndex + 1);
            }
        }
    }

    private static List<String> buildBoardRepresentation(int boardSize, int[] queenPositions) {
        List<String> board = new ArrayList<>(boardSize);
        for (int row = 0; row < boardSize; row++) {
            StringBuilder rowBuilder = new StringBuilder(boardSize);
            for (int col = 0; col < boardSize; col++) {
                rowBuilder.append(col == queenPositions[row] ? 'Q' : '.');
            }
            board.add(rowBuilder.toString());
        }
        return board;
    }

    private static boolean isSafe(int[] queenPositions, int rowIndex, int columnIndex) {
        for (int previousColumn = 0; previousColumn < columnIndex; previousColumn++) {
            int rowDifference = Math.abs(queenPositions[previousColumn] - rowIndex);
            int columnDifference = columnIndex - previousColumn;

            if (rowDifference == 0 || rowDifference == columnDifference) {
                return false;
            }
        }
        return true;
    }
}