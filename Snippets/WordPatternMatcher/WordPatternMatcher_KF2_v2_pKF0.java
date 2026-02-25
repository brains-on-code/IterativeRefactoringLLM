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
        boolean isPatternComplete = patternIndex == pattern.length();
        boolean isInputComplete = inputIndex == input.length();

        if (isPatternComplete && isInputComplete) {
            return true;
        }
        if (isPatternComplete || isInputComplete) {
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

        for (int endIndex = inputIndex + 1; endIndex <= input.length(); endIndex++) {
            String candidateWord = input.substring(inputIndex, endIndex);

            if (wordToChar.containsKey(candidateWord)) {
                continue;
            }

            charToWord.put(currentPatternChar, candidateWord);
            wordToChar.put(candidateWord, currentPatternChar);

            if (backtrack(pattern, input, patternIndex + 1, endIndex, charToWord, wordToChar)) {
                return true;
            }

            charToWord.remove(currentPatternChar);
            wordToChar.remove(candidateWord);
        }

        return false;
    }
}