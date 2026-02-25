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

        for (int person = 0; person < numberOfPeople; person++) {
            candidateStack.push(person);
        }

        while (candidateStack.size() > 1) {
            int candidateA = candidateStack.pop();
            int candidateB = candidateStack.pop();

            boolean candidateAKnowsB = acquaintanceMatrix[candidateA][candidateB] == 1;

            if (candidateAKnowsB) {
                // candidateA knows candidateB, so candidateA cannot be a celebrity
                candidateStack.push(candidateB);
            } else {
                // candidateA does not know candidateB, so candidateB cannot be a celebrity
                candidateStack.push(candidateA);
            }
        }

        int potentialCelebrity = candidateStack.pop();

        for (int person = 0; person < numberOfPeople; person++) {
            if (person == potentialCelebrity) {
                continue;
            }

            boolean celebrityKnowsPerson = acquaintanceMatrix[potentialCelebrity][person] == 1;
            boolean personDoesNotKnowCelebrity = acquaintanceMatrix[person][potentialCelebrity] == 0;

            if (celebrityKnowsPerson || personDoesNotKnowCelebrity) {
                return -1;
            }
        }

        return potentialCelebrity;
    }
}