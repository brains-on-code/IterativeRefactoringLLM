package com.thealgorithms.stacks;

import java.util.Stack;

/**
 * Solves the "celebrity problem" using a stack-based algorithm.
 *
 * <p>A celebrity is a person who:
 * <ul>
 *   <li>is known by everyone else, and</li>
 *   <li>does not know anyone else.</li>
 * </ul>
 */
public final class CelebrityFinder {

    private CelebrityFinder() {
        // Prevent instantiation
    }

    /**
     * Returns the index of the celebrity in the given party matrix, or {@code -1} if none exists.
     *
     * @param party a square matrix where {@code party[i][j] == 1} if person {@code i} knows
     *              person {@code j}, and {@code 0} otherwise
     */
    public static int findCelebrity(int[][] party) {
        if (party == null || party.length == 0) {
            return -1;
        }

        int n = party.length;
        Stack<Integer> candidates = new Stack<>();

        // Initially, everyone is a potential celebrity.
        for (int i = 0; i < n; i++) {
            candidates.push(i);
        }

        // Eliminate non-celebrities until one candidate remains.
        while (candidates.size() > 1) {
            int personA = candidates.pop();
            int personB = candidates.pop();

            // If A knows B, A cannot be a celebrity; otherwise, B cannot be a celebrity.
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