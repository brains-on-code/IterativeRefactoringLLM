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

        Set<Integer> matchStartIndices = new TreeSet<>();
        for (int textIndex = 0; textIndex < text.length(); textIndex++) {
            automaton.consume(text.charAt(textIndex));

            if (automaton.getCurrentState() == pattern.length()) {
                int matchStartIndex = textIndex - pattern.length() + 1;
                matchStartIndices.add(matchStartIndex);
            }
        }
        return matchStartIndices;
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

        for (int candidateState = currentState; candidateState > 0; candidateState--) {
            if (pattern.charAt(candidateState - 1) == characterCode) {
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