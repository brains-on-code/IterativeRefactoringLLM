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
        int numberOfPeople = knowsMatrix.length;
        Stack<Integer> candidateStack = new Stack<>();

        // Push all people as potential candidates
        for (int personId = 0; personId < numberOfPeople; personId++) {
            candidateStack.push(personId);
        }

        // Narrow down to one potential celebrity
        while (candidateStack.size() > 1) {
            int candidateA = candidateStack.pop();
            int candidateB = candidateStack.pop();

            if (knowsMatrix[candidateA][candidateB] == 1) {
                // candidateA knows candidateB, so candidateA cannot be a celebrity
                candidateStack.push(candidateB);
            } else {
                // candidateA does not know candidateB, so candidateB cannot be a celebrity
                candidateStack.push(candidateA);
            }
        }

        // Verify the remaining candidate
        int potentialCelebrity = candidateStack.pop();
        for (int personId = 0; personId < numberOfPeople; personId++) {
            if (personId == potentialCelebrity) {
                continue;
            }

            boolean celebrityKnowsPerson = knowsMatrix[potentialCelebrity][personId] == 1;
            boolean personKnowsCelebrity = knowsMatrix[personId][potentialCelebrity] == 1;

            // Celebrity should not know anyone, and everyone should know celebrity
            if (celebrityKnowsPerson || !personKnowsCelebrity) {
                return -1;
            }
        }

        return potentialCelebrity;
    }
}