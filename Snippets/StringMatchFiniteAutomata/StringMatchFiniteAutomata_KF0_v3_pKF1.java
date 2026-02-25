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

        Set<Integer> matchStartIndices = new TreeSet<>();
        for (int textIndex = 0; textIndex < text.length(); textIndex++) {
            automaton.consume(text.charAt(textIndex));

            if (automaton.getState() == pattern.length()) {
                matchStartIndices.add(textIndex - pattern.length() + 1);
            }
        }
        return matchStartIndices;
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
            for (int inputCharCode = 0; inputCharCode < ALPHABET_SIZE; inputCharCode++) {
                transitionTable[state][inputCharCode] =
                    computeNextState(pattern, patternLength, state, inputCharCode);
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
     * @param inputCharCode The current character from the input alphabet (as int).
     * @return The next state.
     */
    private static int computeNextState(
        final String pattern,
        final int patternLength,
        final int currentState,
        final int inputCharCode
    ) {
        if (currentState < patternLength && inputCharCode == pattern.charAt(currentState)) {
            return currentState + 1;
        }

        for (int candidateState = currentState; candidateState > 0; candidateState--) {
            if (pattern.charAt(candidateState - 1) == inputCharCode) {
                boolean isPrefixMatchingSuffix = true;
                for (int prefixIndex = 0; prefixIndex < candidateState - 1; prefixIndex++) {
                    int suffixIndex = currentState - candidateState + prefixIndex + 1;
                    if (pattern.charAt(prefixIndex) != pattern.charAt(suffixIndex)) {
                        isPrefixMatchingSuffix = false;
                        break;
                    }
                }
                if (isPrefixMatchingSuffix) {
                    return candidateState;
                }
            }
        }

        return 0;
    }

    /**
     * A class representing the finite automata for pattern matching.
     */
    private static final class FiniteAutomaton {
        private int currentState = 0;
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
            currentState = transitionTable[currentState][input];
        }

        /**
         * Gets the current state of the finite automata.
         *
         * @return The current state.
         */
        private int getState() {
            return currentState;
        }
    }
}