package com.thealgorithms.stacks;

import java.util.Stack;

/**
 * Utility class for operations on "celebrity" problems in graphs.
 *
 * A celebrity is defined as a person who:
 * - is known by everyone else, and
 * - knows no one else.
 */
public final class Class1 {

    private Class1() {
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
    public static int method1(int[][] matrix) {

        // Step 1: Push all candidates onto the stack
        Stack<Integer> candidates = new Stack<>();
        for (int i = 0; i < matrix.length; i++) {
            candidates.push(i);
        }

        // Step 2: Eliminate non-celebrities pairwise
        while (candidates.size() > 1) {
            int personA = candidates.pop();
            int personB = candidates.pop();

            if (matrix[personA][personB] == 1) {
                // personA knows personB, so personA cannot be a celebrity
                candidates.push(personB);
            } else {
                // personA does not know personB, so personB cannot be a celebrity
                candidates.push(personA);
            }
        }

        // Step 3: Verify the remaining candidate
        int candidate = candidates.pop();
        for (int i = 0; i < matrix.length; i++) {
            if (i != candidate && (matrix[candidate][i] == 1 || matrix[i][candidate] == 0)) {
                return -1;
            }
        }
        return candidate;
    }
}