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
        for (int person = 0; person < numberOfPeople; person++) {
            candidateStack.push(person);
        }

        while (candidateStack.size() > 1) {
            int firstCandidate = candidateStack.pop();
            int secondCandidate = candidateStack.pop();

            if (acquaintanceMatrix[firstCandidate][secondCandidate] == 1) {
                // firstCandidate knows secondCandidate, so firstCandidate cannot be the celebrity
                candidateStack.push(secondCandidate);
            } else {
                // firstCandidate does not know secondCandidate, so secondCandidate cannot be the celebrity
                candidateStack.push(firstCandidate);
            }
        }

        int celebrityCandidate = candidateStack.pop();

        for (int person = 0; person < numberOfPeople; person++) {
            if (person == celebrityCandidate) {
                continue;
            }

            boolean candidateKnowsPerson = acquaintanceMatrix[celebrityCandidate][person] == 1;
            boolean personKnowsCandidate = acquaintanceMatrix[person][celebrityCandidate] == 1;

            if (candidateKnowsPerson || !personKnowsCandidate) {
                return -1;
            }
        }

        return celebrityCandidate;
    }
}