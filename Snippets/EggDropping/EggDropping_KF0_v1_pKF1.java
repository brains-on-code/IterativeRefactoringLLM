package com.thealgorithms.dynamicprogramming;

/**
 * Dynamic programming solution for the Egg Dropping Puzzle.
 */
public final class EggDropping {

    private EggDropping() {
        // Utility class
    }

    /**
     * Computes the minimum number of trials needed in the worst case to determine
     * the highest floor from which an egg can be dropped without breaking.
     *
     * @param eggCount   number of eggs
     * @param floorCount number of floors
     * @return minimum number of trials in the worst case
     */
    public static int minTrials(int eggCount, int floorCount) {
        int[][] minTrialsForEggAndFloor = new int[eggCount + 1][floorCount + 1];

        // Base cases:
        // 0 floors -> 0 trials, 1 floor -> 1 trial
        for (int eggs = 1; eggs <= eggCount; eggs++) {
            minTrialsForEggAndFloor[eggs][0] = 0;
            minTrialsForEggAndFloor[eggs][1] = 1;
        }

        // With 1 egg, trials equal number of floors
        for (int floors = 1; floors <= floorCount; floors++) {
            minTrialsForEggAndFloor[1][floors] = floors;
        }

        // Bottom-up DP computation
        for (int eggs = 2; eggs <= eggCount; eggs++) {
            for (int floors = 2; floors <= floorCount; floors++) {
                minTrialsForEggAndFloor[eggs][floors] = Integer.MAX_VALUE;

                for (int dropFloor = 1; dropFloor <= floors; dropFloor++) {
                    int trialsIfBreaks = minTrialsForEggAndFloor[eggs - 1][dropFloor - 1];
                    int trialsIfNotBreaks = minTrialsForEggAndFloor[eggs][floors - dropFloor];
                    int worstCaseTrials = 1 + Math.max(trialsIfBreaks, trialsIfNotBreaks);

                    if (worstCaseTrials < minTrialsForEggAndFloor[eggs][floors]) {
                        minTrialsForEggAndFloor[eggs][floors] = worstCaseTrials;
                    }
                }
            }
        }

        return minTrialsForEggAndFloor[eggCount][floorCount];
    }

    public static void main(String[] args) {
        int eggCount = 2;
        int floorCount = 4;
        int result = minTrials(eggCount, floorCount);
        System.out.println(result);
    }
}