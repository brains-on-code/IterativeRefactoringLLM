package com.thealgorithms.searches;

class KMPMatcher {

    /**
     * Searches for the first occurrence of the pattern in the text using the
     * Knuth–Morris–Pratt (KMP) algorithm.
     *
     * @param pattern the pattern to search for
     * @param text    the text in which to search
     * @return the starting index of the first occurrence of the pattern in the text,
     *         or -1 if the pattern is not found
     */
    int search(String pattern, String text) {
        int patternLength = pattern.length();
        int textLength = text.length();

        int[] lps = new int[patternLength];
        computeLpsArray(pattern, lps);

        int patternIndex = 0; // index for pattern
        int textIndex = 0;    // index for text

        while ((textLength - textIndex) >= (patternLength - patternIndex)) {
            // Characters match, move both indices
            if (pattern.charAt(patternIndex) == text.charAt(textIndex)) {
                patternIndex++;
                textIndex++;
            }

            // Full pattern matched
            if (patternIndex == patternLength) {
                int matchIndex = textIndex - patternIndex;
                System.out.println("Found pattern at index " + matchIndex);
                // Prepare for the next possible match
                patternIndex = lps[patternIndex - 1];
                return matchIndex;
            }

            // Mismatch after at least one matching character
            if (textIndex < textLength && pattern.charAt(patternIndex) != text.charAt(textIndex)) {
                // Use LPS to avoid re-checking characters
                if (patternIndex != 0) {
                    patternIndex = lps[patternIndex - 1];
                } else {
                    // No prefix to fall back to, move in the text
                    textIndex++;
                }
            }
        }

        System.out.println("No pattern found");
        return -1;
    }

    /**
     * Computes the Longest Prefix Suffix (LPS) array for the given pattern.
     * lps[i] is the length of the longest proper prefix of the substring
     * pattern[0..i] which is also a suffix of this substring.
     *
     * @param pattern the pattern for which to compute the LPS array
     * @param lps     the array to store the computed LPS values
     */
    void computeLpsArray(String pattern, int[] lps) {
        int patternLength = pattern.length();
        int longestPrefixSuffixLength = 0; // length of the previous longest prefix suffix
        int index = 1;                     // current position in pattern

        lps[0] = 0; // LPS of the first character is always 0

        while (index < patternLength) {
            // Characters match, extend current LPS
            if (pattern.charAt(index) == pattern.charAt(longestPrefixSuffixLength)) {
                longestPrefixSuffixLength++;
                lps[index] = longestPrefixSuffixLength;
                index++;
            } else {
                // Mismatch after at least one matching character
                if (longestPrefixSuffixLength != 0) {
                    // Fall back to the previous longest prefix suffix
                    longestPrefixSuffixLength = lps[longestPrefixSuffixLength - 1];
                } else {
                    // No prefix to fall back to
                    lps[index] = 0;
                    index++;
                }
            }
        }
    }
}