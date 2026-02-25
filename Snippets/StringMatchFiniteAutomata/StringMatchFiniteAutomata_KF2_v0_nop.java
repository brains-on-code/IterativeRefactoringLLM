package com.thealgorithms.strings;

import java.util.Set;
import java.util.TreeSet;


public final class StringMatchFiniteAutomata {

    private static final int CHARS = Character.MAX_VALUE + 1;

    private StringMatchFiniteAutomata() {
    }


    public static Set<Integer> searchPattern(final String text, final String pattern) {
        final var stateTransitionTable = computeStateTransitionTable(pattern);
        FiniteAutomata finiteAutomata = new FiniteAutomata(stateTransitionTable);

        Set<Integer> indexFound = new TreeSet<>();
        for (int i = 0; i < text.length(); i++) {
            finiteAutomata.consume(text.charAt(i));

            if (finiteAutomata.getState() == pattern.length()) {
                indexFound.add(i - pattern.length() + 1);
            }
        }
        return indexFound;
    }


    private static int[][] computeStateTransitionTable(final String pattern) {
        final int patternLength = pattern.length();
        int[][] stateTransitionTable = new int[patternLength + 1][CHARS];

        for (int state = 0; state <= patternLength; ++state) {
            for (int x = 0; x < CHARS; ++x) {
                stateTransitionTable[state][x] = getNextState(pattern, patternLength, state, x);
            }
        }

        return stateTransitionTable;
    }


    private static int getNextState(final String pattern, final int patternLength, final int state, final int x) {
        if (state < patternLength && x == pattern.charAt(state)) {
            return state + 1;
        }

        for (int ns = state; ns > 0; ns--) {
            if (pattern.charAt(ns - 1) == x) {
                boolean match = true;
                for (int i = 0; i < ns - 1; i++) {
                    if (pattern.charAt(i) != pattern.charAt(state - ns + i + 1)) {
                        match = false;
                        break;
                    }
                }
                if (match) {
                    return ns;
                }
            }
        }

        return 0;
    }


    private static final class FiniteAutomata {
        private int state = 0;
        private final int[][] stateTransitionTable;

        private FiniteAutomata(int[][] stateTransitionTable) {
            this.stateTransitionTable = stateTransitionTable;
        }


        private void consume(final char input) {
            state = stateTransitionTable[state][input];
        }


        private int getState() {
            return state;
        }
    }
}
