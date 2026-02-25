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

        if (srcChar == patChar || patChar == '?') {
            return regexRecursion(remainingSrc, remainingPat);
        }
        if (patChar == '*') {
            boolean matchBlank = regexRecursion(src, remainingPat);
            boolean matchMultiple = regexRecursion(remainingSrc, pat);
            return matchBlank || matchMultiple;
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

        if (srcChar == patChar || patChar == '?') {
            return regexRecursion(src, pat, srcIndex + 1, patIndex + 1);
        }
        if (patChar == '*') {
            boolean matchBlank = regexRecursion(src, pat, srcIndex, patIndex + 1);
            boolean matchMultiple = regexRecursion(src, pat, srcIndex + 1, patIndex);
            return matchBlank || matchMultiple;
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
        if (srcChar == patChar || patChar == '?') {
            result = regexRecursion(src, pat, srcIndex + 1, patIndex + 1, memo);
        } else if (patChar == '*') {
            boolean matchBlank = regexRecursion(src, pat, srcIndex, patIndex + 1, memo);
            boolean matchMultiple = regexRecursion(src, pat, srcIndex + 1, patIndex, memo);
            result = matchBlank || matchMultiple;
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
                if (row == srcLen) {
                    dp[row][col] = pat.charAt(col) == '*' && dp[row][col + 1];
                } else {
                    char srcChar = src.charAt(row);
                    char patChar = pat.charAt(col);

                    boolean result;
                    if (srcChar == patChar || patChar == '?') {
                        result = dp[row + 1][col + 1];
                    } else if (patChar == '*') {
                        boolean matchBlank = dp[row][col + 1];
                        boolean matchMultiple = dp[row + 1][col];
                        result = matchBlank || matchMultiple;
                    } else {
                        result = false;
                    }
                    dp[row][col] = result;
                }
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
}