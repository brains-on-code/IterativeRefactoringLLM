package com.thealgorithms.dynamicprogramming;

public final class BoardPath {

    private BoardPath() {
        // Utility class; prevent instantiation
    }

    /**
     * Counts the number of ways to reach {@code end} from {@code start}
     * using a recursive solution without memoization.
     *
     * @param start the current position
     * @param end   the target position
     * @return the number of ways to reach {@code end} from {@code start}
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
     * Counts the number of ways to reach {@code end} from {@code curr}
     * using a top-down dynamic programming approach (recursion + memoization).
     *
     * @param curr the current position
     * @param end  the target position
     * @param dp   memoization array where {@code dp[i]} stores the number of ways
     *             to reach {@code end} from position {@code i}
     * @return the number of ways to reach {@code end} from {@code curr}
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

        int count = 0;
        for (int dice = 1; dice <= 6; dice++) {
            count += countPathsRecursiveMemo(curr + dice, end, dp);
        }
        dp[curr] = count;
        return count;
    }

    /**
     * Counts the number of ways to reach {@code end} from {@code curr}
     * using a bottom-up dynamic programming approach (tabulation).
     *
     * @param curr the starting position (typically 0)
     * @param end  the target position
     * @param dp   tabulation array where {@code dp[i]} will store the number of ways
     *             to reach {@code end} from position {@code i}
     * @return the number of ways to reach {@code end} from {@code curr}
     */
    public static int countPathsIterative(int curr, int end, int[] dp) {
        dp[end] = 1;

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