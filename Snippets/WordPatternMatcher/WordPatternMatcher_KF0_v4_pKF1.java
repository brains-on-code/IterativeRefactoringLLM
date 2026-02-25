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
        Map<Character, String> charToWordMap = new HashMap<>();
        Map<String, Character> wordToCharMap = new HashMap<>();
        return isPatternMatch(pattern, input, 0, 0, charToWordMap, wordToCharMap);
    }

    /**
     * Backtracking helper function to check if the pattern matches the string.
     *
     * @param pattern The pattern string.
     * @param input The string to match against the pattern.
     * @param patternIndex Current index in the pattern.
     * @param inputIndex Current index in the input string.
     * @param charToWordMap Map to store pattern characters to string mappings.
     * @param wordToCharMap Map to store string to pattern character mappings.
     * @return True if the pattern matches, False otherwise.
     */
    private static boolean isPatternMatch(
        String pattern,
        String input,
        int patternIndex,
        int inputIndex,
        Map<Character, String> charToWordMap,
        Map<String, Character> wordToCharMap
    ) {
        boolean isPatternConsumed = patternIndex == pattern.length();
        boolean isInputConsumed = inputIndex == input.length();

        if (isPatternConsumed && isInputConsumed) {
            return true;
        }
        if (isPatternConsumed || isInputConsumed) {
            return false;
        }

        char currentPatternChar = pattern.charAt(patternIndex);

        if (charToWordMap.containsKey(currentPatternChar)) {
            String mappedWord = charToWordMap.get(currentPatternChar);
            if (input.startsWith(mappedWord, inputIndex)) {
                return isPatternMatch(
                    pattern,
                    input,
                    patternIndex + 1,
                    inputIndex + mappedWord.length(),
                    charToWordMap,
                    wordToCharMap
                );
            }
            return false;
        }

        for (int endIndex = inputIndex + 1; endIndex <= input.length(); endIndex++) {
            String candidateWord = input.substring(inputIndex, endIndex);
            if (wordToCharMap.containsKey(candidateWord)) {
                continue;
            }

            charToWordMap.put(currentPatternChar, candidateWord);
            wordToCharMap.put(candidateWord, currentPatternChar);

            if (isPatternMatch(
                pattern,
                input,
                patternIndex + 1,
                endIndex,
                charToWordMap,
                wordToCharMap
            )) {
                return true;
            }

            charToWordMap.remove(currentPatternChar);
            wordToCharMap.remove(candidateWord);
        }

        return false;
    }
}