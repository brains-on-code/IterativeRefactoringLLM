package com.thealgorithms.strings;

import java.util.Set;
import java.util.TreeSet;

public final class StringMatchFiniteAutomata {

    private static final int ALPHABET_SIZE = Character.MAX_VALUE + 1;

    private StringMatchFiniteAutomata() {
    }

    public static Set<Integer> searchPattern(final String text, final String pattern) {
        final int[][] transitionTable = buildTransitionTable(pattern);
        final FiniteAutomaton automaton = new FiniteAutomaton(transitionTable);

        Set<Integer> foundIndices = new TreeSet<>();
        for (int i = 0; i < text.length(); i++) {
            automaton.consume(text.charAt(i));

            if (automaton.getState() == pattern.length()) {
                foundIndices.add(i - pattern.length() + 1);
            }
        }
        return foundIndices;
    }

    private static int[][] buildTransitionTable(final String pattern) {
        final int patternLength = pattern.length();
        int[][] transitionTable = new int[patternLength + 1][ALPHABET_SIZE];

        for (int state = 0; state <= patternLength; state++) {
            for (int ch = 0; ch < ALPHABET_SIZE; ch++) {
                transitionTable[state][ch] =
                    computeNextState(pattern, patternLength, state, ch);
            }
        }

        return transitionTable;
    }

    private static int computeNextState(
        final String pattern,
        final int patternLength,
        final int currentState,
        final int ch
    ) {
        if (currentState < patternLength && ch == pattern.charAt(currentState)) {
            return currentState + 1;
        }

        for (int candidateState = currentState; candidateState > 0; candidateState--) {
            if (pattern.charAt(candidateState - 1) != ch) {
                continue;
            }

            boolean prefixMatches = true;
            for (int i = 0; i < candidateState - 1; i++) {
                int patternIndex = currentState - candidateState + i + 1;
                if (pattern.charAt(i) != pattern.charAt(patternIndex)) {
                    prefixMatches = false;
                    break;
                }
            }

            if (prefixMatches) {
                return candidateState;
            }
        }

        return 0;
    }

    private static final class FiniteAutomaton {
        private int state = 0;
        private final int[][] transitionTable;

        private FiniteAutomaton(final int[][] transitionTable) {
            this.transitionTable = transitionTable;
        }

        private void consume(final char input) {
            state = transitionTable[state][input];
        }

        private int getState() {
            return state;
        }
    }
}