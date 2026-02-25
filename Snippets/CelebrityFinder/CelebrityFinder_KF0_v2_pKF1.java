package com.thealgorithms.stacks;

import java.util.Stack;

/**
 * Solves the celebrity problem using a stack-based algorithm.
 *
 * <p>Celebrity is someone known by everyone but doesn't know anyone else.
 * <p>Applications: Graph theory and social network analysis.
 *
 * @author Hardvan
 */
public final class CelebrityFinder {

    private CelebrityFinder() {
    }

    /**
     * Finds the celebrity in the given acquaintance matrix using a stack-based algorithm.
     *
     * @param acquaintanceMatrix A 2D matrix where acquaintanceMatrix[i][j] is 1 if i knows j, otherwise 0.
     * @return The index of the celebrity, or -1 if there is no celebrity.
     */
    public static int findCelebrity(int[][] acquaintanceMatrix) {

        int numberOfPeople = acquaintanceMatrix.length;

        Stack<Integer> candidateStack = new Stack<>();
        for (int personId = 0; personId < numberOfPeople; personId++) {
            candidateStack.push(personId);
        }

        while (candidateStack.size() > 1) {
            int possibleCelebrityA = candidateStack.pop();
            int possibleCelebrityB = candidateStack.pop();

            if (acquaintanceMatrix[possibleCelebrityA][possibleCelebrityB] == 1) {
                // A knows B, so A cannot be the celebrity
                candidateStack.push(possibleCelebrityB);
            } else {
                // A does not know B, so B cannot be the celebrity
                candidateStack.push(possibleCelebrityA);
            }
        }

        int celebrityCandidate = candidateStack.pop();

        for (int personId = 0; personId < numberOfPeople; personId++) {
            if (personId == celebrityCandidate) {
                continue;
            }

            boolean candidateKnowsPerson = acquaintanceMatrix[celebrityCandidate][personId] == 1;
            boolean personKnowsCandidate = acquaintanceMatrix[personId][celebrityCandidate] == 1;

            if (candidateKnowsPerson || !personKnowsCandidate) {
                return -1;
            }
        }

        return celebrityCandidate;
    }
}