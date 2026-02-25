package com.thealgorithms.dynamicprogramming;

/**
 * Utility class for computing Levenshtein (edit) distance between two strings.
 */
public final class LevenshteinDistance {

    private LevenshteinDistance() {
        // Utility class; prevent instantiation
    }

    /**
     * Computes the Levenshtein distance between two strings using
     * bottom-up dynamic programming.
     *
     * @param source the first string
     * @param target the second string
     * @return the edit distance between {@code source} and {@code target}
     */
    public static int computeIterative(String source, String target) {
        int sourceLength = source.length();
        int targetLength = target.length();

        int[][] dp = new int[sourceLength + 1][targetLength + 1];

        // Initialize base cases: transforming from/to empty string
        for (int i = 0; i <= sourceLength; i++) {
            dp[i][0] = i;
        }
        for (int j = 0; j <= targetLength; j++) {
            dp[0][j] = j;
        }

        for (int i = 1; i <= sourceLength; i++) {
            char sourceChar = source.charAt(i - 1);
            for (int j = 1; j <= targetLength; j++) {
                char targetChar = target.charAt(j - 1);

                if (sourceChar == targetChar) {
                    dp[i][j] = dp[i - 1][j - 1];
                    continue;
                }

                int substitutionCost = dp[i - 1][j - 1] + 1;
                int insertionCost = dp[i][j - 1] + 1;
                int deletionCost = dp[i - 1][j] + 1;

                dp[i][j] = Math.min(substitutionCost, Math.min(insertionCost, deletionCost));
            }
        }

        return dp[sourceLength][targetLength];
    }

    /**
     * Computes the Levenshtein distance between two strings using
     * top-down recursion with memoization.
     *
     * @param source the first string
     * @param target the second string
     * @return the edit distance between {@code source} and {@code target}
     */
    public static int computeRecursive(String source, String target) {
        int sourceLength = source.length();
        int targetLength = target.length();
        int[][] memo = new int[sourceLength + 1][targetLength + 1];

        return computeRecursive(source, target, sourceLength, targetLength, memo);
    }

    /**
     * Helper for the recursive Levenshtein distance computation.
     *
     * @param source       the first string
     * @param target       the second string
     * @param sourceLength current length of the source prefix
     * @param targetLength current length of the target prefix
     * @param memo         memoization table
     * @return the edit distance between {@code source} and {@code target}
     */
    private static int computeRecursive(
        String source,
        String target,
        int sourceLength,
        int targetLength,
        int[][] memo
    ) {
        if (memo[sourceLength][targetLength] != 0) {
            return memo[sourceLength][targetLength];
        }

        if (sourceLength == 0) {
            memo[sourceLength][targetLength] = targetLength;
            return targetLength;
        }

        if (targetLength == 0) {
            memo[sourceLength][targetLength] = sourceLength;
            return sourceLength;
        }

        if (source.charAt(sourceLength - 1) == target.charAt(targetLength - 1)) {
            memo[sourceLength][targetLength] =
                computeRecursive(source, target, sourceLength - 1, targetLength - 1, memo);
        } else {
            int insertCost =
                computeRecursive(source, target, sourceLength, targetLength - 1, memo);
            int deleteCost =
                computeRecursive(source, target, sourceLength - 1, targetLength, memo);
            int replaceCost =
                computeRecursive(source, target, sourceLength - 1, targetLength - 1, memo);

            memo[sourceLength][targetLength] =
                1 + Math.min(insertCost, Math.min(deleteCost, replaceCost));
        }

        return memo[sourceLength][targetLength];
    }
}