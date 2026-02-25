package com.thealgorithms.dynamicprogramming;

public final class BoardPath {

    private static final int MAX_DICE_FACE = 6;

    private BoardPath() {
    }

    public static int countPathsRecursive(int currentPosition, int targetPosition) {
        if (currentPosition == targetPosition) {
            return 1;
        } else if (currentPosition > targetPosition) {
            return 0;
        }

        int totalPaths = 0;
        for (int diceFace = 1; diceFace <= MAX_DICE_FACE; diceFace++) {
            totalPaths += countPathsRecursive(currentPosition + diceFace, targetPosition);
        }
        return totalPaths;
    }

    public static int countPathsMemoized(int currentPosition, int targetPosition, int[] memo) {
        if (currentPosition == targetPosition) {
            return 1;
        } else if (currentPosition > targetPosition) {
            return 0;
        }

        if (memo[currentPosition] != 0) {
            return memo[currentPosition];
        }

        int totalPaths = 0;
        for (int diceFace = 1; diceFace <= MAX_DICE_FACE; diceFace++) {
            totalPaths += countPathsMemoized(currentPosition + diceFace, targetPosition, memo);
        }

        memo[currentPosition] = totalPaths;
        return totalPaths;
    }

    public static int countPathsIterative(int startPosition, int targetPosition, int[] dp) {
        dp[targetPosition] = 1;

        for (int position = targetPosition - 1; position >= 0; position--) {
            int totalPaths = 0;
            for (int diceFace = 1; diceFace <= MAX_DICE_FACE && position + diceFace <= targetPosition; diceFace++) {
                totalPaths += dp[position + diceFace];
            }
            dp[position] = totalPaths;
        }

        return dp[startPosition];
    }
}