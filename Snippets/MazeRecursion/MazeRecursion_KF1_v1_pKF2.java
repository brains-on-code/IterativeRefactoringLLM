package com.thealgorithms.backtracking;

/**
 * Utility class providing backtracking-based pathfinding on a 2D grid.
 *
 * <p>The grid uses the following conventions:
 * <ul>
 *   <li>0 – unvisited, open cell</li>
 *   <li>2 – part of the current path</li>
 *   <li>3 – visited, dead-end cell</li>
 * </ul>
 *
 * <p>Both public methods attempt to find a path from (1,1) to (6,5), but they
 * explore neighboring cells in different orders.
 */
public final class Class1 {

    private static final int TARGET_ROW = 6;
    private static final int TARGET_COL = 5;

    private static final int OPEN = 0;
    private static final int PATH = 2;
    private static final int DEAD_END = 3;

    private Class1() {
        // Utility class; prevent instantiation.
    }

    /**
     * Attempts to find a path from (1,1) to (6,5) using backtracking.
     * Explores neighbors in the order: down, right, up, left.
     *
     * @param grid the grid to search; modified in-place to mark the path
     * @return the grid with the path marked, or {@code null} if no path exists
     */
    public static int[][] method1(int[][] grid) {
        if (findPathDownRightUpLeft(grid, 1, 1)) {
            return grid;
        }
        return null;
    }

    /**
     * Attempts to find a path from (1,1) to (6,5) using backtracking.
     * Explores neighbors in the order: up, right, down, left.
     *
     * @param grid the grid to search; modified in-place to mark the path
     * @return the grid with the path marked, or {@code null} if no path exists
     */
    public static int[][] method2(int[][] grid) {
        if (findPathUpRightDownLeft(grid, 1, 1)) {
            return grid;
        }
        return null;
    }

    /**
     * Backtracking helper: explores neighbors in the order
     * down, right, up, left.
     */
    private static boolean findPathDownRightUpLeft(int[][] grid, int row, int col) {
        if (grid[TARGET_ROW][TARGET_COL] == PATH) {
            return true;
        }

        if (grid[row][col] == OPEN) {
            grid[row][col] = PATH;

            if (findPathDownRightUpLeft(grid, row + 1, col)) {
                return true;
            } else if (findPathDownRightUpLeft(grid, row, col + 1)) {
                return true;
            } else if (findPathDownRightUpLeft(grid, row - 1, col)) {
                return true;
            } else if (findPathDownRightUpLeft(grid, row, col - 1)) {
                return true;
            }

            grid[row][col] = DEAD_END;
            return false;
        }
        return false;
    }

    /**
     * Backtracking helper: explores neighbors in the order
     * up, right, down, left.
     */
    private static boolean findPathUpRightDownLeft(int[][] grid, int row, int col) {
        if (grid[TARGET_ROW][TARGET_COL] == PATH) {
            return true;
        }

        if (grid[row][col] == OPEN) {
            grid[row][col] = PATH;

            if (findPathUpRightDownLeft(grid, row - 1, col)) {
                return true;
            } else if (findPathUpRightDownLeft(grid, row, col + 1)) {
                return true;
            } else if (findPathUpRightDownLeft(grid, row + 1, col)) {
                return true;
            } else if (findPathUpRightDownLeft(grid, row, col - 1)) {
                return true;
            }

            grid[row][col] = DEAD_END;
            return false;
        }
        return false;
    }
}