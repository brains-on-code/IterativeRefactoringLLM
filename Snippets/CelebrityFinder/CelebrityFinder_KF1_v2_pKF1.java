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
        for (int personIndex = 0; personIndex < numberOfPeople; personIndex++) {
            candidateStack.push(personIndex);
        }

        // Narrow down to one potential celebrity
        while (candidateStack.size() > 1) {
            int firstPersonIndex = candidateStack.pop();
            int secondPersonIndex = candidateStack.pop();

            if (knowsMatrix[firstPersonIndex][secondPersonIndex] == 1) {
                // firstPersonIndex knows secondPersonIndex, so firstPersonIndex cannot be a celebrity
                candidateStack.push(secondPersonIndex);
            } else {
                // firstPersonIndex does not know secondPersonIndex, so secondPersonIndex cannot be a celebrity
                candidateStack.push(firstPersonIndex);
            }
        }

        // Verify the remaining candidate
        int celebrityCandidateIndex = candidateStack.pop();
        for (int personIndex = 0; personIndex < numberOfPeople; personIndex++) {
            if (personIndex == celebrityCandidateIndex) {
                continue;
            }
            // Candidate should not know anyone, and everyone should know candidate
            boolean candidateKnowsPerson = knowsMatrix[celebrityCandidateIndex][personIndex] == 1;
            boolean personKnowsCandidate = knowsMatrix[personIndex][celebrityCandidateIndex] == 1;

            if (candidateKnowsPerson || !personKnowsCandidate) {
                return -1;
            }
        }
        return celebrityCandidateIndex;
    }
}