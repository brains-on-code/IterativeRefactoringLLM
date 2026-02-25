package com.thealgorithms.stacks;

import java.util.Stack;

/**
 * Utility class for finding a "celebrity" in a group based on a
 * knows/does-not-know relationship matrix.
 */
public final class CelebrityFinder {

    private CelebrityFinder() {
        // Prevent instantiation
    }

    /**
     * Finds the index of the celebrity in the given matrix.
     *
     * A celebrity is defined as someone who:
     * - is known by everyone else, and
     * - knows no one else.
     *
     * The matrix knowsMatrix is such that:
     * knowsMatrix[i][j] == 1 if person i knows person j,
     * knowsMatrix[i][j] == 0 otherwise.
     *
     * @param knowsMatrix the adjacency matrix representing who knows whom
     * @return the index of the celebrity, or -1 if no celebrity exists
     */
    public static int findCelebrity(int[][] knowsMatrix) {
        int peopleCount = knowsMatrix.length;
        Stack<Integer> candidates = new Stack<>();

        // Push all people as potential candidates
        for (int personIndex = 0; personIndex < peopleCount; personIndex++) {
            candidates.push(personIndex);
        }

        // Narrow down to one potential celebrity
        while (candidates.size() > 1) {
            int firstCandidate = candidates.pop();
            int secondCandidate = candidates.pop();

            if (knowsMatrix[firstCandidate][secondCandidate] == 1) {
                // firstCandidate knows secondCandidate, so firstCandidate cannot be a celebrity
                candidates.push(secondCandidate);
            } else {
                // firstCandidate does not know secondCandidate, so secondCandidate cannot be a celebrity
                candidates.push(firstCandidate);
            }
        }

        // Verify the remaining candidate
        int potentialCelebrity = candidates.pop();
        for (int personIndex = 0; personIndex < peopleCount; personIndex++) {
            if (personIndex == potentialCelebrity) {
                continue;
            }

            boolean potentialCelebrityKnowsPerson = knowsMatrix[potentialCelebrity][personIndex] == 1;
            boolean personKnowsPotentialCelebrity = knowsMatrix[personIndex][potentialCelebrity] == 1;

            // Celebrity should not know anyone, and everyone should know celebrity
            if (potentialCelebrityKnowsPerson || !personKnowsPotentialCelebrity) {
                return -1;
            }
        }

        return potentialCelebrity;
    }
}