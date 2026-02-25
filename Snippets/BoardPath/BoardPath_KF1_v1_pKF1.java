package com.thealgorithms.dynamicprogramming;

public final class DiceThrow {

    private DiceThrow() {
    }

    /**
     * Counts the number of ways to reach the target position from the current
     * position using dice throws (recursive solution).
     *
     * @param currentPosition the starting position
     * @param targetPosition  the destination position
     * @return number of ways to reach targetPosition from currentPosition
     */
    public static int countWaysRecursive(int currentPosition, int targetPosition) {
        if (currentPosition == targetPosition) {
            return 1;
        } else if (currentPosition > targetPosition) {
            return 0;
        }
        int totalWays = 0;
        for (int diceFace = 1; diceFace <= 6; diceFace++) {
            totalWays += countWaysRecursive(currentPosition + diceFace, targetPosition);
        }
        return totalWays;
    }

    /**
     * Counts the number of ways to reach the target position from the current
     * position using dice throws (top-down DP with memoization).
     *
     * @param currentPosition the starting position
     * @param targetPosition  the destination position
     * @param memo            memoization array where memo[i] stores the number of ways
     *                        to reach targetPosition from position i
     * @return number of ways to reach targetPosition from currentPosition
     */
    public static int countWaysMemoized(int currentPosition, int targetPosition, int[] memo) {
        if (currentPosition == targetPosition) {
            return 1;
        } else if (currentPosition > targetPosition) {
            return 0;
        }
        if (memo[currentPosition] != 0) {
            return memo[currentPosition];
        }
        int totalWays = 0;
        for (int diceFace = 1; diceFace <= 6; diceFace++) {
            totalWays += countWaysMemoized(currentPosition + diceFace, targetPosition, memo);
        }
        memo[currentPosition] = totalWays;
        return totalWays;
    }

    /**
     * Counts the number of ways to reach the target position from the current
     * position using dice throws (bottom-up DP).
     *
     * @param startPosition the starting position
     * @param targetPosition the destination position
     * @param dp            DP array where dp[i] will store the number of ways
     *                      to reach targetPosition from position i
     * @return number of ways to reach targetPosition from startPosition
     */
    public static int countWaysBottomUp(int startPosition, int targetPosition, int[] dp) {
        dp[targetPosition] = 1;
        for (int position = targetPosition - 1; position >= 0; position--) {
            int totalWays = 0;
            for (int diceFace = 1; diceFace <= 6 && position + diceFace <= targetPosition; diceFace++) {
                totalWays += dp[position + diceFace];
            }
            dp[position] = totalWays;
        }
        return dp[startPosition];
    }
}