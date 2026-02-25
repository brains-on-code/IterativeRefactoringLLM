package com.thealgorithms.puzzlesandgames;

final class Sudoku {

    private Sudoku() {
    }

    public static boolean isSafe(int[][] grid, int row, int column, int candidateNumber) {
        int gridSize = grid.length;

        for (int currentColumn = 0; currentColumn < gridSize; currentColumn++) {
            if (grid[row][currentColumn] == candidateNumber) {
                return false;
            }
        }

        for (int currentRow = 0; currentRow < gridSize; currentRow++) {
            if (grid[currentRow][column] == candidateNumber) {
                return false;
            }
        }

        int boxSize = (int) Math.sqrt(gridSize);
        int boxRowStart = row - row % boxSize;
        int boxColumnStart = column - column % boxSize;

        for (int currentRow = boxRowStart; currentRow < boxRowStart + boxSize; currentRow++) {
            for (int currentColumn = boxColumnStart; currentColumn < boxColumnStart + boxSize; currentColumn++) {
                if (grid[currentRow][currentColumn] == candidateNumber) {
                    return false;
                }
            }
        }

        return true;
    }

    public static boolean solveSudoku(int[][] grid, int gridSize) {
        int emptyCellRow = -1;
        int emptyCellColumn = -1;
        boolean emptyCellFound = false;

        for (int row = 0; row < gridSize; row++) {
            for (int column = 0; column < gridSize; column++) {
                if (grid[row][column] == 0) {
                    emptyCellRow = row;
                    emptyCellColumn = column;
                    emptyCellFound = true;
                    break;
                }
            }
            if (emptyCellFound) {
                break;
            }
        }

        if (!emptyCellFound) {
            return true;
        }

        for (int candidateNumber = 1; candidateNumber <= gridSize; candidateNumber++) {
            if (isSafe(grid, emptyCellRow, emptyCellColumn, candidateNumber)) {
                grid[emptyCellRow][emptyCellColumn] = candidateNumber;
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
        int boxSize = (int) Math.sqrt(gridSize);

        for (int row = 0; row < gridSize; row++) {
            for (int column = 0; column < gridSize; column++) {
                System.out.print(grid[row][column]);
                System.out.print(" ");
            }
            System.out.print("\n");

            if ((row + 1) % boxSize == 0) {
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