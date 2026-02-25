package com.thealgorithms.strings;

import java.util.Set;
import java.util.TreeSet;

/**
 * A class to perform string matching using <a href="https://en.wikipedia.org/wiki/Finite-state_machine">finite automata</a>.
 *
 * @author <a href="https://github.com/prateekKrOraon">Prateek Kumar Oraon</a>
 */
public final class StringMatchFiniteAutomata {

    private static final int ALPHABET_SIZE = Character.MAX_VALUE + 1;

    private StringMatchFiniteAutomata() {
    }

    /**
     * Searches for the pattern in the given text using finite automata.
     *
     * @param text    The text to search within.
     * @param pattern The pattern to search for.
     */
    public static Set<Integer> searchPattern(final String text, final String pattern) {
        final int[][] transitionTable = buildTransitionTable(pattern);
        FiniteAutomaton automaton = new FiniteAutomaton(transitionTable);

        Set<Integer> matchIndices = new TreeSet<>();
        for (int textIndex = 0; textIndex < text.length(); textIndex++) {
            automaton.consume(text.charAt(textIndex));

            if (automaton.getState() == pattern.length()) {
                matchIndices.add(textIndex - pattern.length() + 1);
            }
        }
        return matchIndices;
    }

    /**
     * Computes the finite automata table for the given pattern.
     *
     * @param pattern The pattern to preprocess.
     * @return The state transition table.
     */
    private static int[][] buildTransitionTable(final String pattern) {
        final int patternLength = pattern.length();
        int[][] transitionTable = new int[patternLength + 1][ALPHABET_SIZE];

        for (int state = 0; state <= patternLength; state++) {
            for (int characterCode = 0; characterCode < ALPHABET_SIZE; characterCode++) {
                transitionTable[state][characterCode] =
                    computeNextState(pattern, patternLength, state, characterCode);
            }
        }

        return transitionTable;
    }

    /**
     * Gets the next state for the finite automata.
     *
     * @param pattern       The pattern being matched.
     * @param patternLength The length of the pattern.
     * @param currentState  The current state.
     * @param characterCode The current character from the input alphabet (as int).
     * @return The next state.
     */
    private static int computeNextState(
        final String pattern,
        final int patternLength,
        final int currentState,
        final int characterCode
    ) {
        if (currentState < patternLength && characterCode == pattern.charAt(currentState)) {
            return currentState + 1;
        }

        for (int nextState = currentState; nextState > 0; nextState--) {
            if (pattern.charAt(nextState - 1) == characterCode) {
                boolean isPrefixSuffixMatch = true;
                for (int patternIndex = 0; patternIndex < nextState - 1; patternIndex++) {
                    if (pattern.charAt(patternIndex)
                        != pattern.charAt(currentState - nextState + patternIndex + 1)) {
                        isPrefixSuffixMatch = false;
                        break;
                    }
                }
                if (isPrefixSuffixMatch) {
                    return nextState;
                }
            }
        }

        return 0;
    }

    /**
     * A class representing the finite automata for pattern matching.
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
         * Gets the current state of the finite automata.
         *
         * @return The current state.
         */
        private int getState() {
            return state;
        }
    }
}