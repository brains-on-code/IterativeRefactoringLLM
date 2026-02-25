package com.thealgorithms.backtracking;

import java.util.ArrayList;
import java.util.List;

/**
 * N-Queens solver.
 *
 * Provides methods to compute and print all valid arrangements of N queens
 * on an N×N chessboard such that no two queens attack each other.
 */
public final class Class1 {

    private Class1() {
        // Utility class; prevent instantiation
    }

    /**
     * Returns all valid N-Queens arrangements for a board of the given size.
     *
     * @param boardSize the size of the board (N)
     * @return a list of solutions, where each solution is a list of strings
     *         representing the board rows; 'Q' for a queen, '.' for empty
     */
    public static List<List<String>> method1(int boardSize) {
        List<List<String>> solutions = new ArrayList<>();
        solveNQueens(boardSize, solutions, new int[boardSize], 0);
        return solutions;
    }

    /**
     * Computes and prints all valid N-Queens arrangements for a board
     * of the given size.
     *
     * @param boardSize the size of the board (N)
     */
    public static void method2(final int boardSize) {
        List<List<String>> solutions = new ArrayList<>();
        solveNQueens(boardSize, solutions, new int[boardSize], 0);

        if (solutions.isEmpty()) {
            System.out.println(
                "There is no way to place " + boardSize + " queens on board of size " + boardSize + "x" + boardSize
            );
        } else {
            System.out.println("Arrangement for placing " + boardSize + " queens");
        }

        for (List<String> solution : solutions) {
            solution.forEach(System.out::println);
            System.out.println();
        }
    }

    /**
     * Backtracking solver for the N-Queens problem.
     *
     * @param boardSize size of the board (N)
     * @param solutions accumulator for all valid solutions
     * @param queenPositions queenPositions[row] = column index of the queen in that row
     * @param currentRow the row currently being processed
     */
    private static void solveNQueens(
        int boardSize,
        List<List<String>> solutions,
        int[] queenPositions,
        int currentRow
    ) {
        if (currentRow == boardSize) {
            solutions.add(buildBoard(boardSize, queenPositions));
            return;
        }

        for (int column = 0; column < boardSize; column++) {
            queenPositions[currentRow] = column;
            if (isSafe(queenPositions, column, currentRow)) {
                solveNQueens(boardSize, solutions, queenPositions, currentRow + 1);
            }
        }
    }

    /**
     * Builds a board representation from queen positions.
     *
     * @param boardSize size of the board (N)
     * @param queenPositions queenPositions[row] = column index of the queen in that row
     * @return a list of strings representing the board
     */
    private static List<String> buildBoard(int boardSize, int[] queenPositions) {
        List<String> board = new ArrayList<>();
        for (int row = 0; row < boardSize; row++) {
            StringBuilder rowBuilder = new StringBuilder();
            for (int column = 0; column < boardSize; column++) {
                rowBuilder.append(column == queenPositions[row] ? 'Q' : '.');
            }
            board.add(rowBuilder.toString());
        }
        return board;
    }

    /**
     * Checks whether placing a queen at (currentRow, currentColumn) is safe
     * given the existing queen positions.
     *
     * @param queenPositions queenPositions[row] = column index of the queen in that row
     * @param currentColumn column index of the queen to place
     * @param currentRow row index of the queen to place
     * @return true if the position is safe, false otherwise
     */
    private static boolean isSafe(int[] queenPositions, int currentColumn, int currentRow) {
        for (int row = 0; row < currentRow; row++) {
            int columnDifference = Math.abs(queenPositions[row] - currentColumn);
            int rowDifference = currentRow - row;

            // Same column or same diagonal
            if (columnDifference == 0 || columnDifference == rowDifference) {
                return false;
            }
        }
        return true;
    }
}