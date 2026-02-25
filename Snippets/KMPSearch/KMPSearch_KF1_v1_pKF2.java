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

        // Longest Prefix Suffix (LPS) array for the pattern
        int[] lps = new int[patternLength];

        // Preprocess the pattern to build the LPS array
        computeLpsArray(pattern, patternLength, lps);

        int patternIndex = 0; // index for pattern
        int textIndex = 0;    // index for text

        while ((textLength - textIndex) >= (patternLength - patternIndex)) {
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

            // Mismatch after patternIndex matches
            else if (textIndex < textLength && pattern.charAt(patternIndex) != text.charAt(textIndex)) {
                // Do not match lps[0..lps[patternIndex-1]] characters,
                // they will match anyway
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
        int length = 0; // length of the previous longest prefix suffix
        int i = 1;

        lps[0] = 0; // lps[0] is always 0

        while (i < patternLength) {
            if (pattern.charAt(i) == pattern.charAt(length)) {
                length++;
                lps[i] = length;
                i++;
            } else {
                // Mismatch after length matches
                if (length != 0) {
                    // Consider the previous longest prefix suffix
                    length = lps[length - 1];
                } else {
                    // No proper prefix which is also suffix found
                    lps[i] = 0;
                    i++;
                }
            }
        }
    }
}