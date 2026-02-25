package com.thealgorithms.stacks;

import java.util.Stack;

public final class CelebrityFinder {

    private CelebrityFinder() {
        // Utility class; prevent instantiation
    }

    /**
     * Finds the celebrity in a party.
     * A celebrity is known by everyone but knows no one.
     *
     * @param party adjacency matrix where party[i][j] == 1 means i knows j
     * @return index of the celebrity, or -1 if none exists
     */
    public static int findCelebrity(int[][] party) {
        if (party == null || party.length == 0) {
            return -1;
        }

        int n = party.length;
        Stack<Integer> candidates = new Stack<>();

        // Initialize stack with all people as potential celebrities
        for (int i = 0; i < n; i++) {
            candidates.push(i);
        }

        // Narrow down to a single candidate
        while (candidates.size() > 1) {
            int personA = candidates.pop();
            int personB = candidates.pop();

            if (knows(party, personA, personB)) {
                // If A knows B, A cannot be a celebrity
                candidates.push(personB);
            } else {
                // If A does not know B, B cannot be a celebrity
                candidates.push(personA);
            }
        }

        int candidate = candidates.pop();

        // Verify the candidate
        if (isCelebrity(party, candidate)) {
            return candidate;
        }

        return -1;
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