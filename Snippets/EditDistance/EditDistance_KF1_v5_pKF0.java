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

        initializeBaseCases(dp, sourceLength, targetLength);
        fillDynamicProgrammingTable(dp, source, target, sourceLength, targetLength);

        return dp[sourceLength][targetLength];
    }

    private static void initializeBaseCases(int[][] dp, int sourceLength, int targetLength) {
        for (int row = 0; row <= sourceLength; row++) {
            dp[row][0] = row;
        }
        for (int col = 0; col <= targetLength; col++) {
            dp[0][col] = col;
        }
    }

    private static void fillDynamicProgrammingTable(
        int[][] dp,
        String source,
        String target,
        int sourceLength,
        int targetLength
    ) {
        for (int row = 1; row <= sourceLength; row++) {
            char sourceChar = source.charAt(row - 1);
            for (int col = 1; col <= targetLength; col++) {
                char targetChar = target.charAt(col - 1);

                if (sourceChar == targetChar) {
                    dp[row][col] = dp[row - 1][col - 1];
                    continue;
                }

                int substitutionCost = dp[row - 1][col - 1] + 1;
                int insertionCost = dp[row][col - 1] + 1;
                int deletionCost = dp[row - 1][col] + 1;

                dp[row][col] = Math.min(substitutionCost, Math.min(insertionCost, deletionCost));
            }
        }
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
            int insertionCost =
                computeRecursive(source, target, sourceLength, targetLength - 1, memo);
            int deletionCost =
                computeRecursive(source, target, sourceLength - 1, targetLength, memo);
            int substitutionCost =
                computeRecursive(source, target, sourceLength - 1, targetLength - 1, memo);

            memo[sourceLength][targetLength] =
                1 + Math.min(insertionCost, Math.min(deletionCost, substitutionCost));
        }

        return memo[sourceLength][targetLength];
    }
}