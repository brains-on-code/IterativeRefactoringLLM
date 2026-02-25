package com.thealgorithms.strings;

/**
 * Utility class for palindrome-related string operations.
 */
public final class LongestPalindromeFinder {

    private LongestPalindromeFinder() {
        // Utility class; prevent instantiation
    }

    /**
     * Finds the longest palindromic substring of the given string using
     * Manacher's algorithm in O(n) time.
     *
     * @param input the input string
     * @return the longest palindromic substring
     */
    public static String findLongestPalindromicSubstring(String input) {
        final String processed = preprocessForManacher(input);
        final int n = processed.length();
        int[] radii = new int[n];

        int center = 0;
        int right = 0;

        int maxRadius = 0;
        int maxCenter = 0;

        for (int i = 1; i < n - 1; i++) {
            int mirror = 2 * center - i;

            // Initialize radius using mirror information if within current right boundary
            if (i < right) {
                radii[i] = Math.min(right - i, radii[mirror]);
            }

            // Expand palindrome centered at i
            while (processed.charAt(i + 1 + radii[i]) == processed.charAt(i - 1 - radii[i])) {
                radii[i]++;
            }

            // Update center and right boundary if this palindrome extends further
            int currentRight = i + radii[i];
            if (currentRight > right) {
                center = i;
                right = currentRight;
            }

            // Track the longest palindrome found so far
            if (radii[i] > maxRadius) {
                maxRadius = radii[i];
                maxCenter = i;
            }
        }

        int startIndex = (maxCenter - maxRadius) / 2;
        return input.substring(startIndex, startIndex + maxRadius);
    }

    /**
     * Preprocesses the input string for Manacher's algorithm by inserting
     * boundary characters so that even- and odd-length palindromes are
     * handled uniformly.
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