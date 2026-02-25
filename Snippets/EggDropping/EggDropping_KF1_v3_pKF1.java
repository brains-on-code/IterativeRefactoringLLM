package com.thealgorithms.dynamicprogramming;

/**
 * Calculates the minimum number of attempts needed in the worst case to find
 * the highest floor from which an egg can be dropped without breaking.
 */
public final class EggDropping {

    private EggDropping() {
    }

    public static int eggDrop(int eggCount, int floorCount) {
        int[][] minAttemptsForEggsAndFloors = new int[eggCount + 1][floorCount + 1];

        for (int currentEggCount = 1; currentEggCount <= eggCount; currentEggCount++) {
            minAttemptsForEggsAndFloors[currentEggCount][0] = 0;
            minAttemptsForEggsAndFloors[currentEggCount][1] = 1;
        }

        for (int currentFloorCount = 1; currentFloorCount <= floorCount; currentFloorCount++) {
            minAttemptsForEggsAndFloors[1][currentFloorCount] = currentFloorCount;
        }

        for (int currentEggCount = 2; currentEggCount <= eggCount; currentEggCount++) {
            for (int currentFloorCount = 2; currentFloorCount <= floorCount; currentFloorCount++) {
                minAttemptsForEggsAndFloors[currentEggCount][currentFloorCount] = Integer.MAX_VALUE;

                for (int dropFloor = 1; dropFloor <= currentFloorCount; dropFloor++) {
                    int attemptsIfEggBreaks =
                            minAttemptsForEggsAndFloors[currentEggCount - 1][dropFloor - 1];
                    int attemptsIfEggSurvives =
                            minAttemptsForEggsAndFloors[currentEggCount][currentFloorCount - dropFloor];
                    int worstCaseAttempts =
                            1 + Math.max(attemptsIfEggBreaks, attemptsIfEggSurvives);

                    if (worstCaseAttempts < minAttemptsForEggsAndFloors[currentEggCount][currentFloorCount]) {
                        minAttemptsForEggsAndFloors[currentEggCount][currentFloorCount] = worstCaseAttempts;
                    }
                }
            }
        }

        return minAttemptsForEggsAndFloors[eggCount][floorCount];
    }

    public static void main(String[] args) {
        int eggCount = 2;
        int floorCount = 4;
        int minimumAttempts = eggDrop(eggCount, floorCount);
        System.out.println(minimumAttempts);
    }
}