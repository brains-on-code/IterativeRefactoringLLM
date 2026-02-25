package com.thealgorithms.backtracking;

public final class MazeSolver {

    private static final int CELL_OPEN = 0;
    private static final int CELL_VISITED = 2;
    private static final int CELL_DEAD_END = 3;

    private static final int DESTINATION_ROW = 6;
    private static final int DESTINATION_COLUMN = 5;

    private MazeSolver() {
    }

    public static int[][] solveMazeDownRightFirst(int[][] mazeGrid) {
        if (searchPathDownRightFirst(mazeGrid, 1, 1)) {
            return mazeGrid;
        }
        return null;
    }

    public static int[][] solveMazeUpRightFirst(int[][] mazeGrid) {
        if (searchPathUpRightFirst(mazeGrid, 1, 1)) {
            return mazeGrid;
        }
        return null;
    }

    private static boolean searchPathDownRightFirst(int[][] mazeGrid, int currentRow, int currentColumn) {
        if (mazeGrid[DESTINATION_ROW][DESTINATION_COLUMN] == CELL_VISITED) {
            return true;
        }

        if (mazeGrid[currentRow][currentColumn] == CELL_OPEN) {
            mazeGrid[currentRow][currentColumn] = CELL_VISITED;

            if (searchPathDownRightFirst(mazeGrid, currentRow + 1, currentColumn)) {
                return true;
            } else if (searchPathDownRightFirst(mazeGrid, currentRow, currentColumn + 1)) {
                return true;
            } else if (searchPathDownRightFirst(mazeGrid, currentRow - 1, currentColumn)) {
                return true;
            } else if (searchPathDownRightFirst(mazeGrid, currentRow, currentColumn - 1)) {
                return true;
            }

            mazeGrid[currentRow][currentColumn] = CELL_DEAD_END;
            return false;
        }
        return false;
    }

    private static boolean searchPathUpRightFirst(int[][] mazeGrid, int currentRow, int currentColumn) {
        if (mazeGrid[DESTINATION_ROW][DESTINATION_COLUMN] == CELL_VISITED) {
            return true;
        }

        if (mazeGrid[currentRow][currentColumn] == CELL_OPEN) {
            mazeGrid[currentRow][currentColumn] = CELL_VISITED;

            if (searchPathUpRightFirst(mazeGrid, currentRow - 1, currentColumn)) {
                return true;
            } else if (searchPathUpRightFirst(mazeGrid, currentRow, currentColumn + 1)) {
                return true;
            } else if (searchPathUpRightFirst(mazeGrid, currentRow + 1, currentColumn)) {
                return true;
            } else if (searchPathUpRightFirst(mazeGrid, currentRow, currentColumn - 1)) {
                return true;
            }

            mazeGrid[currentRow][currentColumn] = CELL_DEAD_END;
            return false;
        }
        return false;
    }
}