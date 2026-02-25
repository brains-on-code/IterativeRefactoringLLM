package com.thealgorithms.dynamicprogramming;

public final class PalindromicPartitioning {

    private PalindromicPartitioning() {
    }

    public static int minimalPartitions(String input) {
        int n = input.length();

        int[] minCutsUpToIndex = new int[n];
        boolean[][] palindromeTable = new boolean[n][n];

        for (int index = 0; index < n; index++) {
            palindromeTable[index][index] = true;
        }

        for (int substringLength = 2; substringLength <= n; substringLength++) {
            for (int startIndex = 0; startIndex <= n - substringLength; startIndex++) {
                int endIndex = startIndex + substringLength - 1;
                if (substringLength == 2) {
                    palindromeTable[startIndex][endIndex] =
                        (input.charAt(startIndex) == input.charAt(endIndex));
                } else {
                    palindromeTable[startIndex][endIndex] =
                        (input.charAt(startIndex) == input.charAt(endIndex))
                            && palindromeTable[startIndex + 1][endIndex - 1];
                }
            }
        }

        for (int endIndex = 0; endIndex < n; endIndex++) {
            if (palindromeTable[0][endIndex]) {
                minCutsUpToIndex[endIndex] = 0;
            } else {
                int minCuts = Integer.MAX_VALUE;
                for (int partitionIndex = 0; partitionIndex < endIndex; partitionIndex++) {
                    if (palindromeTable[partitionIndex + 1][endIndex]
                        && 1 + minCutsUpToIndex[partitionIndex] < minCuts) {
                        minCuts = 1 + minCutsUpToIndex[partitionIndex];
                    }
                }
                minCutsUpToIndex[endIndex] = minCuts;
            }
        }

        return minCutsUpToIndex[n - 1];
    }
}