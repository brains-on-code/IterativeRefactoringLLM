package com.thealgorithms.dynamicprogramming;

/**
 * Utility class for computing Levenshtein (edit) distance between two strings.
 *
 * <p>The Levenshtein distance is the minimum number of single-character edits
 * (insertions, deletions or substitutions) required to change one word into the other.
 */
public final class LevenshteinDistance {

    private LevenshteinDistance() {
        // Prevent instantiation
    }

    /**
     * Computes the Levenshtein distance between two strings using a bottom-up
     * dynamic programming approach.
     *
     * @param first  the first string
     * @param second the second string
     * @return the Levenshtein distance between {@code first} and {@code second}
     */
    public static int computeIterative(String first, String second) {
        int firstLength = first.length();
        int secondLength = second.length();

        // dp[i][j] = edit distance between first[0..i-1] and second[0..j-1]
        int[][] dp = new int[firstLength + 1][secondLength + 1];

        // Distance from prefixes of first to empty second (all deletions)
        for (int i = 0; i <= firstLength; i++) {
            dp[i][0] = i;
        }

        // Distance from empty first to prefixes of second (all insertions)
        for (int j = 0; j <= secondLength; j++) {
            dp[0][j] = j;
        }

        for (int i = 0; i < firstLength; i++) {
            char c1 = first.charAt(i);
            for (int j = 0; j < secondLength; j++) {
                char c2 = second.charAt(j);

                if (c1 == c2) {
                    dp[i + 1][j + 1] = dp[i][j];
                } else {
                    int substitution = dp[i][j] + 1;
                    int deletion = dp[i][j + 1] + 1;
                    int insertion = dp[i + 1][j] + 1;

                    dp[i + 1][j + 1] = Math.min(substitution, Math.min(deletion, insertion));
                }
            }
        }

        return dp[firstLength][secondLength];
    }

    /**
     * Computes the Levenshtein distance between two strings using a top-down
     * recursive approach with memoization.
     *
     * @param first  the first string
     * @param second the second string
     * @return the Levenshtein distance between {@code first} and {@code second}
     */
    public static int computeRecursive(String first, String second) {
        int[][] memo = new int[first.length() + 1][second.length() + 1];
        return computeRecursive(first, second, memo);
    }

    /**
     * Helper for the recursive Levenshtein distance computation with memoization.
     *
     * @param first  the first string
     * @param second the second string
     * @param memo   memoization table where memo[i][j] stores the distance between
     *               first[0..i-1] and second[0..j-1]
     * @return the Levenshtein distance between {@code first} and {@code second}
     */
    public static int computeRecursive(String first, String second, int[][] memo) {
        int firstLength = first.length();
        int secondLength = second.length();

        if (memo[firstLength][secondLength] > 0) {
            return memo[firstLength][secondLength];
        }

        if (firstLength == 0) {
            memo[firstLength][secondLength] = secondLength;
            return secondLength;
        }

        if (secondLength == 0) {
            memo[firstLength][secondLength] = firstLength;
            return firstLength;
        }

        if (first.charAt(0) == second.charAt(0)) {
            memo[firstLength][secondLength] =
                computeRecursive(first.substring(1), second.substring(1), memo);
        } else {
            int insertCost = computeRecursive(first, second.substring(1), memo);
            int deleteCost = computeRecursive(first.substring(1), second, memo);
            int replaceCost = computeRecursive(first.substring(1), second.substring(1), memo);

            memo[firstLength][secondLength] =
                1 + Math.min(insertCost, Math.min(deleteCost, replaceCost));
        }

        return memo[firstLength][secondLength];
    }
}