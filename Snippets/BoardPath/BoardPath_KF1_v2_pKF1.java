package com.thealgorithms.dynamicprogramming;

public final class DiceThrow {

    private static final int DICE_SIDES = 6;

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
        }
        if (currentPosition > targetPosition) {
            return 0;
        }

        int totalWays = 0;
        for (int diceValue = 1; diceValue <= DICE_SIDES; diceValue++) {
            totalWays += countWaysRecursive(currentPosition + diceValue, targetPosition);
        }
        return totalWays;
    }

    /**
     * Counts the number of ways to reach the target position from the current
     * position using dice throws (top-down DP with memoization).
     *
     * @param currentPosition the starting position
     * @param targetPosition  the destination position
     * @param memoizedWays    memoization array where memoizedWays[i] stores the number
     *                        of ways to reach targetPosition from position i
     * @return number of ways to reach targetPosition from currentPosition
     */
    public static int countWaysMemoized(int currentPosition, int targetPosition, int[] memoizedWays) {
        if (currentPosition == targetPosition) {
            return 1;
        }
        if (currentPosition > targetPosition) {
            return 0;
        }
        if (memoizedWays[currentPosition] != 0) {
            return memoizedWays[currentPosition];
        }

        int totalWays = 0;
        for (int diceValue = 1; diceValue <= DICE_SIDES; diceValue++) {
            totalWays += countWaysMemoized(currentPosition + diceValue, targetPosition, memoizedWays);
        }
        memoizedWays[currentPosition] = totalWays;
        return totalWays;
    }

    /**
     * Counts the number of ways to reach the target position from the current
     * position using dice throws (bottom-up DP).
     *
     * @param startPosition  the starting position
     * @param targetPosition the destination position
     * @param waysFromPosition DP array where waysFromPosition[i] will store the number
     *                         of ways to reach targetPosition from position i
     * @return number of ways to reach targetPosition from startPosition
     */
    public static int countWaysBottomUp(int startPosition, int targetPosition, int[] waysFromPosition) {
        waysFromPosition[targetPosition] = 1;

        for (int position = targetPosition - 1; position >= 0; position--) {
            int totalWays = 0;
            for (int diceValue = 1; diceValue <= DICE_SIDES && position + diceValue <= targetPosition; diceValue++) {
                totalWays += waysFromPosition[position + diceValue];
            }
            waysFromPosition[position] = totalWays;
        }
        return waysFromPosition[startPosition];
    }
}