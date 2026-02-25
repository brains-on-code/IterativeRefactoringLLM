package com.thealgorithms.dynamicprogramming;

/**
 * Dynamic programming solution for the Egg Dropping Puzzle.
 *
 * Given a number of eggs and floors, determines the minimum number of trials
 * needed in the worst case to find the highest floor from which an egg can be
 * dropped without breaking.
 */
public final class EggDropping {

    private EggDropping() {
        // Utility class; prevent instantiation
    }

    /**
     * Computes the minimum number of trials needed in the worst case with the
     * given number of eggs and floors.
     *
     * @param eggs   number of eggs
     * @param floors number of floors
     * @return minimum number of trials in the worst case
     */
    public static int minTrials(int eggs, int floors) {
        if (eggs <= 0 || floors <= 0) {
            return 0;
        }

        int[][] dp = new int[eggs + 1][floors + 1];

        // Base cases for any number of eggs:
        // 0 floors -> 0 trials
        // 1 floor  -> 1 trial
        for (int e = 1; e <= eggs; e++) {
            dp[e][0] = 0;
            dp[e][1] = 1;
        }

        // With 1 egg, we must try every floor from 1..floors (linear search)
        for (int f = 1; f <= floors; f++) {
            dp[1][f] = f;
        }

        // Bottom-up DP:
        // dp[e][f] = 1 + min over x in [1..f] of
        //            max(dp[e - 1][x - 1], dp[e][f - x])
        for (int e = 2; e <= eggs; e++) {
            for (int f = 2; f <= floors; f++) {
                dp[e][f] = computeMinTrialsForState(dp, e, f);
            }
        }

        return dp[eggs][floors];
    }

    /**
     * Computes the minimum number of trials needed for a given state (eggs, floors)
     * using the already-filled DP table.
     *
     * @param dp     dynamic programming table
     * @param eggs   current number of eggs
     * @param floors current number of floors
     * @return minimum number of trials for this state
     */
    private static int computeMinTrialsForState(int[][] dp, int eggs, int floors) {
        int minTrials = Integer.MAX_VALUE;

        for (int dropFloor = 1; dropFloor <= floors; dropFloor++) {
            int trialsIfBreaks = dp[eggs - 1][dropFloor - 1];
            int trialsIfSurvives = dp[eggs][floors - dropFloor];
            int worstCaseTrials = 1 + Math.max(trialsIfBreaks, trialsIfSurvives);

            if (worstCaseTrials < minTrials) {
                minTrials = worstCaseTrials;
            }
        }

        return minTrials;
    }

    public static void main(String[] args) {
        int eggs = 2;
        int floors = 4;
        int result = minTrials(eggs, floors);
        System.out.println(result);
    }
}