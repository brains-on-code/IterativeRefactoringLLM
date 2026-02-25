package com.thealgorithms.backtracking;

/**
 * Maze solver using recursive backtracking.
 *
 * <p>Cell values:
 * <ul>
 *   <li>0 - unvisited, open cell</li>
 *   <li>1 - wall</li>
 *   <li>2 - part of the current valid path</li>
 *   <li>3 - visited dead end</li>
 * </ul>
 *
 * <p>Attempts to find a path from (1,1) to (6,5).
 */
public final class MazeRecursion {

    private static final int TARGET_ROW = 6;
    private static final int TARGET_COL = 5;

    private static final int UNVISITED = 0;
    private static final int PATH = 2;
    private static final int DEAD_END = 3;

    private MazeRecursion() {
        // Prevent instantiation.
    }

    /**
     * Solve the maze using movement order: down → right → up → left.
     *
     * @param map the maze grid
     * @return the maze with the path marked, or {@code null} if no path exists
     */
    public static int[][] solveMazeUsingFirstStrategy(int[][] map) {
        return findPathDownRightUpLeft(map, 1, 1) ? map : null;
    }

    /**
     * Solve the maze using movement order: up → right → down → left.
     *
     * @param map the maze grid
     * @return the maze with the path marked, or {@code null} if no path exists
     */
    public static int[][] solveMazeUsingSecondStrategy(int[][] map) {
        return findPathUpRightDownLeft(map, 1, 1) ? map : null;
    }

    /**
     * Recursive backtracking with movement order: down → right → up → left.
     *
     * @param map the maze grid
     * @param row current row
     * @param col current column
     * @return {@code true} if a path to the target is found
     */
    private static boolean findPathDownRightUpLeft(int[][] map, int row, int col) {
        if (map[TARGET_ROW][TARGET_COL] == PATH) {
            return true;
        }

        if (map[row][col] != UNVISITED) {
            return false;
        }

        map[row][col] = PATH;

        // Try moving: down, right, up, left.
        if (findPathDownRightUpLeft(map, row + 1, col)
                || findPathDownRightUpLeft(map, row, col + 1)
                || findPathDownRightUpLeft(map, row - 1, col)
                || findPathDownRightUpLeft(map, row, col - 1)) {
            return true;
        }

        map[row][col] = DEAD_END;
        return false;
    }

    /**
     * Recursive backtracking with movement order: up → right → down → left.
     *
     * @param map the maze grid
     * @param row current row
     * @param col current column
     * @return {@code true} if a path to the target is found
     */
    private static boolean findPathUpRightDownLeft(int[][] map, int row, int col) {
        if (map[TARGET_ROW][TARGET_COL] == PATH) {
            return true;
        }

        if (map[row][col] != UNVISITED) {
            return false;
        }

        map[row][col] = PATH;

        // Try moving: up, right, down, left.
        if (findPathUpRightDownLeft(map, row - 1, col)
                || findPathUpRightDownLeft(map, row, col + 1)
                || findPathUpRightDownLeft(map, row + 1, col)
                || findPathUpRightDownLeft(map, row, col - 1)) {
            return true;
        }

        map[row][col] = DEAD_END;
        return false;
    }
}