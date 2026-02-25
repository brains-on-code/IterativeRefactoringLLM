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
        // Utility class
    }

    public static int[][] solveMazeUsingFirstStrategy(int[][] map) {
        return solveMaze(map, Strategy.FIRST);
    }

    public static int[][] solveMazeUsingSecondStrategy(int[][] map) {
        return solveMaze(map, Strategy.SECOND);
    }

    private static int[][] solveMaze(int[][] map, Strategy strategy) {
        if (map == null || map.length == 0 || map[0].length == 0) {
            return null;
        }
        return findPath(map, START_ROW, START_COL, strategy) ? map : null;
    }

    private enum Strategy {
        FIRST,
        SECOND
    }

    private static boolean findPath(int[][] map, int row, int col, Strategy strategy) {
        if (hasReachedTarget(map)) {
            return true;
        }

        if (!isWithinBounds(map, row, col) || !isCellUnvisited(map, row, col)) {
            return false;
        }

        markCellAsPath(map, row, col);

        for (int[] direction : getDirectionsForStrategy(strategy)) {
            int nextRow = row + direction[0];
            int nextCol = col + direction[1];

            if (findPath(map, nextRow, nextCol, strategy)) {
                return true;
            }
        }

        markCellAsDeadEnd(map, row, col);
        return false;
    }

    private static boolean hasReachedTarget(int[][] map) {
        return map[TARGET_ROW][TARGET_COL] == CELL_PATH;
    }

    private static boolean isWithinBounds(int[][] map, int row, int col) {
        return row >= 0
            && row < map.length
            && col >= 0
            && col < map[0].length;
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
        switch (strategy) {
            case FIRST:
                return FIRST_STRATEGY_DIRECTIONS;
            case SECOND:
                return SECOND_STRATEGY_DIRECTIONS;
            default:
                throw new IllegalArgumentException("Unknown strategy: " + strategy);
        }
    }
}