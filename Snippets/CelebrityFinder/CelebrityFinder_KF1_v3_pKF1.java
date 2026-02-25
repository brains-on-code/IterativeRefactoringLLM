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
        Stack<Integer> candidateIndices = new Stack<>();

        // Push all people as potential candidates
        for (int personIndex = 0; personIndex < numberOfPeople; personIndex++) {
            candidateIndices.push(personIndex);
        }

        // Narrow down to one potential celebrity
        while (candidateIndices.size() > 1) {
            int firstCandidateIndex = candidateIndices.pop();
            int secondCandidateIndex = candidateIndices.pop();

            if (knowsMatrix[firstCandidateIndex][secondCandidateIndex] == 1) {
                // firstCandidateIndex knows secondCandidateIndex, so firstCandidateIndex cannot be a celebrity
                candidateIndices.push(secondCandidateIndex);
            } else {
                // firstCandidateIndex does not know secondCandidateIndex, so secondCandidateIndex cannot be a celebrity
                candidateIndices.push(firstCandidateIndex);
            }
        }

        // Verify the remaining candidate
        int celebrityCandidateIndex = candidateIndices.pop();
        for (int personIndex = 0; personIndex < numberOfPeople; personIndex++) {
            if (personIndex == celebrityCandidateIndex) {
                continue;
            }

            boolean candidateKnowsPerson = knowsMatrix[celebrityCandidateIndex][personIndex] == 1;
            boolean personKnowsCandidate = knowsMatrix[personIndex][celebrityCandidateIndex] == 1;

            // Candidate should not know anyone, and everyone should know candidate
            if (candidateKnowsPerson || !personKnowsCandidate) {
                return -1;
            }
        }

        return celebrityCandidateIndex;
    }
}