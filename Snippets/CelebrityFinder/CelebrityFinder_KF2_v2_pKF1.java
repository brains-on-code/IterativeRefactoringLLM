package com.thealgorithms.stacks;

import java.util.Stack;

public final class CelebrityFinder {

    private CelebrityFinder() {
        // Utility class; prevent instantiation
    }

    /**
     * Finds the index of the celebrity in the given acquaintance matrix.
     *
     * @param acquaintanceMatrix acquaintanceMatrix[i][j] == 1 if person i knows person j, else 0
     * @return index of the celebrity, or -1 if no celebrity exists
     */
    public static int findCelebrity(int[][] acquaintanceMatrix) {
        int numberOfPeople = acquaintanceMatrix.length;
        Stack<Integer> candidateIndices = new Stack<>();

        for (int personIndex = 0; personIndex < numberOfPeople; personIndex++) {
            candidateIndices.push(personIndex);
        }

        while (candidateIndices.size() > 1) {
            int firstCandidateIndex = candidateIndices.pop();
            int secondCandidateIndex = candidateIndices.pop();

            boolean firstCandidateKnowsSecond =
                acquaintanceMatrix[firstCandidateIndex][secondCandidateIndex] == 1;

            if (firstCandidateKnowsSecond) {
                // firstCandidateIndex knows secondCandidateIndex, so firstCandidateIndex cannot be a celebrity
                candidateIndices.push(secondCandidateIndex);
            } else {
                // firstCandidateIndex does not know secondCandidateIndex, so secondCandidateIndex cannot be a celebrity
                candidateIndices.push(firstCandidateIndex);
            }
        }

        int potentialCelebrityIndex = candidateIndices.pop();

        for (int personIndex = 0; personIndex < numberOfPeople; personIndex++) {
            if (personIndex == potentialCelebrityIndex) {
                continue;
            }

            boolean potentialCelebrityKnowsPerson =
                acquaintanceMatrix[potentialCelebrityIndex][personIndex] == 1;
            boolean personDoesNotKnowPotentialCelebrity =
                acquaintanceMatrix[personIndex][potentialCelebrityIndex] == 0;

            if (potentialCelebrityKnowsPerson || personDoesNotKnowPotentialCelebrity) {
                return -1;
            }
        }

        return potentialCelebrityIndex;
    }
}