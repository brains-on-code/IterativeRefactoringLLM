package com.thealgorithms.strings;

import java.util.Set;
import java.util.TreeSet;

/**
 * Utility class for finding all occurrences of a pattern in a text using a
 * finite automaton-based substring search.
 */
public final class Class1 {

    /** Number of possible character values (Unicode BMP). */
    private static final int ALPHABET_SIZE = Character.MAX_VALUE + 1;

    private Class1() {
        // Utility class; prevent instantiation.
    }

    /**
     * Returns all starting indices where {@code pattern} occurs in {@code text}.
     *
     * @param text    the text to search within
     * @param pattern the pattern to search for
     * @return a sorted set of starting indices of all occurrences of {@code pattern} in {@code text}
     */
    public static Set<Integer> method1(final String text, final String pattern) {
        final int[][] transitionTable = buildTransitionTable(pattern);
        Class2 automaton = new Class2(transitionTable);

        Set<Integer> matchPositions = new TreeSet<>();
        for (int i = 0; i < text.length(); i++) {
            automaton.method4(text.charAt(i));

            if (automaton.method5() == pattern.length()) {
                matchPositions.add(i - pattern.length() + 1);
            }
        }
        return matchPositions;
    }

    /**
     * Builds the finite automaton transition table for the given pattern.
     *
     * @param pattern the pattern for which to build the automaton
     * @return a 2D array representing the transition table
     */
    private static int[][] buildTransitionTable(final String pattern) {
        final int patternLength = pattern.length();
        int[][] transitionTable = new int[patternLength + 1][ALPHABET_SIZE];

        for (int state = 0; state <= patternLength; ++state) {
            for (int ch = 0; ch < ALPHABET_SIZE; ++ch) {
                transitionTable[state][ch] = nextState(pattern, patternLength, state, ch);
            }
        }

        return transitionTable;
    }

    /**
     * Computes the next automaton state for a given current state and input character.
     *
     * @param pattern       the pattern
     * @param patternLength the length of the pattern
     * @param currentState  the current state in the automaton
     * @param ch            the next input character (as int)
     * @return the next state
     */
    private static int nextState(final String pattern, final int patternLength, final int currentState, final int ch) {
        // If the next character matches the pattern at the current state, move to the next state.
        if (currentState < patternLength && ch == pattern.charAt(currentState)) {
            return currentState + 1;
        }

        // Otherwise, find the longest prefix of pattern that is a suffix of the current
        // matched sequence plus the new character.
        for (int next = currentState; next > 0; next--) {
            if (pattern.charAt(next - 1) == ch) {
                boolean prefixMatches = true;
                for (int i = 0; i < next - 1; i++) {
                    if (pattern.charAt(i) != pattern.charAt(currentState - next + i + 1)) {
                        prefixMatches = false;
                        break;
                    }
                }
                if (prefixMatches) {
                    return next;
                }
            }
        }

        // No prefix matches; go back to state 0.
        return 0;
    }

    /**
     * Simple finite automaton wrapper that tracks the current state.
     */
    private static final class Class2 {
        private int currentState = 0;
        private final int[][] transitionTable;

        private Class2(int[][] transitionTable) {
            this.transitionTable = transitionTable;
        }

        /**
         * Consumes the next character and updates the automaton state.
         *
         * @param ch the next character
         */
        private void method4(final char ch) {
            currentState = transitionTable[currentState][ch];
        }

        /**
         * Returns the current automaton state.
         *
         * @return the current state
         */
        private int method5() {
            return currentState;
        }
    }
}