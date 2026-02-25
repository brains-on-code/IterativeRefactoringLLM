package com.thealgorithms.searches;

class KMPSearch {

    /**
     * Searches for the first occurrence of {@code pattern} in {@code text}
     * using the Knuth–Morris–Pratt (KMP) algorithm.
     *
     * @param pattern the pattern to search for
     * @param text    the text in which to search
     * @return the starting index of the first occurrence of {@code pattern} in {@code text},
     *         or -1 if {@code pattern} is not found
     */
    int kmpSearch(String pattern, String text) {
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
     * Computes the Longest Proper Prefix which is also Suffix (LPS) array
     * for the given pattern.
     * <p>
     * For each index {@code i}, {@code lps[i]} stores the length of the longest
     * proper prefix of {@code pattern} that is also a suffix of
     * {@code pattern[0..i]}.
     *
     * @param pattern the pattern for which to compute the LPS array
     * @param lps     the array to store the computed LPS values
     */
    void computeLpsArray(String pattern, int[] lps) {
        int length = pattern.length();
        int prefixLength = 0; // length of the current longest prefix-suffix
        int index = 1;        // current position in pattern

        lps[0] = 0; // LPS of the first character is always 0

        while (index < length) {
            if (pattern.charAt(index) == pattern.charAt(prefixLength)) {
                // Characters match: extend current prefix-suffix
                prefixLength++;
                lps[index] = prefixLength;
                index++;
            } else if (prefixLength != 0) {
                // Fallback to the previous longest prefix-suffix
                prefixLength = lps[prefixLength - 1];
            } else {
                // No prefix-suffix found for this position
                lps[index] = 0;
                index++;
            }
        }
    }
}