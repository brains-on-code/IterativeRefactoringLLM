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
        for (int currentEggCount = 1; currentEggCount <= eggCount; currentEggCount++) {
            minTrialsForEggsAndFloors[currentEggCount][0] = 0;
            minTrialsForEggsAndFloors[currentEggCount][1] = 1;
        }

        // With 1 egg, trials equal number of floors
        for (int currentFloorCount = 1; currentFloorCount <= floorCount; currentFloorCount++) {
            minTrialsForEggsAndFloors[1][currentFloorCount] = currentFloorCount;
        }

        // Bottom-up DP computation
        for (int currentEggCount = 2; currentEggCount <= eggCount; currentEggCount++) {
            for (int currentFloorCount = 2; currentFloorCount <= floorCount; currentFloorCount++) {
                minTrialsForEggsAndFloors[currentEggCount][currentFloorCount] = Integer.MAX_VALUE;

                for (int trialFloor = 1; trialFloor <= currentFloorCount; trialFloor++) {
                    int trialsIfEggBreaks =
                            minTrialsForEggsAndFloors[currentEggCount - 1][trialFloor - 1];
                    int trialsIfEggSurvives =
                            minTrialsForEggsAndFloors[currentEggCount][currentFloorCount - trialFloor];
                    int worstCaseTrialsForThisFloor =
                            1 + Math.max(trialsIfEggBreaks, trialsIfEggSurvives);

                    if (worstCaseTrialsForThisFloor
                            < minTrialsForEggsAndFloors[currentEggCount][currentFloorCount]) {
                        minTrialsForEggsAndFloors[currentEggCount][currentFloorCount] =
                                worstCaseTrialsForThisFloor;
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