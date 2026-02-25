package com.thealgorithms.searches;

class KnuthMorrisPrattSearch {

    int search(String pattern, String text) {
        int patternLength = pattern.length();
        int textLength = text.length();

        int[] longestPrefixSuffix = new int[patternLength];
        int patternIndex = 0;

        computeLongestPrefixSuffixArray(pattern, patternLength, longestPrefixSuffix);

        int textIndex = 0;
        while ((textLength - textIndex) >= (patternLength - patternIndex)) {
            if (pattern.charAt(patternIndex) == text.charAt(textIndex)) {
                patternIndex++;
                textIndex++;
            }

            if (patternIndex == patternLength) {
                int matchIndex = textIndex - patternIndex;
                System.out.println("Found pattern at index " + matchIndex);
                patternIndex = longestPrefixSuffix[patternIndex - 1];
                return matchIndex;
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

    void computeLongestPrefixSuffixArray(String pattern, int patternLength, int[] longestPrefixSuffix) {
        int currentLpsLength = 0;
        int currentIndex = 1;

        longestPrefixSuffix[0] = 0;

        while (currentIndex < patternLength) {
            if (pattern.charAt(currentIndex) == pattern.charAt(currentLpsLength)) {
                currentLpsLength++;
                longestPrefixSuffix[currentIndex] = currentLpsLength;
                currentIndex++;
            } else {
                if (currentLpsLength != 0) {
                    currentLpsLength = longestPrefixSuffix[currentLpsLength - 1];
                } else {
                    longestPrefixSuffix[currentIndex] = 0;
                    currentIndex++;
                }
            }
        }
    }
}