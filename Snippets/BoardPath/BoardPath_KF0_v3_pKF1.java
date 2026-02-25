package com.thealgorithms.dynamicprogramming;

public final class BoardPath {

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
        } else if (currentPosition > targetPosition) {
            return 0;
        }

        int totalPaths = 0;
        for (int diceRoll = 1; diceRoll <= 6; diceRoll++) {
            totalPaths += countPathsRecursive(currentPosition + diceRoll, targetPosition);
        }
        return totalPaths;
    }

    /**
     * Recursive solution with memoization.
     *
     * @param currentPosition the current board position
     * @param targetPosition  the target board position
     * @param memoizedResults memoization array
     * @return the number of ways to reach the target from the current position
     */
    public static int countPathsRecursiveMemo(int currentPosition, int targetPosition, int[] memoizedResults) {
        if (currentPosition == targetPosition) {
            return 1;
        } else if (currentPosition > targetPosition) {
            return 0;
        }

        if (memoizedResults[currentPosition] != 0) {
            return memoizedResults[currentPosition];
        }

        int totalPaths = 0;
        for (int diceRoll = 1; diceRoll <= 6; diceRoll++) {
            totalPaths += countPathsRecursiveMemo(currentPosition + diceRoll, targetPosition, memoizedResults);
        }

        memoizedResults[currentPosition] = totalPaths;
        return totalPaths;
    }

    /**
     * Iterative solution with tabulation.
     *
     * @param startPosition the starting board position (typically 0)
     * @param targetPosition the target board position
     * @param dpTable       dynamic programming table
     * @return the number of ways to reach the target from the start position
     */
    public static int countPathsIterative(int startPosition, int targetPosition, int[] dpTable) {
        dpTable[targetPosition] = 1;

        for (int position = targetPosition - 1; position >= 0; position--) {
            int totalPaths = 0;
            for (int diceRoll = 1; diceRoll <= 6 && position + diceRoll <= targetPosition; diceRoll++) {
                totalPaths += dpTable[position + diceRoll];
            }
            dpTable[position] = totalPaths;
        }

        return dpTable[startPosition];
    }
}