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
        Stack<Integer> candidates = new Stack<>();

        // Push all people as potential candidates
        for (int person = 0; person < numberOfPeople; person++) {
            candidates.push(person);
        }

        // Narrow down to one potential celebrity
        while (candidates.size() > 1) {
            int personA = candidates.pop();
            int personB = candidates.pop();

            if (knowsMatrix[personA][personB] == 1) {
                // personA knows personB, so personA cannot be a celebrity
                candidates.push(personB);
            } else {
                // personA does not know personB, so personB cannot be a celebrity
                candidates.push(personA);
            }
        }

        // Verify the remaining candidate
        int candidate = candidates.pop();
        for (int person = 0; person < numberOfPeople; person++) {
            if (person == candidate) {
                continue;
            }
            // Candidate should not know anyone, and everyone should know candidate
            if (knowsMatrix[candidate][person] == 1 || knowsMatrix[person][candidate] == 0) {
                return -1;
            }
        }
        return candidate;
    }
}