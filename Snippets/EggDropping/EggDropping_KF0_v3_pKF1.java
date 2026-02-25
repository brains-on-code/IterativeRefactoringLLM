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
        int[][] minTrialsForEggsAndFloors = new int[eggCount + 1][floorCount + 1];

        // Base cases:
        // 0 floors -> 0 trials, 1 floor -> 1 trial
        for (int eggIndex = 1; eggIndex <= eggCount; eggIndex++) {
            minTrialsForEggsAndFloors[eggIndex][0] = 0;
            minTrialsForEggsAndFloors[eggIndex][1] = 1;
        }

        // With 1 egg, trials equal number of floors
        for (int floorIndex = 1; floorIndex <= floorCount; floorIndex++) {
            minTrialsForEggsAndFloors[1][floorIndex] = floorIndex;
        }

        // Bottom-up DP computation
        for (int eggIndex = 2; eggIndex <= eggCount; eggIndex++) {
            for (int floorIndex = 2; floorIndex <= floorCount; floorIndex++) {
                minTrialsForEggsAndFloors[eggIndex][floorIndex] = Integer.MAX_VALUE;

                for (int dropFloor = 1; dropFloor <= floorIndex; dropFloor++) {
                    int trialsIfEggBreaks = minTrialsForEggsAndFloors[eggIndex - 1][dropFloor - 1];
                    int trialsIfEggSurvives = minTrialsForEggsAndFloors[eggIndex][floorIndex - dropFloor];
                    int worstCaseTrialsForDrop =
                            1 + Math.max(trialsIfEggBreaks, trialsIfEggSurvives);

                    if (worstCaseTrialsForDrop < minTrialsForEggsAndFloors[eggIndex][floorIndex]) {
                        minTrialsForEggsAndFloors[eggIndex][floorIndex] = worstCaseTrialsForDrop;
                    }
                }
            }
        }

        return minTrialsForEggsAndFloors[eggCount][floorCount];
    }

    public static void main(String[] args) {
        int eggCount = 2;
        int floorCount = 4;
        int minimumTrialsRequired = minTrials(eggCount, floorCount);
        System.out.println(minimumTrialsRequired);
    }
}