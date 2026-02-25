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
     * @param source first string
     * @param target second string
     * @return minimum number of operations to convert source to target
     */
    public static int minDistance(String source, String target) {
        validateNotNull(source, target);

        int sourceLength = source.length();
        int targetLength = target.length();

        int[][] dp = new int[sourceLength + 1][targetLength + 1];
        initializeBaseCases(dp, sourceLength, targetLength);

        for (int i = 0; i < sourceLength; i++) {
            char sourceChar = source.charAt(i);

            for (int j = 0; j < targetLength; j++) {
                char targetChar = target.charAt(j);

                if (sourceChar == targetChar) {
                    dp[i + 1][j + 1] = dp[i][j];
                } else {
                    int replaceCost = dp[i][j];
                    int insertCost = dp[i][j + 1];
                    int deleteCost = dp[i + 1][j];

                    dp[i + 1][j + 1] = 1 + min(replaceCost, insertCost, deleteCost);
                }
            }
        }

        return dp[sourceLength][targetLength];
    }

    /**
     * Computes the edit distance between two strings using top-down DP
     * (memoized recursion).
     *
     * @param source first string
     * @param target second string
     * @return minimum number of operations to convert source to target
     */
    public static int editDistance(String source, String target) {
        validateNotNull(source, target);

        int sourceLength = source.length();
        int targetLength = target.length();
        int[][] memo = new int[sourceLength + 1][targetLength + 1];

        return editDistanceRecursive(source, target, sourceLength, targetLength, memo);
    }

    private static void validateNotNull(String first, String second) {
        if (first == null || second == null) {
            throw new IllegalArgumentException("Input strings must not be null");
        }
    }

    private static void initializeBaseCases(int[][] dp, int sourceLength, int targetLength) {
        for (int i = 0; i <= sourceLength; i++) {
            dp[i][0] = i; // delete all characters from source
        }
        for (int j = 0; j <= targetLength; j++) {
            dp[0][j] = j; // insert all characters of target
        }
    }

    private static int min(int first, int second, int third) {
        return Math.min(first, Math.min(second, third));
    }

    private static int editDistanceRecursive(
        String source,
        String target,
        int sourceIndex,
        int targetIndex,
        int[][] memo
    ) {
        if (memo[sourceIndex][targetIndex] != 0) {
            return memo[sourceIndex][targetIndex];
        }

        if (sourceIndex == 0) {
            memo[sourceIndex][targetIndex] = targetIndex;
            return targetIndex;
        }

        if (targetIndex == 0) {
            memo[sourceIndex][targetIndex] = sourceIndex;
            return sourceIndex;
        }

        if (source.charAt(sourceIndex - 1) == target.charAt(targetIndex - 1)) {
            memo[sourceIndex][targetIndex] =
                editDistanceRecursive(source, target, sourceIndex - 1, targetIndex - 1, memo);
        } else {
            int insertCost =
                editDistanceRecursive(source, target, sourceIndex, targetIndex - 1, memo);
            int deleteCost =
                editDistanceRecursive(source, target, sourceIndex - 1, targetIndex, memo);
            int replaceCost =
                editDistanceRecursive(source, target, sourceIndex - 1, targetIndex - 1, memo);

            memo[sourceIndex][targetIndex] = 1 + min(insertCost, deleteCost, replaceCost);
        }

        return memo[sourceIndex][targetIndex];
    }
}