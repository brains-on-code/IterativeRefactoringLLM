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
        int patternLength = pattern.length();
        int textLength = text.length();

        int[] lps = new int[patternLength]; // longest prefix suffix array
        int patternIndex = 0; // index for pattern

        computeLpsArray(pattern, patternLength, lps);

        int textIndex = 0; // index for text
        while ((textLength - textIndex) >= (patternLength - patternIndex)) {
            if (pattern.charAt(patternIndex) == text.charAt(textIndex)) {
                patternIndex++;
                textIndex++;
            }

            if (patternIndex == patternLength) {
                int foundIndex = textIndex - patternIndex;
                System.out.println("Found pattern at index " + foundIndex);
                patternIndex = lps[patternIndex - 1];
                return foundIndex;
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
     *
     * @param pattern       the pattern for which to compute the LPS array
     * @param patternLength the length of the pattern
     * @param lps           the array to store the computed LPS values
     */
    void computeLpsArray(String pattern, int patternLength, int[] lps) {
        int length = 0; // length of the previous longest prefix suffix
        int index = 1;

        lps[0] = 0;

        while (index < patternLength) {
            if (pattern.charAt(index) == pattern.charAt(length)) {
                length++;
                lps[index] = length;
                index++;
            } else {
                if (length != 0) {
                    length = lps[length - 1];
                } else {
                    lps[index] = 0;
                    index++;
                }
            }
        }
    }
}