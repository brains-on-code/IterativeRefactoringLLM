package com.thealgorithms.dynamicprogramming;

public final class EggDropping {

    private EggDropping() {
        // Utility class; prevent instantiation
    }

    /**
     * Computes the minimum number of trials required (in the worst case) to find
     * the highest floor from which an egg can be dropped without breaking.
     *
     * @param eggs   number of eggs available
     * @param floors number of floors to test
     * @return minimum number of trials in the worst case
     */
    public static int minTrials(int eggs, int floors) {
        int[][] dp = new int[eggs + 1][floors + 1];

        initializeBaseCases(dp, eggs, floors);
        fillDpTable(dp, eggs, floors);

        return dp[eggs][floors];
    }

    /**
     * Initializes the DP table with base cases:
     * - 0 floors  -> 0 trials
     * - 1 floor   -> 1 trial
     * - 1 egg, f floors -> f trials (must test each floor sequentially)
     */
    private static void initializeBaseCases(int[][] dp, int eggs, int floors) {
        for (int e = 1; e <= eggs; e++) {
            dp[e][0] = 0;
            dp[e][1] = 1;
        }

        for (int f = 1; f <= floors; f++) {
            dp[1][f] = f;
        }
    }

    /**
     * Fills the DP table using the recurrence:
     * dp[e][f] = 1 + min over x in [1, f] of
     *            max(dp[e - 1][x - 1], dp[e][f - x])
     * where:
     * - x is the floor from which we drop the egg,
     * - dp[e - 1][x - 1] is the case when the egg breaks,
     * - dp[e][f - x] is the case when the egg does not break.
     */
    private static void fillDpTable(int[][] dp, int eggs, int floors) {
        for (int e = 2; e <= eggs; e++) {
            for (int f = 2; f <= floors; f++) {
                int minTrialsForCurrent = Integer.MAX_VALUE;

                for (int x = 1; x <= f; x++) {
                    int trialsIfBreaks = dp[e - 1][x - 1];
                    int trialsIfNotBreaks = dp[e][f - x];
                    int worstCaseTrials = 1 + Math.max(trialsIfBreaks, trialsIfNotBreaks);

                    if (worstCaseTrials < minTrialsForCurrent) {
                        minTrialsForCurrent = worstCaseTrials;
                    }
                }

                dp[e][f] = minTrialsForCurrent;
            }
        }
    }

    public static void main(String[] args) {
        int eggs = 2;
        int floors = 4;
        int result = minTrials(eggs, floors);
        System.out.println(result);
    }
}