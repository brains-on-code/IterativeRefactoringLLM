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

    void computeLongestPrefixSuffixArray(String pattern, int patternLength, int[] longestPrefixSuffix) {
        int lengthOfPreviousLps = 0;
        int currentIndex = 1;

        longestPrefixSuffix[0] = 0;

        while (currentIndex < patternLength) {
            if (pattern.charAt(currentIndex) == pattern.charAt(lengthOfPreviousLps)) {
                lengthOfPreviousLps++;
                longestPrefixSuffix[currentIndex] = lengthOfPreviousLps;
                currentIndex++;
            } else {
                if (lengthOfPreviousLps != 0) {
                    lengthOfPreviousLps = longestPrefixSuffix[lengthOfPreviousLps - 1];
                } else {
                    longestPrefixSuffix[currentIndex] = 0;
                    currentIndex++;
                }
            }
        }
    }
}