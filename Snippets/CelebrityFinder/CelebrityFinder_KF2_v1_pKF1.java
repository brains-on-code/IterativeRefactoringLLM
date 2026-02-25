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
        Stack<Integer> candidateStack = new Stack<>();

        for (int personIndex = 0; personIndex < numberOfPeople; personIndex++) {
            candidateStack.push(personIndex);
        }

        while (candidateStack.size() > 1) {
            int firstCandidate = candidateStack.pop();
            int secondCandidate = candidateStack.pop();

            if (acquaintanceMatrix[firstCandidate][secondCandidate] == 1) {
                // firstCandidate knows secondCandidate, so firstCandidate cannot be a celebrity
                candidateStack.push(secondCandidate);
            } else {
                // firstCandidate does not know secondCandidate, so secondCandidate cannot be a celebrity
                candidateStack.push(firstCandidate);
            }
        }

        int potentialCelebrity = candidateStack.pop();

        for (int personIndex = 0; personIndex < numberOfPeople; personIndex++) {
            if (personIndex == potentialCelebrity) {
                continue;
            }

            boolean potentialCelebrityKnowsPerson =
                acquaintanceMatrix[potentialCelebrity][personIndex] == 1;
            boolean personDoesNotKnowPotentialCelebrity =
                acquaintanceMatrix[personIndex][potentialCelebrity] == 0;

            if (potentialCelebrityKnowsPerson || personDoesNotKnowPotentialCelebrity) {
                return -1;
            }
        }

        return potentialCelebrity;
    }
}