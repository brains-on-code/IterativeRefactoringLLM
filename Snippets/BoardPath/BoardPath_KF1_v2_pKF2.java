package com.thealgorithms.dynamicprogramming;

/**
 * Utility class for counting the number of ways to reach a target position
 * using dice-like moves (1–6 steps at a time).
 */
public final class DicePathCounter {

    private static final int MIN_STEP = 1;
    private static final int MAX_STEP = 6;

    private DicePathCounter() {
        // Utility class; prevent instantiation
    }

    /**
     * Counts the number of ways to reach {@code target} from {@code start}
     * using steps of size 1 to 6 (plain recursion, no memoization).
     *
     * @param start  the starting position
     * @param target the target position
     * @return the number of distinct ways to reach {@code target}
     */
    public static int countWaysRecursive(int start, int target) {
        if (start == target) {
            return 1;
        }
        if (start > target) {
            return 0;
        }

        int ways = 0;
        for (int step = MIN_STEP; step <= MAX_STEP; step++) {
            ways += countWaysRecursive(start + step, target);
        }
        return ways;
    }

    /**
     * Counts the number of ways to reach {@code target} from {@code start}
     * using steps of size 1 to 6 (top-down dynamic programming with memoization).
     *
     * @param start the starting position
     * @param target the target position
     * @param memo memoization array where {@code memo[i]} stores the number
     *             of ways to reach {@code target} from position {@code i};
     *             must be at least of length {@code target + 1}
     * @return the number of distinct ways to reach {@code target}
     */
    public static int countWaysTopDown(int start, int target, int[] memo) {
        if (start == target) {
            return 1;
        }
        if (start > target) {
            return 0;
        }

        if (memo[start] != 0) {
            return memo[start];
        }

        int ways = 0;
        for (int step = MIN_STEP; step <= MAX_STEP; step++) {
            ways += countWaysTopDown(start + step, target, memo);
        }
        memo[start] = ways;
        return ways;
    }

    /**
     * Counts the number of ways to reach {@code target} from {@code start}
     * using steps of size 1 to 6 (bottom-up dynamic programming).
     *
     * @param start the starting position
     * @param target the target position
     * @param dp DP array where {@code dp[i]} will store the number of ways
     *           to reach {@code target} from position {@code i};
     *           must be at least of length {@code target + 1}
     * @return the number of distinct ways to reach {@code target}
     */
    public static int countWaysBottomUp(int start, int target, int[] dp) {
        dp[target] = 1;

        for (int position = target - 1; position >= 0; position--) {
            int ways = 0;
            for (int step = MIN_STEP; step <= MAX_STEP && position + step <= target; step++) {
                ways += dp[position + step];
            }
            dp[position] = ways;
        }

        return dp[start];
    }
}