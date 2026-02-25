package com.thealgorithms.dynamicprogramming;

public final class WildcardMatching {

    private WildcardMatching() {
        // Utility class; prevent instantiation
    }

    public static boolean isMatch(String text, String pattern) {
        int textLength = text.length();
        int patternLength = pattern.length();

        boolean[][] dp = new boolean[textLength + 1][patternLength + 1];

        initializeEmptyTextRow(pattern, patternLength, dp);
        fillDpTable(text, pattern, textLength, patternLength, dp);

        return dp[textLength][patternLength];
    }

    private static void initializeEmptyTextRow(String pattern, int patternLength, boolean[][] dp) {
        dp[0][0] = true; // Empty pattern matches empty text

        // Handle leading '*' in pattern that can match empty text
        for (int p = 1; p <= patternLength; p++) {
            if (pattern.charAt(p - 1) == '*') {
                dp[0][p] = dp[0][p - 1];
            } else {
                // Once a non-'*' is found, no further positions can match empty text
                break;
            }
        }
    }

    private static void fillDpTable(
        String text,
        String pattern,
        int textLength,
        int patternLength,
        boolean[][] dp
    ) {
        for (int t = 1; t <= textLength; t++) {
            char textChar = text.charAt(t - 1);

            for (int p = 1; p <= patternLength; p++) {
                char patternChar = pattern.charAt(p - 1);

                if (isDirectMatch(textChar, patternChar)) {
                    dp[t][p] = dp[t - 1][p - 1];
                } else if (patternChar == '*') {
                    dp[t][p] = matchesZeroCharacters(dp, t, p) || matchesOneOrMoreCharacters(dp, t, p);
                }
            }
        }
    }

    private static boolean isDirectMatch(char textChar, char patternChar) {
        return patternChar == textChar || patternChar == '?';
    }

    private static boolean matchesZeroCharacters(boolean[][] dp, int t, int p) {
        return dp[t][p - 1];
    }

    private static boolean matchesOneOrMoreCharacters(boolean[][] dp, int t, int p) {
        return dp[t - 1][p];
    }
}