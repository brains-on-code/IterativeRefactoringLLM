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

    public static int minimalPartitions(String word) {
        int length = word.length();

        /*
         * We make two arrays to create a bottom-up solution.
         * minCuts[i] = Minimum number of cuts needed for palindrome partitioning of substring
         * word[0..i]
         * isPalindrome[i][j] = true if substring word[i..j] is palindrome
         * Base Condition: minCuts[i] is 0 if isPalindrome[0][i] = true
         */
        int[] minCuts = new int[length];
        boolean[][] isPalindrome = new boolean[length][length];

        // Every substring of length 1 is a palindrome
        for (int index = 0; index < length; index++) {
            isPalindrome[index][index] = true;
        }

        /*
         * substringLength is substring length. Build the solution in bottom up manner by
         * considering all substrings of length starting from 2 to n.
         */
        for (int substringLength = 2; substringLength <= length; substringLength++) {
            // For substring of length substringLength, set different possible starting indexes
            for (int startIndex = 0; startIndex <= length - substringLength; startIndex++) {
                int endIndex = startIndex + substringLength - 1; // Ending index

                if (substringLength == 2) {
                    isPalindrome[startIndex][endIndex] =
                        (word.charAt(startIndex) == word.charAt(endIndex));
                } else {
                    isPalindrome[startIndex][endIndex] =
                        (word.charAt(startIndex) == word.charAt(endIndex))
                            && isPalindrome[startIndex + 1][endIndex - 1];
                }
            }
        }

        // We find the minimum cuts for each index
        for (int endIndex = 0; endIndex < length; endIndex++) {
            if (isPalindrome[0][endIndex]) {
                minCuts[endIndex] = 0;
            } else {
                minCuts[endIndex] = Integer.MAX_VALUE;
                for (int cutPosition = 0; cutPosition < endIndex; cutPosition++) {
                    if (isPalindrome[cutPosition + 1][endIndex]
                        && 1 + minCuts[cutPosition] < minCuts[endIndex]) {
                        minCuts[endIndex] = 1 + minCuts[cutPosition];
                    }
                }
            }
        }

        // Return the min cut value for complete string, i.e., word[0..length-1]
        return minCuts[length - 1];
    }
}