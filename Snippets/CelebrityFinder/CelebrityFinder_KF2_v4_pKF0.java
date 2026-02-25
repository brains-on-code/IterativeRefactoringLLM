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
        if (!isValidPartyMatrix(party)) {
            return -1;
        }

        int n = party.length;
        Stack<Integer> candidates = initializeCandidates(n);

        int candidate = resolveCandidate(party, candidates);
        return isCelebrity(party, candidate) ? candidate : -1;
    }

    private static boolean isValidPartyMatrix(int[][] party) {
        return party != null && party.length > 0;
    }

    private static Stack<Integer> initializeCandidates(int size) {
        Stack<Integer> candidates = new Stack<>();
        for (int person = 0; person < size; person++) {
            candidates.push(person);
        }
        return candidates;
    }

    private static int resolveCandidate(int[][] party, Stack<Integer> candidates) {
        while (candidates.size() > 1) {
            int firstPerson = candidates.pop();
            int secondPerson = candidates.pop();

            if (knows(party, firstPerson, secondPerson)) {
                candidates.push(secondPerson);
            } else {
                candidates.push(firstPerson);
            }
        }
        return candidates.pop();
    }

    private static boolean knows(int[][] party, int a, int b) {
        return party[a][b] == 1;
    }

    private static boolean isCelebrity(int[][] party, int candidate) {
        int n = party.length;

        for (int person = 0; person < n; person++) {
            if (person == candidate) {
                continue;
            }

            boolean candidateKnowsPerson = knows(party, candidate, person);
            boolean personKnowsCandidate = knows(party, person, candidate);

            if (candidateKnowsPerson || !personKnowsCandidate) {
                return false;
            }
        }

        return true;
    }
}