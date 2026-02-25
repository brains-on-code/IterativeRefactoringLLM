package com.thealgorithms.backtracking;

import java.util.HashMap;
import java.util.Map;


public final class WordPatternMatcher {
    private WordPatternMatcher() {
    }


    public static boolean matchWordPattern(String pattern, String inputString) {
        Map<Character, String> patternMap = new HashMap<>();
        Map<String, Character> strMap = new HashMap<>();
        return backtrack(pattern, inputString, 0, 0, patternMap, strMap);
    }


    private static boolean backtrack(String pattern, String inputString, int patternIndex, int strIndex, Map<Character, String> patternMap, Map<String, Character> strMap) {
        if (patternIndex == pattern.length() && strIndex == inputString.length()) {
            return true;
        }
        if (patternIndex == pattern.length() || strIndex == inputString.length()) {
            return false;
        }

        char currentChar = pattern.charAt(patternIndex);
        if (patternMap.containsKey(currentChar)) {
            String mappedStr = patternMap.get(currentChar);
            if (inputString.startsWith(mappedStr, strIndex)) {
                return backtrack(pattern, inputString, patternIndex + 1, strIndex + mappedStr.length(), patternMap, strMap);
            } else {
                return false;
            }
        }

        for (int end = strIndex + 1; end <= inputString.length(); end++) {
            String substring = inputString.substring(strIndex, end);
            if (strMap.containsKey(substring)) {
                continue;
            }

            patternMap.put(currentChar, substring);
            strMap.put(substring, currentChar);
            if (backtrack(pattern, inputString, patternIndex + 1, end, patternMap, strMap)) {
                return true;
            }

            patternMap.remove(currentChar);
            strMap.remove(substring);
        }

        return false;
    }
}
