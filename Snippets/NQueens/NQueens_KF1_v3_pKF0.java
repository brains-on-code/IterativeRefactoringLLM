package com.thealgorithms.backtracking;

import java.util.ArrayList;
import java.util.List;

/**
 * N-Queens solver.
 *
 * Provides methods to compute and print all valid arrangements of N queens
 * on an N×N chessboard such that no two queens attack each other.
 */
public final class NQueensSolver {

    private NQueensSolver() {
        // Utility class; prevent instantiation
    }

    /**
     * Returns all valid N-Queens arrangements for a board of the given size.
     *
     * @param boardSize the size of the board (N)
     * @return a list of solutions, where each solution is a list of strings
     *         representing the board rows; 'Q' for a queen, '.' for empty
     */
    public static List<List<String>> solve(int boardSize) {
        List<List<String>> solutions = new ArrayList<>();
        int[] queenPositions = new int[boardSize];
        solveRecursively(boardSize, solutions, queenPositions, 0);
        return solutions;
    }

    /**
     * Computes and prints all valid N-Queens arrangements for a board
     * of the given size.
     *
     * @param boardSize the size of the board (N)
     */
    public static void printSolutions(final int boardSize) {
        List<List<String>> solutions = solve(boardSize);

        if (solutions.isEmpty()) {
            System.out.printf(
                "There is no way to place %d queens on a board of size %dx%d%n",
                boardSize,
                boardSize,
                boardSize
            );
            return;
        }

        System.out.printf("Arrangements for placing %d queens:%n", boardSize);

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
    private static void solveRecursively(
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
            if (isSafePosition(queenPositions, column, currentRow)) {
                queenPositions[currentRow] = column;
                solveRecursively(boardSize, solutions, queenPositions, currentRow + 1);
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
        List<String> board = new ArrayList<>(boardSize);

        for (int row = 0; row < boardSize; row++) {
            StringBuilder rowBuilder = new StringBuilder(boardSize);
            int queenColumn = queenPositions[row];

            for (int column = 0; column < boardSize; column++) {
                rowBuilder.append(column == queenColumn ? 'Q' : '.');
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
    private static boolean isSafePosition(int[] queenPositions, int currentColumn, int currentRow) {
        for (int row = 0; row < currentRow; row++) {
            int existingColumn = queenPositions[row];

            // Same column
            if (existingColumn == currentColumn) {
                return false;
            }

            // Same diagonal
            int columnDifference = Math.abs(existingColumn - currentColumn);
            int rowDifference = currentRow - row;
            if (columnDifference == rowDifference) {
                return false;
            }
        }
        return true;
    }
}