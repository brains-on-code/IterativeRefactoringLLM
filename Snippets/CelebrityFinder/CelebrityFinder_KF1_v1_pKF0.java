package com.thealgorithms.stacks;

import java.util.Stack;

/**
 * Utility class for finding a "celebrity" in a group.
 *
 * A celebrity is defined as someone who:
 * - is known by everyone else, and
 * - does not know anyone else.
 *
 * The input is an adjacency matrix `knowsMatrix` where:
 * - knowsMatrix[i][j] == 1 means person i knows person j
 * - knowsMatrix[i][j] == 0 means person i does not know person j
 */
public final class Class1 {

    private Class1() {
        // Prevent instantiation
    }

    /**
     * Finds the index of the celebrity in the given matrix, if one exists.
     *
     * @param knowsMatrix adjacency matrix representing who knows whom
     * @return the index of the celebrity, or -1 if no celebrity exists
     */
    public static int method1(int[][] knowsMatrix) {
        int n = knowsMatrix.length;
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

            if (knowsMatrix[personA][personB] == 1) {
                // personA knows personB, so personA cannot be a celebrity
                candidates.push(personB);
            } else {
                // personA does not know personB, so personB cannot be a celebrity
                candidates.push(personA);
            }
        }

        int candidate = candidates.pop();

        // Verify the candidate
        for (int i = 0; i < n; i++) {
            if (i == candidate) {
                continue;
            }
            // Candidate should not know i, and everyone should know candidate
            if (knowsMatrix[candidate][i] == 1 || knowsMatrix[i][candidate] == 0) {
                return -1;
            }
        }

        return candidate;
    }
}