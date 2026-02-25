package com.thealgorithms.backtracking;

public final class Class1 {

    private static final int PATH = 0;
    private static final int VISITED = 2;
    private static final int DEAD_END = 3;

    private static final int TARGET_ROW = 6;
    private static final int TARGET_COL = 5;

    private Class1() {
    }

    public static int[][] method1(int[][] maze) {
        if (solveMazeDownFirst(maze, 1, 1)) {
            return maze;
        }
        return null;
    }

    public static int[][] method2(int[][] maze) {
        if (solveMazeUpFirst(maze, 1, 1)) {
            return maze;
        }
        return null;
    }

    private static boolean solveMazeDownFirst(int[][] maze, int row, int col) {
        if (maze[TARGET_ROW][TARGET_COL] == VISITED) {
            return true;
        }

        if (maze[row][col] == PATH) {
            maze[row][col] = VISITED;

            if (solveMazeDownFirst(maze, row + 1, col)) {
                return true;
            } else if (solveMazeDownFirst(maze, row, col + 1)) {
                return true;
            } else if (solveMazeDownFirst(maze, row - 1, col)) {
                return true;
            } else if (solveMazeDownFirst(maze, row, col - 1)) {
                return true;
            }

            maze[row][col] = DEAD_END;
            return false;
        }
        return false;
    }

    private static boolean solveMazeUpFirst(int[][] maze, int row, int col) {
        if (maze[TARGET_ROW][TARGET_COL] == VISITED) {
            return true;
        }

        if (maze[row][col] == PATH) {
            maze[row][col] = VISITED;

            if (solveMazeUpFirst(maze, row - 1, col)) {
                return true;
            } else if (solveMazeUpFirst(maze, row, col + 1)) {
                return true;
            } else if (solveMazeUpFirst(maze, row + 1, col)) {
                return true;
            } else if (solveMazeUpFirst(maze, row, col - 1)) {
                return true;
            }

            maze[row][col] = DEAD_END;
            return false;
        }
        return false;
    }
}