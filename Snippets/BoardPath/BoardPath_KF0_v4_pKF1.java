package com.thealgorithms.dynamicprogramming;

public final class BoardPath {

    private static final int MIN_DICE_VALUE = 1;
    private static final int MAX_DICE_VALUE = 6;

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

        int totalPathsFromCurrent = 0;
        for (int diceValue = MIN_DICE_VALUE; diceValue <= MAX_DICE_VALUE; diceValue++) {
            totalPathsFromCurrent += countPathsRecursive(currentPosition + diceValue, targetPosition);
        }
        return totalPathsFromCurrent;
    }

    /**
     * Recursive solution with memoization.
     *
     * @param currentPosition the current board position
     * @param targetPosition  the target board position
     * @param memoizedPaths   memoization array
     * @return the number of ways to reach the target from the current position
     */
    public static int countPathsRecursiveMemo(int currentPosition, int targetPosition, int[] memoizedPaths) {
        if (currentPosition == targetPosition) {
            return 1;
        }
        if (currentPosition > targetPosition) {
            return 0;
        }

        if (memoizedPaths[currentPosition] != 0) {
            return memoizedPaths[currentPosition];
        }

        int totalPathsFromCurrent = 0;
        for (int diceValue = MIN_DICE_VALUE; diceValue <= MAX_DICE_VALUE; diceValue++) {
            totalPathsFromCurrent += countPathsRecursiveMemo(currentPosition + diceValue, targetPosition, memoizedPaths);
        }

        memoizedPaths[currentPosition] = totalPathsFromCurrent;
        return totalPathsFromCurrent;
    }

    /**
     * Iterative solution with tabulation.
     *
     * @param startPosition  the starting board position (typically 0)
     * @param targetPosition the target board position
     * @param pathsFromIndex dynamic programming table where index represents position
     * @return the number of ways to reach the target from the start position
     */
    public static int countPathsIterative(int startPosition, int targetPosition, int[] pathsFromIndex) {
        pathsFromIndex[targetPosition] = 1;

        for (int position = targetPosition - 1; position >= 0; position--) {
            int totalPathsFromPosition = 0;
            for (int diceValue = MIN_DICE_VALUE;
                 diceValue <= MAX_DICE_VALUE && position + diceValue <= targetPosition;
                 diceValue++) {
                totalPathsFromPosition += pathsFromIndex[position + diceValue];
            }
            pathsFromIndex[position] = totalPathsFromPosition;
        }

        return pathsFromIndex[startPosition];
    }
}