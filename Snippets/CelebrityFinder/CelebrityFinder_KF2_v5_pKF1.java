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
            int candidateA = candidateIndices.pop();
            int candidateB = candidateIndices.pop();

            boolean candidateAKnowsB = acquaintanceMatrix[candidateA][candidateB] == 1;

            if (candidateAKnowsB) {
                // candidateA knows candidateB, so candidateA cannot be a celebrity
                candidateIndices.push(candidateB);
            } else {
                // candidateA does not know candidateB, so candidateB cannot be a celebrity
                candidateIndices.push(candidateA);
            }
        }

        int potentialCelebrityIndex = candidateIndices.pop();

        for (int personIndex = 0; personIndex < numberOfPeople; personIndex++) {
            if (personIndex == potentialCelebrityIndex) {
                continue;
            }

            boolean celebrityKnowsPerson =
                acquaintanceMatrix[potentialCelebrityIndex][personIndex] == 1;
            boolean personDoesNotKnowCelebrity =
                acquaintanceMatrix[personIndex][potentialCelebrityIndex] == 0;

            if (celebrityKnowsPerson || personDoesNotKnowCelebrity) {
                return -1;
            }
        }

        return potentialCelebrityIndex;
    }
}