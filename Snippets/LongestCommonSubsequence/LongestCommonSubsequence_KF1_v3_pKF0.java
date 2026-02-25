package com.thealgorithms.dynamicprogramming;

/**
 * Utility class for computing the Longest Common Subsequence (LCS) between two strings.
 */
final class LongestCommonSubsequence {

    private LongestCommonSubsequence() {
        // Prevent instantiation
    }

    /**
     * Computes the Longest Common Subsequence (LCS) of two strings.
     *
     * @param first  the first input string
     * @param second the second input string
     * @return the LCS of {@code first} and {@code second}, or {@code null} if either is {@code null}
     */
    public static String computeLCS(String first, String second) {
        if (first == null || second == null) {
            return null;
        }
        if (first.isEmpty() || second.isEmpty()) {
            return "";
        }

        final char[] firstChars = first.toCharArray();
        final char[] secondChars = second.toCharArray();

        final int firstLength = firstChars.length;
        final int secondLength = secondChars.length;

        final int[][] lcsLengths = buildLcsLengthTable(firstChars, secondChars, firstLength, secondLength);

        return reconstructLCS(first, second, lcsLengths);
    }

    private static int[][] buildLcsLengthTable(char[] firstChars, char[] secondChars, int firstLength, int secondLength) {
        int[][] lcsLengths = new int[firstLength + 1][secondLength + 1];

        for (int i = 1; i <= firstLength; i++) {
            for (int j = 1; j <= secondLength; j++) {
                if (firstChars[i - 1] == secondChars[j - 1]) {
                    lcsLengths[i][j] = lcsLengths[i - 1][j - 1] + 1;
                } else {
                    lcsLengths[i][j] = Math.max(lcsLengths[i - 1][j], lcsLengths[i][j - 1]);
                }
            }
        }

        return lcsLengths;
    }

    /**
     * Reconstructs the Longest Common Subsequence (LCS) from the DP table.
     *
     * @param first      the first input string
     * @param second     the second input string
     * @param lcsLengths the DP table containing LCS lengths
     * @return the reconstructed LCS
     */
    private static String reconstructLCS(String first, String second, int[][] lcsLengths) {
        StringBuilder lcsBuilder = new StringBuilder();
        int i = first.length();
        int j = second.length();

        while (i > 0 && j > 0) {
            char firstChar = first.charAt(i - 1);
            char secondChar = second.charAt(j - 1);

            if (firstChar == secondChar) {
                lcsBuilder.append(firstChar);
                i--;
                j--;
            } else if (lcsLengths[i - 1][j] >= lcsLengths[i][j - 1]) {
                i--;
            } else {
                j--;
            }
        }

        return lcsBuilder.reverse().toString();
    }
}