package com.thealgorithms.dynamicprogramming;

import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for dynamic programming string operations.
 */
public final class Class1 {

    private Class1() {
        // Utility class; prevent instantiation
    }

    /**
     * Computes all possible segmentations of {@code input} using words from {@code dictionary}.
     *
     * Each result is a list of words whose concatenation equals {@code input}.
     * This is the classic "Word Break II" problem.
     *
     * @param input      the string to segment
     * @param dictionary an iterable collection of allowed words
     * @return a list of all segmentations, where each segmentation is a list of words
     */
    public static List<List<String>> method1(String input, Iterable<String> dictionary) {
        int n = input.length();

        /*
         * dp[i] holds all segmentations of the prefix input[0..i),
         * where each segmentation is represented as a list of words.
         */
        List<List<List<String>>> dp = new ArrayList<>(n + 1);
        for (int i = 0; i <= n; i++) {
            dp.add(new ArrayList<>());
        }

        // Base case: the empty prefix has one valid segmentation (the empty list).
        dp.get(0).add(new ArrayList<>());

        for (int i = 0; i <= n; i++) {
            // If no segmentation ends at position i, skip.
            if (dp.get(i).isEmpty()) {
                continue;
            }

            for (String word : dictionary) {
                int nextIndex = i + word.length();
                if (nextIndex > n) {
                    continue;
                }

                // Check if 'word' matches the substring starting at position i.
                if (!input.regionMatches(i, word, 0, word.length())) {
                    continue;
                }

                // Append 'word' to each segmentation that ends at position i.
                for (List<String> segmentation : dp.get(i)) {
                    List<String> extendedSegmentation = new ArrayList<>(segmentation);
                    extendedSegmentation.add(word);
                    dp.get(nextIndex).add(extendedSegmentation);
                }
            }
        }

        // All complete segmentations of the entire string.
        return dp.get(n);
    }
}