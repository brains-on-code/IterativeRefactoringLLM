package com.thealgorithms.strings;

import java.util.Set;
import java.util.TreeSet;

public final class StringMatchFiniteAutomata {

    /**
     * Number of possible character values (covers full UTF-16 range).
     */
    private static final int ALPHABET_SIZE = Character.MAX_VALUE + 1;

    private StringMatchFiniteAutomata() {
        // Prevent instantiation of utility class.
    }

    /**
     * Finds all starting indices where {@code pattern} occurs in {@code text}
     * using a finite automaton-based string matching algorithm.
     *
     * @param text    the text to search within
     * @param pattern the pattern to search for
     * @return a sorted set of starting indices where the pattern is found
     */
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

    /**
     * Builds the transition table for the finite automaton representing the given pattern.
     * <p>
     * The table has {@code patternLength + 1} states (0..patternLength) and one column
     * for each possible character. Entry {@code table[state][ch]} gives the next state
     * when reading character {@code ch} from {@code state}.
     *
     * @param pattern the pattern for which to build the automaton
     * @return the transition table
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
     * Computes the next state of the automaton.
     * <p>
     * Given the current {@code state} (which corresponds to how many characters
     * of the pattern have been matched so far) and an input character {@code ch},
     * this method returns the length of the longest prefix of {@code pattern}
     * that is a suffix of the current matched string plus {@code ch}.
     *
     * @param pattern       the pattern
     * @param patternLength the length of the pattern
     * @param state         current state (0..patternLength)
     * @param ch            next input character (as int)
     * @return the next state
     */
    private static int computeNextState(
        final String pattern,
        final int patternLength,
        final int state,
        final int ch
    ) {
        // Case 1: the next character continues the current match.
        if (state < patternLength && ch == pattern.charAt(state)) {
            return state + 1;
        }

        // Case 2: find the longest prefix of pattern that is a suffix of
        // (pattern[0..state-1] + ch).
        for (int nextState = state; nextState > 0; nextState--) {
            if (pattern.charAt(nextState - 1) != ch) {
                continue;
            }

            boolean prefixMatches = true;
            for (int i = 0; i < nextState - 1; i++) {
                if (pattern.charAt(i) != pattern.charAt(state - nextState + i + 1)) {
                    prefixMatches = false;
                    break;
                }
            }

            if (prefixMatches) {
                return nextState;
            }
        }

        // Case 3: no prefix matches; go back to state 0.
        return 0;
    }

    /**
     * Simple finite automaton that uses a precomputed transition table.
     */
    private static final class FiniteAutomaton {
        private int state = 0;
        private final int[][] transitionTable;

        private FiniteAutomaton(int[][] transitionTable) {
            this.transitionTable = transitionTable;
        }

        /**
         * Consumes a single input character and updates the current state.
         *
         * @param input the next input character
         */
        private void consume(final char input) {
            state = transitionTable[state][input];
        }

        /**
         * Returns the current state of the automaton.
         *
         * @return current state
         */
        private int getState() {
            return state;
        }
    }
}