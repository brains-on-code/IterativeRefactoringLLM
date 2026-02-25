package com.thealgorithms.backtracking;

/**
 * This class contains methods to solve a maze using recursive backtracking.
 * The maze is represented as a 2D array where walls, paths, and visited/dead
 * ends are marked with different integers.
 *
 * The goal is to find a path from a starting position to the target position
 * (maze[6][5]) while navigating through the maze.
 */
public final class MazeRecursion {

    private static final int UNVISITED = 0;
    private static final int PATH = 2;
    private static final int DEAD_END = 3;

    private static final int TARGET_ROW = 6;
    private static final int TARGET_COLUMN = 5;

    private static final int START_ROW = 1;
    private static final int START_COLUMN = 1;

    private MazeRecursion() {
    }

    /**
     * This method solves the maze using the "down -> right -> up -> left"
     * movement strategy.
     *
     * @param maze The 2D array representing the maze (walls, paths, etc.)
     * @return The solved maze with paths marked, or null if no solution exists.
     */
    public static int[][] solveMazeUsingFirstStrategy(int[][] maze) {
        if (findPathDownRightUpLeft(maze, START_ROW, START_COLUMN)) {
            return maze;
        }
        return null;
    }

    /**
     * This method solves the maze using the "up -> right -> down -> left"
     * movement strategy.
     *
     * @param maze The 2D array representing the maze (walls, paths, etc.)
     * @return The solved maze with paths marked, or null if no solution exists.
     */
    public static int[][] solveMazeUsingSecondStrategy(int[][] maze) {
        if (findPathUpRightDownLeft(maze, START_ROW, START_COLUMN)) {
            return maze;
        }
        return null;
    }

    /**
     * Attempts to find a path through the maze using a "down -> right -> up -> left"
     * movement strategy. The path is marked with '2' for valid paths and '3' for dead ends.
     *
     * @param maze      The 2D array representing the maze (walls, paths, etc.)
     * @param rowIndex  The current row index
     * @param columnIndex The current column index
     * @return True if a path is found to (TARGET_ROW, TARGET_COLUMN), otherwise false
     */
    private static boolean findPathDownRightUpLeft(int[][] maze, int rowIndex, int columnIndex) {
        if (maze[TARGET_ROW][TARGET_COLUMN] == PATH) {
            return true;
        }

        if (maze[rowIndex][columnIndex] == UNVISITED) {
            maze[rowIndex][columnIndex] = PATH;

            // Move down
            if (findPathDownRightUpLeft(maze, rowIndex + 1, columnIndex)) {
                return true;
            }
            // Move right
            if (findPathDownRightUpLeft(maze, rowIndex, columnIndex + 1)) {
                return true;
            }
            // Move up
            if (findPathDownRightUpLeft(maze, rowIndex - 1, columnIndex)) {
                return true;
            }
            // Move left
            if (findPathDownRightUpLeft(maze, rowIndex, columnIndex - 1)) {
                return true;
            }

            maze[rowIndex][columnIndex] = DEAD_END;
            return false;
        }
        return false;
    }

    /**
     * Attempts to find a path through the maze using an alternative movement
     * strategy "up -> right -> down -> left".
     *
     * @param maze        The 2D array representing the maze (walls, paths, etc.)
     * @param rowIndex    The current row index
     * @param columnIndex The current column index
     * @return True if a path is found to (TARGET_ROW, TARGET_COLUMN), otherwise false
     */
    private static boolean findPathUpRightDownLeft(int[][] maze, int rowIndex, int columnIndex) {
        if (maze[TARGET_ROW][TARGET_COLUMN] == PATH) {
            return true;
        }

        if (maze[rowIndex][columnIndex] == UNVISITED) {
            maze[rowIndex][columnIndex] = PATH;

            // Move up
            if (findPathUpRightDownLeft(maze, rowIndex - 1, columnIndex)) {
                return true;
            }
            // Move right
            if (findPathUpRightDownLeft(maze, rowIndex, columnIndex + 1)) {
                return true;
            }
            // Move down
            if (findPathUpRightDownLeft(maze, rowIndex + 1, columnIndex)) {
                return true;
            }
            // Move left
            if (findPathUpRightDownLeft(maze, rowIndex, columnIndex - 1)) {
                return true;
            }

            maze[rowIndex][columnIndex] = DEAD_END;
            return false;
        }
        return false;
    }
}