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

        for (int eggIndex = 1; eggIndex <= eggCount; eggIndex++) {
            minAttemptsForEggsAndFloors[eggIndex][0] = 0;
            minAttemptsForEggsAndFloors[eggIndex][1] = 1;
        }

        for (int floorIndex = 1; floorIndex <= floorCount; floorIndex++) {
            minAttemptsForEggsAndFloors[1][floorIndex] = floorIndex;
        }

        for (int eggIndex = 2; eggIndex <= eggCount; eggIndex++) {
            for (int floorIndex = 2; floorIndex <= floorCount; floorIndex++) {
                minAttemptsForEggsAndFloors[eggIndex][floorIndex] = Integer.MAX_VALUE;

                for (int currentDropFloor = 1; currentDropFloor <= floorIndex; currentDropFloor++) {
                    int attemptsIfEggBreaks =
                        minAttemptsForEggsAndFloors[eggIndex - 1][currentDropFloor - 1];
                    int attemptsIfEggSurvives =
                        minAttemptsForEggsAndFloors[eggIndex][floorIndex - currentDropFloor];
                    int worstCaseAttempts =
                        1 + Math.max(attemptsIfEggBreaks, attemptsIfEggSurvives);

                    if (worstCaseAttempts < minAttemptsForEggsAndFloors[eggIndex][floorIndex]) {
                        minAttemptsForEggsAndFloors[eggIndex][floorIndex] = worstCaseAttempts;
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