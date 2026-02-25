package com.thealgorithms.backtracking;

public final class Class1 {

    private static final int CELL_PATH = 0;
    private static final int CELL_VISITED = 2;
    private static final int CELL_DEAD_END = 3;

    private static final int TARGET_ROW = 6;
    private static final int TARGET_COL = 5;

    private static final int START_ROW = 1;
    private static final int START_COL = 1;

    private Class1() {
        // Utility class
    }

    public static int[][] method1(int[][] maze) {
        return solveMaze(maze, START_ROW, START_COL, DirectionOrder.DOWN_FIRST) ? maze : null;
    }

    public static int[][] method2(int[][] maze) {
        return solveMaze(maze, START_ROW, START_COL, DirectionOrder.UP_FIRST) ? maze : null;
    }

    private enum DirectionOrder {
        DOWN_FIRST(new int[][]{{1, 0}, {0, 1}, {-1, 0}, {0, -1}}),
        UP_FIRST(new int[][]{{-1, 0}, {0, 1}, {1, 0}, {0, -1}});

        private final int[][] directions;

        DirectionOrder(int[][] directions) {
            this.directions = directions;
        }

        public int[][] getDirections() {
            return directions;
        }
    }

    private static boolean solveMaze(int[][] maze, int row, int col, DirectionOrder directionOrder) {
        if (!isInBounds(maze, row, col) || !isPath(maze, row, col)) {
            return false;
        }

        markVisited(maze, row, col);

        if (hasReachedTarget(maze)) {
            return true;
        }

        for (int[] direction : directionOrder.getDirections()) {
            int nextRow = row + direction[0];
            int nextCol = col + direction[1];
            if (solveMaze(maze, nextRow, nextCol, directionOrder)) {
                return true;
            }
        }

        markDeadEnd(maze, row, col);
        return false;
    }

    private static boolean hasReachedTarget(int[][] maze) {
        return maze[TARGET_ROW][TARGET_COL] == CELL_VISITED;
    }

    private static boolean isPath(int[][] maze, int row, int col) {
        return maze[row][col] == CELL_PATH;
    }

    private static void markVisited(int[][] maze, int row, int col) {
        maze[row][col] = CELL_VISITED;
    }

    private static void markDeadEnd(int[][] maze, int row, int col) {
        maze[row][col] = CELL_DEAD_END;
    }

    private static boolean isInBounds(int[][] maze, int row, int col) {
        return row >= 0
            && row < maze.length
            && col >= 0
            && col < maze[0].length;
    }
}