package com.thealgorithms.dynamicprogramming;

public final class EggDropping {

    private EggDropping() {}

    public static int minTrials(int numberOfEggs, int numberOfFloors) {
        int[][] minTrialsForEggsAndFloors = new int[numberOfEggs + 1][numberOfFloors + 1];

        for (int eggIndex = 1; eggIndex <= numberOfEggs; eggIndex++) {
            minTrialsForEggsAndFloors[eggIndex][0] = 0;
            minTrialsForEggsAndFloors[eggIndex][1] = 1;
        }

        for (int floorIndex = 1; floorIndex <= numberOfFloors; floorIndex++) {
            minTrialsForEggsAndFloors[1][floorIndex] = floorIndex;
        }

        for (int eggIndex = 2; eggIndex <= numberOfEggs; eggIndex++) {
            for (int floorIndex = 2; floorIndex <= numberOfFloors; floorIndex++) {
                minTrialsForEggsAndFloors[eggIndex][floorIndex] = Integer.MAX_VALUE;

                for (int currentDropFloor = 1; currentDropFloor <= floorIndex; currentDropFloor++) {
                    int trialsIfEggBreaks =
                        minTrialsForEggsAndFloors[eggIndex - 1][currentDropFloor - 1];
                    int trialsIfEggSurvives =
                        minTrialsForEggsAndFloors[eggIndex][floorIndex - currentDropFloor];
                    int worstCaseTrials =
                        1 + Math.max(trialsIfEggBreaks, trialsIfEggSurvives);

                    if (worstCaseTrials < minTrialsForEggsAndFloors[eggIndex][floorIndex]) {
                        minTrialsForEggsAndFloors[eggIndex][floorIndex] = worstCaseTrials;
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