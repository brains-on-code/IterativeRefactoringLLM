package com.thealgorithms.dynamicprogramming;

public final class EggDropping {

    private EggDropping() {
        // Utility class; prevent instantiation
    }

    /**
     * Computes the minimum number of trials needed in the worst case to determine
     * the highest floor from which an egg can be dropped without breaking.
     *
     * @param eggs   number of eggs available
     * @param floors number of floors to test
     * @return minimum number of trials in the worst case
     */
    public static int minTrials(int eggs, int floors) {
        int[][] dp = new int[eggs + 1][floors + 1];

        // Base cases:
        // With 0 floors, 0 trials are needed; with 1 floor, 1 trial is needed.
        for (int e = 1; e <= eggs; e++) {
            dp[e][0] = 0;
            dp[e][1] = 1;
        }

        // With 1 egg, we need 'f' trials for 'f' floors (linear search).
        for (int f = 1; f <= floors; f++) {
            dp[1][f] = f;
        }

        // Fill the rest of the table using dynamic programming.
        for (int e = 2; e <= eggs; e++) {
            for (int f = 2; f <= floors; f++) {
                dp[e][f] = Integer.MAX_VALUE;

                // Try dropping from each floor x and take the worst-case result:
                // - If the egg breaks: we have e-1 eggs and x-1 floors below.
                // - If the egg doesn't break: we still have e eggs and f-x floors above.
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

        return dp[eggs][floors];
    }

    public static void main(String[] args) {
        int eggs = 2;
        int floors = 4;
        int result = minTrials(eggs, floors);
        System.out.println(result);
    }
}