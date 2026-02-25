package com.thealgorithms.dynamicprogramming;

final class LongestCommonSubsequence {

    private LongestCommonSubsequence() {
        // Utility class; prevent instantiation
    }

    public static String getLCS(String str1, String str2) {
        if (str1 == null || str2 == null) {
            return null;
        }
        if (str1.isEmpty() || str2.isEmpty()) {
            return "";
        }

        int len1 = str1.length();
        int len2 = str2.length();
        int[][] lcsMatrix = new int[len1 + 1][len2 + 1];

        // Build LCS length matrix
        for (int i = 1; i <= len1; i++) {
            char c1 = str1.charAt(i - 1);
            for (int j = 1; j <= len2; j++) {
                char c2 = str2.charAt(j - 1);
                if (c1 == c2) {
                    lcsMatrix[i][j] = lcsMatrix[i - 1][j - 1] + 1;
                } else {
                    lcsMatrix[i][j] = Math.max(lcsMatrix[i - 1][j], lcsMatrix[i][j - 1]);
                }
            }
        }

        return buildLcsString(str1, str2, lcsMatrix);
    }

    private static String buildLcsString(String str1, String str2, int[][] lcsMatrix) {
        StringBuilder lcs = new StringBuilder();
        int i = str1.length();
        int j = str2.length();

        // Reconstruct LCS from matrix
        while (i > 0 && j > 0) {
            char c1 = str1.charAt(i - 1);
            char c2 = str2.charAt(j - 1);

            if (c1 == c2) {
                lcs.append(c1);
                i--;
                j--;
            } else if (lcsMatrix[i - 1][j] >= lcsMatrix[i][j - 1]) {
                i--;
            } else {
                j--;
            }
        }

        return lcs.reverse().toString();
    }
}