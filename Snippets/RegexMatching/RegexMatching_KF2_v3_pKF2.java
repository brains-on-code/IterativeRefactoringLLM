package com.thealgorithms.dynamicprogramming;

public final class RegexMatching {

    private RegexMatching() {
        // Utility class; prevent instantiation
    }

    /**
     * Recursive wildcard matching using substring-based recursion.
     *
     * Supported pattern characters:
     *  - '?' matches exactly one character
     *  - '*' matches zero or more characters
     */
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
            boolean matchEmpty = regexRecursion(src, remainingPat);
            boolean matchMultiple = regexRecursion(remainingSrc, pat);
            return matchEmpty || matchMultiple;
        }

        return false;
    }

    /**
     * Recursive wildcard matching using indices (no substring allocations).
     */
    static boolean regexRecursion(String src, String pat, int srcIdx, int patIdx) {
        if (srcIdx == src.length() && patIdx == pat.length()) {
            return true;
        }

        if (srcIdx != src.length() && patIdx == pat.length()) {
            return false;
        }

        if (srcIdx == src.length()) {
            return isAllStars(pat, patIdx);
        }

        char srcChar = src.charAt(srcIdx);
        char patChar = pat.charAt(patIdx);

        if (srcChar == patChar || patChar == '?') {
            return regexRecursion(src, pat, srcIdx + 1, patIdx + 1);
        }

        if (patChar == '*') {
            boolean matchEmpty = regexRecursion(src, pat, srcIdx, patIdx + 1);
            boolean matchMultiple = regexRecursion(src, pat, srcIdx + 1, patIdx);
            return matchEmpty || matchMultiple;
        }

        return false;
    }

    /**
     * Recursive wildcard matching with memoization.
     *
     * strg[srcIdx][patIdx] encoding:
     *  0 = not computed
     *  1 = false
     *  2 = true
     */
    public static boolean regexRecursion(String src, String pat, int srcIdx, int patIdx, int[][] strg) {
        if (srcIdx == src.length() && patIdx == pat.length()) {
            return true;
        }

        if (srcIdx != src.length() && patIdx == pat.length()) {
            return false;
        }

        if (srcIdx == src.length()) {
            return isAllStars(pat, patIdx);
        }

        if (strg[srcIdx][patIdx] != 0) {
            return strg[srcIdx][patIdx] == 2;
        }

        char srcChar = src.charAt(srcIdx);
        char patChar = pat.charAt(patIdx);

        boolean result;
        if (srcChar == patChar || patChar == '?') {
            result = regexRecursion(src, pat, srcIdx + 1, patIdx + 1, strg);
        } else if (patChar == '*') {
            boolean matchEmpty = regexRecursion(src, pat, srcIdx, patIdx + 1, strg);
            boolean matchMultiple = regexRecursion(src, pat, srcIdx + 1, patIdx, strg);
            result = matchEmpty || matchMultiple;
        } else {
            result = false;
        }

        strg[srcIdx][patIdx] = result ? 2 : 1;
        return result;
    }

    /**
     * Bottom-up dynamic programming implementation of wildcard matching.
     */
    static boolean regexBU(String src, String pat) {
        int n = src.length();
        int m = pat.length();
        boolean[][] dp = new boolean[n + 1][m + 1];

        dp[n][m] = true;

        for (int row = n; row >= 0; row--) {
            for (int col = m - 1; col >= 0; col--) {
                if (row == n) {
                    dp[row][col] = pat.charAt(col) == '*' && dp[row][col + 1];
                } else {
                    char srcChar = src.charAt(row);
                    char patChar = pat.charAt(col);

                    boolean result;
                    if (srcChar == patChar || patChar == '?') {
                        result = dp[row + 1][col + 1];
                    } else if (patChar == '*') {
                        boolean matchEmpty = dp[row][col + 1];
                        boolean matchMultiple = dp[row + 1][col];
                        result = matchEmpty || matchMultiple;
                    } else {
                        result = false;
                    }

                    dp[row][col] = result;
                }
            }
        }

        return dp[0][0];
    }

    /**
     * Returns true if pat[index..end] consists only of '*' characters.
     */
    private static boolean isAllStars(String pat, int index) {
        for (int i = index; i < pat.length(); i++) {
            if (pat.charAt(i) != '*') {
                return false;
            }
        }
        return true;
    }
}