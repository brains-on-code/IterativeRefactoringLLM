package com.thealgorithms.strings;

import java.util.Set;
import java.util.TreeSet;

/**
 * Utility class for pattern searching using a finite automaton.
 */
public final class Class1 {

    private static final int ALPHABET_SIZE = Character.MAX_VALUE + 1;

    private Class1() {
        // Utility class; prevent instantiation
    }

    /**
     * Finds all starting indices of occurrences of {@code pattern} in {@code text}.
     *
     * @param text    the text to search in
     * @param pattern the pattern to search for
     * @return a sorted set of starting indices where {@code pattern} occurs in {@code text}
     */
    public static Set<Integer> method1(final String text, final String pattern) {
        final int[][] transitionTable = buildTransitionTable(pattern);
        final Automaton automaton = new Automaton(transitionTable);

        final Set<Integer> matchIndices = new TreeSet<>();
        final int patternLength = pattern.length();

        for (int i = 0; i < text.length(); i++) {
            automaton.processChar(text.charAt(i));

            if (automaton.getCurrentState() == patternLength) {
                matchIndices.add(i - patternLength + 1);
            }
        }

        return matchIndices;
    }

    /**
     * Builds the transition table for the given pattern.
     *
     * @param pattern the pattern for which to build the automaton
     * @return the transition table
     */
    private static int[][] buildTransitionTable(final String pattern) {
        final int patternLength = pattern.length();
        final int[][] transitionTable = new int[patternLength + 1][ALPHABET_SIZE];

        for (int state = 0; state <= patternLength; state++) {
            for (int ch = 0; ch < ALPHABET_SIZE; ch++) {
                transitionTable[state][ch] = getNextState(pattern, patternLength, state, ch);
            }
        }

        return transitionTable;
    }

    /**
     * Computes the next state of the automaton.
     *
     * @param pattern       the pattern
     * @param patternLength the length of the pattern
     * @param currentState  the current state
     * @param ch            the next character (as int)
     * @return the next state
     */
    private static int getNextState(
        final String pattern,
        final int patternLength,
        final int currentState,
        final int ch
    ) {
        if (currentState < patternLength && ch == pattern.charAt(currentState)) {
            return currentState + 1;
        }

        for (int nextState = currentState; nextState > 0; nextState--) {
            if (pattern.charAt(nextState - 1) != ch) {
                continue;
            }

            boolean prefixMatches = true;
            final int shift = currentState - nextState + 1;

            for (int i = 0; i < nextState - 1; i++) {
                if (pattern.charAt(i) != pattern.charAt(shift + i)) {
                    prefixMatches = false;
                    break;
                }
            }

            if (prefixMatches) {
                return nextState;
            }
        }

        return 0;
    }

    /**
     * Finite automaton for pattern matching.
     */
    private static final class Automaton {
        private int currentState = 0;
        private final int[][] transitionTable;

        private Automaton(final int[][] transitionTable) {
            this.transitionTable = transitionTable;
        }

        private void processChar(final char ch) {
            currentState = transitionTable[currentState][ch];
        }

        private int getCurrentState() {
            return currentState;
        }
    }
}