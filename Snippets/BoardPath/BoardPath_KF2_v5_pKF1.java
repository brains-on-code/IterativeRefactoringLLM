package com.thealgorithms.dynamicprogramming;

public final class BoardPath {

    private static final int MAX_DICE_FACE = 6;

    private BoardPath() {
    }

    public static int countPathsRecursive(int currentIndex, int targetIndex) {
        if (currentIndex == targetIndex) {
            return 1;
        } else if (currentIndex > targetIndex) {
            return 0;
        }

        int totalPaths = 0;
        for (int diceFace = 1; diceFace <= MAX_DICE_FACE; diceFace++) {
            totalPaths += countPathsRecursive(currentIndex + diceFace, targetIndex);
        }
        return totalPaths;
    }

    public static int countPathsMemoized(int currentIndex, int targetIndex, int[] memo) {
        if (currentIndex == targetIndex) {
            return 1;
        } else if (currentIndex > targetIndex) {
            return 0;
        }

        if (memo[currentIndex] != 0) {
            return memo[currentIndex];
        }

        int totalPaths = 0;
        for (int diceFace = 1; diceFace <= MAX_DICE_FACE; diceFace++) {
            totalPaths += countPathsMemoized(currentIndex + diceFace, targetIndex, memo);
        }

        memo[currentIndex] = totalPaths;
        return totalPaths;
    }

    public static int countPathsIterative(int startIndex, int targetIndex, int[] dp) {
        dp[targetIndex] = 1;

        for (int currentIndex = targetIndex - 1; currentIndex >= 0; currentIndex--) {
            int totalPaths = 0;
            for (int diceFace = 1;
                 diceFace <= MAX_DICE_FACE && currentIndex + diceFace <= targetIndex;
                 diceFace++) {
                totalPaths += dp[currentIndex + diceFace];
            }
            dp[currentIndex] = totalPaths;
        }

        return dp[startIndex];
    }
}