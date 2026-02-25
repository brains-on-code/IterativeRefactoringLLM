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
    public static int countPathsMemoized(int current, int end, int[] memo) {
        if (current == end) {
            return 1;
        }
        if (current > end) {
            return 0;
        }

        if (memo[current] != 0) {
            return memo[current];
        }

        int totalPaths = 0;
        for (int diceRoll = MIN_DICE; diceRoll <= MAX_DICE; diceRoll++) {
            totalPaths += countPathsMemoized(current + diceRoll, end, memo);
        }

        memo[current] = totalPaths;
        return totalPaths;
    }

    /**
     * Bottom-up DP (iterative tabulation).
     * {@code dp[position]} stores number of ways to reach {@code end} from {@code position}.
     */
    public static int countPathsIterative(int current, int end, int[] dp) {
        dp[end] = 1;

        for (int position = end - 1; position >= 0; position--) {
            int totalPaths = 0;
            for (int diceRoll = MIN_DICE; diceRoll <= MAX_DICE && position + diceRoll <= end; diceRoll++) {
                totalPaths += dp[position + diceRoll];
            }
            dp[position] = totalPaths;
        }

        return dp[current];
    }
}