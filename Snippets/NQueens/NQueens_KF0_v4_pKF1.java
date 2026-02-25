package com.thealgorithms.backtracking;

import java.util.ArrayList;
import java.util.List;

public final class NQueens {

    private NQueens() {
    }

    public static List<List<String>> getNQueensArrangements(int boardSize) {
        List<List<String>> arrangements = new ArrayList<>();
        int[] queenRowByColumn = new int[boardSize];
        solveNQueens(boardSize, arrangements, queenRowByColumn, 0);
        return arrangements;
    }

    public static void placeQueens(final int boardSize) {
        List<List<String>> arrangements = new ArrayList<>();
        int[] queenRowByColumn = new int[boardSize];
        solveNQueens(boardSize, arrangements, queenRowByColumn, 0);

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
     * @param boardSize        size of chess board
     * @param arrangements     holds all possible arrangements
     * @param queenRowByColumn queenRowByColumn[column] = row where queen is placed in that column
     * @param columnIndex      current column in which a queen is being placed
     */
    private static void solveNQueens(
        int boardSize,
        List<List<String>> arrangements,
        int[] queenRowByColumn,
        int columnIndex
    ) {
        if (columnIndex == boardSize) {
            List<String> board = new ArrayList<>();
            for (int row = 0; row < boardSize; row++) {
                StringBuilder rowBuilder = new StringBuilder();
                for (int column = 0; column < boardSize; column++) {
                    rowBuilder.append(queenRowByColumn[column] == row ? "Q" : ".");
                }
                board.add(rowBuilder.toString());
            }
            arrangements.add(board);
            return;
        }

        for (int row = 0; row < boardSize; row++) {
            queenRowByColumn[columnIndex] = row;
            if (isSafePosition(queenRowByColumn, row, columnIndex)) {
                solveNQueens(boardSize, arrangements, queenRowByColumn, columnIndex + 1);
            }
        }
    }

    /**
     * Checks if a queen can be placed at (row, column) safely.
     *
     * @param queenRowByColumn queenRowByColumn[column] = row where queen is placed in that column
     * @param row              row in which queen has to be placed
     * @param column           column in which queen is being placed
     * @return true if queen can be placed safely, false otherwise
     */
    private static boolean isSafePosition(int[] queenRowByColumn, int row, int column) {
        for (int previousColumn = 0; previousColumn < column; previousColumn++) {
            int previousRow = queenRowByColumn[previousColumn];

            boolean isSameRow = previousRow == row;
            boolean isSameDiagonal =
                Math.abs(previousRow - row) == Math.abs(previousColumn - column);

            if (isSameRow || isSameDiagonal) {
                return false;
            }
        }
        return true;
    }
}