package com.thealgorithms.backtracking;

public final class MazeRecursion {

    private static final int START_ROW = 1;
    private static final int START_COL = 1;
    private static final int TARGET_ROW = 6;
    private static final int TARGET_COL = 5;

    private static final int CELL_UNVISITED = 0;
    private static final int CELL_PATH = 2;
    private static final int CELL_DEAD_END = 3;

    private static final int[][] FIRST_STRATEGY_DIRECTIONS = {
        {1, 0},  // down
        {0, 1},  // right
        {-1, 0}, // up
        {0, -1}  // left
    };

    private static final int[][] SECOND_STRATEGY_DIRECTIONS = {
        {-1, 0}, // up
        {0, 1},  // right
        {1, 0},  // down
        {0, -1}  // left
    };

    private MazeRecursion() {
    }

    public static int[][] solveMazeUsingFirstStrategy(int[][] map) {
        return findPath(map, START_ROW, START_COL, Strategy.FIRST) ? map : null;
    }

    public static int[][] solveMazeUsingSecondStrategy(int[][] map) {
        return findPath(map, START_ROW, START_COL, Strategy.SECOND) ? map : null;
    }

    private enum Strategy {
        FIRST,
        SECOND
    }

    private static boolean findPath(int[][] map, int row, int col, Strategy strategy) {
        if (map[TARGET_ROW][TARGET_COL] == CELL_PATH) {
            return true;
        }

        if (!isCellUnvisited(map, row, col)) {
            return false;
        }

        markCellAsPath(map, row, col);

        int[][] directions = getDirectionsForStrategy(strategy);

        for (int[] direction : directions) {
            int nextRow = row + direction[0];
            int nextCol = col + direction[1];

            if (findPath(map, nextRow, nextCol, strategy)) {
                return true;
            }
        }

        markCellAsDeadEnd(map, row, col);
        return false;
    }

    private static boolean isCellUnvisited(int[][] map, int row, int col) {
        return map[row][col] == CELL_UNVISITED;
    }

    private static void markCellAsPath(int[][] map, int row, int col) {
        map[row][col] = CELL_PATH;
    }

    private static void markCellAsDeadEnd(int[][] map, int row, int col) {
        map[row][col] = CELL_DEAD_END;
    }

    private static int[][] getDirectionsForStrategy(Strategy strategy) {
        return strategy == Strategy.FIRST ? FIRST_STRATEGY_DIRECTIONS : SECOND_STRATEGY_DIRECTIONS;
    }
}