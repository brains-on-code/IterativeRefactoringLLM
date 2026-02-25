package com.thealgorithms.dynamicprogramming;

public final class EggDropping {

    private EggDropping() {}

    public static int minTrials(int eggCount, int floorCount) {
        int[][] minTrialsTable = new int[eggCount + 1][floorCount + 1];

        for (int eggs = 1; eggs <= eggCount; eggs++) {
            minTrialsTable[eggs][0] = 0;
            minTrialsTable[eggs][1] = 1;
        }

        for (int floors = 1; floors <= floorCount; floors++) {
            minTrialsTable[1][floors] = floors;
        }

        for (int eggs = 2; eggs <= eggCount; eggs++) {
            for (int floors = 2; floors <= floorCount; floors++) {
                minTrialsTable[eggs][floors] = Integer.MAX_VALUE;

                for (int dropFloor = 1; dropFloor <= floors; dropFloor++) {
                    int trialsIfEggBreaks = minTrialsTable[eggs - 1][dropFloor - 1];
                    int trialsIfEggSurvives = minTrialsTable[eggs][floors - dropFloor];
                    int worstCaseTrials = 1 + Math.max(trialsIfEggBreaks, trialsIfEggSurvives);

                    if (worstCaseTrials < minTrialsTable[eggs][floors]) {
                        minTrialsTable[eggs][floors] = worstCaseTrials;
                    }
                }
            }
        }

        return minTrialsTable[eggCount][floorCount];
    }

    public static void main(String[] args) {
        int eggCount = 2;
        int floorCount = 4;
        int minimumTrialsRequired = minTrials(eggCount, floorCount);
        System.out.println(minimumTrialsRequired);
    }
}