package com.thealgorithms.dynamicprogramming;

/**
 * Given a text and wildcard pattern implement a wildcard pattern matching
 * algorithm that finds if wildcard is matched with text. The matching should
 * cover the entire text ?-> matches single characters *-> match the sequence of
 * characters
 *
 * For calculation of Time and Space Complexity. Let N be length of src and M be length of pat
 *
 * Memoization vs Tabulation : https://www.geeksforgeeks.org/tabulation-vs-memoization/
 * Question Link : https://practice.geeksforgeeks.org/problems/wildcard-pattern-matching/1
 */
public final class RegexMatching {

    private RegexMatching() {
        // Utility class
    }

    /**
     * Method 1: Determines if the given source string matches the given pattern using a recursive approach.
     * This method directly applies recursion to check if the source string matches the pattern, considering
     * the wildcards '?' and '*'.
     *
     * Time Complexity: O(2^(N+M)), where N is the length of the source string and M is the length of the pattern.
     * Space Complexity: O(N + M) due to the recursion stack.
     *
     * @param src The source string to be matched against the pattern.
     * @param pat The pattern containing wildcards ('*' matches a sequence of characters, '?' matches a single character).
     * @return {@code true} if the source string matches the pattern, {@code false} otherwise.
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
            return regexRecursion(src, remainingPat) || regexRecursion(remainingSrc, pat);
        }
        return false;
    }

    /**
     * Method 2: Determines if the given source string matches the given pattern using recursion.
     * This method utilizes a virtual index for both the source string and the pattern to manage the recursion.
     *
     * Time Complexity: O(2^(N+M)) where N is the length of the source string and M is the length of the pattern.
     * Space Complexity: O(N + M) due to the recursion stack.
     *
     * @param src The source string to be matched against the pattern.
     * @param pat The pattern containing wildcards ('*' matches a sequence of characters, '?' matches a single character).
     * @param srcIndex The current index in the source string.
     * @param patIndex The current index in the pattern.
     * @return {@code true} if the source string matches the pattern, {@code false} otherwise.
     */
    static boolean regexRecursion(String src, String pat, int srcIndex, int patIndex) {
        int srcLength = src.length();
        int patLength = pat.length();

        if (srcIndex == srcLength && patIndex == patLength) {
            return true;
        }
        if (patIndex == patLength) {
            return false;
        }
        if (srcIndex == srcLength) {
            return isAllStars(pat, patIndex);
        }

        char srcChar = src.charAt(srcIndex);
        char patChar = pat.charAt(patIndex);

        if (srcChar == patChar || patChar == '?') {
            return regexRecursion(src, pat, srcIndex + 1, patIndex + 1);
        }
        if (patChar == '*') {
            return regexRecursion(src, pat, srcIndex, patIndex + 1)
                || regexRecursion(src, pat, srcIndex + 1, patIndex);
        }
        return false;
    }

    /**
     * Method 3: Determines if the given source string matches the given pattern using top-down dynamic programming (memoization).
     * This method utilizes memoization to store intermediate results, reducing redundant computations and improving efficiency.
     *
     * Time Complexity: O(N * M), where N is the length of the source string and M is the length of the pattern.
     * Space Complexity: O(N * M) for the memoization table, plus additional space for the recursion stack.
     *
     * @param src The source string to be matched against the pattern.
     * @param pat The pattern containing wildcards ('*' matches a sequence of characters, '?' matches a single character).
     * @param srcIndex The current index in the source string.
     * @param patIndex The current index in the pattern.
     * @param memo A 2D array used for memoization to store the results of subproblems.
     *             0 = uncomputed, 1 = false, 2 = true
     * @return {@code true} if the source string matches the pattern, {@code false} otherwise.
     */
    public static boolean regexRecursion(String src, String pat, int srcIndex, int patIndex, int[][] memo) {
        int srcLength = src.length();
        int patLength = pat.length();

        if (srcIndex == srcLength && patIndex == patLength) {
            return true;
        }
        if (patIndex == patLength) {
            return false;
        }
        if (srcIndex == srcLength) {
            return isAllStars(pat, patIndex);
        }

        if (memo[srcIndex][patIndex] != 0) {
            return memo[srcIndex][patIndex] == 2;
        }

        char srcChar = src.charAt(srcIndex);
        char patChar = pat.charAt(patIndex);

        boolean result;
        if (srcChar == patChar || patChar == '?') {
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

    /**
     * Method 4: Determines if the given source string matches the given pattern using bottom-up dynamic programming (tabulation).
     * This method builds a solution iteratively by filling out a table, where each cell represents whether a substring
     * of the source string matches a substring of the pattern.
     *
     * Time Complexity: O(N * M), where N is the length of the source string and M is the length of the pattern.
     * Space Complexity: O(N * M) for the table used in the tabulation process.
     *
     * @param src The source string to be matched against the pattern.
     * @param pat The pattern containing wildcards ('*' matches a sequence of characters, '?' matches a single character).
     * @return {@code true} if the source string matches the pattern, {@code false} otherwise.
     */
    static boolean regexBU(String src, String pat) {
        int srcLength = src.length();
        int patLength = pat.length();

        boolean[][] dp = new boolean[srcLength + 1][patLength + 1];
        dp[srcLength][patLength] = true;

        for (int row = srcLength; row >= 0; row--) {
            for (int col = patLength - 1; col >= 0; col--) {
                char patChar = pat.charAt(col);

                if (row == srcLength) {
                    dp[row][col] = patChar == '*' && dp[row][col + 1];
                    continue;
                }

                char srcChar = src.charAt(row);

                if (srcChar == patChar || patChar == '?') {
                    dp[row][col] = dp[row + 1][col + 1];
                } else if (patChar == '*') {
                    dp[row][col] = dp[row][col + 1] || dp[row + 1][col];
                } else {
                    dp[row][col] = false;
                }
            }
        }
        return dp[0][0];
    }

    /**
     * Utility to check if the remaining pattern from a given index consists only of '*'.
     */
    private static boolean isAllStars(String pattern, int startIndex) {
        for (int i = startIndex; i < pattern.length(); i++) {
            if (pattern.charAt(i) != '*') {
                return false;
            }
        }
        return true;
    }
}