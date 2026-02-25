package com.thealgorithms.dynamicprogramming;

public final class RegexMatching {

    private RegexMatching() {}

    public static boolean regexRecursion(String src, String pat) {
        if (src.isEmpty() && pat.isEmpty()) {
            return true;
        }
        if (!src.isEmpty() && pat.isEmpty()) {
            return false;
        }
        if (src.isEmpty()) {
            return isAllStars(pat, 0);
        }

        char srcChar = src.charAt(0);
        char patChar = pat.charAt(0);

        String remainingSrc = src.substring(1);
        String remainingPat = pat.substring(1);

        if (isCharMatch(srcChar, patChar)) {
            return regexRecursion(remainingSrc, remainingPat);
        }
        if (patChar == '*') {
            return regexRecursion(src, remainingPat) || regexRecursion(remainingSrc, pat);
        }
        return false;
    }

    static boolean regexRecursion(String src, String pat, int srcIndex, int patIndex) {
        if (srcIndex == src.length() && patIndex == pat.length()) {
            return true;
        }
        if (srcIndex != src.length() && patIndex == pat.length()) {
            return false;
        }
        if (srcIndex == src.length()) {
            return isAllStars(pat, patIndex);
        }

        char srcChar = src.charAt(srcIndex);
        char patChar = pat.charAt(patIndex);

        if (isCharMatch(srcChar, patChar)) {
            return regexRecursion(src, pat, srcIndex + 1, patIndex + 1);
        }
        if (patChar == '*') {
            return regexRecursion(src, pat, srcIndex, patIndex + 1)
                || regexRecursion(src, pat, srcIndex + 1, patIndex);
        }
        return false;
    }

    public static boolean regexRecursion(String src, String pat, int srcIndex, int patIndex, int[][] memo) {
        if (srcIndex == src.length() && patIndex == pat.length()) {
            return true;
        }
        if (srcIndex != src.length() && patIndex == pat.length()) {
            return false;
        }
        if (srcIndex == src.length()) {
            return isAllStars(pat, patIndex);
        }

        if (memo[srcIndex][patIndex] != 0) {
            return memo[srcIndex][patIndex] != 1;
        }

        char srcChar = src.charAt(srcIndex);
        char patChar = pat.charAt(patIndex);

        boolean result;
        if (isCharMatch(srcChar, patChar)) {
            result = regexRecursion(src, pat, srcIndex + 1, patIndex + 1, memo);
        } else if (patChar == '*') {
            result =
                regexRecursion(src, pat, srcIndex, patIndex + 1, memo)
                    || regexRecursion(src, pat, srcIndex + 1, patIndex, memo);
        } else {
            result = false;
        }

        memo[srcIndex][patIndex] = result ? 2 : 1;
        return result;
    }

    static boolean regexBU(String src, String pat) {
        int srcLen = src.length();
        int patLen = pat.length();
        boolean[][] dp = new boolean[srcLen + 1][patLen + 1];

        dp[srcLen][patLen] = true;

        for (int row = srcLen; row >= 0; row--) {
            for (int col = patLen - 1; col >= 0; col--) {
                char patChar = pat.charAt(col);

                if (row == srcLen) {
                    dp[row][col] = patChar == '*' && dp[row][col + 1];
                    continue;
                }

                char srcChar = src.charAt(row);
                boolean result;

                if (isCharMatch(srcChar, patChar)) {
                    result = dp[row + 1][col + 1];
                } else if (patChar == '*') {
                    result = dp[row][col + 1] || dp[row + 1][col];
                } else {
                    result = false;
                }

                dp[row][col] = result;
            }
        }
        return dp[0][0];
    }

    private static boolean isAllStars(String pattern, int startIndex) {
        for (int i = startIndex; i < pattern.length(); i++) {
            if (pattern.charAt(i) != '*') {
                return false;
            }
        }
        return true;
    }

    private static boolean isCharMatch(char srcChar, char patChar) {
        return srcChar == patChar || patChar == '?';
    }
}