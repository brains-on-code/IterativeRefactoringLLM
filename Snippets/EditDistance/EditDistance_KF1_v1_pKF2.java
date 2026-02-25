package com.thealgorithms.dynamicprogramming;

/**
 * Utility class for computing Levenshtein (edit) distance between two strings.
 *
 * <p>The Levenshtein distance is the minimum number of single-character edits
 * (insertions, deletions or substitutions) required to change one word into the other.
 */
public final class Class1 {

    private Class1() {
        // Utility class; prevent instantiation
    }

    /**
     * Computes the Levenshtein distance between two strings using a bottom-up
     * dynamic programming approach.
     *
     * @param first  the first string
     * @param second the second string
     * @return the Levenshtein distance between {@code first} and {@code second}
     */
    public static int method1(String first, String second) {
        int firstLength = first.length();
        int secondLength = second.length();

        // dp[i][j] = edit distance between first[0..i-1] and second[0..j-1]
        int[][] dp = new int[firstLength + 1][secondLength + 1];

        // Base case: distance from empty string to prefixes of second (all insertions)
        for (int i = 0; i <= firstLength; i++) {
            dp[i][0] = i;
        }

        // Base case: distance from empty string to prefixes of first (all deletions)
        for (int j = 0; j <= secondLength; j++) {
            dp[0][j] = j;
        }

        // Fill DP table
        for (int i = 0; i < firstLength; i++) {
            char c1 = first.charAt(i);
            for (int j = 0; j < secondLength; j++) {
                char c2 = second.charAt(j);

                if (c1 == c2) {
                    // No operation needed
                    dp[i + 1][j + 1] = dp[i][j];
                } else {
                    int substitution = dp[i][j] + 1;
                    int deletion = dp[i][j + 1] + 1;
                    int insertion = dp[i + 1][j] + 1;

                    int min = Math.min(substitution, deletion);
                    min = Math.min(min, insertion);
                    dp[i + 1][j + 1] = min;
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
    public static int method3(String first, String second) {
        int[][] memo = new int[first.length() + 1][second.length() + 1];
        return method3(first, second, memo);
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
    public static int method3(String first, String second, int[][] memo) {
        int firstLength = first.length();
        int secondLength = second.length();

        if (memo[firstLength][secondLength] > 0) {
            return memo[firstLength][secondLength];
        }

        // If first string is empty, distance is length of second (all insertions)
        if (firstLength == 0) {
            memo[firstLength][secondLength] = secondLength;
            return memo[firstLength][secondLength];
        }

        // If second string is empty, distance is length of first (all deletions)
        if (secondLength == 0) {
            memo[firstLength][secondLength] = firstLength;
            return memo[firstLength][secondLength];
        }

        if (first.charAt(0) == second.charAt(0)) {
            // Characters match; move to the next pair
            memo[firstLength][secondLength] =
                method3(first.substring(1), second.substring(1), memo);
        } else {
            int insertCost = method3(first, second.substring(1), memo);
            int deleteCost = method3(first.substring(1), second, memo);
            int replaceCost = method3(first.substring(1), second.substring(1), memo);

            memo[firstLength][secondLength] =
                1 + Math.min(insertCost, Math.min(deleteCost, replaceCost));
        }

        return memo[firstLength][secondLength];
    }
}