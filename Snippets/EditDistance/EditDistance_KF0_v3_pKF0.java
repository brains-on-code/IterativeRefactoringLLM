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
        validateNotNull(word1, word2);

        int len1 = word1.length();
        int len2 = word2.length();

        int[][] dp = new int[len1 + 1][len2 + 1];

        initializeBaseCases(dp, len1, len2);

        for (int i = 0; i < len1; i++) {
            char c1 = word1.charAt(i);
            for (int j = 0; j < len2; j++) {
                char c2 = word2.charAt(j);

                if (c1 == c2) {
                    dp[i + 1][j + 1] = dp[i][j];
                } else {
                    dp[i + 1][j + 1] = 1 + min(
                        dp[i][j],     // replace
                        dp[i][j + 1], // insert
                        dp[i + 1][j]  // delete
                    );
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
        validateNotNull(s1, s2);

        int m = s1.length();
        int n = s2.length();
        int[][] memo = new int[m + 1][n + 1];

        return editDistanceRecursive(s1, s2, m, n, memo);
    }

    private static void validateNotNull(String first, String second) {
        if (first == null || second == null) {
            throw new IllegalArgumentException("Input strings must not be null");
        }
    }

    private static void initializeBaseCases(int[][] dp, int len1, int len2) {
        for (int i = 0; i <= len1; i++) {
            dp[i][0] = i; // delete all characters from word1
        }
        for (int j = 0; j <= len2; j++) {
            dp[0][j] = j; // insert all characters of word2
        }
    }

    private static int min(int a, int b, int c) {
        return Math.min(a, Math.min(b, c));
    }

    private static int editDistanceRecursive(String s1, String s2, int i, int j, int[][] memo) {
        if (memo[i][j] != 0) {
            return memo[i][j];
        }

        if (i == 0) {
            memo[i][j] = j;
            return j;
        }

        if (j == 0) {
            memo[i][j] = i;
            return i;
        }

        if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
            memo[i][j] = editDistanceRecursive(s1, s2, i - 1, j - 1, memo);
        } else {
            int insertCost = editDistanceRecursive(s1, s2, i, j - 1, memo);
            int deleteCost = editDistanceRecursive(s1, s2, i - 1, j, memo);
            int replaceCost = editDistanceRecursive(s1, s2, i - 1, j - 1, memo);

            memo[i][j] = 1 + min(insertCost, deleteCost, replaceCost);
        }

        return memo[i][j];
    }
}