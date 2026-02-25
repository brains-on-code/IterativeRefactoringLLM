package com.thealgorithms.strings;

/**
 * Implementation of Manacher's algorithm for finding the longest palindromic substring.
 *
 * Reference: https://en.wikipedia.org/wiki/Longest_palindromic_substring#Manacher's_algorithm
 */
public final class Manacher {

    private Manacher() {
        // Utility class; prevent instantiation
    }

    /**
     * Returns the longest palindromic substring of the given string using Manacher's algorithm.
     *
     * @param s input string
     * @return longest palindromic substring in {@code s}
     */
    public static String longestPalindrome(String s) {
        final String processed = preprocess(s);
        final int n = processed.length();

        int[] palindromeRadius = new int[n];
        int center = 0;
        int right = 0;

        int maxRadius = 0;
        int maxCenter = 0;

        for (int i = 1; i < n - 1; i++) {
            int mirror = 2 * center - i;

            if (i < right) {
                palindromeRadius[i] = Math.min(right - i, palindromeRadius[mirror]);
            }

            while (processed.charAt(i + 1 + palindromeRadius[i])
                    == processed.charAt(i - 1 - palindromeRadius[i])) {
                palindromeRadius[i]++;
            }

            if (i + palindromeRadius[i] > right) {
                center = i;
                right = i + palindromeRadius[i];
            }

            if (palindromeRadius[i] > maxRadius) {
                maxRadius = palindromeRadius[i];
                maxCenter = i;
            }
        }

        int start = (maxCenter - maxRadius) / 2;
        return s.substring(start, start + maxRadius);
    }

    /**
     * Transforms the input string to simplify palindrome expansion.
     *
     * For example, "abba" becomes "^#a#b#b#a#$".
     * This ensures:
     * - All palindromes in the transformed string have odd length.
     * - Boundaries are handled without explicit checks.
     *
     * @param s original string
     * @return transformed string with sentinels and separators
     */
    private static String preprocess(String s) {
        if (s.isEmpty()) {
            return "^$";
        }

        StringBuilder builder = new StringBuilder("^");
        for (char c : s.toCharArray()) {
            builder.append('#').append(c);
        }
        builder.append("#$");

        return builder.toString();
    }
}