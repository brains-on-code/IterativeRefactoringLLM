package com.thealgorithms.stacks;

import java.util.Stack;

/**
 * Utility class for solving the "celebrity problem" using a stack-based algorithm.
 *
 * <p>A celebrity is a person who:
 * <ul>
 *   <li>is known by everyone else, and</li>
 *   <li>does not know anyone else.</li>
 * </ul>
 */
public final class CelebrityFinder {

    private CelebrityFinder() {
        // Utility class; prevent instantiation
    }

    /**
     * Finds the index of the celebrity in the given party matrix.
     *
     * <p>A value of {@code 1} at {@code party[i][j]} means person {@code i} knows person {@code j}.
     * A value of {@code 0} means they do not.
     *
     * @param party a non-null, square matrix representing who knows whom
     * @return the index of the celebrity, or {@code -1} if no celebrity exists
     */
    public static int findCelebrity(int[][] party) {
        if (party == null || party.length == 0) {
            return -1;
        }

        int n = party.length;
        Stack<Integer> candidates = new Stack<>();

        // Start with all people as potential celebrities
        for (int i = 0; i < n; i++) {
            candidates.push(i);
        }

        // Narrow down to a single candidate
        while (candidates.size() > 1) {
            int personA = candidates.pop();
            int personB = candidates.pop();

            // If A knows B, A cannot be a celebrity; otherwise, B cannot be a celebrity
            if (party[personA][personB] == 1) {
                candidates.push(personB);
            } else {
                candidates.push(personA);
            }
        }

        int candidate = candidates.pop();

        // Verify that the candidate:
        // 1) knows nobody else (candidate's row is all zeros),
        // 2) is known by everyone else (candidate's column is all ones, except self).
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