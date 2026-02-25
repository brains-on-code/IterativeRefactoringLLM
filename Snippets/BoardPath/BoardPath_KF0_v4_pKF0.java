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
     * @param start the current position
     * @param end   the target position
     * @return the number of ways to reach the end from the start
     */
    public static int countPathsRecursive(int start, int end) {
        if (start == end) {
            return 1;
        }
        if (start > end) {
            return 0;
        }

        int totalWays = 0;
        for (int diceValue = MIN_DICE_VALUE; diceValue <= MAX_DICE_VALUE; diceValue++) {
            totalWays += countPathsRecursive(start + diceValue, end);
        }
        return totalWays;
    }

    /**
     * Recursive solution with memoization.
     *
     * @param current the current position
     * @param end     the target position
     * @param memo    memoization array
     * @return the number of ways to reach the end from the current position
     */
    public static int countPathsRecursiveMemo(int current, int end, int[] memo) {
        if (current == end) {
            return 1;
        }
        if (current > end) {
            return 0;
        }

        if (memo[current] != 0) {
            return memo[current];
        }

        memo[current] = calculateWaysFromPositionRecursive(current, end, memo);
        return memo[current];
    }

    private static int calculateWaysFromPositionRecursive(int position, int end, int[] memo) {
        int totalWays = 0;
        for (int diceValue = MIN_DICE_VALUE; diceValue <= MAX_DICE_VALUE; diceValue++) {
            totalWays += countPathsRecursiveMemo(position + diceValue, end, memo);
        }
        return totalWays;
    }

    /**
     * Iterative solution with tabulation.
     *
     * @param start the starting position (typically 0)
     * @param end   the target position
     * @param dp    tabulation array
     * @return the number of ways to reach the end from the start
     */
    public static int countPathsIterative(int start, int end, int[] dp) {
        dp[end] = 1;

        for (int position = end - 1; position >= start; position--) {
            dp[position] = calculateWaysFromPositionIterative(position, end, dp);
        }

        return dp[start];
    }

    private static int calculateWaysFromPositionIterative(int position, int end, int[] dp) {
        int totalWays = 0;

        for (int diceValue = MIN_DICE_VALUE; diceValue <= MAX_DICE_VALUE; diceValue++) {
            int nextPosition = position + diceValue;
            if (nextPosition > end) {
                break;
            }
            totalWays += dp[nextPosition];
        }

        return totalWays;
    }
}