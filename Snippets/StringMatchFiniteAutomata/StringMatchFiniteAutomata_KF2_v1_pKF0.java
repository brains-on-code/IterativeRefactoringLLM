package com.thealgorithms.strings;

import java.util.Set;
import java.util.TreeSet;

public final class StringMatchFiniteAutomata {

    private static final int ALPHABET_SIZE = Character.MAX_VALUE + 1;

    private StringMatchFiniteAutomata() {
        // Utility class; prevent instantiation
    }

    public static Set<Integer> searchPattern(final String text, final String pattern) {
        if (text == null || pattern == null) {
            throw new IllegalArgumentException("Text and pattern must not be null");
        }

        final int patternLength = pattern.length();
        if (patternLength == 0) {
            return Set.of();
        }

        final int[][] transitionTable = buildTransitionTable(pattern);
        final FiniteAutomaton automaton = new FiniteAutomaton(transitionTable);

        final Set<Integer> foundIndices = new TreeSet<>();
        for (int i = 0; i < text.length(); i++) {
            automaton.consume(text.charAt(i));

            if (automaton.getState() == patternLength) {
                foundIndices.add(i - patternLength + 1);
            }
        }
        return foundIndices;
    }

    private static int[][] buildTransitionTable(final String pattern) {
        final int patternLength = pattern.length();
        final int[][] transitionTable = new int[patternLength + 1][ALPHABET_SIZE];

        for (int state = 0; state <= patternLength; state++) {
            for (int ch = 0; ch < ALPHABET_SIZE; ch++) {
                transitionTable[state][ch] = computeNextState(pattern, patternLength, state, ch);
            }
        }

        return transitionTable;
    }

    private static int computeNextState(
        final String pattern,
        final int patternLength,
        final int currentState,
        final int inputChar
    ) {
        if (currentState < patternLength && inputChar == pattern.charAt(currentState)) {
            return currentState + 1;
        }

        for (int nextState = currentState; nextState > 0; nextState--) {
            if (pattern.charAt(nextState - 1) != inputChar) {
                continue;
            }

            if (isPrefixMatchingSuffix(pattern, currentState, nextState)) {
                return nextState;
            }
        }

        return 0;
    }

    private static boolean isPrefixMatchingSuffix(
        final String pattern,
        final int currentState,
        final int nextState
    ) {
        for (int i = 0; i < nextState - 1; i++) {
            if (pattern.charAt(i) != pattern.charAt(currentState - nextState + i + 1)) {
                return false;
            }
        }
        return true;
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