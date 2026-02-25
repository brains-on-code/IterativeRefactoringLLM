package com.thealgorithms.dynamicprogramming;

public final class BoardPath {

    private static final int MIN_DICE = 1;
    private static final int MAX_DICE = 6;

    private BoardPath() {
        // Prevent instantiation
    }

    /**
     * Returns the number of ways to reach {@code end} from {@code start}
     * using plain recursion (no memoization).
     */
    public static int countPathsRecursive(int start, int end) {
        if (start == end) {
            return 1;
        }
        if (start > end) {
            return 0;
        }

        int ways = 0;
        for (int dice = MIN_DICE; dice <= MAX_DICE; dice++) {
            ways += countPathsRecursive(start + dice, end);
        }
        return ways;
    }

    /**
     * Returns the number of ways to reach {@code end} from {@code curr}
     * using recursion with memoization (top-down DP).
     *
     * <p>{@code dp[i]} stores the number of ways to reach {@code end}
     * from position {@code i}. A value of 0 means "not computed yet".</p>
     */
    public static int countPathsRecursiveMemo(int curr, int end, int[] dp) {
        if (curr == end) {
            return 1;
        }
        if (curr > end) {
            return 0;
        }
        if (dp[curr] != 0) {
            return dp[curr];
        }

        int ways = 0;
        for (int dice = MIN_DICE; dice <= MAX_DICE; dice++) {
            ways += countPathsRecursiveMemo(curr + dice, end, dp);
        }
        dp[curr] = ways;
        return ways;
    }

    /**
     * Returns the number of ways to reach {@code end} from {@code curr}
     * using tabulation (bottom-up DP).
     *
     * <p>{@code dp[i]} will contain the number of ways to reach {@code end}
     * from position {@code i} after this method completes.</p>
     */
    public static int countPathsIterative(int curr, int end, int[] dp) {
        dp[end] = 1;

        for (int position = end - 1; position >= 0; position--) {
            int ways = 0;
            for (int dice = MIN_DICE; dice <= MAX_DICE && position + dice <= end; dice++) {
                ways += dp[position + dice];
            }
            dp[position] = ways;
        }

        return dp[curr];
    }
}