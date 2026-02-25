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

    private static final int START_ROW = 1;
    private static final int START_COL = 1;

    private MazeRecursion() {
        // Utility class; prevent instantiation
    }

    /**
     * Solves the maze using the "down -> right -> up -> left" movement strategy.
     *
     * @param map The 2D array representing the maze (walls, paths, etc.)
     * @return The solved maze with paths marked, or null if no solution exists.
     */
    public static int[][] solveMazeUsingFirstStrategy(int[][] map) {
        return solveMaze(map, MovementStrategy.FIRST);
    }

    /**
     * Solves the maze using the "up -> right -> down -> left" movement strategy.
     *
     * @param map The 2D array representing the maze (walls, paths, etc.)
     * @return The solved maze with paths marked, or null if no solution exists.
     */
    public static int[][] solveMazeUsingSecondStrategy(int[][] map) {
        return solveMaze(map, MovementStrategy.SECOND);
    }

    private static int[][] solveMaze(int[][] map, MovementStrategy strategy) {
        if (!isValidMap(map)) {
            return null;
        }
        return findPath(map, START_ROW, START_COL, strategy) ? map : null;
    }

    private static boolean isValidMap(int[][] map) {
        return map != null && map.length > 0;
    }

    private enum MovementStrategy {
        // down, right, up, left
        FIRST(new int[][]{{1, 0}, {0, 1}, {-1, 0}, {0, -1}}),
        // up, right, down, left
        SECOND(new int[][]{{-1, 0}, {0, 1}, {1, 0}, {0, -1}});

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
     * @param map      The 2D array representing the maze (walls, paths, etc.)
     * @param row      The current row index
     * @param col      The current column index
     * @param strategy The movement strategy to use
     * @return True if a path is found to (TARGET_ROW, TARGET_COL), otherwise false
     */
    private static boolean findPath(int[][] map, int row, int col, MovementStrategy strategy) {
        if (isTargetReached(map)) {
            return true;
        }

        if (!isWithinBounds(map, row, col) || !isCellUnvisited(map, row, col)) {
            return false;
        }

        markAsPath(map, row, col);

        for (int[] direction : strategy.getDirections()) {
            int nextRow = row + direction[0];
            int nextCol = col + direction[1];

            if (findPath(map, nextRow, nextCol, strategy)) {
                return true;
            }
        }

        markAsDeadEnd(map, row, col);
        return false;
    }

    private static boolean isWithinBounds(int[][] map, int row, int col) {
        return row >= 0 && row < map.length && col >= 0 && col < map[row].length;
    }

    private static boolean isTargetReached(int[][] map) {
        return map[TARGET_ROW][TARGET_COL] == PATH;
    }

    private static boolean isCellUnvisited(int[][] map, int row, int col) {
        return map[row][col] == UNVISITED;
    }

    private static void markAsPath(int[][] map, int row, int col) {
        map[row][col] = PATH;
    }

    private static void markAsDeadEnd(int[][] map, int row, int col) {
        map[row][col] = DEAD_END;
    }
}