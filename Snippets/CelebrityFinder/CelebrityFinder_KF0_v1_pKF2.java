package com.thealgorithms.stacks;

import java.util.Stack;

/**
 * Solves the celebrity problem using a stack-based algorithm.
 *
 * <p>A celebrity is someone who is known by everyone but does not know anyone else.
 */
public final class CelebrityFinder {

    private CelebrityFinder() {
        // Utility class; prevent instantiation
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

        // Initialize stack with all people as potential celebrities
        for (int i = 0; i < n; i++) {
            stack.push(i);
        }

        // Narrow down to a single potential celebrity
        while (stack.size() > 1) {
            int person1 = stack.pop();
            int person2 = stack.pop();

            // If person1 knows person2, person1 cannot be a celebrity
            // Otherwise, person2 cannot be a celebrity
            if (party[person1][person2] == 1) {
                stack.push(person2);
            } else {
                stack.push(person1);
            }
        }

        int candidate = stack.pop();

        // Verify that the candidate is known by everyone and knows no one
        for (int i = 0; i < n; i++) {
            if (i == candidate) {
                continue;
            }
            boolean candidateKnowsI = party[candidate][i] == 1;
            boolean iKnowsCandidate = party[i][candidate] == 1;

            if (candidateKnowsI || !iKnowsCandidate) {
                return -1;
            }
        }

        return candidate;
    }
}