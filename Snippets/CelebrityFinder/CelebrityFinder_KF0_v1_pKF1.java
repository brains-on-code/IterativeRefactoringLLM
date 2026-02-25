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

        Stack<Integer> peopleStack = new Stack<>();
        for (int personIndex = 0; personIndex < numberOfPeople; personIndex++) {
            peopleStack.push(personIndex);
        }

        while (peopleStack.size() > 1) {
            int firstPerson = peopleStack.pop();
            int secondPerson = peopleStack.pop();

            if (acquaintanceMatrix[firstPerson][secondPerson] == 1) {
                // firstPerson knows secondPerson, so firstPerson cannot be the celebrity
                peopleStack.push(secondPerson);
            } else {
                // firstPerson does not know secondPerson, so secondPerson cannot be the celebrity
                peopleStack.push(firstPerson);
            }
        }

        int candidate = peopleStack.pop();

        for (int personIndex = 0; personIndex < numberOfPeople; personIndex++) {
            if (personIndex == candidate) {
                continue;
            }

            boolean candidateKnowsPerson = acquaintanceMatrix[candidate][personIndex] == 1;
            boolean personKnowsCandidate = acquaintanceMatrix[personIndex][candidate] == 1;

            if (candidateKnowsPerson || !personKnowsCandidate) {
                return -1;
            }
        }

        return candidate;
    }
}