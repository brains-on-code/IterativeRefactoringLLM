package com.thealgorithms.backtracking;

public final class MazeSolver {

    private static final int CELL_OPEN = 0;
    private static final int CELL_VISITED = 2;
    private static final int CELL_DEAD_END = 3;

    private static final int TARGET_ROW = 6;
    private static final int TARGET_COLUMN = 5;

    private static final int START_ROW = 1;
    private static final int START_COLUMN = 1;

    private MazeSolver() {
    }

    public static int[][] solveMazeDownRightFirst(int[][] maze) {
        if (searchPathDownRightFirst(maze, START_ROW, START_COLUMN)) {
            return maze;
        }
        return null;
    }

    public static int[][] solveMazeUpRightFirst(int[][] maze) {
        if (searchPathUpRightFirst(maze, START_ROW, START_COLUMN)) {
            return maze;
        }
        return null;
    }

    private static boolean searchPathDownRightFirst(int[][] maze, int row, int column) {
        if (maze[TARGET_ROW][TARGET_COLUMN] == CELL_VISITED) {
            return true;
        }

        if (maze[row][column] == CELL_OPEN) {
            maze[row][column] = CELL_VISITED;

            if (searchPathDownRightFirst(maze, row + 1, column)) {
                return true;
            } else if (searchPathDownRightFirst(maze, row, column + 1)) {
                return true;
            } else if (searchPathDownRightFirst(maze, row - 1, column)) {
                return true;
            } else if (searchPathDownRightFirst(maze, row, column - 1)) {
                return true;
            }

            maze[row][column] = CELL_DEAD_END;
            return false;
        }
        return false;
    }

    private static boolean searchPathUpRightFirst(int[][] maze, int row, int column) {
        if (maze[TARGET_ROW][TARGET_COLUMN] == CELL_VISITED) {
            return true;
        }

        if (maze[row][column] == CELL_OPEN) {
            maze[row][column] = CELL_VISITED;

            if (searchPathUpRightFirst(maze, row - 1, column)) {
                return true;
            } else if (searchPathUpRightFirst(maze, row, column + 1)) {
                return true;
            } else if (searchPathUpRightFirst(maze, row + 1, column)) {
                return true;
            } else if (searchPathUpRightFirst(maze, row, column - 1)) {
                return true;
            }

            maze[row][column] = CELL_DEAD_END;
            return false;
        }
        return false;
    }
}