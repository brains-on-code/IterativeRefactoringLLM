package com.thealgorithms.dynamicprogramming;

public final class BoardPath {

    private BoardPath() {
        // Utility class; prevent instantiation
    }

    /**
     * Recursive solution without memoization.
     *
     * @param currentPosition the current position
     * @param targetPosition  the target position
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
     * @param currentPosition the current position
     * @param targetPosition  the target position
     * @param memo            memoization array
     * @return the number of ways to reach the target from the current position
     */
    public static int countPathsRecursiveMemo(int currentPosition, int targetPosition, int[] memo) {
        if (currentPosition == targetPosition) {
            return 1;
        } else if (currentPosition > targetPosition) {
            return 0;
        }

        if (memo[currentPosition] != 0) {
            return memo[currentPosition];
        }

        int totalPaths = 0;
        for (int diceRoll = 1; diceRoll <= 6; diceRoll++) {
            totalPaths += countPathsRecursiveMemo(currentPosition + diceRoll, targetPosition, memo);
        }

        memo[currentPosition] = totalPaths;
        return totalPaths;
    }

    /**
     * Iterative solution with tabulation.
     *
     * @param startPosition the starting position (always starts from 0)
     * @param targetPosition the target position
     * @param dpTable        dynamic programming table
     * @return the number of ways to reach the target from the start
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