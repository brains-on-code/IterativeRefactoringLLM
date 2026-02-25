package com.thealgorithms.puzzlesandgames;

final class SudokuSolver {

    private SudokuSolver() {
    }

    public static boolean isSafeToPlace(int[][] grid, int rowIndex, int columnIndex, int candidateValue) {
        int gridSize = grid.length;

        // Check row
        for (int currentColumn = 0; currentColumn < gridSize; currentColumn++) {
            if (grid[rowIndex][currentColumn] == candidateValue) {
                return false;
            }
        }

        // Check column
        for (int currentRow = 0; currentRow < gridSize; currentRow++) {
            if (grid[currentRow][columnIndex] == candidateValue) {
                return false;
            }
        }

        // Check subgrid
        int subgridSize = (int) Math.sqrt(gridSize);
        int subgridStartRow = rowIndex - rowIndex % subgridSize;
        int subgridStartColumn = columnIndex - columnIndex % subgridSize;

        for (int currentRow = subgridStartRow; currentRow < subgridStartRow + subgridSize; currentRow++) {
            for (int currentColumn = subgridStartColumn; currentColumn < subgridStartColumn + subgridSize; currentColumn++) {
                if (grid[currentRow][currentColumn] == candidateValue) {
                    return false;
                }
            }
        }

        return true;
    }

    public static boolean solveSudoku(int[][] grid, int gridSize) {
        int emptyCellRow = -1;
        int emptyCellColumn = -1;
        boolean hasEmptyCell = false;

        for (int rowIndex = 0; rowIndex < gridSize; rowIndex++) {
            for (int columnIndex = 0; columnIndex < gridSize; columnIndex++) {
                if (grid[rowIndex][columnIndex] == 0) {
                    emptyCellRow = rowIndex;
                    emptyCellColumn = columnIndex;
                    hasEmptyCell = true;
                    break;
                }
            }
            if (hasEmptyCell) {
                break;
            }
        }

        if (!hasEmptyCell) {
            return true;
        }

        for (int candidateValue = 1; candidateValue <= gridSize; candidateValue++) {
            if (isSafeToPlace(grid, emptyCellRow, emptyCellColumn, candidateValue)) {
                grid[emptyCellRow][emptyCellColumn] = candidateValue;
                if (solveSudoku(grid, gridSize)) {
                    return true;
                } else {
                    grid[emptyCellRow][emptyCellColumn] = 0;
                }
            }
        }
        return false;
    }

    public static void printBoard(int[][] grid, int gridSize) {
        int subgridSize = (int) Math.sqrt(gridSize);

        for (int rowIndex = 0; rowIndex < gridSize; rowIndex++) {
            for (int columnIndex = 0; columnIndex < gridSize; columnIndex++) {
                System.out.print(grid[rowIndex][columnIndex]);
                System.out.print(" ");
            }
            System.out.print("\n");

            if ((rowIndex + 1) % subgridSize == 0) {
                System.out.print("");
            }
        }
    }

    public static void main(String[] args) {
        int[][] grid =
                new int[][] {
                    {3, 0, 6, 5, 0, 8, 4, 0, 0},
                    {5, 2, 0, 0, 0, 0, 0, 0, 0},
                    {0, 8, 7, 0, 0, 0, 0, 3, 1},
                    {0, 0, 3, 0, 1, 0, 0, 8, 0},
                    {9, 0, 0, 8, 6, 3, 0, 0, 5},
                    {0, 5, 0, 0, 9, 0, 6, 0, 0},
                    {1, 3, 0, 0, 0, 0, 2, 5, 0},
                    {0, 0, 0, 0, 0, 0, 0, 7, 4},
                    {0, 0, 5, 2, 0, 6, 3, 0, 0},
                };
        int gridSize = grid.length;

        if (solveSudoku(grid, gridSize)) {
            printBoard(grid, gridSize);
        } else {
            System.out.println("No solution");
        }
    }
}