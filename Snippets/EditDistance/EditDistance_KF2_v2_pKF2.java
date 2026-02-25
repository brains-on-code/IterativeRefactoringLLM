package com.thealgorithms.dynamicprogramming;

public final class EditDistance {

    private EditDistance() {
        // Prevent instantiation
    }

    /**
     * Computes the Levenshtein edit distance between two strings using
     * bottom-up dynamic programming.
     *
     * @param word1 first string
     * @param word2 second string
     * @return minimum number of single-character edits (insertions, deletions,
     *         or substitutions) required to change word1 into word2
     */
    public static int minDistance(String word1, String word2) {
        int len1 = word1.length();
        int len2 = word2.length();

        // dp[i][j] = edit distance between
        // word1 prefix of length i and word2 prefix of length j
        int[][] dp = new int[len1 + 1][len2 + 1];

        // Transform word1 prefix of length i into empty string (all deletions)
        for (int i = 0; i <= len1; i++) {
            dp[i][0] = i;
        }

        // Transform empty string into word2 prefix of length j (all insertions)
        for (int j = 0; j <= len2; j++) {
            dp[0][j] = j;
        }

        // Fill DP table
        for (int i = 0; i < len1; i++) {
            char c1 = word1.charAt(i);
            for (int j = 0; j < len2; j++) {
                char c2 = word2.charAt(j);

                if (c1 == c2) {
                    // Characters match: carry over previous cost
                    dp[i + 1][j + 1] = dp[i][j];
                } else {
                    int replaceCost = dp[i][j] + 1;       // replace c1 with c2
                    int insertCost = dp[i][j + 1] + 1;    // insert c2
                    int deleteCost = dp[i + 1][j] + 1;    // delete c1

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
     * @param s1   first string
     * @param s2   second string
     * @param memo memo[m][n] stores edit distance between
     *             suffixes of s1 and s2 of lengths m and n respectively
     * @return minimum edit distance between s1 and s2
     */
    public static int editDistance(String s1, String s2, int[][] memo) {
        int m = s1.length();
        int n = s2.length();

        // Use cached result if available
        if (memo[m][n] > 0) {
            return memo[m][n];
        }

        if (m == 0) {
            // s1 is empty: need n insertions
            memo[m][n] = n;
            return memo[m][n];
        }

        if (n == 0) {
            // s2 is empty: need m deletions
            memo[m][n] = m;
            return memo[m][n];
        }

        if (s1.charAt(0) == s2.charAt(0)) {
            // Characters match: move to next characters without extra cost
            memo[m][n] = editDistance(s1.substring(1), s2.substring(1), memo);
        } else {
            int insertCost = editDistance(s1, s2.substring(1), memo);          // insert first char of s2
            int deleteCost = editDistance(s1.substring(1), s2, memo);          // delete first char of s1
            int replaceCost = editDistance(s1.substring(1), s2.substring(1), memo); // replace first char of s1

            memo[m][n] = 1 + Math.min(insertCost, Math.min(deleteCost, replaceCost));
        }

        return memo[m][n];
    }
}