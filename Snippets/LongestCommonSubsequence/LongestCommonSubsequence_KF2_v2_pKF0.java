package com.thealgorithms.dynamicprogramming;

final class LongestCommonSubsequence {

    private LongestCommonSubsequence() {
        // Utility class; prevent instantiation
    }

    public static String getLCS(String first, String second) {
        if (first == null || second == null) {
            return null;
        }
        if (first.isEmpty() || second.isEmpty()) {
            return "";
        }

        int firstLength = first.length();
        int secondLength = second.length();
        int[][] lcsLengths = new int[firstLength + 1][secondLength + 1];

        fillLcsLengthMatrix(first, second, lcsLengths);
        return buildLcsString(first, second, lcsLengths);
    }

    private static void fillLcsLengthMatrix(String first, String second, int[][] lcsLengths) {
        int firstLength = first.length();
        int secondLength = second.length();

        for (int i = 1; i <= firstLength; i++) {
            char firstChar = first.charAt(i - 1);
            for (int j = 1; j <= secondLength; j++) {
                char secondChar = second.charAt(j - 1);
                if (firstChar == secondChar) {
                    lcsLengths[i][j] = lcsLengths[i - 1][j - 1] + 1;
                } else {
                    lcsLengths[i][j] = Math.max(lcsLengths[i - 1][j], lcsLengths[i][j - 1]);
                }
            }
        }
    }

    private static String buildLcsString(String first, String second, int[][] lcsLengths) {
        StringBuilder lcs = new StringBuilder();
        int i = first.length();
        int j = second.length();

        while (i > 0 && j > 0) {
            char firstChar = first.charAt(i - 1);
            char secondChar = second.charAt(j - 1);

            if (firstChar == secondChar) {
                lcs.append(firstChar);
                i--;
                j--;
            } else if (lcsLengths[i - 1][j] >= lcsLengths[i][j - 1]) {
                i--;
            } else {
                j--;
            }
        }

        return lcs.reverse().toString();
    }
}