package com.thealgorithms.dynamicprogramming;

public final class PalindromicPartitioning {

    private PalindromicPartitioning() {
    }

    public static int minimalPartitions(String input) {
        int length = input.length();

        int[] minCuts = new int[length];
        boolean[][] isPalindrome = new boolean[length][length];

        // Every single character is a palindrome
        for (int index = 0; index < length; index++) {
            isPalindrome[index][index] = true;
        }

        // Build palindrome table for all substring lengths
        for (int currentLength = 2; currentLength <= length; currentLength++) {
            for (int start = 0; start <= length - currentLength; start++) {
                int end = start + currentLength - 1;
                if (currentLength == 2) {
                    isPalindrome[start][end] = input.charAt(start) == input.charAt(end);
                } else {
                    isPalindrome[start][end] =
                        input.charAt(start) == input.charAt(end)
                            && isPalindrome[start + 1][end - 1];
                }
            }
        }

        // Compute minimum cuts needed for palindromic partitioning
        for (int end = 0; end < length; end++) {
            if (isPalindrome[0][end]) {
                minCuts[end] = 0;
            } else {
                int minCutsForEnd = Integer.MAX_VALUE;
                for (int partition = 0; partition < end; partition++) {
                    if (isPalindrome[partition + 1][end]
                        && 1 + minCuts[partition] < minCutsForEnd) {
                        minCutsForEnd = 1 + minCuts[partition];
                    }
                }
                minCuts[end] = minCutsForEnd;
            }
        }

        return minCuts[length - 1];
    }
}