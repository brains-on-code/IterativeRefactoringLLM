package com.thealgorithms.backtracking;

import java.util.HashMap;
import java.util.Map;

public final class WordPatternMatcher {

    private WordPatternMatcher() {
    }

    public static boolean matchWordPattern(String pattern, String text) {
        Map<Character, String> patternToWordMap = new HashMap<>();
        Map<String, Character> wordToPatternMap = new HashMap<>();
        return isMatch(pattern, text, 0, 0, patternToWordMap, wordToPatternMap);
    }

    private static boolean isMatch(
            String pattern,
            String text,
            int patternIndex,
            int textIndex,
            Map<Character, String> patternToWordMap,
            Map<String, Character> wordToPatternMap
    ) {
        if (patternIndex == pattern.length() && textIndex == text.length()) {
            return true;
        }
        if (patternIndex == pattern.length() || textIndex == text.length()) {
            return false;
        }

        char currentPatternChar = pattern.charAt(patternIndex);

        if (patternToWordMap.containsKey(currentPatternChar)) {
            String mappedWord = patternToWordMap.get(currentPatternChar);
            if (text.startsWith(mappedWord, textIndex)) {
                return isMatch(
                        pattern,
                        text,
                        patternIndex + 1,
                        textIndex + mappedWord.length(),
                        patternToWordMap,
                        wordToPatternMap
                );
            }
            return false;
        }

        for (int endIndex = textIndex + 1; endIndex <= text.length(); endIndex++) {
            String candidateWord = text.substring(textIndex, endIndex);
            if (wordToPatternMap.containsKey(candidateWord)) {
                continue;
            }

            patternToWordMap.put(currentPatternChar, candidateWord);
            wordToPatternMap.put(candidateWord, currentPatternChar);

            if (isMatch(pattern, text, patternIndex + 1, endIndex, patternToWordMap, wordToPatternMap)) {
                return true;
            }

            patternToWordMap.remove(currentPatternChar);
            wordToPatternMap.remove(candidateWord);
        }

        return false;
    }
}