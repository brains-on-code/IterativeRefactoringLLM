package com.thealgorithms.dynamicprogramming;

/**
 * Provides functionality to solve the Palindrome Partitioning II problem, which involves finding
 * the minimum number of partitions needed to divide a given string into palindromic substrings.
 *
 * <p>
 * The problem is solved using dynamic programming. The approach involves checking all possible
 * substrings and determining whether they are palindromes. The minimum number of cuts required
 * for palindrome partitioning is computed in a bottom-up manner.
 * </p>
 *
 * <p>
 * Example:
 * <ul>
 *     <li>Input: "nitik" => Output: 2 (Partitioning: "n | iti | k")</li>
 *     <li>Input: "ababbbabbababa" => Output: 3 (Partitioning: "aba | b | bbabb | ababa")</li>
 * </ul>
 * </p>
 *
 * @see <a href="https://leetcode.com/problems/palindrome-partitioning-ii/">Palindrome Partitioning II</a>
 * @see <a href="https://www.geeksforgeeks.org/palindrome-partitioning-dp-17/">Palindrome Partitioning (GeeksforGeeks)</a>
 */
public final class PalindromicPartitioning {

    private PalindromicPartitioning() {
    }

    public static int minimalPartitions(String text) {
        int textLength = text.length();

        /*
         * We make two arrays to create a bottom-up solution.
         * minCuts[i] = Minimum number of cuts needed for palindrome partitioning of substring
         * text[0..i]
         * isPalindrome[i][j] = true if substring text[i..j] is palindrome
         * Base Condition: minCuts[i] is 0 if isPalindrome[0][i] = true
         */
        int[] minCuts = new int[textLength];
        boolean[][] isPalindrome = new boolean[textLength][textLength];

        // Every substring of length 1 is a palindrome
        for (int index = 0; index < textLength; index++) {
            isPalindrome[index][index] = true;
        }

        /*
         * substringLength is substring length. Build the solution in bottom up manner by
         * considering all substrings of length starting from 2 to n.
         */
        for (int substringLength = 2; substringLength <= textLength; substringLength++) {
            // For substring of length substringLength, set different possible starting indexes
            for (int startIndex = 0; startIndex <= textLength - substringLength; startIndex++) {
                int endIndex = startIndex + substringLength - 1; // Ending index

                if (substringLength == 2) {
                    isPalindrome[startIndex][endIndex] =
                        (text.charAt(startIndex) == text.charAt(endIndex));
                } else {
                    isPalindrome[startIndex][endIndex] =
                        (text.charAt(startIndex) == text.charAt(endIndex))
                            && isPalindrome[startIndex + 1][endIndex - 1];
                }
            }
        }

        // We find the minimum cuts for each index
        for (int endIndex = 0; endIndex < textLength; endIndex++) {
            if (isPalindrome[0][endIndex]) {
                minCuts[endIndex] = 0;
            } else {
                minCuts[endIndex] = Integer.MAX_VALUE;
                for (int cutIndex = 0; cutIndex < endIndex; cutIndex++) {
                    if (isPalindrome[cutIndex + 1][endIndex]
                        && 1 + minCuts[cutIndex] < minCuts[endIndex]) {
                        minCuts[endIndex] = 1 + minCuts[cutIndex];
                    }
                }
            }
        }

        // Return the min cut value for complete string, i.e., text[0..textLength-1]
        return minCuts[textLength - 1];
    }
}