package com.thealgorithms.dynamicprogramming;

public final class BoardPath {

    private BoardPath() {
        // Utility class; prevent instantiation
    }

    /**
     * Counts the number of ways to move from {@code start} to {@code end}
     * using dice rolls (1–6) with plain recursion.
     */
    public static int countPathsRecursive(int start, int end) {
        if (start == end) {
            return 1;
        }
        if (start > end) {
            return 0;
        }

        int count = 0;
        for (int dice = 1; dice <= 6; dice++) {
            count += countPathsRecursive(start + dice, end);
        }
        return count;
    }

    /**
     * Counts the number of ways to move from {@code curr} to {@code end}
     * using dice rolls (1–6) with recursion + memoization.
     *
     * @param curr current position
     * @param end  target position
     * @param memo memoization array where {@code memo[i]} stores the number
     *             of ways to reach {@code end} from position {@code i}
     */
    public static int countPathsRecursiveMemo(int curr, int end, int[] memo) {
        if (curr == end) {
            return 1;
        }
        if (curr > end) {
            return 0;
        }

        if (memo[curr] != 0) {
            return memo[curr];
        }

        int count = 0;
        for (int dice = 1; dice <= 6; dice++) {
            count += countPathsRecursiveMemo(curr + dice, end, memo);
        }

        memo[curr] = count;
        return count;
    }

    /**
     * Counts the number of ways to move from {@code curr} to {@code end}
     * using dice rolls (1–6) with bottom-up tabulation.
     *
     * @param curr starting position
     * @param end  target position
     * @param dp   DP array where {@code dp[i]} will store the number of ways
     *             to reach {@code end} from position {@code i}
     */
    public static int countPathsIterative(int curr, int end, int[] dp) {
        dp[end] = 1; // Base case: exactly one way to be at the end

        for (int i = end - 1; i >= 0; i--) {
            int count = 0;
            for (int dice = 1; dice <= 6 && i + dice <= end; dice++) {
                count += dp[i + dice];
            }
            dp[i] = count;
        }

        return dp[curr];
    }
}