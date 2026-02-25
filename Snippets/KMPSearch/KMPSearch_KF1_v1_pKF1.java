package com.thealgorithms.searches;

class KnuthMorrisPrattSearch {

    int search(String pattern, String text) {
        int patternLength = pattern.length();
        int textLength = text.length();

        int[] longestPrefixSuffix = new int[patternLength];
        int patternIndex = 0;

        computeLpsArray(pattern, patternLength, longestPrefixSuffix);

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
                    textIndex = textIndex + 1;
                }
            }
        }
        System.out.println("No pattern found");
        return -1;
    }

    void computeLpsArray(String pattern, int patternLength, int[] longestPrefixSuffix) {
        int length = 0;
        int index = 1;
        longestPrefixSuffix[0] = 0;

        while (index < patternLength) {
            if (pattern.charAt(index) == pattern.charAt(length)) {
                length++;
                longestPrefixSuffix[index] = length;
                index++;
            } else {
                if (length != 0) {
                    length = longestPrefixSuffix[length - 1];
                } else {
                    longestPrefixSuffix[index] = length;
                    index++;
                }
            }
        }
    }
}