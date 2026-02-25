package com.thealgorithms.searches;

class KMPMatcher {

    /**
     * Searches for the first occurrence of the pattern in the text using the
     * Knuth-Morris-Pratt (KMP) algorithm.
     *
     * @param pattern the pattern to search for
     * @param text    the text in which to search
     * @return the starting index of the first occurrence of the pattern in the text,
     *         or -1 if the pattern is not found
     */
    int search(String pattern, String text) {
        if (pattern == null || text == null) {
            return -1;
        }

        int patternLength = pattern.length();
        int textLength = text.length();

        if (patternLength == 0 || textLength == 0 || patternLength > textLength) {
            return -1;
        }

        int[] longestPrefixSuffix = buildLongestPrefixSuffixArray(pattern);
        int patternIndex = 0;
        int textIndex = 0;

        while (textIndex < textLength && patternIndex < patternLength) {
            char patternChar = pattern.charAt(patternIndex);
            char textChar = text.charAt(textIndex);

            if (patternChar == textChar) {
                patternIndex++;
                textIndex++;

                if (patternIndex == patternLength) {
                    return textIndex - patternIndex;
                }
            } else if (patternIndex > 0) {
                patternIndex = longestPrefixSuffix[patternIndex - 1];
            } else {
                textIndex++;
            }
        }

        return -1;
    }

    /**
     * Builds the Longest Prefix Suffix (LPS) array for the given pattern.
     *
     * @param pattern the pattern for which to compute the LPS array
     * @return the computed LPS array
     */
    private int[] buildLongestPrefixSuffixArray(String pattern) {
        int length = pattern.length();
        int[] lps = new int[length];

        int prefixLength = 0;
        int index = 1;

        while (index < length) {
            if (pattern.charAt(index) == pattern.charAt(prefixLength)) {
                prefixLength++;
                lps[index] = prefixLength;
                index++;
            } else if (prefixLength > 0) {
                prefixLength = lps[prefixLength - 1];
            } else {
                lps[index] = 0;
                index++;
            }
        }

        return lps;
    }
}