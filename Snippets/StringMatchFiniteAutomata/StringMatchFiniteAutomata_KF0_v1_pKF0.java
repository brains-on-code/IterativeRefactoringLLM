package com.thealgorithms.strings;

import java.util.Set;
import java.util.TreeSet;

/**
 * A class to perform string matching using
 * <a href="https://en.wikipedia.org/wiki/Finite-state_machine">finite automata</a>.
 *
 * @author <a href="https://github.com/prateekKrOraon">Prateek Kumar Oraon</a>
 */
public final class StringMatchFiniteAutomata {

    /** Total number of characters in the input alphabet. */
    private static final int ALPHABET_SIZE = Character.MAX_VALUE + 1;

    private StringMatchFiniteAutomata() {
        // Utility class; prevent instantiation
    }

    /**
     * Searches for the pattern in the given text using finite automata.
     *
     * @param text    The text to search within.
     * @param pattern The pattern to search for.
     * @return A sorted set of starting indices where the pattern is found.
     */
    public static Set<Integer> searchPattern(final String text, final String pattern) {
        if (text == null || pattern == null) {
            throw new IllegalArgumentException("Text and pattern must not be null.");
        }
        if (pattern.isEmpty() || text.isEmpty() || pattern.length() > text.length()) {
            return Set.of();
        }

        int[][] transitionTable = buildTransitionTable(pattern);
        FiniteAutomaton automaton = new FiniteAutomaton(transitionTable);

        Set<Integer> foundIndices = new TreeSet<>();
        int patternLength = pattern.length();

        for (int i = 0; i < text.length(); i++) {
            automaton.consume(text.charAt(i));

            if (automaton.getState() == patternLength) {
                foundIndices.add(i - patternLength + 1);
            }
        }

        return foundIndices;
    }

    /**
     * Builds the finite automaton transition table for the given pattern.
     *
     * @param pattern The pattern to preprocess.
     * @return The state transition table.
     */
    private static int[][] buildTransitionTable(final String pattern) {
        final int patternLength = pattern.length();
        int[][] transitionTable = new int[patternLength + 1][ALPHABET_SIZE];

        for (int state = 0; state <= patternLength; state++) {
            for (int ch = 0; ch < ALPHABET_SIZE; ch++) {
                transitionTable[state][ch] = computeNextState(pattern, patternLength, state, ch);
            }
        }

        return transitionTable;
    }

    /**
     * Computes the next state for the finite automaton.
     *
     * @param pattern       The pattern being matched.
     * @param patternLength The length of the pattern.
     * @param currentState  The current state.
     * @param inputChar     The current character from the input alphabet (as int).
     * @return The next state.
     */
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

            boolean isPrefixSuffix = true;
            for (int i = 0; i < nextState - 1; i++) {
                if (pattern.charAt(i) != pattern.charAt(currentState - nextState + i + 1)) {
                    isPrefixSuffix = false;
                    break;
                }
            }

            if (isPrefixSuffix) {
                return nextState;
            }
        }

        return 0;
    }

    /**
     * A class representing the finite automaton for pattern matching.
     */
    private static final class FiniteAutomaton {
        private int state = 0;
        private final int[][] transitionTable;

        private FiniteAutomaton(int[][] transitionTable) {
            this.transitionTable = transitionTable;
        }

        /**
         * Consumes an input character and transitions to the next state.
         *
         * @param input The input character.
         */
        private void consume(final char input) {
            state = transitionTable[state][input];
        }

        /**
         * Gets the current state of the finite automaton.
         *
         * @return The current state.
         */
        private int getState() {
            return state;
        }
    }
}