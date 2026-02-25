package com.thealgorithms.dynamicprogramming;

public final class BoardPath {

    private BoardPath() {
        // Utility class; prevent instantiation
    }

    /**
     * Recursive solution without memoization.
     *
     * @param currentIndex the current board index
     * @param targetIndex  the target board index
     * @return the number of ways to reach the target from the current index
     */
    public static int countPathsRecursive(int currentIndex, int targetIndex) {
        if (currentIndex == targetIndex) {
            return 1;
        } else if (currentIndex > targetIndex) {
            return 0;
        }

        int pathCount = 0;
        for (int diceFace = 1; diceFace <= 6; diceFace++) {
            pathCount += countPathsRecursive(currentIndex + diceFace, targetIndex);
        }
        return pathCount;
    }

    /**
     * Recursive solution with memoization.
     *
     * @param currentIndex the current board index
     * @param targetIndex  the target board index
     * @param memo         memoization array
     * @return the number of ways to reach the target from the current index
     */
    public static int countPathsRecursiveMemo(int currentIndex, int targetIndex, int[] memo) {
        if (currentIndex == targetIndex) {
            return 1;
        } else if (currentIndex > targetIndex) {
            return 0;
        }

        if (memo[currentIndex] != 0) {
            return memo[currentIndex];
        }

        int pathCount = 0;
        for (int diceFace = 1; diceFace <= 6; diceFace++) {
            pathCount += countPathsRecursiveMemo(currentIndex + diceFace, targetIndex, memo);
        }

        memo[currentIndex] = pathCount;
        return pathCount;
    }

    /**
     * Iterative solution with tabulation.
     *
     * @param startIndex  the starting board index (typically 0)
     * @param targetIndex the target board index
     * @param dp          dynamic programming table
     * @return the number of ways to reach the target from the start index
     */
    public static int countPathsIterative(int startIndex, int targetIndex, int[] dp) {
        dp[targetIndex] = 1;

        for (int index = targetIndex - 1; index >= 0; index--) {
            int pathCount = 0;
            for (int diceFace = 1; diceFace <= 6 && index + diceFace <= targetIndex; diceFace++) {
                pathCount += dp[index + diceFace];
            }
            dp[index] = pathCount;
        }

        return dp[startIndex];
    }
}