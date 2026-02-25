package com.thealgorithms.searches;

class KMPSearch {

    /**
     * Searches for the first occurrence of the pattern in the given text using
     * the Knuth-Morris-Pratt (KMP) algorithm.
     *
     * @param pattern the pattern to search for
     * @param text    the text in which to search
     * @return the starting index of the first occurrence of the pattern in the text,
     *         or -1 if the pattern is not found
     */
    int kmpSearch(String pattern, String text) {
        int patternLength = pattern.length();
        int textLength = text.length();

        int[] longestPrefixSuffix = new int[patternLength];
        int patternIndex = 0; // index for pattern

        computeLPSArray(pattern, patternLength, longestPrefixSuffix);

        int textIndex = 0; // index for text
        while ((textLength - textIndex) >= (patternLength - patternIndex)) {
            if (pattern.charAt(patternIndex) == text.charAt(textIndex)) {
                patternIndex++;
                textIndex++;
            }

            if (patternIndex == patternLength) {
                int foundIndex = textIndex - patternIndex;
                System.out.println("Found pattern at index " + foundIndex);
                patternIndex = longestPrefixSuffix[patternIndex - 1];
                return foundIndex;
            } else if (textIndex < textLength && pattern.charAt(patternIndex) != text.charAt(textIndex)) {
                if (patternIndex != 0) {
                    patternIndex = longestPrefixSuffix[patternIndex - 1];
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
     * lps[i] stores the length of the longest proper prefix of the pattern
     * which is also a suffix for the substring pattern[0..i].
     *
     * @param pattern the pattern for which to compute the LPS array
     * @param length  the length of the pattern
     * @param lps     the array to store the computed LPS values
     */
    void computeLPSArray(String pattern, int length, int[] lps) {
        int prefixLength = 0; // length of the previous longest prefix suffix
        int index = 1;

        lps[0] = 0; // lps[0] is always 0

        while (index < length) {
            if (pattern.charAt(index) == pattern.charAt(prefixLength)) {
                prefixLength++;
                lps[index] = prefixLength;
                index++;
            } else {
                if (prefixLength != 0) {
                    prefixLength = lps[prefixLength - 1];
                } else {
                    lps[index] = 0;
                    index++;
                }
            }
        }
    }
}