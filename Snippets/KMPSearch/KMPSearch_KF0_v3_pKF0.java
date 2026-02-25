package com.thealgorithms.searches;

class KMPSearch {

    int kmpSearch(String pattern, String text) {
        if (pattern == null || text == null) {
            return -1;
        }

        int patternLength = pattern.length();
        int textLength = text.length();

        if (patternLength == 0 || textLength == 0 || patternLength > textLength) {
            System.out.println("No pattern found");
            return -1;
        }

        int[] longestPrefixSuffix = buildLongestPrefixSuffixArray(pattern);

        int textIndex = 0;
        int patternIndex = 0;

        while (textIndex < textLength) {
            char currentTextChar = text.charAt(textIndex);
            char currentPatternChar = pattern.charAt(patternIndex);

            if (currentPatternChar == currentTextChar) {
                textIndex++;
                patternIndex++;

                if (patternIndex == patternLength) {
                    int foundIndex = textIndex - patternIndex;
                    System.out.println("Found pattern at index " + foundIndex);
                    return foundIndex;
                }
            } else {
                if (patternIndex != 0) {
                    patternIndex = longestPrefixSuffix[patternIndex - 1];
                } else {
                    textIndex++;
                }
            }

            boolean remainingTextShorterThanPattern =
                (textLength - textIndex) < (patternLength - patternIndex);
            if (remainingTextShorterThanPattern) {
                break;
            }
        }

        System.out.println("No pattern found");
        return -1;
    }

    private int[] buildLongestPrefixSuffixArray(String pattern) {
        int patternLength = pattern.length();
        int[] longestPrefixSuffix = new int[patternLength];

        int prefixLength = 0;
        int index = 1;

        longestPrefixSuffix[0] = 0;

        while (index < patternLength) {
            if (pattern.charAt(index) == pattern.charAt(prefixLength)) {
                prefixLength++;
                longestPrefixSuffix[index] = prefixLength;
                index++;
            } else if (prefixLength != 0) {
                prefixLength = longestPrefixSuffix[prefixLength - 1];
            } else {
                longestPrefixSuffix[index] = 0;
                index++;
            }
        }

        return longestPrefixSuffix;
    }
}