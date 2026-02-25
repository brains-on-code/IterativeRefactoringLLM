package com.thealgorithms.dynamicprogramming;

/**
 * Dynamic Programming solutions for the Edit Distance problem.
 *
 * <p>Edit distance quantifies how dissimilar two strings are by counting the
 * minimum number of operations (insertion, deletion, substitution) required to
 * transform one string into the other.
 *
 * <p>Example: The distance between "kitten" and "sitting" is 3:
 * <ul>
 *   <li>kitten → sitten (substitute 's' for 'k')</li>
 *   <li>sitten → sittin (substitute 'i' for 'e')</li>
 *   <li>sittin → sitting (insert 'g')</li>
 * </ul>
 */
public final class EditDistance {

    private EditDistance() {
        // Utility class; prevent instantiation
    }

    /**
     * Computes the edit distance between two strings using bottom-up DP.
     *
     * @param word1 first string
     * @param word2 second string
     * @return minimum number of operations to convert word1 to word2
     */
    public static int minDistance(String word1, String word2) {
        int len1 = word1.length();
        int len2 = word2.length();

        int[][] dp = new int[len1 + 1][len2 + 1];

        // If second string is empty, remove all characters of first string
        for (int i = 0; i <= len1; i++) {
            dp[i][0] = i;
        }

        // If first string is empty, insert all characters of second string
        for (int j = 0; j <= len2; j++) {
            dp[0][j] = j;
        }

        for (int i = 0; i < len1; i++) {
            char c1 = word1.charAt(i);
            for (int j = 0; j < len2; j++) {
                char c2 = word2.charAt(j);

                if (c1 == c2) {
                    dp[i + 1][j + 1] = dp[i][j];
                } else {
                    int replace = dp[i][j] + 1;
                    int insert = dp[i][j + 1] + 1;
                    int delete = dp[i + 1][j] + 1;

                    dp[i + 1][j + 1] = Math.min(replace, Math.min(insert, delete));
                }
            }
        }

        return dp[len1][len2];
    }

    /**
     * Computes the edit distance between two strings using top-down DP
     * (memoized recursion).
     *
     * @param s1 first string
     * @param s2 second string
     * @return minimum number of operations to convert s1 to s2
     */
    public static int editDistance(String s1, String s2) {
        int[][] memo = new int[s1.length() + 1][s2.length() + 1];
        return editDistance(s1, s2, memo);
    }

    private static int editDistance(String s1, String s2, int[][] memo) {
        int m = s1.length();
        int n = s2.length();

        if (memo[m][n] > 0) {
            return memo[m][n];
        }

        if (m == 0) {
            memo[m][n] = n;
            return n;
        }

        if (n == 0) {
            memo[m][n] = m;
            return m;
        }

        if (s1.charAt(0) == s2.charAt(0)) {
            memo[m][n] = editDistance(s1.substring(1), s2.substring(1), memo);
        } else {
            int insert = editDistance(s1, s2.substring(1), memo);
            int delete = editDistance(s1.substring(1), s2, memo);
            int replace = editDistance(s1.substring(1), s2.substring(1), memo);

            memo[m][n] = 1 + Math.min(insert, Math.min(delete, replace));
        }

        return memo[m][n];
    }
}