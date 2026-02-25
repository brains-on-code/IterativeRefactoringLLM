package com.thealgorithms.strings;

import java.util.Set;
import java.util.TreeSet;

/**
 * String pattern matching using a finite automaton.
 *
 * <p>Builds a deterministic finite automaton (DFA) for the pattern and then
 * runs the text through it to find all occurrences.</p>
 */
public final class StringMatchFiniteAutomata {

    /** Size of the input alphabet (all possible char values). */
    private static final int ALPHABET_SIZE = Character.MAX_VALUE + 1;

    private StringMatchFiniteAutomata() {
        // Utility class; prevent instantiation.
    }

    /**
     * Finds all starting indices where {@code pattern} occurs in {@code text}.
     *
     * @param text    text to search in
     * @param pattern pattern to search for
     * @return sorted set of starting indices of all matches
     */
    public static Set<Integer> searchPattern(final String text, final String pattern) {
        final int[][] transitionTable = buildTransitionTable(pattern);
        final FiniteAutomaton automaton = new FiniteAutomaton(transitionTable);

        Set<Integer> matchIndices = new TreeSet<>();
        for (int i = 0; i < text.length(); i++) {
            automaton.consume(text.charAt(i));

            if (automaton.getState() == pattern.length()) {
                matchIndices.add(i - pattern.length() + 1);
            }
        }
        return matchIndices;
    }

    /**
     * Builds the DFA transition table for the given pattern.
     *
     * <p>{@code table[state][ch]} gives the next state when the automaton is in
     * {@code state} and reads character {@code ch}.</p>
     *
     * @param pattern pattern to preprocess
     * @return transition table for the DFA
     */
    private static int[][] buildTransitionTable(final String pattern) {
        final int patternLength = pattern.length();
        int[][] table = new int[patternLength + 1][ALPHABET_SIZE];

        for (int state = 0; state <= patternLength; state++) {
            for (int ch = 0; ch < ALPHABET_SIZE; ch++) {
                table[state][ch] = computeNextState(pattern, patternLength, state, ch);
            }
        }

        return table;
    }

    /**
     * Computes the next DFA state.
     *
     * <p>Given the current {@code state} (which represents how many characters
     * of the pattern have been matched so far) and the next input character
     * {@code ch}, this method returns the length of the longest prefix of
     * {@code pattern} that is a suffix of the current matched string plus
     * {@code ch}.</p>
     *
     * @param pattern       pattern being matched
     * @param patternLength length of the pattern
     * @param state         current state (0..patternLength)
     * @param ch            next input character (as int)
     * @return next state
     */
    private static int computeNextState(
        final String pattern,
        final int patternLength,
        final int state,
        final int ch
    ) {
        // If we can extend the current match by one character, do so.
        if (state < patternLength && ch == pattern.charAt(state)) {
            return state + 1;
        }

        // Otherwise, find the longest prefix of pattern that is a suffix of
        // (pattern[0..state-1] + ch).
        for (int nextState = state; nextState > 0; nextState--) {
            if (pattern.charAt(nextState - 1) != ch) {
                continue;
            }

            boolean matches = true;
            for (int i = 0; i < nextState - 1; i++) {
                if (pattern.charAt(i) != pattern.charAt(state - nextState + i + 1)) {
                    matches = false;
                    break;
                }
            }
            if (matches) {
                return nextState;
            }
        }

        // No prefix-suffix match; go back to state 0.
        return 0;
    }

    /**
     * Deterministic finite automaton for a fixed pattern.
     */
    private static final class FiniteAutomaton {
        private int state = 0;
        private final int[][] transitionTable;

        private FiniteAutomaton(int[][] transitionTable) {
            this.transitionTable = transitionTable;
        }

        /**
         * Consumes one input character and updates the current state.
         *
         * @param input next input character
         */
        private void consume(final char input) {
            state = transitionTable[state][input];
        }

        /**
         * Returns the current DFA state.
         *
         * @return current state
         */
        private int getState() {
            return state;
        }
    }
}