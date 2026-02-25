package com.thealgorithms.backtracking;

import java.util.HashMap;
import java.util.Map;

public final class WordPatternMatcher {

    private WordPatternMatcher() {
    }

    public static boolean matchWordPattern(String pattern, String text) {
        Map<Character, String> patternToWord = new HashMap<>();
        Map<String, Character> wordToPattern = new HashMap<>();
        return backtrack(pattern, text, 0, 0, patternToWord, wordToPattern);
    }

    private static boolean backtrack(
            String pattern,
            String text,
            int patternPos,
            int textPos,
            Map<Character, String> patternToWord,
            Map<String, Character> wordToPattern
    ) {
        if (patternPos == pattern.length() && textPos == text.length()) {
            return true;
        }
        if (patternPos == pattern.length() || textPos == text.length()) {
            return false;
        }

        char patternChar = pattern.charAt(patternPos);

        if (patternToWord.containsKey(patternChar)) {
            String mappedWord = patternToWord.get(patternChar);
            if (text.startsWith(mappedWord, textPos)) {
                return backtrack(
                        pattern,
                        text,
                        patternPos + 1,
                        textPos + mappedWord.length(),
                        patternToWord,
                        wordToPattern
                );
            }
            return false;
        }

        for (int endPos = textPos + 1; endPos <= text.length(); endPos++) {
            String candidate = text.substring(textPos, endPos);
            if (wordToPattern.containsKey(candidate)) {
                continue;
            }

            patternToWord.put(patternChar, candidate);
            wordToPattern.put(candidate, patternChar);

            if (backtrack(pattern, text, patternPos + 1, endPos, patternToWord, wordToPattern)) {
                return true;
            }

            patternToWord.remove(patternChar);
            wordToPattern.remove(candidate);
        }

        return false;
    }
}