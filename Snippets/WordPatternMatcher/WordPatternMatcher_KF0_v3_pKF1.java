package com.thealgorithms.backtracking;

import java.util.HashMap;
import java.util.Map;

/**
 * Class to determine if a pattern matches a string using backtracking.
 *
 * Example:
 * Pattern: "abab"
 * Input String: "JavaPythonJavaPython"
 * Output: true
 *
 * Pattern: "aaaa"
 * Input String: "JavaJavaJavaJava"
 * Output: true
 *
 * Pattern: "aabb"
 * Input String: "JavaPythonPythonJava"
 * Output: false
 */
public final class WordPatternMatcher {

    private WordPatternMatcher() {
    }

    /**
     * Determines if the given pattern matches the input string using backtracking.
     *
     * @param pattern The pattern to match.
     * @param input The string to match against the pattern.
     * @return True if the pattern matches the string, False otherwise.
     */
    public static boolean matchWordPattern(String pattern, String input) {
        Map<Character, String> patternToWord = new HashMap<>();
        Map<String, Character> wordToPattern = new HashMap<>();
        return isPatternMatch(pattern, input, 0, 0, patternToWord, wordToPattern);
    }

    /**
     * Backtracking helper function to check if the pattern matches the string.
     *
     * @param pattern The pattern string.
     * @param input The string to match against the pattern.
     * @param patternIndex Current index in the pattern.
     * @param inputIndex Current index in the input string.
     * @param patternToWord Map to store pattern characters to string mappings.
     * @param wordToPattern Map to store string to pattern character mappings.
     * @return True if the pattern matches, False otherwise.
     */
    private static boolean isPatternMatch(
        String pattern,
        String input,
        int patternIndex,
        int inputIndex,
        Map<Character, String> patternToWord,
        Map<String, Character> wordToPattern
    ) {
        boolean patternFullyConsumed = patternIndex == pattern.length();
        boolean inputFullyConsumed = inputIndex == input.length();

        if (patternFullyConsumed && inputFullyConsumed) {
            return true;
        }
        if (patternFullyConsumed || inputFullyConsumed) {
            return false;
        }

        char patternChar = pattern.charAt(patternIndex);

        if (patternToWord.containsKey(patternChar)) {
            String mappedSegment = patternToWord.get(patternChar);
            if (input.startsWith(mappedSegment, inputIndex)) {
                return isPatternMatch(
                    pattern,
                    input,
                    patternIndex + 1,
                    inputIndex + mappedSegment.length(),
                    patternToWord,
                    wordToPattern
                );
            }
            return false;
        }

        for (int segmentEndIndex = inputIndex + 1; segmentEndIndex <= input.length(); segmentEndIndex++) {
            String candidateSegment = input.substring(inputIndex, segmentEndIndex);
            if (wordToPattern.containsKey(candidateSegment)) {
                continue;
            }

            patternToWord.put(patternChar, candidateSegment);
            wordToPattern.put(candidateSegment, patternChar);

            if (isPatternMatch(
                pattern,
                input,
                patternIndex + 1,
                segmentEndIndex,
                patternToWord,
                wordToPattern
            )) {
                return true;
            }

            patternToWord.remove(patternChar);
            wordToPattern.remove(candidateSegment);
        }

        return false;
    }
}