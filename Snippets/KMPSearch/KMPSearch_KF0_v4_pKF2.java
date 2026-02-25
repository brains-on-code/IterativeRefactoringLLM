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

        // Quick rejection for empty inputs or when pattern is longer than text
        if (patternLength == 0 || textLength == 0 || patternLength > textLength) {
            System.out.println("No pattern found");
            return -1;
        }

        // Preprocess the pattern to build the LPS (Longest Prefix Suffix) array
        int[] lps = new int[patternLength];
        computeLPSArray(pattern, lps);

        int patternIndex = 0; // index for pattern
        int textIndex = 0;    // index for text

        // Continue while there is enough remaining text to match the remaining pattern
        while (textIndex < textLength && (textLength - textIndex) >= (patternLength - patternIndex)) {
            if (pattern.charAt(patternIndex) == text.charAt(textIndex)) {
                patternIndex++;
                textIndex++;
            }

            // Full pattern matched
            if (patternIndex == patternLength) {
                int foundIndex = textIndex - patternIndex;
                System.out.println("Found pattern at index " + foundIndex);
                return foundIndex;
            }

            // Mismatch after at least one matching character
            if (textIndex < textLength && pattern.charAt(patternIndex) != text.charAt(textIndex)) {
                if (patternIndex != 0) {
                    // Use LPS to avoid re-checking characters
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
     * Builds the Longest Prefix Suffix (LPS) array for the given pattern.
     * lps[i] contains the length of the longest proper prefix of the substring
     * pattern[0..i] which is also a suffix of this substring.
     *
     * @param pattern the pattern for which to compute the LPS array
     * @param lps     the array to fill with LPS values
     */
    void computeLPSArray(String pattern, int[] lps) {
        int patternLength = pattern.length();

        // Length of the previous longest prefix suffix
        int lengthOfPreviousLPS = 0;

        // LPS value for the first character is always 0
        lps[0] = 0;

        int index = 1; // current position in pattern

        while (index < patternLength) {
            if (pattern.charAt(index) == pattern.charAt(lengthOfPreviousLPS)) {
                lengthOfPreviousLPS++;
                lps[index] = lengthOfPreviousLPS;
                index++;
            } else if (lengthOfPreviousLPS != 0) {
                // Fall back to the previous longest prefix suffix
                lengthOfPreviousLPS = lps[lengthOfPreviousLPS - 1];
            } else {
                // No prefix suffix match found for this position
                lps[index] = 0;
                index++;
            }
        }
    }
}