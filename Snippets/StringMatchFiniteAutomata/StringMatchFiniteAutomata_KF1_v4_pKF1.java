package com.thealgorithms.strings;

import java.util.Set;
import java.util.TreeSet;

public final class FiniteAutomatonMatcher {

    private static final int ALPHABET_SIZE = Character.MAX_VALUE + 1;

    private FiniteAutomatonMatcher() {
    }

    public static Set<Integer> findPatternOccurrences(final String text, final String pattern) {
        final var transitionFunction = buildTransitionFunction(pattern);
        Automaton automaton = new Automaton(transitionFunction);

        Set<Integer> matchPositions = new TreeSet<>();
        for (int textIndex = 0; textIndex < text.length(); textIndex++) {
            automaton.processCharacter(text.charAt(textIndex));

            if (automaton.getCurrentState() == pattern.length()) {
                matchPositions.add(textIndex - pattern.length() + 1);
            }
        }
        return matchPositions;
    }

    private static int[][] buildTransitionFunction(final String pattern) {
        final int patternLength = pattern.length();
        int[][] transitionFunction = new int[patternLength + 1][ALPHABET_SIZE];

        for (int state = 0; state <= patternLength; state++) {
            for (int characterCode = 0; characterCode < ALPHABET_SIZE; characterCode++) {
                transitionFunction[state][characterCode] =
                        computeNextState(pattern, patternLength, state, characterCode);
            }
        }

        return transitionFunction;
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
                boolean prefixMatches = true;
                for (int prefixIndex = 0; prefixIndex < candidateState - 1; prefixIndex++) {
                    if (pattern.charAt(prefixIndex)
                            != pattern.charAt(currentState - candidateState + prefixIndex + 1)) {
                        prefixMatches = false;
                        break;
                    }
                }
                if (prefixMatches) {
                    return candidateState;
                }
            }
        }

        return 0;
    }

    private static final class Automaton {
        private int currentState = 0;
        private final int[][] transitionFunction;

        private Automaton(int[][] transitionFunction) {
            this.transitionFunction = transitionFunction;
        }

        private void processCharacter(final char inputCharacter) {
            currentState = transitionFunction[currentState][inputCharacter];
        }

        private int getCurrentState() {
            return currentState;
        }
    }
}