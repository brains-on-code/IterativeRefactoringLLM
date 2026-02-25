package com.thealgorithms.dynamicprogramming;

/**
 * Calculates the minimum number of attempts needed in the worst case to find
 * the highest floor from which an egg can be dropped without breaking.
 */
public final class EggDropping {

    private EggDropping() {
    }

    public static int eggDrop(int eggCount, int floorCount) {
        int[][] minAttempts = new int[eggCount + 1][floorCount + 1];

        for (int eggs = 1; eggs <= eggCount; eggs++) {
            minAttempts[eggs][0] = 0;
            minAttempts[eggs][1] = 1;
        }

        for (int floors = 1; floors <= floorCount; floors++) {
            minAttempts[1][floors] = floors;
        }

        for (int eggs = 2; eggs <= eggCount; eggs++) {
            for (int floors = 2; floors <= floorCount; floors++) {
                minAttempts[eggs][floors] = Integer.MAX_VALUE;

                for (int dropFloor = 1; dropFloor <= floors; dropFloor++) {
                    int attemptsIfEggBreaks = minAttempts[eggs - 1][dropFloor - 1];
                    int attemptsIfEggSurvives = minAttempts[eggs][floors - dropFloor];
                    int worstCaseAttempts = 1 + Math.max(attemptsIfEggBreaks, attemptsIfEggSurvives);

                    if (worstCaseAttempts < minAttempts[eggs][floors]) {
                        minAttempts[eggs][floors] = worstCaseAttempts;
                    }
                }
            }
        }

        return minAttempts[eggCount][floorCount];
    }

    public static void main(String[] args) {
        int eggCount = 2;
        int floorCount = 4;
        int minimumAttempts = eggDrop(eggCount, floorCount);
        System.out.println(minimumAttempts);
    }
}