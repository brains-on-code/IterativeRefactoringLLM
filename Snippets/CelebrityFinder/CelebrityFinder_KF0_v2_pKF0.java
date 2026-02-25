package com.thealgorithms.stacks;

import java.util.Stack;

/**
 * Solves the celebrity problem using a stack-based algorithm.
 *
 * <p>Celebrity is someone known by everyone but doesn't know anyone else.
 * <p>Applications: Graph theory and social network analysis.
 */
public final class CelebrityFinder {

    private CelebrityFinder() {
        // Utility class; prevent instantiation
    }

    /**
     * Finds the celebrity in the given party matrix using a stack-based algorithm.
     *
     * @param party A 2D matrix where party[i][j] is 1 if i knows j, otherwise 0.
     * @return The index of the celebrity, or -1 if there is no celebrity.
     */
    public static int findCelebrity(int[][] party) {
        if (!isValidPartyMatrix(party)) {
            return -1;
        }

        int n = party.length;
        Stack<Integer> candidates = initializeCandidates(n);

        int candidate = findPotentialCelebrity(party, candidates);
        return isCelebrity(party, candidate) ? candidate : -1;
    }

    private static boolean isValidPartyMatrix(int[][] party) {
        return party != null && party.length > 0;
    }

    private static Stack<Integer> initializeCandidates(int n) {
        Stack<Integer> candidates = new Stack<>();
        for (int i = 0; i < n; i++) {
            candidates.push(i);
        }
        return candidates;
    }

    private static int findPotentialCelebrity(int[][] party, Stack<Integer> candidates) {
        while (candidates.size() > 1) {
            int personA = candidates.pop();
            int personB = candidates.pop();

            if (knows(party, personA, personB)) {
                // A knows B, so A cannot be a celebrity
                candidates.push(personB);
            } else {
                // A does not know B, so B cannot be a celebrity
                candidates.push(personA);
            }
        }
        return candidates.pop();
    }

    private static boolean knows(int[][] party, int a, int b) {
        return party[a][b] == 1;
    }

    private static boolean isCelebrity(int[][] party, int candidate) {
        int n = party.length;

        for (int i = 0; i < n; i++) {
            if (i == candidate) {
                continue;
            }

            // Celebrity knows no one, and everyone knows celebrity
            if (knows(party, candidate, i) || !knows(party, i, candidate)) {
                return false;
            }
        }

        return true;
    }
}