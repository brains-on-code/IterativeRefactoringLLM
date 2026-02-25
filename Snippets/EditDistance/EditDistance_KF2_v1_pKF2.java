package com.thealgorithms.dynamicprogramming;

public final class EditDistance {

    private EditDistance() {
        // Utility class; prevent instantiation
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
        // prefix word1[0..i-1] and prefix word2[0..j-1]
        int[][] dp = new int[len1 + 1][len2 + 1];

        // Base case: transform prefix of length i into empty string (all deletions)
        for (int i = 0; i <= len1; i++) {
            dp[i][0] = i;
        }

        // Base case: transform empty string into prefix of length j (all insertions)
        for (int j = 0; j <= len2; j++) {
            dp[0][j] = j;
        }

        // Fill DP table
        for (int i = 0; i < len1; i++) {
            char c1 = word1.charAt(i);
            for (int j = 0; j < len2; j++) {
                char c2 = word2.charAt(j);

                if (c1 == c2) {
                    // Characters match: no additional cost
                    dp[i + 1][j + 1] = dp[i][j];
                } else {
                    // Consider all three operations:
                    // 1) replace c1 with c2
                    int replaceCost = dp[i][j] + 1;
                    // 2) insert c2
                    int insertCost = dp[i][j + 1] + 1;
                    // 3) delete c1
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
     * @param s1     first string
     * @param s2     second string
     * @param memo   memo[m][n] stores edit distance between
     *               s1 suffix of length m and s2 suffix of length n
     * @return minimum edit distance between s1 and s2
     */
    public static int editDistance(String s1, String s2, int[][] memo) {
        int m = s1.length();
        int n = s2.length();

        // Return cached result if available
        if (memo[m][n] > 0) {
            return memo[m][n];
        }

        // If first string is empty, we need n insertions
        if (m == 0) {
            memo[m][n] = n;
            return memo[m][n];
        }

        // If second string is empty, we need m deletions
        if (n == 0) {
            memo[m][n] = m;
            return memo[m][n];
        }

        if (s1.charAt(0) == s2.charAt(0)) {
            // Characters match: move to next characters without extra cost
            memo[m][n] = editDistance(s1.substring(1), s2.substring(1), memo);
        } else {
            // Consider:
            // op1: insert first char of s2 into s1
            int insertCost = editDistance(s1, s2.substring(1), memo);
            // op2: delete first char of s1
            int deleteCost = editDistance(s1.substring(1), s2, memo);
            // op3: replace first char of s1 with first char of s2
            int replaceCost = editDistance(s1.substring(1), s2.substring(1), memo);

            memo[m][n] = 1 + Math.min(insertCost, Math.min(deleteCost, replaceCost));
        }

        return memo[m][n];
    }
}