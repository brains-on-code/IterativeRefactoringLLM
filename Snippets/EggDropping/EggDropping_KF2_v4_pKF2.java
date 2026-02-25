package com.thealgorithms.dynamicprogramming;

public final class EggDropping {

    private EggDropping() {
        // Prevent instantiation
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

    private static void initializeBaseCases(int[][] dp, int eggs, int floors) {
        // For any number of eggs:
        // - 0 floors -> 0 trials
        // - 1 floor  -> 1 trial
        for (int e = 1; e <= eggs; e++) {
            dp[e][0] = 0;
            dp[e][1] = 1;
        }

        // With 1 egg and f floors, we must try every floor from 1 to f.
        for (int f = 1; f <= floors; f++) {
            dp[1][f] = f;
        }
    }

    private static void fillDpTable(int[][] dp, int eggs, int floors) {
        for (int e = 2; e <= eggs; e++) {
            for (int f = 2; f <= floors; f++) {
                dp[e][f] = Integer.MAX_VALUE;

                for (int x = 1; x <= f; x++) {
                    int trialsIfBreaks = dp[e - 1][x - 1];
                    int trialsIfNotBreaks = dp[e][f - x];
                    int worstCaseTrials = 1 + Math.max(trialsIfBreaks, trialsIfNotBreaks);

                    if (worstCaseTrials < dp[e][f]) {
                        dp[e][f] = worstCaseTrials;
                    }
                }
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