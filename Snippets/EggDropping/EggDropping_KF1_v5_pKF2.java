package com.thealgorithms.dynamicprogramming;

/**
 * Egg Dropping Puzzle:
 *
 * Given a number of eggs and floors, determine the minimum number of attempts
 * needed in the worst case to find the highest floor from which an egg can be
 * dropped without breaking.
 */
public final class EggDropping {

    private EggDropping() {
        // Utility class; prevent instantiation
    }

    /**
     * Computes the minimum number of attempts needed in the worst case to find
     * the critical floor, given a number of eggs and floors.
     *
     * @param eggs   number of eggs available
     * @param floors number of floors to test
     * @return minimum number of attempts in the worst case
     */
    public static int minAttempts(int eggs, int floors) {
        if (eggs <= 0 || floors <= 0) {
            return 0;
        }

        int[][] dp = new int[eggs + 1][floors + 1];

        // Initialize base cases for floors:
        // 0 floors -> 0 attempts, 1 floor -> 1 attempt
        for (int e = 1; e <= eggs; e++) {
            dp[e][0] = 0;
            dp[e][1] = 1;
        }

        // Initialize base case for eggs:
        // With 1 egg, we must try every floor (linear search)
        for (int f = 1; f <= floors; f++) {
            dp[1][f] = f;
        }

        // Fill the DP table for e >= 2 eggs and f >= 2 floors
        for (int e = 2; e <= eggs; e++) {
            for (int f = 2; f <= floors; f++) {
                dp[e][f] = Integer.MAX_VALUE;

                // Try dropping an egg from each floor x (1..f) and consider the worst case:
                // - Egg breaks:    e - 1 eggs, x - 1 floors below
                // - Egg survives:  e eggs,     f - x floors above
                for (int x = 1; x <= f; x++) {
                    int worstCaseAttempts = 1 + Math.max(dp[e - 1][x - 1], dp[e][f - x]);
                    dp[e][f] = Math.min(dp[e][f], worstCaseAttempts);
                }
            }
        }

        return dp[eggs][floors];
    }

    public static void main(String[] args) {
        int eggs = 2;
        int floors = 4;
        int result = minAttempts(eggs, floors);
        System.out.println(result);
    }
}