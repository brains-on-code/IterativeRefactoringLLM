package com.thealgorithms.searches;

class KMPSearch {

    int kmpSearch(String pattern, String text) {
        int patternLength = pattern.length();
        int textLength = text.length();

        int[] longestPrefixSuffix = new int[patternLength];
        int patternIndex = 0;

        computeLPSArray(pattern, patternLength, longestPrefixSuffix);

        int textIndex = 0;
        while ((textLength - textIndex) >= (patternLength - patternIndex)) {
            if (pattern.charAt(patternIndex) == text.charAt(textIndex)) {
                patternIndex++;
                textIndex++;
            }

            if (patternIndex == patternLength) {
                System.out.println("Found pattern at index " + (textIndex - patternIndex));
                int foundIndex = textIndex - patternIndex;
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

    void computeLPSArray(String pattern, int patternLength, int[] longestPrefixSuffix) {
        int lengthOfPreviousLPS = 0;
        int index = 1;

        longestPrefixSuffix[0] = 0;

        while (index < patternLength) {
            if (pattern.charAt(index) == pattern.charAt(lengthOfPreviousLPS)) {
                lengthOfPreviousLPS++;
                longestPrefixSuffix[index] = lengthOfPreviousLPS;
                index++;
            } else {
                if (lengthOfPreviousLPS != 0) {
                    lengthOfPreviousLPS = longestPrefixSuffix[lengthOfPreviousLPS - 1];
                } else {
                    longestPrefixSuffix[index] = 0;
                    index++;
                }
            }
        }
    }
}