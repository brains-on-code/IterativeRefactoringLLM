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
        computeLpsArray(pattern, patternLength, lps);

        int patternIndex = 0;
        int textIndex = 0;

        while ((textLength - textIndex) >= (patternLength - patternIndex)) {
            if (pattern.charAt(patternIndex) == text.charAt(textIndex)) {
                patternIndex++;
                textIndex++;
            }

            if (patternIndex == patternLength) {
                int matchIndex = textIndex - patternIndex;
                System.out.println("Found pattern at index " + matchIndex);
                patternIndex = lps[patternIndex - 1];
                return matchIndex;
            } else if (textIndex < textLength && pattern.charAt(patternIndex) != text.charAt(textIndex)) {
                if (patternIndex != 0) {
                    patternIndex = lps[patternIndex - 1];
                } else {
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
     * @param pattern       the pattern for which to compute the LPS array
     * @param patternLength the length of the pattern
     * @param lps           the array to store the computed LPS values
     */
    void computeLpsArray(String pattern, int patternLength, int[] lps) {
        int longestPrefixSuffixLength = 0;
        int index = 1;

        lps[0] = 0;

        while (index < patternLength) {
            if (pattern.charAt(index) == pattern.charAt(longestPrefixSuffixLength)) {
                longestPrefixSuffixLength++;
                lps[index] = longestPrefixSuffixLength;
                index++;
            } else {
                if (longestPrefixSuffixLength != 0) {
                    longestPrefixSuffixLength = lps[longestPrefixSuffixLength - 1];
                } else {
                    lps[index] = 0;
                    index++;
                }
            }
        }
    }
}