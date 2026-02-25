package com.thealgorithms.backtracking;

/**
 * This class contains methods to solve a maze using recursive backtracking.
 * The maze is represented as a 2D array where walls, paths, and visited/dead
 * ends are marked with different integers.
 *
 * The goal is to find a path from a starting position to the target position
 * (map[6][5]) while navigating through the maze.
 */
public final class MazeRecursion {

    private static final int TARGET_ROW = 6;
    private static final int TARGET_COL = 5;

    private static final int UNVISITED = 0;
    private static final int PATH = 2;
    private static final int DEAD_END = 3;

    private MazeRecursion() {
    }

    /**
     * This method solves the maze using the "down -> right -> up -> left"
     * movement strategy.
     *
     * @param map The 2D array representing the maze (walls, paths, etc.)
     * @return The solved maze with paths marked, or null if no solution exists.
     */
    public static int[][] solveMazeUsingFirstStrategy(int[][] map) {
        return findPath(map, 1, 1, MovementStrategy.FIRST) ? map : null;
    }

    /**
     * This method solves the maze using the "up -> right -> down -> left"
     * movement strategy.
     *
     * @param map The 2D array representing the maze (walls, paths, etc.)
     * @return The solved maze with paths marked, or null if no solution exists.
     */
    public static int[][] solveMazeUsingSecondStrategy(int[][] map) {
        return findPath(map, 1, 1, MovementStrategy.SECOND) ? map : null;
    }

    private enum MovementStrategy {
        FIRST(new int[][]{{1, 0}, {0, 1}, {-1, 0}, {0, -1}}),   // down, right, up, left
        SECOND(new int[][]{{-1, 0}, {0, 1}, {1, 0}, {0, -1}});  // up, right, down, left

        private final int[][] directions;

        MovementStrategy(int[][] directions) {
            this.directions = directions;
        }

        public int[][] getDirections() {
            return directions;
        }
    }

    /**
     * Attempts to find a path through the maze using the given movement strategy.
     * The path is marked with '2' for valid paths and '3' for dead ends.
     *
     * @param map       The 2D array representing the maze (walls, paths, etc.)
     * @param row       The current row index
     * @param col       The current column index
     * @param strategy  The movement strategy to use
     * @return True if a path is found to (TARGET_ROW, TARGET_COL), otherwise false
     */
    private static boolean findPath(int[][] map, int row, int col, MovementStrategy strategy) {
        if (map[TARGET_ROW][TARGET_COL] == PATH) {
            return true;
        }

        if (map[row][col] != UNVISITED) {
            return false;
        }

        map[row][col] = PATH;

        for (int[] direction : strategy.getDirections()) {
            int nextRow = row + direction[0];
            int nextCol = col + direction[1];

            if (findPath(map, nextRow, nextCol, strategy)) {
                return true;
            }
        }

        map[row][col] = DEAD_END;
        return false;
    }
}