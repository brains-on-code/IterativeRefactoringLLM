package com.thealgorithms.dynamicprogramming;

final class LongestCommonSubsequence {

    private LongestCommonSubsequence() {
    }

    public static String getLCS(String firstString, String secondString) {
        if (firstString == null || secondString == null) {
            return null;
        }
        if (firstString.isEmpty() || secondString.isEmpty()) {
            return "";
        }

        String[] firstChars = firstString.split("");
        String[] secondChars = secondString.split("");

        int[][] lcsLengths = new int[firstChars.length + 1][secondChars.length + 1];

        for (int row = 0; row <= firstChars.length; row++) {
            lcsLengths[row][0] = 0;
        }
        for (int col = 0; col <= secondChars.length; col++) {
            lcsLengths[0][col] = 0;
        }

        for (int row = 1; row <= firstChars.length; row++) {
            for (int col = 1; col <= secondChars.length; col++) {
                if (firstChars[row - 1].equals(secondChars[col - 1])) {
                    lcsLengths[row][col] = lcsLengths[row - 1][col - 1] + 1;
                } else {
                    lcsLengths[row][col] =
                        Math.max(lcsLengths[row - 1][col], lcsLengths[row][col - 1]);
                }
            }
        }

        return buildLcsString(firstString, secondString, lcsLengths);
    }

    public static String buildLcsString(String firstString, String secondString, int[][] lcsLengths) {
        StringBuilder lcsBuilder = new StringBuilder();
        int row = firstString.length();
        int col = secondString.length();

        while (row > 0 && col > 0) {
            if (firstString.charAt(row - 1) == secondString.charAt(col - 1)) {
                lcsBuilder.append(firstString.charAt(row - 1));
                row--;
                col--;
            } else if (lcsLengths[row - 1][col] > lcsLengths[row][col - 1]) {
                row--;
            } else {
                col--;
            }
        }

        return lcsBuilder.reverse().toString();
    }
}