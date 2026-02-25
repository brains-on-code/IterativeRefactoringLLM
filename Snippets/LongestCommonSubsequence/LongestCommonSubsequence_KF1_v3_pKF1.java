package com.thealgorithms.dynamicprogramming;

/**
 * Utility class for computing the Longest Common Subsequence (LCS) between two strings.
 */
final class LongestCommonSubsequence {

    private LongestCommonSubsequence() {
        // Utility class; prevent instantiation.
    }

    /**
     * Finds the Longest Common Subsequence (LCS) between two strings.
     *
     * @param first  the first input string
     * @param second the second input string
     * @return the LCS as a string, or {@code null} if either input is {@code null}
     */
    public static String findLCS(String first, String second) {
        if (first == null || second == null) {
            return null;
        }
        if (first.isEmpty() || second.isEmpty()) {
            return "";
        }

        String[] firstChars = first.split("");
        String[] secondChars = second.split("");

        int firstLength = firstChars.length;
        int secondLength = secondChars.length;

        int[][] lcsLengths = new int[firstLength + 1][secondLength + 1];

        for (int row = 0; row <= firstLength; row++) {
            lcsLengths[row][0] = 0;
        }
        for (int col = 0; col <= secondLength; col++) {
            lcsLengths[0][col] = 0;
        }

        for (int row = 1; row <= firstLength; row++) {
            for (int col = 1; col <= secondLength; col++) {
                if (firstChars[row - 1].equals(secondChars[col - 1])) {
                    lcsLengths[row][col] = lcsLengths[row - 1][col - 1] + 1;
                } else {
                    lcsLengths[row][col] = Math.max(lcsLengths[row - 1][col], lcsLengths[row][col - 1]);
                }
            }
        }

        return buildLCS(first, second, lcsLengths);
    }

    /**
     * Reconstructs the Longest Common Subsequence (LCS) from the DP table.
     *
     * @param first      the first input string
     * @param second     the second input string
     * @param lcsLengths the DP table containing LCS lengths
     * @return the reconstructed LCS as a string
     */
    public static String buildLCS(String first, String second, int[][] lcsLengths) {
        StringBuilder lcsBuilder = new StringBuilder();
        int firstIndex = first.length();
        int secondIndex = second.length();

        while (firstIndex > 0 && secondIndex > 0) {
            char firstChar = first.charAt(firstIndex - 1);
            char secondChar = second.charAt(secondIndex - 1);

            if (firstChar == secondChar) {
                lcsBuilder.append(firstChar);
                firstIndex--;
                secondIndex--;
            } else if (lcsLengths[firstIndex - 1][secondIndex] > lcsLengths[firstIndex][secondIndex - 1]) {
                firstIndex--;
            } else {
                secondIndex--;
            }
        }

        return lcsBuilder.reverse().toString();
    }
}