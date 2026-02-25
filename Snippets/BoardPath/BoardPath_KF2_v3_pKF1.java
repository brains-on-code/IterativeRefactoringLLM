package com.thealgorithms.dynamicprogramming;

public final class BoardPath {

    private static final int MAX_DICE_FACE = 6;

    private BoardPath() {
    }

    public static int countPathsRecursive(int position, int targetPosition) {
        if (position == targetPosition) {
            return 1;
        } else if (position > targetPosition) {
            return 0;
        }

        int totalPaths = 0;
        for (int diceFace = 1; diceFace <= MAX_DICE_FACE; diceFace++) {
            totalPaths += countPathsRecursive(position + diceFace, targetPosition);
        }
        return totalPaths;
    }

    public static int countPathsMemoized(int position, int targetPosition, int[] memo) {
        if (position == targetPosition) {
            return 1;
        } else if (position > targetPosition) {
            return 0;
        }

        if (memo[position] != 0) {
            return memo[position];
        }

        int totalPaths = 0;
        for (int diceFace = 1; diceFace <= MAX_DICE_FACE; diceFace++) {
            totalPaths += countPathsMemoized(position + diceFace, targetPosition, memo);
        }

        memo[position] = totalPaths;
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