package com.thealgorithms.stacks;

import java.util.Stack;

/**
 * Solves the celebrity problem using a stack-based algorithm.
 *
 * <p>A celebrity is someone who is known by everyone but does not know anyone else.
 */
public final class CelebrityFinder {

    private CelebrityFinder() {
        // Prevent instantiation of utility class
    }

    /**
     * Finds the celebrity in the given party matrix using a stack-based algorithm.
     *
     * @param party a 2D matrix where party[i][j] is 1 if person i knows person j, otherwise 0
     * @return the index of the celebrity, or -1 if there is no celebrity
     */
    public static int findCelebrity(int[][] party) {
        if (party == null || party.length == 0) {
            return -1;
        }

        int n = party.length;
        Stack<Integer> stack = new Stack<>();

        // Push all people onto the stack as initial candidates
        for (int i = 0; i < n; i++) {
            stack.push(i);
        }

        // Eliminate non-celebrities until only one candidate remains
        while (stack.size() > 1) {
            int person1 = stack.pop();
            int person2 = stack.pop();

            // If person1 knows person2, person1 cannot be a celebrity.
            // Otherwise, person2 cannot be a celebrity.
            if (party[person1][person2] == 1) {
                stack.push(person2);
            } else {
                stack.push(person1);
            }
        }

        int candidate = stack.pop();

        // Verify that the candidate:
        // 1) knows nobody else (row of zeros),
        // 2) is known by everybody else (column of ones, except diagonal).
        for (int i = 0; i < n; i++) {
            if (i == candidate) {
                continue;
            }

            boolean candidateKnowsPerson = party[candidate][i] == 1;
            boolean personKnowsCandidate = party[i][candidate] == 1;

            if (candidateKnowsPerson || !personKnowsCandidate) {
                return -1;
            }
        }

        return candidate;
    }
}