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

        int count = 0;
        for (int dice = MIN_DICE; dice <= MAX_DICE; dice++) {
            count += countPathsRecursive(start + dice, end);
        }
        return count;
    }

    /**
     * Top-down DP with memoization.
     * {@code memo[i]} stores number of ways to reach {@code end} from position {@code i}.
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

        int count = 0;
        for (int dice = MIN_DICE; dice <= MAX_DICE; dice++) {
            count += countPathsMemoized(current + dice, end, memo);
        }

        memo[current] = count;
        return count;
    }

    /**
     * Bottom-up DP (iterative tabulation).
     * {@code dp[i]} stores number of ways to reach {@code end} from position {@code i}.
     */
    public static int countPathsIterative(int current, int end, int[] dp) {
        dp[end] = 1;

        for (int position = end - 1; position >= 0; position--) {
            int count = 0;
            for (int dice = MIN_DICE; dice <= MAX_DICE && position + dice <= end; dice++) {
                count += dp[position + dice];
            }
            dp[position] = count;
        }

        return dp[current];
    }
}