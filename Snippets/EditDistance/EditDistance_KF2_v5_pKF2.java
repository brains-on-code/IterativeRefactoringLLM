package com.thealgorithms.dynamicprogramming;

public final class EditDistance {

    private EditDistance() {
        // Utility class; prevent instantiation
    }

    /**
     * Computes the Levenshtein edit distance between two strings using
     * bottom-up dynamic programming.
     *
     * dp[i][j] is the edit distance between:
     * - the first i characters of word1
     * - the first j characters of word2
     *
     * @param word1 first string
     * @param word2 second string
     * @return minimum number of single-character edits (insertions, deletions,
     *         or substitutions) required to change word1 into word2
     */
    public static int minDistance(String word1, String word2) {
        int len1 = word1.length();
        int len2 = word2.length();

        int[][] dp = new int[len1 + 1][len2 + 1];

        // Base case: distance from a prefix to an empty string
        for (int i = 0; i <= len1; i++) {
            dp[i][0] = i; // delete i characters from word1
        }
        for (int j = 0; j <= len2; j++) {
            dp[0][j] = j; // insert j characters to match word2
        }

        // Fill DP table
        for (int i = 0; i < len1; i++) {
            char c1 = word1.charAt(i);
            for (int j = 0; j < len2; j++) {
                char c2 = word2.charAt(j);

                if (c1 == c2) {
                    // Characters match: carry over previous distance
                    dp[i + 1][j + 1] = dp[i][j];
                } else {
                    int replaceCost = dp[i][j] + 1;
                    int insertCost = dp[i][j + 1] + 1;
                    int deleteCost = dp[i + 1][j] + 1;

                    dp[i + 1][j + 1] = Math.min(replaceCost, Math.min(insertCost, deleteCost));
                }
            }
        }

        return dp[len1][len2];
    }

    /**
     * Computes the Levenshtein edit distance between two strings using
     * top-down recursion with memoization.
     *
     * @param s1 first string
     * @param s2 second string
     * @return minimum edit distance between s1 and s2
     */
    public static int editDistance(String s1, String s2) {
        int[][] memo = new int[s1.length() + 1][s2.length() + 1];
        return editDistance(s1, s2, memo);
    }

    /**
     * Recursive helper for edit distance with memoization.
     *
     * memo[m][n] stores the edit distance between:
     * - the last m characters of s1
     * - the last n characters of s2
     *
     * @param s1   first string
     * @param s2   second string
     * @param memo memoization table
     * @return minimum edit distance between s1 and s2
     */
    public static int editDistance(String s1, String s2, int[][] memo) {
        int m = s1.length();
        int n = s2.length();

        if (memo[m][n] > 0) {
            return memo[m][n];
        }

        if (m == 0) {
            // Need to insert all remaining characters of s2
            memo[m][n] = n;
            return n;
        }

        if (n == 0) {
            // Need to delete all remaining characters of s1
            memo[m][n] = m;
            return m;
        }

        if (s1.charAt(0) == s2.charAt(0)) {
            // First characters match: move to the next pair
            memo[m][n] = editDistance(s1.substring(1), s2.substring(1), memo);
        } else {
            int insertCost = editDistance(s1, s2.substring(1), memo);
            int deleteCost = editDistance(s1.substring(1), s2, memo);
            int replaceCost = editDistance(s1.substring(1), s2.substring(1), memo);

            memo[m][n] = 1 + Math.min(insertCost, Math.min(deleteCost, replaceCost));
        }

        return memo[m][n];
    }
}