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
 * Problem reference:
 * https://practice.geeksforgeeks.org/problems/wildcard-pattern-matching/1
 */
public final class RegexMatching {

    private RegexMatching() {
        // Prevent instantiation
    }

    /**
     * Method 1: Pure recursion using substrings.
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
            // Source is empty: pattern must consist only of '*'
            for (int i = 0; i < pat.length(); i++) {
                if (pat.charAt(i) != '*') {
                    return false;
                }
            }
            return true;
        }

        char srcChar = src.charAt(0);
        char patChar = pat.charAt(0);

        String remainingSrc = src.substring(1);
        String remainingPat = pat.substring(1);

        if (srcChar == patChar || patChar == '?') {
            return regexRecursion(remainingSrc, remainingPat);
        } else if (patChar == '*') {
            // '*' can match:
            // - empty sequence: stay on src, move on pat
            // - one or more characters: move on src, stay on pat
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
    static boolean regexRecursion(String src, String pat, int srcIdx, int patIdx) {
        if (srcIdx == src.length() && patIdx == pat.length()) {
            return true;
        }

        if (srcIdx != src.length() && patIdx == pat.length()) {
            return false;
        }

        if (srcIdx == src.length()) {
            // Source is exhausted: remaining pattern must consist only of '*'
            for (int i = patIdx; i < pat.length(); i++) {
                if (pat.charAt(i) != '*') {
                    return false;
                }
            }
            return true;
        }

        char srcChar = src.charAt(srcIdx);
        char patChar = pat.charAt(patIdx);

        if (srcChar == patChar || patChar == '?') {
            return regexRecursion(src, pat, srcIdx + 1, patIdx + 1);
        } else if (patChar == '*') {
            // '*' can match:
            // - empty sequence: stay on srcIdx, move patIdx
            // - one or more characters: move srcIdx, stay on patIdx
            boolean blank = regexRecursion(src, pat, srcIdx, patIdx + 1);
            boolean multiple = regexRecursion(src, pat, srcIdx + 1, patIdx);
            return blank || multiple;
        } else {
            return false;
        }
    }

    /**
     * Method 3: Top-down DP (memoization).
     *
     * strg[srcIdx][patIdx] encoding:
     *   0 -> not computed
     *   1 -> false
     *   2 -> true
     *
     * Time:  O(N * M)
     * Space: O(N * M) + recursion stack
     */
    public static boolean regexRecursion(String src, String pat, int srcIdx, int patIdx, int[][] strg) {
        if (srcIdx == src.length() && patIdx == pat.length()) {
            return true;
        }

        if (srcIdx != src.length() && patIdx == pat.length()) {
            return false;
        }

        if (srcIdx == src.length()) {
            // Source is exhausted: remaining pattern must consist only of '*'
            for (int i = patIdx; i < pat.length(); i++) {
                if (pat.charAt(i) != '*') {
                    return false;
                }
            }
            return true;
        }

        if (strg[srcIdx][patIdx] != 0) {
            return strg[srcIdx][patIdx] == 2;
        }

        char srcChar = src.charAt(srcIdx);
        char patChar = pat.charAt(patIdx);

        boolean ans;
        if (srcChar == patChar || patChar == '?') {
            ans = regexRecursion(src, pat, srcIdx + 1, patIdx + 1, strg);
        } else if (patChar == '*') {
            // '*' can match:
            // - empty sequence: stay on srcIdx, move patIdx
            // - one or more characters: move srcIdx, stay on patIdx
            boolean blank = regexRecursion(src, pat, srcIdx, patIdx + 1, strg);
            boolean multiple = regexRecursion(src, pat, srcIdx + 1, patIdx, strg);
            ans = blank || multiple;
        } else {
            ans = false;
        }

        strg[srcIdx][patIdx] = ans ? 2 : 1;
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

        // Empty source and empty pattern match
        dp[n][m] = true;

        for (int row = n; row >= 0; row--) {
            for (int col = m - 1; col >= 0; col--) {
                if (row == n) {
                    // Source exhausted: pat[col..] must be all '*'
                    dp[row][col] = pat.charAt(col) == '*' && dp[row][col + 1];
                } else {
                    char srcChar = src.charAt(row);
                    char patChar = pat.charAt(col);

                    if (srcChar == patChar || patChar == '?') {
                        dp[row][col] = dp[row + 1][col + 1];
                    } else if (patChar == '*') {
                        // '*' can match:
                        // - empty sequence: move pattern index
                        // - one or more characters: move source index
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