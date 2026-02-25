package com.thealgorithms.backtracking;

public final class MazeRecursion {

    private static final int START_ROW = 1;
    private static final int START_COL = 1;
    private static final int END_ROW = 6;
    private static final int END_COL = 5;

    private static final int CELL_UNVISITED = 0;
    private static final int CELL_PATH = 2;
    private static final int CELL_DEAD_END = 3;

    private MazeRecursion() {
        // Utility class; prevent instantiation
    }

    public static int[][] solveMazeUsingFirstStrategy(int[][] map) {
        return findPath(map, START_ROW, START_COL, MovementStrategy.FIRST) ? map : null;
    }

    public static int[][] solveMazeUsingSecondStrategy(int[][] map) {
        return findPath(map, START_ROW, START_COL, MovementStrategy.SECOND) ? map : null;
    }

    private enum MovementStrategy {
        FIRST,  // down → right → up → left
        SECOND  // up → right → down → left
    }

    private static boolean findPath(int[][] map, int row, int col, MovementStrategy strategy) {
        if (map[END_ROW][END_COL] == CELL_PATH) {
            return true;
        }

        if (map[row][col] != CELL_UNVISITED) {
            return false;
        }

        map[row][col] = CELL_PATH;

        int[][] directions = strategy == MovementStrategy.FIRST
            ? new int[][] { {1, 0}, {0, 1}, {-1, 0}, {0, -1} }
            : new int[][] { {-1, 0}, {0, 1}, {1, 0}, {0, -1} };

        for (int[] direction : directions) {
            int nextRow = row + direction[0];
            int nextCol = col + direction[1];

            if (findPath(map, nextRow, nextCol, strategy)) {
                return true;
            }
        }

        map[row][col] = CELL_DEAD_END;
        return false;
    }
}