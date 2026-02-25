package com.thealgorithms.dynamicprogramming;

/**
 * Dynamic programming solution for the Egg Dropping Puzzle.
 */
public final class EggDropping {

    private EggDropping() {
        // Utility class
    }

    /**
     * Computes the minimum number of trials needed in the worst case with the given
     * number of eggs and floors.
     *
     * @param eggs   number of eggs
     * @param floors number of floors
     * @return minimum number of trials in the worst case
     */
    public static int minTrials(int eggs, int floors) {
        if (eggs <= 0 || floors < 0) {
            throw new IllegalArgumentException("Eggs must be > 0 and floors must be >= 0.");
        }

        int[][] dp = new int[eggs + 1][floors + 1];

        // Base cases:
        // 0 floors -> 0 trials, 1 floor -> 1 trial
        for (int e = 1; e <= eggs; e++) {
            dp[e][0] = 0;
            dp[e][1] = 1;
        }

        // With 1 egg, need 'f' trials for 'f' floors
        for (int f = 1; f <= floors; f++) {
            dp[1][f] = f;
        }

        // Fill the rest of the table using bottom-up DP
        for (int e = 2; e <= eggs; e++) {
            for (int f = 2; f <= floors; f++) {
                int minTrials = Integer.MAX_VALUE;

                for (int dropFloor = 1; dropFloor <= f; dropFloor++) {
                    int trialsIfBreaks = dp[e - 1][dropFloor - 1];
                    int trialsIfNotBreaks = dp[e][f - dropFloor];
                    int worstCaseTrials = 1 + Math.max(trialsIfBreaks, trialsIfNotBreaks);

                    if (worstCaseTrials < minTrials) {
                        minTrials = worstCaseTrials;
                    }
                }

                dp[e][f] = minTrials;
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