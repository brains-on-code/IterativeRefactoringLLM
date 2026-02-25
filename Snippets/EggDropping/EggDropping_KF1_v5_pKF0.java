package com.thealgorithms.dynamicprogramming;

/**
 * Computes the minimum number of attempts needed in the egg dropping problem.
 */
public final class EggDropping {

    private EggDropping() {
        // Utility class; prevent instantiation
    }

    /**
     * Computes the minimum number of attempts needed in the worst case to find
     * the highest floor from which an egg can be dropped without breaking.
     *
     * @param eggs   number of eggs available
     * @param floors number of floors to test
     * @return minimum number of attempts in the worst case
     */
    public static int minAttempts(int eggs, int floors) {
        if (eggs <= 0 || floors <= 0) {
            return 0;
        }

        int[][] attempts = new int[eggs + 1][floors + 1];

        initializeBaseCases(attempts, eggs, floors);
        fillDynamicProgrammingTable(attempts, eggs, floors);

        return attempts[eggs][floors];
    }

    private static void initializeBaseCases(int[][] attempts, int eggs, int floors) {
        // With 1 egg, we need 'floor' attempts (linear search).
        for (int floor = 1; floor <= floors; floor++) {
            attempts[1][floor] = floor;
        }

        // With any number of eggs:
        // 0 floors needs 0 attempts, 1 floor needs 1 attempt.
        for (int egg = 1; egg <= eggs; egg++) {
            attempts[egg][0] = 0;
            attempts[egg][1] = 1;
        }
    }

    private static void fillDynamicProgrammingTable(int[][] attempts, int eggs, int floors) {
        for (int egg = 2; egg <= eggs; egg++) {
            for (int floor = 2; floor <= floors; floor++) {
                attempts[egg][floor] = computeMinAttemptsForState(attempts, egg, floor);
            }
        }
    }

    private static int computeMinAttemptsForState(int[][] attempts, int eggs, int floors) {
        int minAttempts = Integer.MAX_VALUE;

        for (int testFloor = 1; testFloor <= floors; testFloor++) {
            int attemptsIfBreaks = attempts[eggs - 1][testFloor - 1];
            int attemptsIfNotBreaks = attempts[eggs][floors - testFloor];
            int worstCaseAttempts = 1 + Math.max(attemptsIfBreaks, attemptsIfNotBreaks);

            minAttempts = Math.min(minAttempts, worstCaseAttempts);
        }

        return minAttempts;
    }

    public static void main(String[] args) {
        int eggs = 2;
        int floors = 4;
        int result = minAttempts(eggs, floors);
        System.out.println(result);
    }
}