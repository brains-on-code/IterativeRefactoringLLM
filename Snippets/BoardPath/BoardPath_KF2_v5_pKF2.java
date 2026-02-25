package com.thealgorithms.dynamicprogramming;

public final class BoardPath {

    private static final int MIN_DICE = 1;
    private static final int MAX_DICE = 6;

    private BoardPath() {
        // Utility class; prevent instantiation
    }

    /**
     * Computes the number of ways to move from {@code start} to {@code end}
     * using dice rolls in the range [1, 6], via plain recursion.
     *
     * @param start starting position
     * @param end   target position
     * @return number of distinct paths from {@code start} to {@code end}
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
     * Computes the number of ways to move from {@code curr} to {@code end}
     * using dice rolls in the range [1, 6], via recursion with memoization.
     *
     * @param curr current position
     * @param end  target position
     * @param memo memoization array where {@code memo[i]} stores the number
     *             of ways to reach {@code end} from position {@code i}
     * @return number of distinct paths from {@code curr} to {@code end}
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
        for (int dice = MIN_DICE; dice <= MAX_DICE; dice++) {
            count += countPathsRecursiveMemo(curr + dice, end, memo);
        }

        memo[curr] = count;
        return count;
    }

    /**
     * Computes the number of ways to move from {@code curr} to {@code end}
     * using dice rolls in the range [1, 6], via bottom-up dynamic programming.
     *
     * @param curr starting position
     * @param end  target position
     * @param dp   array where {@code dp[i]} stores the number of ways
     *             to reach {@code end} from position {@code i}
     * @return number of distinct paths from {@code curr} to {@code end}
     */
    public static int countPathsIterative(int curr, int end, int[] dp) {
        dp[end] = 1;

        for (int position = end - 1; position >= 0; position--) {
            int count = 0;
            for (int dice = MIN_DICE; dice <= MAX_DICE && position + dice <= end; dice++) {
                count += dp[position + dice];
            }
            dp[position] = count;
        }

        return dp[curr];
    }
}