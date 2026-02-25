package com.thealgorithms.dynamicprogramming;

/**
 * Computes the minimum number of attempts needed in the egg dropping problem.
 */
public final class EggDropping {

    private EggDropping() {
        // Utility class; prevent instantiation
    }

    /**
     * Computes the minimum number of attempts needed in the worst case to find
     * the highest floor from which an egg can be dropped without breaking.
     *
     * @param eggs   number of eggs available
     * @param floors number of floors to test
     * @return minimum number of attempts in the worst case
     */
    public static int minAttempts(int eggs, int floors) {
        int[][] dp = new int[eggs + 1][floors + 1];

        // Base cases:
        // With 1 egg, we need 'floor' attempts (linear search).
        for (int floor = 1; floor <= floors; floor++) {
            dp[1][floor] = floor;
        }

        // With any number of eggs, 0 floors needs 0 attempts, 1 floor needs 1 attempt.
        for (int egg = 1; egg <= eggs; egg++) {
            dp[egg][0] = 0;
            dp[egg][1] = 1;
        }

        // Fill the rest of the table using DP
        for (int egg = 2; egg <= eggs; egg++) {
            for (int floor = 2; floor <= floors; floor++) {
                dp[egg][floor] = Integer.MAX_VALUE;

                for (int dropFloor = 1; dropFloor <= floor; dropFloor++) {
                    int attemptsIfBreaks = dp[egg - 1][dropFloor - 1];
                    int attemptsIfNotBreaks = dp[egg][floor - dropFloor];
                    int worstCaseAttempts = 1 + Math.max(attemptsIfBreaks, attemptsIfNotBreaks);

                    if (worstCaseAttempts < dp[egg][floor]) {
                        dp[egg][floor] = worstCaseAttempts;
                    }
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