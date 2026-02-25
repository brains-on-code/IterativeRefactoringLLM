package com.thealgorithms.backtracking;

/**
 * Backtracking-based pathfinding on a 2D grid.
 *
 * <p>Cell states:
 * <ul>
 *   <li>0 – unvisited, open cell</li>
 *   <li>2 – part of the current path</li>
 *   <li>3 – visited, dead-end cell</li>
 * </ul>
 *
 * <p>Both public methods search for a path from (1,1) to (6,5), differing only
 * in the order in which neighboring cells are explored.
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
     * Finds a path from (1,1) to (6,5) using backtracking.
     * Neighbor exploration order: down, right, up, left.
     *
     * @param grid the grid to search; modified in-place to mark the path
     * @return the grid with the path marked, or {@code null} if no path exists
     */
    public static int[][] method1(int[][] grid) {
        return findPathDownRightUpLeft(grid, 1, 1) ? grid : null;
    }

    /**
     * Finds a path from (1,1) to (6,5) using backtracking.
     * Neighbor exploration order: up, right, down, left.
     *
     * @param grid the grid to search; modified in-place to mark the path
     * @return the grid with the path marked, or {@code null} if no path exists
     */
    public static int[][] method2(int[][] grid) {
        return findPathUpRightDownLeft(grid, 1, 1) ? grid : null;
    }

    /**
     * Backtracking helper with neighbor order: down, right, up, left.
     */
    private static boolean findPathDownRightUpLeft(int[][] grid, int row, int col) {
        if (grid[TARGET_ROW][TARGET_COL] == PATH) {
            return true;
        }

        if (grid[row][col] != OPEN) {
            return false;
        }

        grid[row][col] = PATH;

        if (findPathDownRightUpLeft(grid, row + 1, col)   // down
                || findPathDownRightUpLeft(grid, row, col + 1) // right
                || findPathDownRightUpLeft(grid, row - 1, col) // up
                || findPathDownRightUpLeft(grid, row, col - 1) // left
        ) {
            return true;
        }

        grid[row][col] = DEAD_END;
        return false;
    }

    /**
     * Backtracking helper with neighbor order: up, right, down, left.
     */
    private static boolean findPathUpRightDownLeft(int[][] grid, int row, int col) {
        if (grid[TARGET_ROW][TARGET_COL] == PATH) {
            return true;
        }

        if (grid[row][col] != OPEN) {
            return false;
        }

        grid[row][col] = PATH;

        if (findPathUpRightDownLeft(grid, row - 1, col)   // up
                || findPathUpRightDownLeft(grid, row, col + 1) // right
                || findPathUpRightDownLeft(grid, row + 1, col) // down
                || findPathUpRightDownLeft(grid, row, col - 1) // left
        ) {
            return true;
        }

        grid[row][col] = DEAD_END;
        return false;
    }
}