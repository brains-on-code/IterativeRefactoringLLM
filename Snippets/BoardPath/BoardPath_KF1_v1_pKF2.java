package com.thealgorithms.dynamicprogramming;

/**
 * Utility class for counting the number of ways to reach a target position
 * using dice rolls (1–6 steps at a time).
 */
public final class Class1 {

    private Class1() {
        // Prevent instantiation
    }

    /**
     * Counts the number of ways to reach {@code target} from {@code current}
     * using steps of size 1 to 6 (recursive, no memoization).
     *
     * @param current the starting position
     * @param target  the target position
     * @return the number of distinct ways to reach {@code target}
     */
    public static int method1(int current, int target) {
        if (current == target) {
            return 1;
        } else if (current > target) {
            return 0;
        }

        int ways = 0;
        for (int step = 1; step <= 6; step++) {
            ways += method1(current + step, target);
        }
        return ways;
    }

    /**
     * Counts the number of ways to reach {@code target} from {@code current}
     * using steps of size 1 to 6 (top-down DP with memoization).
     *
     * @param current the starting position
     * @param target  the target position
     * @param memo    memoization array where {@code memo[i]} stores the number
     *                of ways to reach {@code target} from position {@code i};
     *                must be at least of length {@code target + 1}
     * @return the number of distinct ways to reach {@code target}
     */
    public static int method2(int current, int target, int[] memo) {
        if (current == target) {
            return 1;
        } else if (current > target) {
            return 0;
        }

        if (memo[current] != 0) {
            return memo[current];
        }

        int ways = 0;
        for (int step = 1; step <= 6; step++) {
            ways += method2(current + step, target, memo);
        }
        memo[current] = ways;
        return ways;
    }

    /**
     * Counts the number of ways to reach {@code target} from {@code current}
     * using steps of size 1 to 6 (bottom-up DP).
     *
     * @param current the starting position
     * @param target  the target position
     * @param dp      DP array where {@code dp[i]} will store the number of ways
     *                to reach {@code target} from position {@code i};
     *                must be at least of length {@code target + 1}
     * @return the number of distinct ways to reach {@code target}
     */
    public static int method3(int current, int target, int[] dp) {
        dp[target] = 1;

        for (int pos = target - 1; pos >= 0; pos--) {
            int ways = 0;
            for (int step = 1; step <= 6 && pos + step <= target; step++) {
                ways += dp[pos + step];
            }
            dp[pos] = ways;
        }

        return dp[current];
    }
}