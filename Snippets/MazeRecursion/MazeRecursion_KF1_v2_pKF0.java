package com.thealgorithms.backtracking;

public final class Class1 {

    private static final int PATH = 0;
    private static final int VISITED = 2;
    private static final int DEAD_END = 3;

    private static final int TARGET_ROW = 6;
    private static final int TARGET_COL = 5;

    private Class1() {
        // Utility class
    }

    public static int[][] method1(int[][] maze) {
        return solveMaze(maze, 1, 1, DirectionOrder.DOWN_FIRST) ? maze : null;
    }

    public static int[][] method2(int[][] maze) {
        return solveMaze(maze, 1, 1, DirectionOrder.UP_FIRST) ? maze : null;
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

    private static boolean solveMaze(int[][] maze, int row, int col, DirectionOrder order) {
        if (maze[TARGET_ROW][TARGET_COL] == VISITED) {
            return true;
        }

        if (!isInBounds(maze, row, col) || maze[row][col] != PATH) {
            return false;
        }

        maze[row][col] = VISITED;

        for (int[] dir : order.getDirections()) {
            int nextRow = row + dir[0];
            int nextCol = col + dir[1];
            if (solveMaze(maze, nextRow, nextCol, order)) {
                return true;
            }
        }

        maze[row][col] = DEAD_END;
        return false;
    }

    private static boolean isInBounds(int[][] maze, int row, int col) {
        return row >= 0 && row < maze.length && col >= 0 && col < maze[0].length;
    }
}