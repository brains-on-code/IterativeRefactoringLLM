package com.thealgorithms.dynamicprogramming;

public final class Class1 {

    private static final int MAX_STEP = 6;

    private Class1() {
        // Utility class; prevent instantiation
    }

    /**
     * Counts the number of ways to reach the target position from the current
     * position using steps of size 1 to 6 (inclusive).
     *
     * @param current the current position
     * @param target  the target position
     * @return the number of ways to reach {@code target} from {@code current}
     */
    public static int countWaysRecursive(int current, int target) {
        if (current == target) {
            return 1;
        }
        if (current > target) {
            return 0;
        }

        int ways = 0;
        for (int step = 1; step <= MAX_STEP; step++) {
            ways += countWaysRecursive(current + step, target);
        }
        return ways;
    }

    /**
     * Counts the number of ways to reach the target position from the current
     * position using steps of size 1 to 6 (inclusive), with memoization.
     *
     * @param current the current position
     * @param target  the target position
     * @param memo    memoization array where {@code memo[i]} stores the number
     *                of ways to reach {@code target} from position {@code i}
     * @return the number of ways to reach {@code target} from {@code current}
     */
    public static int countWaysMemoized(int current, int target, int[] memo) {
        if (current == target) {
            return 1;
        }
        if (current > target) {
            return 0;
        }

        if (memo[current] != 0) {
            return memo[current];
        }

        memo[current] = countWaysFromPositionMemo(current, target, memo);
        return memo[current];
    }

    private static int countWaysFromPositionMemo(int current, int target, int[] memo) {
        int ways = 0;
        for (int step = 1; step <= MAX_STEP; step++) {
            ways += countWaysMemoized(current + step, target, memo);
        }
        return ways;
    }

    /**
     * Counts the number of ways to reach the target position from the current
     * position using steps of size 1 to 6 (inclusive), using bottom-up DP.
     *
     * @param current the current position
     * @param target  the target position
     * @param dp      DP array where {@code dp[i]} will store the number of ways
     *                to reach {@code target} from position {@code i}
     * @return the number of ways to reach {@code target} from {@code current}
     */
    public static int countWaysBottomUp(int current, int target, int[] dp) {
        dp[target] = 1;

        for (int position = target - 1; position >= 0; position--) {
            dp[position] = countWaysFromPositionBottomUp(position, target, dp);
        }

        return dp[current];
    }

    private static int countWaysFromPositionBottomUp(int position, int target, int[] dp) {
        int ways = 0;
        for (int step = 1; step <= MAX_STEP && position + step <= target; step++) {
            ways += dp[position + step];
        }
        return ways;
    }
}