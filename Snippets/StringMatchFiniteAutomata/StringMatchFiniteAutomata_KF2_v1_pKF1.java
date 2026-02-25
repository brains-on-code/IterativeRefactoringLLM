package com.thealgorithms.strings;

import java.util.Set;
import java.util.TreeSet;

public final class StringMatchFiniteAutomata {

    private static final int CHARACTER_SET_SIZE = Character.MAX_VALUE + 1;

    private StringMatchFiniteAutomata() {
    }

    public static Set<Integer> searchPattern(final String text, final String pattern) {
        final int[][] transitionTable = buildTransitionTable(pattern);
        FiniteAutomaton automaton = new FiniteAutomaton(transitionTable);

        Set<Integer> matchIndices = new TreeSet<>();
        for (int textIndex = 0; textIndex < text.length(); textIndex++) {
            automaton.consume(text.charAt(textIndex));

            if (automaton.getCurrentState() == pattern.length()) {
                matchIndices.add(textIndex - pattern.length() + 1);
            }
        }
        return matchIndices;
    }

    private static int[][] buildTransitionTable(final String pattern) {
        final int patternLength = pattern.length();
        int[][] transitionTable = new int[patternLength + 1][CHARACTER_SET_SIZE];

        for (int state = 0; state <= patternLength; state++) {
            for (int characterCode = 0; characterCode < CHARACTER_SET_SIZE; characterCode++) {
                transitionTable[state][characterCode] =
                    computeNextState(pattern, patternLength, state, characterCode);
            }
        }

        return transitionTable;
    }

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
                for (int index = 0; index < nextState - 1; index++) {
                    if (pattern.charAt(index)
                        != pattern.charAt(currentState - nextState + index + 1)) {
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

    private static final class FiniteAutomaton {
        private int currentState = 0;
        private final int[][] transitionTable;

        private FiniteAutomaton(int[][] transitionTable) {
            this.transitionTable = transitionTable;
        }

        private void consume(final char inputCharacter) {
            currentState = transitionTable[currentState][inputCharacter];
        }

        private int getCurrentState() {
            return currentState;
        }
    }
}