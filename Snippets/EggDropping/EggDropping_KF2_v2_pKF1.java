package com.thealgorithms.dynamicprogramming;

public final class EggDropping {

    private EggDropping() {}

    public static int minTrials(int eggCount, int floorCount) {
        int[][] minTrialsTable = new int[eggCount + 1][floorCount + 1];

        for (int egg = 1; egg <= eggCount; egg++) {
            minTrialsTable[egg][0] = 0;
            minTrialsTable[egg][1] = 1;
        }

        for (int floor = 1; floor <= floorCount; floor++) {
            minTrialsTable[1][floor] = floor;
        }

        for (int egg = 2; egg <= eggCount; egg++) {
            for (int floor = 2; floor <= floorCount; floor++) {
                minTrialsTable[egg][floor] = Integer.MAX_VALUE;

                for (int dropFloor = 1; dropFloor <= floor; dropFloor++) {
                    int trialsIfEggBreaks = minTrialsTable[egg - 1][dropFloor - 1];
                    int trialsIfEggSurvives = minTrialsTable[egg][floor - dropFloor];
                    int worstCaseTrials = 1 + Math.max(trialsIfEggBreaks, trialsIfEggSurvives);

                    if (worstCaseTrials < minTrialsTable[egg][floor]) {
                        minTrialsTable[egg][floor] = worstCaseTrials;
                    }
                }
            }
        }

        return minTrialsTable[eggCount][floorCount];
    }

    public static void main(String[] args) {
        int eggCount = 2;
        int floorCount = 4;
        int result = minTrials(eggCount, floorCount);
        System.out.println(result);
    }
}