package com.thealgorithms.dynamicprogramming;

public final class EggDropping {

    private EggDropping() {
        // Utility class; prevent instantiation
    }

    /**
     * Computes the minimum number of trials needed in the worst case to determine
     * the highest floor from which an egg can be dropped without breaking.
     *
     * @param eggs   number of eggs (must be > 0)
     * @param floors number of floors (must be >= 0)
     * @return minimum number of trials in the worst case
     */
    public static int minTrials(int eggs, int floors) {
        validateInput(eggs, floors);

        int[][] dp = new int[eggs + 1][floors + 1];

        initializeBaseCases(dp, eggs, floors);
        fillDpTable(dp, eggs, floors);

        return dp[eggs][floors];
    }

    private static void validateInput(int eggs, int floors) {
        if (eggs <= 0) {
            throw new IllegalArgumentException("Eggs must be > 0.");
        }
        if (floors < 0) {
            throw new IllegalArgumentException("Floors must be >= 0.");
        }
    }

    private static void initializeBaseCases(int[][] dp, int eggs, int floors) {
        // 0 floors -> 0 trials, 1 floor -> 1 trial (for any number of eggs)
        for (int e = 1; e <= eggs; e++) {
            dp[e][0] = 0;
            if (floors >= 1) {
                dp[e][1] = 1;
            }
        }

        // With 1 egg, trials = number of floors (linear search)
        for (int f = 1; f <= floors; f++) {
            dp[1][f] = f;
        }
    }

    private static void fillDpTable(int[][] dp, int eggs, int floors) {
        for (int e = 2; e <= eggs; e++) {
            for (int f = 2; f <= floors; f++) {
                dp[e][f] = computeMinTrialsForState(dp, e, f);
            }
        }
    }

    private static int computeMinTrialsForState(int[][] dp, int eggs, int floors) {
        int minTrials = Integer.MAX_VALUE;

        for (int dropFloor = 1; dropFloor <= floors; dropFloor++) {
            int trialsIfBreaks = dp[eggs - 1][dropFloor - 1];
            int trialsIfNotBreaks = dp[eggs][floors - dropFloor];
            int worstCaseTrials = 1 + Math.max(trialsIfBreaks, trialsIfNotBreaks);

            minTrials = Math.min(minTrials, worstCaseTrials);
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