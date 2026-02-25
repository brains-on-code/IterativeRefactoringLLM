package com.thealgorithms.backtracking;

import java.util.ArrayList;
import java.util.List;

public final class NQueensSolver {

    private NQueensSolver() {
    }

    public static List<List<String>> solve(int boardSize) {
        List<List<String>> solutions = new ArrayList<>();
        solveNQueens(boardSize, solutions, new int[boardSize], 0);
        return solutions;
    }

    public static void printSolutions(final int boardSize) {
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

    private static void solveNQueens(
        int boardSize,
        List<List<String>> solutions,
        int[] queenColumnByRow,
        int currentRow
    ) {
        if (currentRow == boardSize) {
            List<String> boardRepresentation = new ArrayList<>();
            for (int row = 0; row < boardSize; row++) {
                StringBuilder rowBuilder = new StringBuilder();
                for (int column = 0; column < boardSize; column++) {
                    rowBuilder.append(column == queenColumnByRow[row] ? "Q" : ".");
                }
                boardRepresentation.add(rowBuilder.toString());
            }
            solutions.add(boardRepresentation);
            return;
        }

        for (int candidateColumn = 0; candidateColumn < boardSize; candidateColumn++) {
            queenColumnByRow[currentRow] = candidateColumn;
            if (isSafePlacement(queenColumnByRow, candidateColumn, currentRow)) {
                solveNQueens(boardSize, solutions, queenColumnByRow, currentRow + 1);
            }
        }
    }

    private static boolean isSafePlacement(int[] queenColumnByRow, int candidateColumn, int candidateRow) {
        for (int previousRow = 0; previousRow < candidateRow; previousRow++) {
            int columnDifference = Math.abs(queenColumnByRow[previousRow] - candidateColumn);
            int rowDifference = candidateRow - previousRow;

            if (columnDifference == 0 || rowDifference == columnDifference) {
                return false;
            }
        }
        return true;
    }
}