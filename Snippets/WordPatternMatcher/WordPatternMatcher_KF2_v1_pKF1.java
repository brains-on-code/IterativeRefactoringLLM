package com.thealgorithms.backtracking;

import java.util.HashMap;
import java.util.Map;

public final class WordPatternMatcher {

    private WordPatternMatcher() {
    }

    public static boolean matchWordPattern(String pattern, String text) {
        Map<Character, String> charToWord = new HashMap<>();
        Map<String, Character> wordToChar = new HashMap<>();
        return backtrack(pattern, text, 0, 0, charToWord, wordToChar);
    }

    private static boolean backtrack(
            String pattern,
            String text,
            int patternPos,
            int textPos,
            Map<Character, String> charToWord,
            Map<String, Character> wordToChar
    ) {
        if (patternPos == pattern.length() && textPos == text.length()) {
            return true;
        }
        if (patternPos == pattern.length() || textPos == text.length()) {
            return false;
        }

        char patternChar = pattern.charAt(patternPos);

        if (charToWord.containsKey(patternChar)) {
            String mappedWord = charToWord.get(patternChar);
            if (text.startsWith(mappedWord, textPos)) {
                return backtrack(
                        pattern,
                        text,
                        patternPos + 1,
                        textPos + mappedWord.length(),
                        charToWord,
                        wordToChar
                );
            }
            return false;
        }

        for (int end = textPos + 1; end <= text.length(); end++) {
            String candidateWord = text.substring(textPos, end);
            if (wordToChar.containsKey(candidateWord)) {
                continue;
            }

            charToWord.put(patternChar, candidateWord);
            wordToChar.put(candidateWord, patternChar);

            if (backtrack(pattern, text, patternPos + 1, end, charToWord, wordToChar)) {
                return true;
            }

            charToWord.remove(patternChar);
            wordToChar.remove(candidateWord);
        }

        return false;
    }
}