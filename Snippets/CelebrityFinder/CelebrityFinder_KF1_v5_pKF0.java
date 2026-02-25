package com.thealgorithms.stacks;

import java.util.Stack;

/**
 * Utility class for finding a "celebrity" in a group.
 *
 * A celebrity is defined as someone who:
 * - is known by everyone else, and
 * - does not know anyone else.
 *
 * The input is an adjacency matrix {@code knowsMatrix} where:
 * - knowsMatrix[i][j] == 1 means person i knows person j
 * - knowsMatrix[i][j] == 0 means person i does not know person j
 */
public final class CelebrityFinder {

    private CelebrityFinder() {
        // Prevent instantiation
    }

    /**
     * Finds the index of the celebrity in the given matrix, if one exists.
     *
     * @param knowsMatrix adjacency matrix representing who knows whom
     * @return the index of the celebrity, or -1 if no celebrity exists
     */
    public static int findCelebrity(int[][] knowsMatrix) {
        if (!isValidMatrix(knowsMatrix)) {
            return -1;
        }

        int numberOfPeople = knowsMatrix.length;
        Stack<Integer> candidates = createCandidateStack(numberOfPeople);

        int potentialCelebrity = resolvePotentialCelebrity(knowsMatrix, candidates);
        return isCelebrity(knowsMatrix, potentialCelebrity) ? potentialCelebrity : -1;
    }

    private static boolean isValidMatrix(int[][] knowsMatrix) {
        if (knowsMatrix == null || knowsMatrix.length == 0) {
            return false;
        }

        int size = knowsMatrix.length;
        for (int[] row : knowsMatrix) {
            if (row == null || row.length != size) {
                return false;
            }
        }
        return true;
    }

    private static Stack<Integer> createCandidateStack(int numberOfPeople) {
        Stack<Integer> candidates = new Stack<>();
        for (int person = 0; person < numberOfPeople; person++) {
            candidates.push(person);
        }
        return candidates;
    }

    private static int resolvePotentialCelebrity(int[][] knowsMatrix, Stack<Integer> candidates) {
        while (candidates.size() > 1) {
            int personA = candidates.pop();
            int personB = candidates.pop();

            if (knows(knowsMatrix, personA, personB)) {
                // personA knows personB, so personA cannot be a celebrity
                candidates.push(personB);
            } else {
                // personA does not know personB, so personB cannot be a celebrity
                candidates.push(personA);
            }
        }
        return candidates.pop();
    }

    private static boolean isCelebrity(int[][] knowsMatrix, int candidate) {
        int numberOfPeople = knowsMatrix.length;

        for (int person = 0; person < numberOfPeople; person++) {
            if (person == candidate) {
                continue;
            }

            if (knows(knowsMatrix, candidate, person) || !knows(knowsMatrix, person, candidate)) {
                return false;
            }
        }
        return true;
    }

    private static boolean knows(int[][] knowsMatrix, int a, int b) {
        return knowsMatrix[a][b] == 1;
    }
}