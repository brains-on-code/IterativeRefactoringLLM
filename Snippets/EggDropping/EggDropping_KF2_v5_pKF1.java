package com.thealgorithms.dynamicprogramming;

public final class EggDropping {

    private EggDropping() {}

    public static int minTrials(int numberOfEggs, int numberOfFloors) {
        int[][] minTrialsForEggsAndFloors = new int[numberOfEggs + 1][numberOfFloors + 1];

        for (int egg = 1; egg <= numberOfEggs; egg++) {
            minTrialsForEggsAndFloors[egg][0] = 0;
            minTrialsForEggsAndFloors[egg][1] = 1;
        }

        for (int floor = 1; floor <= numberOfFloors; floor++) {
            minTrialsForEggsAndFloors[1][floor] = floor;
        }

        for (int egg = 2; egg <= numberOfEggs; egg++) {
            for (int floor = 2; floor <= numberOfFloors; floor++) {
                minTrialsForEggsAndFloors[egg][floor] = Integer.MAX_VALUE;

                for (int currentDropFloor = 1; currentDropFloor <= floor; currentDropFloor++) {
                    int trialsIfEggBreaks = minTrialsForEggsAndFloors[egg - 1][currentDropFloor - 1];
                    int trialsIfEggSurvives = minTrialsForEggsAndFloors[egg][floor - currentDropFloor];
                    int worstCaseTrials = 1 + Math.max(trialsIfEggBreaks, trialsIfEggSurvives);

                    if (worstCaseTrials < minTrialsForEggsAndFloors[egg][floor]) {
                        minTrialsForEggsAndFloors[egg][floor] = worstCaseTrials;
                    }
                }
            }
        }

        return minTrialsForEggsAndFloors[numberOfEggs][numberOfFloors];
    }

    public static void main(String[] args) {
        int numberOfEggs = 2;
        int numberOfFloors = 4;
        int minimumTrialsRequired = minTrials(numberOfEggs, numberOfFloors);
        System.out.println(minimumTrialsRequired);
    }
}