package com.thealgorithms.dynamicprogramming;

public final class PalindromicPartitioning {

    private PalindromicPartitioning() {
    }

    public static int minimalPartitions(String input) {
        int length = input.length();

        int[] minCuts = new int[length];
        boolean[][] isPalindrome = new boolean[length][length];

        for (int i = 0; i < length; i++) {
            isPalindrome[i][i] = true;
        }

        for (int currentLength = 2; currentLength <= length; currentLength++) {
            for (int start = 0; start <= length - currentLength; start++) {
                int end = start + currentLength - 1;
                if (currentLength == 2) {
                    isPalindrome[start][end] = input.charAt(start) == input.charAt(end);
                } else {
                    isPalindrome[start][end] =
                        input.charAt(start) == input.charAt(end) && isPalindrome[start + 1][end - 1];
                }
            }
        }

        for (int end = 0; end < length; end++) {
            if (isPalindrome[0][end]) {
                minCuts[end] = 0;
            } else {
                int currentMinCuts = Integer.MAX_VALUE;
                for (int partition = 0; partition < end; partition++) {
                    if (isPalindrome[partition + 1][end]
                        && 1 + minCuts[partition] < currentMinCuts) {
                        currentMinCuts = 1 + minCuts[partition];
                    }
                }
                minCuts[end] = currentMinCuts;
            }
        }

        return minCuts[length - 1];
    }
}