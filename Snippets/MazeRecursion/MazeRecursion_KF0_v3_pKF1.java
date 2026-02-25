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

    private static final int CELL_UNVISITED = 0;
    private static final int CELL_PATH = 2;
    private static final int CELL_DEAD_END = 3;

    private static final int TARGET_ROW_INDEX = 6;
    private static final int TARGET_COLUMN_INDEX = 5;

    private static final int START_ROW_INDEX = 1;
    private static final int START_COLUMN_INDEX = 1;

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
        if (findPathDownRightUpLeft(maze, START_ROW_INDEX, START_COLUMN_INDEX)) {
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
        if (findPathUpRightDownLeft(maze, START_ROW_INDEX, START_COLUMN_INDEX)) {
            return maze;
        }
        return null;
    }

    /**
     * Attempts to find a path through the maze using a "down -> right -> up -> left"
     * movement strategy. The path is marked with '2' for valid paths and '3' for dead ends.
     *
     * @param maze        The 2D array representing the maze (walls, paths, etc.)
     * @param currentRow  The current row index
     * @param currentCol  The current column index
     * @return True if a path is found to (TARGET_ROW_INDEX, TARGET_COLUMN_INDEX), otherwise false
     */
    private static boolean findPathDownRightUpLeft(int[][] maze, int currentRow, int currentCol) {
        if (maze[TARGET_ROW_INDEX][TARGET_COLUMN_INDEX] == CELL_PATH) {
            return true;
        }

        if (maze[currentRow][currentCol] == CELL_UNVISITED) {
            maze[currentRow][currentCol] = CELL_PATH;

            // Move down
            if (findPathDownRightUpLeft(maze, currentRow + 1, currentCol)) {
                return true;
            }
            // Move right
            if (findPathDownRightUpLeft(maze, currentRow, currentCol + 1)) {
                return true;
            }
            // Move up
            if (findPathDownRightUpLeft(maze, currentRow - 1, currentCol)) {
                return true;
            }
            // Move left
            if (findPathDownRightUpLeft(maze, currentRow, currentCol - 1)) {
                return true;
            }

            maze[currentRow][currentCol] = CELL_DEAD_END;
            return false;
        }
        return false;
    }

    /**
     * Attempts to find a path through the maze using an alternative movement
     * strategy "up -> right -> down -> left".
     *
     * @param maze        The 2D array representing the maze (walls, paths, etc.)
     * @param currentRow  The current row index
     * @param currentCol  The current column index
     * @return True if a path is found to (TARGET_ROW_INDEX, TARGET_COLUMN_INDEX), otherwise false
     */
    private static boolean findPathUpRightDownLeft(int[][] maze, int currentRow, int currentCol) {
        if (maze[TARGET_ROW_INDEX][TARGET_COLUMN_INDEX] == CELL_PATH) {
            return true;
        }

        if (maze[currentRow][currentCol] == CELL_UNVISITED) {
            maze[currentRow][currentCol] = CELL_PATH;

            // Move up
            if (findPathUpRightDownLeft(maze, currentRow - 1, currentCol)) {
                return true;
            }
            // Move right
            if (findPathUpRightDownLeft(maze, currentRow, currentCol + 1)) {
                return true;
            }
            // Move down
            if (findPathUpRightDownLeft(maze, currentRow + 1, currentCol)) {
                return true;
            }
            // Move left
            if (findPathUpRightDownLeft(maze, currentRow, currentCol - 1)) {
                return true;
            }

            maze[currentRow][currentCol] = CELL_DEAD_END;
            return false;
        }
        return false;
    }
}