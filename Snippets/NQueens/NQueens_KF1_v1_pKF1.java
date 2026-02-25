package com.thealgorithms.backtracking;

import java.util.ArrayList;
import java.util.List;

public final class NQueensSolver {

    private NQueensSolver() {
    }

    public static List<List<String>> solve(int boardSize) {
        List<List<String>> solutions = new ArrayList<>();
        placeQueens(boardSize, solutions, new int[boardSize], 0);
        return solutions;
    }

    public static void printSolutions(final int boardSize) {
        List<List<String>> solutions = new ArrayList<>();
        placeQueens(boardSize, solutions, new int[boardSize], 0);

        if (solutions.isEmpty()) {
            System.out.println("There is no way to place " + boardSize + " queens on board of size " + boardSize + "x" + boardSize);
        } else {
            System.out.println("Arrangement for placing " + boardSize + " queens");
        }

        for (List<String> solution : solutions) {
            solution.forEach(System.out::println);
            System.out.println();
        }
    }

    private static void placeQueens(int boardSize, List<List<String>> solutions, int[] queenPositions, int currentRow) {
        if (currentRow == boardSize) {
            List<String> boardRepresentation = new ArrayList<>();
            for (int row = 0; row < boardSize; row++) {
                StringBuilder rowBuilder = new StringBuilder();
                for (int col = 0; col < boardSize; col++) {
                    rowBuilder.append(col == queenPositions[row] ? "Q" : ".");
                }
                boardRepresentation.add(rowBuilder.toString());
            }
            solutions.add(boardRepresentation);
            return;
        }

        for (int column = 0; column < boardSize; column++) {
            queenPositions[currentRow] = column;
            if (isSafe(queenPositions, column, currentRow)) {
                placeQueens(boardSize, solutions, queenPositions, currentRow + 1);
            }
        }
    }

    private static boolean isSafe(int[] queenPositions, int column, int currentRow) {
        for (int previousRow = 0; previousRow < currentRow; previousRow++) {
            int columnDifference = Math.abs(queenPositions[previousRow] - column);
            if (columnDifference == 0 || currentRow - previousRow == columnDifference) {
                return false;
            }
        }
        return true;
    }
}