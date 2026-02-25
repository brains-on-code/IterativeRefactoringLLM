package com.thealgorithms.dynamicprogramming;


public final class PalindromicPartitioning {
    private PalindromicPartitioning() {
    }

    public static int minimalPartitions(String word) {
        int len = word.length();

        int[] minCuts = new int[len];
        boolean[][] isPalindrome = new boolean[len][len];

        int i;
        int j;
        int subLen;

        for (i = 0; i < len; i++) {
            isPalindrome[i][i] = true;
        }


        for (subLen = 2; subLen <= len; subLen++) {
            for (i = 0; i < len - subLen + 1; i++) {
                j = i + subLen - 1;
                if (subLen == 2) {
                    isPalindrome[i][j] = (word.charAt(i) == word.charAt(j));
                } else {
                    isPalindrome[i][j] = (word.charAt(i) == word.charAt(j)) && isPalindrome[i + 1][j - 1];
                }
            }
        }

        for (i = 0; i < len; i++) {
            if (isPalindrome[0][i]) {
                minCuts[i] = 0;
            } else {
                minCuts[i] = Integer.MAX_VALUE;
                for (j = 0; j < i; j++) {
                    if (isPalindrome[j + 1][i] && 1 + minCuts[j] < minCuts[i]) {
                        minCuts[i] = 1 + minCuts[j];
                    }
                }
            }
        }

        return minCuts[len - 1];
    }
}
