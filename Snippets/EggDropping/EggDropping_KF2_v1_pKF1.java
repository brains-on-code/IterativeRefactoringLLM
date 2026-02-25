package com.thealgorithms.dynamicprogramming;

public final class EggDropping {

    private EggDropping() {}

    public static int minTrials(int eggCount, int floorCount) {
        int[][] minTrialsForEggsAndFloors = new int[eggCount + 1][floorCount + 1];

        for (int eggs = 1; eggs <= eggCount; eggs++) {
            minTrialsForEggsAndFloors[eggs][0] = 0;
            minTrialsForEggsAndFloors[eggs][1] = 1;
        }

        for (int floors = 1; floors <= floorCount; floors++) {
            minTrialsForEggsAndFloors[1][floors] = floors;
        }

        for (int eggs = 2; eggs <= eggCount; eggs++) {
            for (int floors = 2; floors <= floorCount; floors++) {
                minTrialsForEggsAndFloors[eggs][floors] = Integer.MAX_VALUE;

                for (int droppingFloor = 1; droppingFloor <= floors; droppingFloor++) {
                    int trialsIfEggBreaks = minTrialsForEggsAndFloors[eggs - 1][droppingFloor - 1];
                    int trialsIfEggSurvives = minTrialsForEggsAndFloors[eggs][floors - droppingFloor];
                    int worstCaseTrials = 1 + Math.max(trialsIfEggBreaks, trialsIfEggSurvives);

                    if (worstCaseTrials < minTrialsForEggsAndFloors[eggs][floors]) {
                        minTrialsForEggsAndFloors[eggs][floors] = worstCaseTrials;
                    }
                }
            }
        }

        return minTrialsForEggsAndFloors[eggCount][floorCount];
    }

    public static void main(String[] args) {
        int eggCount = 2;
        int floorCount = 4;
        int result = minTrials(eggCount, floorCount);
        System.out.println(result);
    }
}