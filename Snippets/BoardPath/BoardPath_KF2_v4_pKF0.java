package com.thealgorithms.dynamicprogramming;

public final class BoardPath {

    private static final int MIN_DICE = 1;
    private static final int MAX_DICE = 6;

    private BoardPath() {
        // Utility class; prevent instantiation
    }

    /**
     * Pure recursive solution: count ways to reach {@code end} from {@code start}.
     */
    public static int countPathsRecursive(int start, int end) {
        if (start == end) {
            return 1;
        }
        if (start > end) {
            return 0;
        }

        int totalPaths = 0;
        for (int diceRoll = MIN_DICE; diceRoll <= MAX_DICE; diceRoll++) {
            totalPaths += countPathsRecursive(start + diceRoll, end);
        }
        return totalPaths;
    }

    /**
     * Top-down DP with memoization.
     * {@code memo[position]} stores number of ways to reach {@code end} from {@code position}.
     */
    public static int countPathsMemoized(int position, int end, int[] memo) {
        if (position == end) {
            return 1;
        }
        if (position > end) {
            return 0;
        }

        if (memo[position] != 0) {
            return memo[position];
        }

        memo[position] = calculatePathsFromPosition(position, end, memo);
        return memo[position];
    }

    private static int calculatePathsFromPosition(int position, int end, int[] memo) {
        int totalPaths = 0;
        for (int diceRoll = MIN_DICE; diceRoll <= MAX_DICE; diceRoll++) {
            totalPaths += countPathsMemoized(position + diceRoll, end, memo);
        }
        return totalPaths;
    }

    /**
     * Bottom-up DP (iterative tabulation).
     * {@code dp[position]} stores number of ways to reach {@code end} from {@code position}.
     */
    public static int countPathsIterative(int start, int end, int[] dp) {
        dp[end] = 1;

        for (int position = end - 1; position >= 0; position--) {
            dp[position] = calculateIterativePaths(position, end, dp);
        }

        return dp[start];
    }

    private static int calculateIterativePaths(int position, int end, int[] dp) {
        int totalPaths = 0;
        for (int diceRoll = MIN_DICE; diceRoll <= MAX_DICE && position + diceRoll <= end; diceRoll++) {
            totalPaths += dp[position + diceRoll];
        }
        return totalPaths;
    }
}