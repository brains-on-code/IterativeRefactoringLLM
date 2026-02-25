package com.thealgorithms.strings;

public final class Manacher {

    private Manacher() {
        // Prevent instantiation of utility class
    }

    /**
     * Finds the longest palindromic substring in the given string using
     * Manacher's algorithm in O(n) time.
     *
     * @param s input string
     * @return longest palindromic substring
     */
    public static String longestPalindrome(String s) {
        final String processed = preprocess(s);
        final int n = processed.length();

        int[] radius = new int[n]; // radius[i] = radius of palindrome centered at i in processed string
        int center = 0;            // center of the current rightmost palindrome
        int right = 0;             // right boundary of the current rightmost palindrome

        int maxRadius = 0;         // radius of the longest palindrome found so far
        int maxCenter = 0;         // center of the longest palindrome found so far

        for (int i = 1; i < n - 1; i++) {
            int mirror = 2 * center - i; // mirror position of i around current center

            // Use previously computed palindrome information if within the current right boundary
            if (i < right) {
                radius[i] = Math.min(right - i, radius[mirror]);
            }

            // Attempt to expand palindrome centered at i
            while (processed.charAt(i + 1 + radius[i]) == processed.charAt(i - 1 - radius[i])) {
                radius[i]++;
            }

            // If palindrome centered at i expands beyond right, adjust center and right
            if (i + radius[i] > right) {
                center = i;
                right = i + radius[i];
            }

            // Track the longest palindrome found so far
            if (radius[i] > maxRadius) {
                maxRadius = radius[i];
                maxCenter = i;
            }
        }

        // Convert center and radius in processed string back to indices in original string
        int start = (maxCenter - maxRadius) / 2;
        return s.substring(start, start + maxRadius);
    }

    /**
     * Preprocesses the input string by inserting separators and sentinels so that
     * palindromes of even and odd length are handled uniformly.
     *
     * Example:
     *   "abba" -> "^#a#b#b#a#$"
     *
     * @param s input string
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