package com.thealgorithms.dynamicprogramming;

/**
 * Wildcard pattern matching with support for:
 * - '?' : matches any single character
 * - '*' : matches any sequence of characters (including empty)
 *
 * Matching must cover the entire source string.
 *
 * Let N be the length of src and M be the length of pat.
 *
 * Memoization vs Tabulation:
 * https://www.geeksforgeeks.org/tabulation-vs-memoization/
 *
 * Problem reference:
 * https://practice.geeksforgeeks.org/problems/wildcard-pattern-matching/1
 */
public final class RegexMatching {

    private RegexMatching() {
        // Utility class; prevent instantiation
    }

    /**
     * Method 1: Pure recursion on substrings.
     *
     * Time:  O(2^(N+M))
     * Space: O(N + M) (recursion stack)
     */
    public static boolean regexRecursion(String src, String pat) {
        if (src.length() == 0 && pat.length() == 0) {
            return true;
        }

        if (src.length() != 0 && pat.length() == 0) {
            return false;
        }

        if (src.length() == 0) {
            // src is empty, pat must be all '*'
            for (int i = 0; i < pat.length(); i++) {
                if (pat.charAt(i) != '*') {
                    return false;
                }
            }
            return true;
        }

        char chs = src.charAt(0);
        char chp = pat.charAt(0);

        String remainingSrc = src.substring(1);
        String remainingPat = pat.substring(1);

        if (chs == chp || chp == '?') {
            return regexRecursion(remainingSrc, remainingPat);
        } else if (chp == '*') {
            // '*' matches empty or one/more characters
            boolean blank = regexRecursion(src, remainingPat);
            boolean multiple = regexRecursion(remainingSrc, pat);
            return blank || multiple;
        } else {
            return false;
        }
    }

    /**
     * Method 2: Recursion using indices instead of substrings.
     *
     * Time:  O(2^(N+M))
     * Space: O(N + M) (recursion stack)
     */
    static boolean regexRecursion(String src, String pat, int svidx, int pvidx) {
        if (svidx == src.length() && pvidx == pat.length()) {
            return true;
        }

        if (svidx != src.length() && pvidx == pat.length()) {
            return false;
        }

        if (svidx == src.length()) {
            // src is exhausted; remaining pat must be all '*'
            for (int i = pvidx; i < pat.length(); i++) {
                if (pat.charAt(i) != '*') {
                    return false;
                }
            }
            return true;
        }

        char chs = src.charAt(svidx);
        char chp = pat.charAt(pvidx);

        if (chs == chp || chp == '?') {
            return regexRecursion(src, pat, svidx + 1, pvidx + 1);
        } else if (chp == '*') {
            // '*' matches empty or one/more characters
            boolean blank = regexRecursion(src, pat, svidx, pvidx + 1);
            boolean multiple = regexRecursion(src, pat, svidx + 1, pvidx);
            return blank || multiple;
        } else {
            return false;
        }
    }

    /**
     * Method 3: Top-down DP (memoization).
     *
     * strg[svidx][pvidx] encoding:
     *   0 -> not computed
     *   1 -> false
     *   2 -> true
     *
     * Time:  O(N * M)
     * Space: O(N * M) + recursion stack
     */
    public static boolean regexRecursion(String src, String pat, int svidx, int pvidx, int[][] strg) {
        if (svidx == src.length() && pvidx == pat.length()) {
            return true;
        }

        if (svidx != src.length() && pvidx == pat.length()) {
            return false;
        }

        if (svidx == src.length()) {
            // src is exhausted; remaining pat must be all '*'
            for (int i = pvidx; i < pat.length(); i++) {
                if (pat.charAt(i) != '*') {
                    return false;
                }
            }
            return true;
        }

        if (strg[svidx][pvidx] != 0) {
            return strg[svidx][pvidx] == 2;
        }

        char chs = src.charAt(svidx);
        char chp = pat.charAt(pvidx);

        boolean ans;
        if (chs == chp || chp == '?') {
            ans = regexRecursion(src, pat, svidx + 1, pvidx + 1, strg);
        } else if (chp == '*') {
            // '*' matches empty or one/more characters
            boolean blank = regexRecursion(src, pat, svidx, pvidx + 1, strg);
            boolean multiple = regexRecursion(src, pat, svidx + 1, pvidx, strg);
            ans = blank || multiple;
        } else {
            ans = false;
        }

        strg[svidx][pvidx] = ans ? 2 : 1;
        return ans;
    }

    /**
     * Method 4: Bottom-up DP (tabulation).
     *
     * Time:  O(N * M)
     * Space: O(N * M)
     */
    static boolean regexBU(String src, String pat) {
        int n = src.length();
        int m = pat.length();

        boolean[][] dp = new boolean[n + 1][m + 1];

        // Empty src and empty pat match
        dp[n][m] = true;

        for (int row = n; row >= 0; row--) {
            for (int col = m - 1; col >= 0; col--) {
                if (row == n) {
                    // src exhausted: pat[col..] must be all '*'
                    dp[row][col] = pat.charAt(col) == '*' && dp[row][col + 1];
                } else {
                    char chs = src.charAt(row);
                    char chp = pat.charAt(col);

                    if (chs == chp || chp == '?') {
                        dp[row][col] = dp[row + 1][col + 1];
                    } else if (chp == '*') {
                        // '*' matches empty or one/more characters
                        boolean blank = dp[row][col + 1];
                        boolean multiple = dp[row + 1][col];
                        dp[row][col] = blank || multiple;
                    } else {
                        dp[row][col] = false;
                    }
                }
            }
        }

        return dp[0][0];
    }
}