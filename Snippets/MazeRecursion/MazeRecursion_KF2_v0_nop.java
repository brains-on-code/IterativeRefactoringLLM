package com.thealgorithms.backtracking;


public final class MazeRecursion {

    private MazeRecursion() {
    }


    public static int[][] solveMazeUsingFirstStrategy(int[][] map) {
        if (setWay(map, 1, 1)) {
            return map;
        }
        return null;
    }


    public static int[][] solveMazeUsingSecondStrategy(int[][] map) {
        if (setWay2(map, 1, 1)) {
            return map;
        }
        return null;
    }


    private static boolean setWay(int[][] map, int i, int j) {
        if (map[6][5] == 2) {
            return true;
        }

        if (map[i][j] == 0) {
            map[i][j] = 2;

            if (setWay(map, i + 1, j)) {
                return true;
            }
            else if (setWay(map, i, j + 1)) {
                return true;
            }
            else if (setWay(map, i - 1, j)) {
                return true;
            }
            else if (setWay(map, i, j - 1)) {
                return true;
            }

            map[i][j] = 3;
            return false;
        }
        return false;
    }


    private static boolean setWay2(int[][] map, int i, int j) {
        if (map[6][5] == 2) {
            return true;
        }

        if (map[i][j] == 0) {
            map[i][j] = 2;

            if (setWay2(map, i - 1, j)) {
                return true;
            }
            else if (setWay2(map, i, j + 1)) {
                return true;
            }
            else if (setWay2(map, i + 1, j)) {
                return true;
            }
            else if (setWay2(map, i, j - 1)) {
                return true;
            }

            map[i][j] = 3;
            return false;
        }
        return false;
    }
}
