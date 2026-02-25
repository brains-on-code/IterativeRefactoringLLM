package com.thealgorithms.searches;

class KMPSearch {

    /**
     * Searches for the first occurrence of the given pattern in the given text
     * using the Knuth-Morris-Pratt (KMP) algorithm.
     *
     * @param pattern the substring to search for
     * @param text    the text in which to search
     * @return the starting index of the first occurrence of pattern in text,
     *         or -1 if the pattern is not found
     */
    int kmpSearch(String pattern, String text) {
        int patternLength = pattern.length();
        int textLength = text.length();

        if (patternLength == 0 || textLength == 0 || patternLength > textLength) {
            System.out.println("No pattern found");
            return -1;
        }

        int[] lps = new int[patternLength];
        computeLPSArray(pattern, lps);

        int patternIndex = 0;
        int textIndex = 0;

        while (textIndex < textLength && (textLength - textIndex) >= (patternLength - patternIndex)) {
            if (pattern.charAt(patternIndex) == text.charAt(textIndex)) {
                patternIndex++;
                textIndex++;
            }

            if (patternIndex == patternLength) {
                int foundIndex = textIndex - patternIndex;
                System.out.println("Found pattern at index " + foundIndex);
                return foundIndex;
            }

            if (textIndex < textLength && pattern.charAt(patternIndex) != text.charAt(textIndex)) {
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
     * Builds the Longest Prefix Suffix (LPS) array for the given pattern.
     * lps[i] contains the length of the longest proper prefix of the substring
     * pattern[0..i] which is also a suffix of this substring.
     *
     * @param pattern the pattern for which to compute the LPS array
     * @param lps     the array to fill with LPS values
     */
    void computeLPSArray(String pattern, int[] lps) {
        int patternLength = pattern.length();
        int lengthOfPreviousLPS = 0;
        int index = 1;

        lps[0] = 0;

        while (index < patternLength) {
            if (pattern.charAt(index) == pattern.charAt(lengthOfPreviousLPS)) {
                lengthOfPreviousLPS++;
                lps[index] = lengthOfPreviousLPS;
                index++;
            } else if (lengthOfPreviousLPS != 0) {
                lengthOfPreviousLPS = lps[lengthOfPreviousLPS - 1];
            } else {
                lps[index] = 0;
                index++;
            }
        }
    }
}