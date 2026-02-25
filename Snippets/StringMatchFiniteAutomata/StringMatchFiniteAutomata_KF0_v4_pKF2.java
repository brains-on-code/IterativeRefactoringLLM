package com.thealgorithms.strings;

import java.util.Set;
import java.util.TreeSet;

/**
 * String pattern matching using a deterministic finite automaton (DFA).
 *
 * <p>Builds a DFA for the pattern and runs the text through it to find all
 * occurrences of the pattern.</p>
 */
public final class StringMatchFiniteAutomata {

    /** Size of the input alphabet (all possible char values). */
    private static final int ALPHABET_SIZE = Character.MAX_VALUE + 1;

    private StringMatchFiniteAutomata() {
        // Prevent instantiation.
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
     * <p>{@code table[state][ch]} is the next state when the automaton is in
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
     * <p>The current {@code state} represents how many characters of the pattern
     * have been matched so far. Given {@code state} and the next input character
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
        // If possible, extend the current match by one character.
        if (state < patternLength && ch == pattern.charAt(state)) {
            return state + 1;
        }

        /*
         * Otherwise, find the longest prefix of the pattern that is also a suffix of
         * (pattern[0..state-1] + ch).
         */
        for (int nextState = state; nextState > 0; nextState--) {
            if (pattern.charAt(nextState - 1) != ch) {
                continue;
            }

            boolean matches = true;
            for (int i = 0; i < nextState - 1; i++) {
                int patternIndex = state - nextState + i + 1;
                if (pattern.charAt(i) != pattern.charAt(patternIndex)) {
                    matches = false;
                    break;
                }
            }
            if (matches) {
                return nextState;
            }
        }

        // No prefix-suffix match; reset to state 0.
        return 0;
    }

    /** Deterministic finite automaton for a fixed pattern. */
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

        /** Returns the current DFA state. */
        private int getState() {
            return state;
        }
    }
}