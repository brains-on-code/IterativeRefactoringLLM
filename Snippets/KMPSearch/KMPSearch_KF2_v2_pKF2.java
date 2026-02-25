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

        int[] lps = new int[patternLength];
        computeLpsArray(pattern, lps);

        int patternIndex = 0;
        int textIndex = 0;

        while ((textLength - textIndex) >= (patternLength - patternIndex)) {
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
     * Computes the Longest Prefix Suffix (LPS) array for the given pattern.
     * lps[i] stores the length of the longest proper prefix of the pattern
     * which is also a suffix for the substring pattern[0..i].
     *
     * @param pattern the pattern for which to compute the LPS array
     * @param lps     the array to store the computed LPS values
     */
    void computeLpsArray(String pattern, int[] lps) {
        int length = pattern.length();
        int prefixLength = 0;
        int index = 1;

        lps[0] = 0;

        while (index < length) {
            if (pattern.charAt(index) == pattern.charAt(prefixLength)) {
                prefixLength++;
                lps[index] = prefixLength;
                index++;
            } else if (prefixLength != 0) {
                prefixLength = lps[prefixLength - 1];
            } else {
                lps[index] = 0;
                index++;
            }
        }
    }
}