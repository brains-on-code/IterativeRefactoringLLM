package com.thealgorithms.backtracking;

public final class MazeRecursion {

    private static final int START_ROW_INDEX = 1;
    private static final int START_COLUMN_INDEX = 1;
    private static final int TARGET_ROW_INDEX = 6;
    private static final int TARGET_COLUMN_INDEX = 5;

    private static final int CELL_STATE_UNVISITED = 0;
    private static final int CELL_STATE_PATH = 2;
    private static final int CELL_STATE_DEAD_END = 3;

    private MazeRecursion() {
    }

    public static int[][] solveMazeUsingFirstStrategy(int[][] mazeGrid) {
        if (findPathStrategyOne(mazeGrid, START_ROW_INDEX, START_COLUMN_INDEX)) {
            return mazeGrid;
        }
        return null;
    }

    public static int[][] solveMazeUsingSecondStrategy(int[][] mazeGrid) {
        if (findPathStrategyTwo(mazeGrid, START_ROW_INDEX, START_COLUMN_INDEX)) {
            return mazeGrid;
        }
        return null;
    }

    private static boolean findPathStrategyOne(int[][] mazeGrid, int currentRow, int currentColumn) {
        if (mazeGrid[TARGET_ROW_INDEX][TARGET_COLUMN_INDEX] == CELL_STATE_PATH) {
            return true;
        }

        if (mazeGrid[currentRow][currentColumn] == CELL_STATE_UNVISITED) {
            mazeGrid[currentRow][currentColumn] = CELL_STATE_PATH;

            if (findPathStrategyOne(mazeGrid, currentRow + 1, currentColumn)) {
                return true;
            } else if (findPathStrategyOne(mazeGrid, currentRow, currentColumn + 1)) {
                return true;
            } else if (findPathStrategyOne(mazeGrid, currentRow - 1, currentColumn)) {
                return true;
            } else if (findPathStrategyOne(mazeGrid, currentRow, currentColumn - 1)) {
                return true;
            }

            mazeGrid[currentRow][currentColumn] = CELL_STATE_DEAD_END;
            return false;
        }
        return false;
    }

    private static boolean findPathStrategyTwo(int[][] mazeGrid, int currentRow, int currentColumn) {
        if (mazeGrid[TARGET_ROW_INDEX][TARGET_COLUMN_INDEX] == CELL_STATE_PATH) {
            return true;
        }

        if (mazeGrid[currentRow][currentColumn] == CELL_STATE_UNVISITED) {
            mazeGrid[currentRow][currentColumn] = CELL_STATE_PATH;

            if (findPathStrategyTwo(mazeGrid, currentRow - 1, currentColumn)) {
                return true;
            } else if (findPathStrategyTwo(mazeGrid, currentRow, currentColumn + 1)) {
                return true;
            } else if (findPathStrategyTwo(mazeGrid, currentRow + 1, currentColumn)) {
                return true;
            } else if (findPathStrategyTwo(mazeGrid, currentRow, currentColumn - 1)) {
                return true;
            }

            mazeGrid[currentRow][currentColumn] = CELL_STATE_DEAD_END;
            return false;
        }
        return false;
    }
}