package com.thealgorithms.backtracking;

/**
 * Solves a maze using recursive backtracking.
 *
 * <p>The maze is represented as a 2D int array with the following semantics:
 * <ul>
 *   <li>0 - unvisited, open cell</li>
 *   <li>1 - wall</li>
 *   <li>2 - part of the current valid path</li>
 *   <li>3 - visited dead end</li>
 * </ul>
 *
 * <p>The goal is to find a path from the starting position (1,1) to the target
 * position (6,5).
 */
public final class MazeRecursion {

    private static final int TARGET_ROW = 6;
    private static final int TARGET_COL = 5;

    private static final int UNVISITED = 0;
    private static final int PATH = 2;
    private static final int DEAD_END = 3;

    private MazeRecursion() {
        // Utility class; prevent instantiation.
    }

    /**
     * Solves the maze using the movement order: down → right → up → left.
     *
     * @param map the maze grid
     * @return the maze with the path marked, or {@code null} if no path exists
     */
    public static int[][] solveMazeUsingFirstStrategy(int[][] map) {
        return setWay(map, 1, 1) ? map : null;
    }

    /**
     * Solves the maze using the movement order: up → right → down → left.
     *
     * @param map the maze grid
     * @return the maze with the path marked, or {@code null} if no path exists
     */
    public static int[][] solveMazeUsingSecondStrategy(int[][] map) {
        return setWay2(map, 1, 1) ? map : null;
    }

    /**
     * Recursive backtracking with movement order: down → right → up → left.
     *
     * @param map the maze grid
     * @param row current row
     * @param col current column
     * @return {@code true} if a path to the target is found
     */
    private static boolean setWay(int[][] map, int row, int col) {
        if (map[TARGET_ROW][TARGET_COL] == PATH) {
            return true;
        }

        if (map[row][col] == UNVISITED) {
            map[row][col] = PATH;

            // down
            if (setWay(map, row + 1, col)) {
                return true;
            }
            // right
            if (setWay(map, row, col + 1)) {
                return true;
            }
            // up
            if (setWay(map, row - 1, col)) {
                return true;
            }
            // left
            if (setWay(map, row, col - 1)) {
                return true;
            }

            map[row][col] = DEAD_END;
        }

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
    private static boolean setWay2(int[][] map, int row, int col) {
        if (map[TARGET_ROW][TARGET_COL] == PATH) {
            return true;
        }

        if (map[row][col] == UNVISITED) {
            map[row][col] = PATH;

            // up
            if (setWay2(map, row - 1, col)) {
                return true;
            }
            // right
            if (setWay2(map, row, col + 1)) {
                return true;
            }
            // down
            if (setWay2(map, row + 1, col)) {
                return true;
            }
            // left
            if (setWay2(map, row, col - 1)) {
                return true;
            }

            map[row][col] = DEAD_END;
        }

        return false;
    }
}