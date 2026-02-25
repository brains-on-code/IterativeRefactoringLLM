package com.thealgorithms.puzzlesandgames;

final class Sudoku {

    private Sudoku() {
    }

    public static boolean isSafe(int[][] grid, int rowIndex, int columnIndex, int candidateValue) {
        int gridSize = grid.length;

        for (int currentColumnIndex = 0; currentColumnIndex < gridSize; currentColumnIndex++) {
            if (grid[rowIndex][currentColumnIndex] == candidateValue) {
                return false;
            }
        }

        for (int currentRowIndex = 0; currentRowIndex < gridSize; currentRowIndex++) {
            if (grid[currentRowIndex][columnIndex] == candidateValue) {
                return false;
            }
        }

        int subgridSize = (int) Math.sqrt(gridSize);
        int subgridRowStart = rowIndex - rowIndex % subgridSize;
        int subgridColumnStart = columnIndex - columnIndex % subgridSize;

        for (int currentRowIndex = subgridRowStart; currentRowIndex < subgridRowStart + subgridSize; currentRowIndex++) {
            for (int currentColumnIndex = subgridColumnStart; currentColumnIndex < subgridColumnStart + subgridSize; currentColumnIndex++) {
                if (grid[currentRowIndex][currentColumnIndex] == candidateValue) {
                    return false;
                }
            }
        }

        return true;
    }

    public static boolean solveSudoku(int[][] grid, int gridSize) {
        int emptyRowIndex = -1;
        int emptyColumnIndex = -1;
        boolean hasEmptyCell = false;

        for (int rowIndex = 0; rowIndex < gridSize; rowIndex++) {
            for (int columnIndex = 0; columnIndex < gridSize; columnIndex++) {
                if (grid[rowIndex][columnIndex] == 0) {
                    emptyRowIndex = rowIndex;
                    emptyColumnIndex = columnIndex;
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
            if (isSafe(grid, emptyRowIndex, emptyColumnIndex, candidateValue)) {
                grid[emptyRowIndex][emptyColumnIndex] = candidateValue;
                if (solveSudoku(grid, gridSize)) {
                    return true;
                } else {
                    grid[emptyRowIndex][emptyColumnIndex] = 0;
                }
            }
        }
        return false;
    }

    public static void printGrid(int[][] grid, int gridSize) {
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
            printGrid(grid, gridSize);
        } else {
            System.out.println("No solution");
        }
    }
}