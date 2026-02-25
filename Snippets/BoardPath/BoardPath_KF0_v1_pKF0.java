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
        for (int dice = MIN_DICE_VALUE; dice <= MAX_DICE_VALUE; dice++) {
            totalWays += countPathsRecursive(start + dice, end);
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

        int totalWays = 0;
        for (int dice = MIN_DICE_VALUE; dice <= MAX_DICE_VALUE; dice++) {
            totalWays += countPathsRecursiveMemo(current + dice, end, memo);
        }
        memo[current] = totalWays;
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

        for (int position = end - 1; position >= 0; position--) {
            int totalWays = 0;
            for (int dice = MIN_DICE_VALUE; dice <= MAX_DICE_VALUE && position + dice <= end; dice++) {
                totalWays += dp[position + dice];
            }
            dp[position] = totalWays;
        }

        return dp[start];
    }
}