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
     * @param word1 first string
     * @param word2 second string
     * @return minimum number of edit operations required to transform word1 into word2
     */
    public static int minDistance(String word1, String word2) {
        int len1 = word1.length();
        int len2 = word2.length();

        // dp[i][j] = edit distance between prefixes
        // word1[0..i-1] and word2[0..j-1]
        int[][] dp = new int[len1 + 1][len2 + 1];

        // Base case: transform prefix of length i into empty string (j = 0)
        // requires i deletions.
        for (int i = 0; i <= len1; i++) {
            dp[i][0] = i;
        }

        // Base case: transform empty string (i = 0) into prefix of length j
        // requires j insertions.
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
                    // Characters differ: consider all three operations.
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
     * @param s1      first string
     * @param s2      second string
     * @param storage memoization table where storage[m][n] stores the edit
     *                distance between s1 suffix of length m and s2 suffix of length n
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
            return storage[m][n];
        }

        // If second string is empty, we need m deletions.
        if (n == 0) {
            storage[m][n] = m;
            return storage[m][n];
        }

        if (s1.charAt(0) == s2.charAt(0)) {
            // Characters match: move to the next pair without additional cost.
            storage[m][n] = editDistance(s1.substring(1), s2.substring(1), storage);
        } else {
            // Consider all three operations:
            // op1: insert into s1 (advance in s2)
            int op1 = editDistance(s1, s2.substring(1), storage);
            // op2: delete from s1 (advance in s1)
            int op2 = editDistance(s1.substring(1), s2, storage);
            // op3: substitute (advance in both)
            int op3 = editDistance(s1.substring(1), s2.substring(1), storage);

            storage[m][n] = 1 + Math.min(op1, Math.min(op2, op3));
        }

        return storage[m][n];
    }
}