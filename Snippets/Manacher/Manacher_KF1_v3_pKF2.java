package com.thealgorithms.strings;

/**
 * Utility class for palindrome-related string operations.
 */
public final class LongestPalindromeFinder {

    private LongestPalindromeFinder() {
        // Prevent instantiation
    }

    /**
     * Returns the longest palindromic substring of the given string using
     * Manacher's algorithm in O(n) time.
     *
     * @param input the input string
     * @return the longest palindromic substring
     */
    public static String findLongestPalindromicSubstring(String input) {
        final String processed = preprocessForManacher(input);
        final int length = processed.length();
        int[] palindromeRadii = new int[length];

        int center = 0;
        int rightBoundary = 0;

        int maxRadius = 0;
        int maxCenter = 0;

        for (int i = 1; i < length - 1; i++) {
            int mirror = 2 * center - i;

            // Use previously computed palindrome information if within the current right boundary
            if (i < rightBoundary) {
                palindromeRadii[i] = Math.min(rightBoundary - i, palindromeRadii[mirror]);
            }

            // Expand palindrome centered at i
            while (processed.charAt(i + 1 + palindromeRadii[i]) == processed.charAt(i - 1 - palindromeRadii[i])) {
                palindromeRadii[i]++;
            }

            // Update center and right boundary if the palindrome extends beyond the current boundary
            int currentRight = i + palindromeRadii[i];
            if (currentRight > rightBoundary) {
                center = i;
                rightBoundary = currentRight;
            }

            // Track the longest palindrome found so far
            if (palindromeRadii[i] > maxRadius) {
                maxRadius = palindromeRadii[i];
                maxCenter = i;
            }
        }

        int startIndex = (maxCenter - maxRadius) / 2;
        return input.substring(startIndex, startIndex + maxRadius);
    }

    /**
     * Transforms the input string by inserting boundary characters so that
     * even- and odd-length palindromes are handled uniformly by Manacher's algorithm.
     *
     * Example:
     *   "abba" -> "^#a#b#b#a#$"
     *
     * @param input the original string
     * @return the transformed string with boundary markers
     */
    private static String preprocessForManacher(String input) {
        if (input.isEmpty()) {
            return "^$";
        }

        StringBuilder builder = new StringBuilder("^");
        for (char c : input.toCharArray()) {
            builder.append('#').append(c);
        }
        builder.append("#$");
        return builder.toString();
    }
}