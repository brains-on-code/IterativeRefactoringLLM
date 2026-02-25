package com.thealgorithms.backtracking;

public final class MazeSolver {

    private static final int CELL_PATH = 0;
    private static final int CELL_VISITED = 2;
    private static final int CELL_DEAD_END = 3;

    private static final int GOAL_ROW = 6;
    private static final int GOAL_COL = 5;

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

    private static boolean searchDownRightFirst(int[][] maze, int currentRow, int currentCol) {
        if (maze[GOAL_ROW][GOAL_COL] == CELL_VISITED) {
            return true;
        }

        if (maze[currentRow][currentCol] == CELL_PATH) {
            maze[currentRow][currentCol] = CELL_VISITED;

            if (searchDownRightFirst(maze, currentRow + 1, currentCol)) {
                return true;
            } else if (searchDownRightFirst(maze, currentRow, currentCol + 1)) {
                return true;
            } else if (searchDownRightFirst(maze, currentRow - 1, currentCol)) {
                return true;
            } else if (searchDownRightFirst(maze, currentRow, currentCol - 1)) {
                return true;
            }

            maze[currentRow][currentCol] = CELL_DEAD_END;
            return false;
        }
        return false;
    }

    private static boolean searchUpRightFirst(int[][] maze, int currentRow, int currentCol) {
        if (maze[GOAL_ROW][GOAL_COL] == CELL_VISITED) {
            return true;
        }

        if (maze[currentRow][currentCol] == CELL_PATH) {
            maze[currentRow][currentCol] = CELL_VISITED;

            if (searchUpRightFirst(maze, currentRow - 1, currentCol)) {
                return true;
            } else if (searchUpRightFirst(maze, currentRow, currentCol + 1)) {
                return true;
            } else if (searchUpRightFirst(maze, currentRow + 1, currentCol)) {
                return true;
            } else if (searchUpRightFirst(maze, currentRow, currentCol - 1)) {
                return true;
            }

            maze[currentRow][currentCol] = CELL_DEAD_END;
            return false;
        }
        return false;
    }
}