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

        for (int row = 1; row <= firstLength; row++) {
            char firstChar = first.charAt(row - 1);
            for (int col = 1; col <= secondLength; col++) {
                char secondChar = second.charAt(col - 1);
                if (firstChar == secondChar) {
                    lcsLengths[row][col] = lcsLengths[row - 1][col - 1] + 1;
                } else {
                    lcsLengths[row][col] =
                        Math.max(lcsLengths[row - 1][col], lcsLengths[row][col - 1]);
                }
            }
        }
    }

    private static String buildLcsString(String first, String second, int[][] lcsLengths) {
        StringBuilder lcsBuilder = new StringBuilder();
        int row = first.length();
        int col = second.length();

        while (row > 0 && col > 0) {
            char firstChar = first.charAt(row - 1);
            char secondChar = second.charAt(col - 1);

            if (firstChar == secondChar) {
                lcsBuilder.append(firstChar);
                row--;
                col--;
            } else if (lcsLengths[row - 1][col] >= lcsLengths[row][col - 1]) {
                row--;
            } else {
                col--;
            }
        }

        return lcsBuilder.reverse().toString();
    }
}