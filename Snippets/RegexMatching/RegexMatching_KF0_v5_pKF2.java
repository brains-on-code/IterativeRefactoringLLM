package com.thealgorithms.dynamicprogramming;

/**
 * Wildcard pattern matching with support for:
 * <ul>
 *   <li>'?' – matches any single character</li>
 *   <li>'*' – matches any sequence of characters (including empty)</li>
 * </ul>
 *
 * Matching must cover the entire source string.
 *
 * Let N be the length of {@code src} and M be the length of {@code pat}.
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
        }

        if (patChar == '*') {
            // '*' can match:
            // - empty sequence: move to next pattern character
            // - one or more characters: move to next source character
            boolean matchEmpty = regexRecursion(src, remainingPat);
            boolean matchMultiple = regexRecursion(remainingSrc, pat);
            return matchEmpty || matchMultiple;
        }

        return false;
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
            // Source exhausted: remaining pattern must consist only of '*'
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
        }

        if (patChar == '*') {
            // '*' can match:
            // - empty sequence: move to next pattern character
            // - one or more characters: move to next source character
            boolean matchEmpty = regexRecursion(src, pat, srcIdx, patIdx + 1);
            boolean matchMultiple = regexRecursion(src, pat, srcIdx + 1, patIdx);
            return matchEmpty || matchMultiple;
        }

        return false;
    }

    /**
     * Method 3: Top-down DP (memoization).
     *
     * {@code strg[srcIdx][patIdx]} encoding:
     * <ul>
     *   <li>0 – not computed</li>
     *   <li>1 – false</li>
     *   <li>2 – true</li>
     * </ul>
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
            // Source exhausted: remaining pattern must consist only of '*'
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
            // - empty sequence: move to next pattern character
            // - one or more characters: move to next source character
            boolean matchEmpty = regexRecursion(src, pat, srcIdx, patIdx + 1, strg);
            boolean matchMultiple = regexRecursion(src, pat, srcIdx + 1, patIdx, strg);
            ans = matchEmpty || matchMultiple;
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

        // Base case: empty source and empty pattern match
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
                        // - empty sequence: move to next pattern character
                        // - one or more characters: move to next source character
                        boolean matchEmpty = dp[row][col + 1];
                        boolean matchMultiple = dp[row + 1][col];
                        dp[row][col] = matchEmpty || matchMultiple;
                    } else {
                        dp[row][col] = false;
                    }
                }
            }
        }

        return dp[0][0];
    }
}