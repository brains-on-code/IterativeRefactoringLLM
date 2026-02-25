package com.thealgorithms.dynamicprogramming;

/**
 * Dynamic programming solutions for the Edit Distance (Levenshtein distance) problem.
 *
 * <p>Edit distance quantifies how dissimilar two strings are by counting the
 * minimum number of operations required to transform one string into the other.
 * Allowed operations:
 * <ul>
 *     <li>Insertion of a character</li>
 *     <li>Deletion of a character</li>
 *     <li>Substitution of one character for another</li>
 * </ul>
 *
 * <p>Example:
 * <pre>
 * kitten → sitten  (substitute 's' for 'k')
 * sitten → sittin  (substitute 'i' for 'e')
 * sittin → sitting (insert 'g')
 * </pre>
 * The edit distance between "kitten" and "sitting" is 3.
 */
public final class EditDistance {

    private EditDistance() {
        // Utility class; prevent instantiation.
    }

    /**
     * Computes the edit distance between two strings using bottom-up dynamic programming.
     *
     * <p>dp[i][j] represents the edit distance between:
     * <ul>
     *     <li>prefix word1[0..i-1], and</li>
     *     <li>prefix word2[0..j-1]</li>
     * </ul>
     *
     * @param word1 first string
     * @param word2 second string
     * @return minimum number of edit operations required to transform word1 into word2
     */
    public static int minDistance(String word1, String word2) {
        int len1 = word1.length();
        int len2 = word2.length();

        int[][] dp = new int[len1 + 1][len2 + 1];

        // Base cases: transform prefix of length i into empty string (j = 0): i deletions.
        for (int i = 0; i <= len1; i++) {
            dp[i][0] = i;
        }

        // Base cases: transform empty string (i = 0) into prefix of length j: j insertions.
        for (int j = 0; j <= len2; j++) {
            dp[0][j] = j;
        }

        // Fill the DP table.
        for (int i = 0; i < len1; i++) {
            char c1 = word1.charAt(i);
            for (int j = 0; j < len2; j++) {
                char c2 = word2.charAt(j);

                if (c1 == c2) {
                    // Characters match: no additional cost.
                    dp[i + 1][j + 1] = dp[i][j];
                } else {
                    int replace = dp[i][j] + 1;     // substitution
                    int insert = dp[i][j + 1] + 1;  // insertion
                    int delete = dp[i + 1][j] + 1;  // deletion

                    dp[i + 1][j + 1] = Math.min(replace, Math.min(insert, delete));
                }
            }
        }

        return dp[len1][len2];
    }

    /**
     * Computes the edit distance between two strings using top-down recursion
     * with memoization.
     *
     * @param s1 first string
     * @param s2 second string
     * @return minimum number of edit operations required to transform s1 into s2
     */
    public static int editDistance(String s1, String s2) {
        int[][] storage = new int[s1.length() + 1][s2.length() + 1];
        return editDistance(s1, s2, storage);
    }

    /**
     * Helper for the recursive edit distance computation with memoization.
     *
     * <p>storage[m][n] stores the edit distance between:
     * <ul>
     *     <li>suffix of s1 with length m, and</li>
     *     <li>suffix of s2 with length n</li>
     * </ul>
     *
     * @param s1      first string
     * @param s2      second string
     * @param storage memoization table
     * @return minimum number of edit operations required to transform s1 into s2
     */
    public static int editDistance(String s1, String s2, int[][] storage) {
        int m = s1.length();
        int n = s2.length();

        if (storage[m][n] > 0) {
            return storage[m][n];
        }

        // If first string is empty, we need n insertions.
        if (m == 0) {
            storage[m][n] = n;
            return n;
        }

        // If second string is empty, we need m deletions.
        if (n == 0) {
            storage[m][n] = m;
            return m;
        }

        if (s1.charAt(0) == s2.charAt(0)) {
            // Characters match: move to the next pair without additional cost.
            storage[m][n] = editDistance(s1.substring(1), s2.substring(1), storage);
        } else {
            // Consider all three operations.
            int insertCost = editDistance(s1, s2.substring(1), storage);               // insert into s1
            int deleteCost = editDistance(s1.substring(1), s2, storage);               // delete from s1
            int replaceCost = editDistance(s1.substring(1), s2.substring(1), storage); // substitute

            storage[m][n] = 1 + Math.min(insertCost, Math.min(deleteCost, replaceCost));
        }

        return storage[m][n];
    }
}