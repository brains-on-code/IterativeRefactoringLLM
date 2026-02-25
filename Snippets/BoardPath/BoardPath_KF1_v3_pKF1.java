package com.thealgorithms.dynamicprogramming;

public final class DiceThrow {

    private static final int FACES_PER_DIE = 6;

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

        int numberOfWays = 0;
        for (int faceValue = 1; faceValue <= FACES_PER_DIE; faceValue++) {
            numberOfWays += countWaysRecursive(currentPosition + faceValue, targetPosition);
        }
        return numberOfWays;
    }

    /**
     * Counts the number of ways to reach the target position from the current
     * position using dice throws (top-down DP with memoization).
     *
     * @param currentPosition the starting position
     * @param targetPosition  the destination position
     * @param waysFromPositionCache memoization array where waysFromPositionCache[i]
     *                              stores the number of ways to reach targetPosition
     *                              from position i
     * @return number of ways to reach targetPosition from currentPosition
     */
    public static int countWaysMemoized(int currentPosition, int targetPosition, int[] waysFromPositionCache) {
        if (currentPosition == targetPosition) {
            return 1;
        }
        if (currentPosition > targetPosition) {
            return 0;
        }
        if (waysFromPositionCache[currentPosition] != 0) {
            return waysFromPositionCache[currentPosition];
        }

        int numberOfWays = 0;
        for (int faceValue = 1; faceValue <= FACES_PER_DIE; faceValue++) {
            numberOfWays += countWaysMemoized(currentPosition + faceValue, targetPosition, waysFromPositionCache);
        }
        waysFromPositionCache[currentPosition] = numberOfWays;
        return numberOfWays;
    }

    /**
     * Counts the number of ways to reach the target position from the current
     * position using dice throws (bottom-up DP).
     *
     * @param startPosition     the starting position
     * @param targetPosition    the destination position
     * @param waysFromPosition  DP array where waysFromPosition[i] will store the
     *                          number of ways to reach targetPosition from position i
     * @return number of ways to reach targetPosition from startPosition
     */
    public static int countWaysBottomUp(int startPosition, int targetPosition, int[] waysFromPosition) {
        waysFromPosition[targetPosition] = 1;

        for (int position = targetPosition - 1; position >= 0; position--) {
            int numberOfWays = 0;
            for (int faceValue = 1; faceValue <= FACES_PER_DIE && position + faceValue <= targetPosition; faceValue++) {
                numberOfWays += waysFromPosition[position + faceValue];
            }
            waysFromPosition[position] = numberOfWays;
        }
        return waysFromPosition[startPosition];
    }
}