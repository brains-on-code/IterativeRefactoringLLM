package com.thealgorithms.backtracking;

public final class MazeSolver {

    private static final int PATH = 0;
    private static final int VISITED = 2;
    private static final int DEAD_END = 3;

    private static final int TARGET_ROW = 6;
    private static final int TARGET_COL = 5;

    private MazeSolver() {
    }

    public static int[][] solveMazeDownRightFirst(int[][] maze) {
        if (searchDownRightFirst(maze, 1, 1)) {
            return maze;
        }
        return null;
    }

    public static int[][] solveMazeUpRightFirst(int[][] maze) {
        if (searchUpRightFirst(maze, 1, 1)) {
            return maze;
        }
        return null;
    }

    private static boolean searchDownRightFirst(int[][] maze, int row, int col) {
        if (maze[TARGET_ROW][TARGET_COL] == VISITED) {
            return true;
        }

        if (maze[row][col] == PATH) {
            maze[row][col] = VISITED;

            if (searchDownRightFirst(maze, row + 1, col)) {
                return true;
            } else if (searchDownRightFirst(maze, row, col + 1)) {
                return true;
            } else if (searchDownRightFirst(maze, row - 1, col)) {
                return true;
            } else if (searchDownRightFirst(maze, row, col - 1)) {
                return true;
            }

            maze[row][col] = DEAD_END;
            return false;
        }
        return false;
    }

    private static boolean searchUpRightFirst(int[][] maze, int row, int col) {
        if (maze[TARGET_ROW][TARGET_COL] == VISITED) {
            return true;
        }

        if (maze[row][col] == PATH) {
            maze[row][col] = VISITED;

            if (searchUpRightFirst(maze, row - 1, col)) {
                return true;
            } else if (searchUpRightFirst(maze, row, col + 1)) {
                return true;
            } else if (searchUpRightFirst(maze, row + 1, col)) {
                return true;
            } else if (searchUpRightFirst(maze, row, col - 1)) {
                return true;
            }

            maze[row][col] = DEAD_END;
            return false;
        }
        return false;
    }
}