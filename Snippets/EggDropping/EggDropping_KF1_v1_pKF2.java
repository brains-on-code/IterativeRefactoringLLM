package com.thealgorithms.dynamicprogramming;

/**
 * Egg Dropping Puzzle:
 *
 * Given a certain number of eggs and floors, determine the minimum number of
 * attempts needed in the worst case to find the highest floor from which an egg
 * can be dropped without breaking.
 */
public final class Class1 {

    private Class1() {
        // Utility class; prevent instantiation.
    }

    /**
     * Computes the minimum number of attempts needed in the worst case to find
     * the critical floor, given a number of eggs and floors.
     *
     * @param eggs   number of eggs available
     * @param floors number of floors to test
     * @return minimum number of attempts in the worst case
     */
    public static int method1(int eggs, int floors) {
        int[][] dp = new int[eggs + 1][floors + 1];

        // Base cases:
        // With any number of eggs, 0 floors require 0 attempts, 1 floor requires 1 attempt.
        for (int e = 1; e <= eggs; e++) {
            dp[e][0] = 0;
            dp[e][1] = 1;
        }

        // With 1 egg, we must try every floor from 1 to floors (linear search).
        for (int f = 1; f <= floors; f++) {
            dp[1][f] = f;
        }

        // Fill the DP table for e >= 2 eggs and f >= 2 floors.
        for (int e = 2; e <= eggs; e++) {
            for (int f = 2; f <= floors; f++) {
                dp[e][f] = Integer.MAX_VALUE;

                // Try dropping an egg from each floor x (1..f) and take the worst case:
                // - If the egg breaks: we have e-1 eggs and x-1 floors below.
                // - If the egg doesn't break: we still have e eggs and f-x floors above.
                for (int x = 1; x <= f; x++) {
                    int attempts = 1 + Math.max(dp[e - 1][x - 1], dp[e][f - x]);
                    dp[e][f] = Math.min(dp[e][f], attempts);
                }
            }
        }

        return dp[eggs][floors];
    }

    public static void method2(String[] args) {
        int eggs = 2;
        int floors = 4;
        int result = method1(eggs, floors);
        System.out.println(result);
    }
}