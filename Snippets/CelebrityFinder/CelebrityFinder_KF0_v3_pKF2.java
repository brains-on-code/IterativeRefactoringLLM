package com.thealgorithms.stacks;

import java.util.Stack;

/**
 * Utility class to solve the "celebrity problem" using a stack-based algorithm.
 *
 * <p>A celebrity is defined as a person who:
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
     * Finds the celebrity in the given party matrix using a stack-based algorithm.
     *
     * @param party a square matrix where {@code party[i][j] == 1} if person {@code i} knows
     *              person {@code j}, and {@code 0} otherwise
     * @return the index of the celebrity, or {@code -1} if there is no celebrity
     */
    public static int findCelebrity(int[][] party) {
        if (party == null || party.length == 0) {
            return -1;
        }

        int n = party.length;
        Stack<Integer> candidates = new Stack<>();

        // Initially, assume every person is a potential celebrity.
        for (int i = 0; i < n; i++) {
            candidates.push(i);
        }

        // Narrow down to a single candidate:
        // Compare two people at a time and discard the one who cannot be a celebrity.
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

        // Verify the candidate:
        // 1) Candidate knows nobody else (candidate's row must be all zeros).
        // 2) Everyone else knows the candidate (candidate's column must be all ones, except self).
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