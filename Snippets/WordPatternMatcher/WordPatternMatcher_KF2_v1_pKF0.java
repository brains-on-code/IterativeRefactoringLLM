package com.thealgorithms.backtracking;

import java.util.HashMap;
import java.util.Map;

public final class WordPatternMatcher {

    private WordPatternMatcher() {
        // Utility class; prevent instantiation
    }

    public static boolean matchWordPattern(String pattern, String input) {
        Map<Character, String> charToWord = new HashMap<>();
        Map<String, Character> wordToChar = new HashMap<>();
        return backtrack(pattern, input, 0, 0, charToWord, wordToChar);
    }

    private static boolean backtrack(
            String pattern,
            String input,
            int patternIndex,
            int inputIndex,
            Map<Character, String> charToWord,
            Map<String, Character> wordToChar
    ) {
        boolean patternExhausted = patternIndex == pattern.length();
        boolean inputExhausted = inputIndex == input.length();

        if (patternExhausted && inputExhausted) {
            return true;
        }
        if (patternExhausted || inputExhausted) {
            return false;
        }

        char currentPatternChar = pattern.charAt(patternIndex);

        if (charToWord.containsKey(currentPatternChar)) {
            String mappedWord = charToWord.get(currentPatternChar);
            if (!input.startsWith(mappedWord, inputIndex)) {
                return false;
            }
            return backtrack(
                    pattern,
                    input,
                    patternIndex + 1,
                    inputIndex + mappedWord.length(),
                    charToWord,
                    wordToChar
            );
        }

        for (int end = inputIndex + 1; end <= input.length(); end++) {
            String candidate = input.substring(inputIndex, end);

            if (wordToChar.containsKey(candidate)) {
                continue;
            }

            charToWord.put(currentPatternChar, candidate);
            wordToChar.put(candidate, currentPatternChar);

            if (backtrack(pattern, input, patternIndex + 1, end, charToWord, wordToChar)) {
                return true;
            }

            charToWord.remove(currentPatternChar);
            wordToChar.remove(candidate);
        }

        return false;
    }
}