package com.thealgorithms.stacks;

import java.util.Stack;

public final class CelebrityFinder {

    private CelebrityFinder() {
        // Utility class; prevent instantiation
    }

    /**
     * Finds the celebrity in a party.
     *
     * A celebrity is defined as someone who:
     * - is known by everyone else, and
     * - knows no one else.
     *
     * The party matrix uses:
     * party[a][b] == 1 if person a knows person b, otherwise 0.
     *
     * @param party adjacency matrix representing who knows whom
     * @return index of the celebrity, or -1 if no celebrity exists
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

        // Narrow down to a single potential celebrity
        while (candidates.size() > 1) {
            int personA = candidates.pop();
            int personB = candidates.pop();

            // If A knows B, A cannot be a celebrity; keep B
            // If A does not know B, B cannot be a celebrity; keep A
            if (party[personA][personB] == 1) {
                candidates.push(personB);
            } else {
                candidates.push(personA);
            }
        }

        int candidate = candidates.pop();

        // Verify candidate:
        // - candidate knows nobody else
        // - everyone else knows candidate
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