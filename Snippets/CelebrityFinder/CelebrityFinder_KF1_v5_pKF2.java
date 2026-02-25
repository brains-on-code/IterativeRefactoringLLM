package com.thealgorithms.stacks;

import java.util.Stack;

/**
 * Utility class for solving the "celebrity" problem.
 *
 * A celebrity is a person who:
 * - is known by everyone else, and
 * - does not know anyone else.
 */
public final class CelebrityFinder {

    private CelebrityFinder() {
        // Prevent instantiation
    }

    /**
     * Finds the index of the celebrity in a group, if one exists.
     *
     * The input is an adjacency matrix where:
     * - matrix[i][j] == 1 means person i knows person j
     * - matrix[i][j] == 0 means person i does not know person j
     *
     * @param matrix adjacency matrix representing "knows" relationships
     * @return index of the celebrity, or -1 if no celebrity exists
     */
    public static int findCelebrity(int[][] matrix) {
        int n = matrix.length;

        Stack<Integer> candidates = new Stack<>();
        for (int i = 0; i < n; i++) {
            candidates.push(i);
        }

        // Narrow down to a single potential celebrity
        while (candidates.size() > 1) {
            int personA = candidates.pop();
            int personB = candidates.pop();

            // If A knows B, A cannot be a celebrity; otherwise, B cannot be a celebrity
            if (knows(matrix, personA, personB)) {
                candidates.push(personB);
            } else {
                candidates.push(personA);
            }
        }

        int candidate = candidates.pop();
        return isCelebrity(matrix, candidate) ? candidate : -1;
    }

    private static boolean knows(int[][] matrix, int a, int b) {
        return matrix[a][b] == 1;
    }

    private static boolean isCelebrity(int[][] matrix, int candidate) {
        int n = matrix.length;

        for (int i = 0; i < n; i++) {
            if (i == candidate) {
                continue;
            }

            boolean candidateKnowsPerson = matrix[candidate][i] == 1;
            boolean personDoesNotKnowCandidate = matrix[i][candidate] == 0;

            // Celebrity must know nobody and be known by everybody
            if (candidateKnowsPerson || personDoesNotKnowCandidate) {
                return false;
            }
        }
        return true;
    }
}