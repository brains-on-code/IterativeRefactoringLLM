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

        // Continue while remaining text is at least as long as remaining pattern
        while ((textLength - textIndex) >= (patternLength - patternIndex)) {

            // Characters match: move both indices forward
            if (pattern.charAt(patternIndex) == text.charAt(textIndex)) {
                patternIndex++;
                textIndex++;
            }

            // Full pattern matched
            if (patternIndex == patternLength) {
                int matchIndex = textIndex - patternIndex;
                System.out.println("Found pattern at index " + matchIndex);
                // Prepare for possible next match (standard KMP behavior)
                patternIndex = lps[patternIndex - 1];
                return matchIndex;
            }

            // Mismatch after at least one matching character
            if (textIndex < textLength && pattern.charAt(patternIndex) != text.charAt(textIndex)) {
                // Use LPS to avoid re-checking characters
                if (patternIndex != 0) {
                    patternIndex = lps[patternIndex - 1];
                } else {
                    // No prefix to fall back to; move to next character in text
                    textIndex++;
                }
            }
        }

        System.out.println("No pattern found");
        return -1;
    }

    /**
     * Computes the Longest Prefix Suffix (LPS) array for the given pattern.
     * For each index i, lps[i] stores the length of the longest proper prefix
     * of pattern[0..i] which is also a suffix of pattern[0..i].
     *
     * @param pattern the pattern for which to compute the LPS array
     * @param lps     the array to store the computed LPS values
     */
    void computeLpsArray(String pattern, int[] lps) {
        int patternLength = pattern.length();

        // Length of the previous longest prefix suffix
        int longestPrefixSuffixLength = 0;

        // LPS value for the first character is always 0
        lps[0] = 0;

        int index = 1;

        // Build the LPS array for pattern[0..patternLength-1]
        while (index < patternLength) {

            // Characters match: extend current prefix-suffix length
            if (pattern.charAt(index) == pattern.charAt(longestPrefixSuffixLength)) {
                longestPrefixSuffixLength++;
                lps[index] = longestPrefixSuffixLength;
                index++;
            } else {
                // Mismatch after at least one matching character
                if (longestPrefixSuffixLength != 0) {
                    // Fall back to the previous longest prefix-suffix
                    longestPrefixSuffixLength = lps[longestPrefixSuffixLength - 1];
                } else {
                    // No prefix-suffix to fall back to; set LPS to 0
                    lps[index] = 0;
                    index++;
                }
            }
        }
    }
}