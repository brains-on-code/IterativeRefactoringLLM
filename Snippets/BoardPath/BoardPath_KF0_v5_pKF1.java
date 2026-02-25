package com.thealgorithms.dynamicprogramming;

public final class BoardPath {

    private static final int MIN_DIE_FACE = 1;
    private static final int MAX_DIE_FACE = 6;

    private BoardPath() {
        // Utility class; prevent instantiation
    }

    /**
     * Recursive solution without memoization.
     *
     * @param currentPosition the current board position
     * @param targetPosition  the target board position
     * @return the number of ways to reach the target from the current position
     */
    public static int countPathsRecursive(int currentPosition, int targetPosition) {
        if (currentPosition == targetPosition) {
            return 1;
        }
        if (currentPosition > targetPosition) {
            return 0;
        }

        int totalPaths = 0;
        for (int dieFace = MIN_DIE_FACE; dieFace <= MAX_DIE_FACE; dieFace++) {
            totalPaths += countPathsRecursive(currentPosition + dieFace, targetPosition);
        }
        return totalPaths;
    }

    /**
     * Recursive solution with memoization.
     *
     * @param currentPosition the current board position
     * @param targetPosition  the target board position
     * @param memoizedCounts  memoization array
     * @return the number of ways to reach the target from the current position
     */
    public static int countPathsRecursiveMemo(int currentPosition, int targetPosition, int[] memoizedCounts) {
        if (currentPosition == targetPosition) {
            return 1;
        }
        if (currentPosition > targetPosition) {
            return 0;
        }

        if (memoizedCounts[currentPosition] != 0) {
            return memoizedCounts[currentPosition];
        }

        int totalPaths = 0;
        for (int dieFace = MIN_DIE_FACE; dieFace <= MAX_DIE_FACE; dieFace++) {
            totalPaths += countPathsRecursiveMemo(currentPosition + dieFace, targetPosition, memoizedCounts);
        }

        memoizedCounts[currentPosition] = totalPaths;
        return totalPaths;
    }

    /**
     * Iterative solution with tabulation.
     *
     * @param startPosition the starting board position (typically 0)
     * @param targetPosition the target board position
     * @param pathsFromPosition dynamic programming table where index represents position
     * @return the number of ways to reach the target from the start position
     */
    public static int countPathsIterative(int startPosition, int targetPosition, int[] pathsFromPosition) {
        pathsFromPosition[targetPosition] = 1;

        for (int position = targetPosition - 1; position >= 0; position--) {
            int totalPaths = 0;
            for (int dieFace = MIN_DIE_FACE;
                 dieFace <= MAX_DIE_FACE && position + dieFace <= targetPosition;
                 dieFace++) {
                totalPaths += pathsFromPosition[position + dieFace];
            }
            pathsFromPosition[position] = totalPaths;
        }

        return pathsFromPosition[startPosition];
    }
}