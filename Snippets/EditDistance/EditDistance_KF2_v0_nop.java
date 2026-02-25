package com.thealgorithms.dynamicprogramming;


public final class EditDistance {
    private EditDistance() {
    }

    public static int minDistance(String word1, String word2) {
        int len1 = word1.length();
        int len2 = word2.length();
        int[][] dp = new int[len1 + 1][len2 + 1];

        for (int i = 0; i <= len1; i++) {
            dp[i][0] = i;
        }

        for (int j = 0; j <= len2; j++) {
            dp[0][j] = j;
        }
        for (int i = 0; i < len1; i++) {
            char c1 = word1.charAt(i);
            for (int j = 0; j < len2; j++) {
                char c2 = word2.charAt(j);
                if (c1 == c2) {
                    dp[i + 1][j + 1] = dp[i][j];
                } else {

                    int replace = dp[i][j] + 1;
                    int insert = dp[i][j + 1] + 1;
                    int delete = dp[i + 1][j] + 1;

                    int min = Math.min(replace, insert);
                    min = Math.min(delete, min);
                    dp[i + 1][j + 1] = min;
                }
            }
        }

        return dp[len1][len2];
    }

    public static int editDistance(String s1, String s2) {
        int[][] storage = new int[s1.length() + 1][s2.length() + 1];
        return editDistance(s1, s2, storage);
    }

    public static int editDistance(String s1, String s2, int[][] storage) {
        int m = s1.length();
        int n = s2.length();
        if (storage[m][n] > 0) {
            return storage[m][n];
        }
        if (m == 0) {
            storage[m][n] = n;
            return storage[m][n];
        }
        if (n == 0) {
            storage[m][n] = m;
            return storage[m][n];
        }
        if (s1.charAt(0) == s2.charAt(0)) {
            storage[m][n] = editDistance(s1.substring(1), s2.substring(1), storage);
        } else {
            int op1 = editDistance(s1, s2.substring(1), storage);
            int op2 = editDistance(s1.substring(1), s2, storage);
            int op3 = editDistance(s1.substring(1), s2.substring(1), storage);
            storage[m][n] = 1 + Math.min(op1, Math.min(op2, op3));
        }
        return storage[m][n];
    }
}
