package com.thealgorithms.backtracking;

import java.util.ArrayList;
import java.util.List;

/**
 * Solves the N-Queens problem using backtracking.
 *
 * The N-Queens problem is to place N queens on an N×N chessboard such that
 * no two queens attack each other (no two queens share the same row, column,
 * or diagonal).
 */
public final class Class1 {

    private Class1() {
        // Prevent instantiation
    }

    /**
     * Returns all valid arrangements for placing {@code n} queens on an
     * {@code n x n} chessboard.
     *
     * Each arrangement is represented as a list of strings, where each string
     * represents a row of the board. A 'Q' denotes a queen and '.' denotes
     * an empty square.
     *
     * @param n the size of the board and the number of queens
     * @return list of all valid board configurations
     */
    public static List<List<String>> method1(int n) {
        List<List<String>> solutions = new ArrayList<>();
        backtrack(n, solutions, new int[n], 0);
        return solutions;
    }

    /**
     * Computes and prints all valid arrangements for placing {@code n} queens
     * on an {@code n x n} chessboard.
     *
     * @param n the size of the board and the number of queens
     */
    public static void method2(final int n) {
        List<List<String>> solutions = new ArrayList<>();
        backtrack(n, solutions, new int[n], 0);

        if (solutions.isEmpty()) {
            System.out.println("There is no way to place " + n + " queens on board of size " + n + "x" + n);
        } else {
            System.out.println("Arrangement for placing " + n + " queens");
        }

        for (List<String> board : solutions) {
            board.forEach(System.out::println);
            System.out.println();
        }
    }

    /**
     * Backtracking helper to generate all valid N-Queens configurations.
     *
     * @param n          board size / number of queens
     * @param solutions  accumulator for valid board configurations
     * @param positions  positions[row] = column index of the queen in that row
     * @param row        current row to place a queen
     */
    private static void backtrack(int n, List<List<String>> solutions, int[] positions, int row) {
        if (row == n) {
            solutions.add(buildBoard(positions, n));
            return;
        }

        for (int col = 0; col < n; col++) {
            positions[row] = col;
            if (isSafe(positions, col, row)) {
                backtrack(n, solutions, positions, row + 1);
            }
        }
    }

    /**
     * Builds a board representation from the positions array.
     *
     * @param positions positions[row] = column index of the queen in that row
     * @param n         board size
     * @return board representation as a list of strings
     */
    private static List<String> buildBoard(int[] positions, int n) {
        List<String> board = new ArrayList<>();
        for (int r = 0; r < n; r++) {
            StringBuilder rowBuilder = new StringBuilder();
            for (int c = 0; c < n; c++) {
                rowBuilder.append(c == positions[r] ? 'Q' : '.');
            }
            board.add(rowBuilder.toString());
        }
        return board;
    }

    /**
     * Checks whether placing a queen at (row, col) is safe with respect to
     * previously placed queens.
     *
     * @param positions positions[r] = column index of the queen in row r
     * @param col       column index for the current row
     * @param row       current row index
     * @return {@code true} if no conflicts occur; {@code false} otherwise
     */
    private static boolean isSafe(int[] positions, int col, int row) {
        for (int r = 0; r < row; r++) {
            int colDiff = Math.abs(positions[r] - col);
            if (colDiff == 0 || row - r == colDiff) {
                return false;
            }
        }
        return true;
    }
}