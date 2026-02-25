package com.thealgorithms.backtracking;

public final class MazeRecursion {

    private static final int START_ROW = 1;
    private static final int START_COL = 1;
    private static final int TARGET_ROW = 6;
    private static final int TARGET_COL = 5;

    private static final int CELL_UNVISITED = 0;
    private static final int CELL_PATH = 2;
    private static final int CELL_DEAD_END = 3;

    private MazeRecursion() {
    }

    public static int[][] solveMazeUsingFirstStrategy(int[][] maze) {
        if (findPathStrategyOne(maze, START_ROW, START_COL)) {
            return maze;
        }
        return null;
    }

    public static int[][] solveMazeUsingSecondStrategy(int[][] maze) {
        if (findPathStrategyTwo(maze, START_ROW, START_COL)) {
            return maze;
        }
        return null;
    }

    private static boolean findPathStrategyOne(int[][] maze, int row, int col) {
        if (maze[TARGET_ROW][TARGET_COL] == CELL_PATH) {
            return true;
        }

        if (maze[row][col] == CELL_UNVISITED) {
            maze[row][col] = CELL_PATH;

            if (findPathStrategyOne(maze, row + 1, col)) {
                return true;
            } else if (findPathStrategyOne(maze, row, col + 1)) {
                return true;
            } else if (findPathStrategyOne(maze, row - 1, col)) {
                return true;
            } else if (findPathStrategyOne(maze, row, col - 1)) {
                return true;
            }

            maze[row][col] = CELL_DEAD_END;
            return false;
        }
        return false;
    }

    private static boolean findPathStrategyTwo(int[][] maze, int row, int col) {
        if (maze[TARGET_ROW][TARGET_COL] == CELL_PATH) {
            return true;
        }

        if (maze[row][col] == CELL_UNVISITED) {
            maze[row][col] = CELL_PATH;

            if (findPathStrategyTwo(maze, row - 1, col)) {
                return true;
            } else if (findPathStrategyTwo(maze, row, col + 1)) {
                return true;
            } else if (findPathStrategyTwo(maze, row + 1, col)) {
                return true;
            } else if (findPathStrategyTwo(maze, row, col - 1)) {
                return true;
            }

            maze[row][col] = CELL_DEAD_END;
            return false;
        }
        return false;
    }
}