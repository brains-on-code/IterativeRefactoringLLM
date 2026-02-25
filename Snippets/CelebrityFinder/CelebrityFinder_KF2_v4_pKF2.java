package com.thealgorithms.stacks;

import java.util.Stack;

public final class CelebrityFinder {

    private CelebrityFinder() {
        // Prevent instantiation
    }

    /**
     * Returns the index of the celebrity in the party, or -1 if none exists.
     *
     * A celebrity is someone who:
     * - is known by everyone else, and
     * - does not know anyone else.
     *
     * party[a][b] == 1 if person a knows person b, otherwise 0.
     */
    public static int findCelebrity(int[][] party) {
        int n = party.length;
        if (n == 0) {
            return -1;
        }

        Stack<Integer> candidates = new Stack<>();
        for (int i = 0; i < n; i++) {
            candidates.push(i);
        }

        // Reduce the stack to a single potential celebrity
        while (candidates.size() > 1) {
            int personA = candidates.pop();
            int personB = candidates.pop();

            // If personA knows personB, personA cannot be a celebrity; otherwise personB cannot be
            if (party[personA][personB] == 1) {
                candidates.push(personB);
            } else {
                candidates.push(personA);
            }
        }

        int candidate = candidates.pop();

        // Verify that the candidate is known by everyone and knows no one
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