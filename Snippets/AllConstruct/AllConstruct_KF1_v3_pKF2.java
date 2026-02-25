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
     * Returns all possible segmentations of the input string using the given dictionary.
     *
     * Each result is a list of words whose concatenation equals {@code input}.
     * This corresponds to the "word break II" problem.
     *
     * @param input      the string to segment
     * @param dictionary an iterable collection of allowed words
     * @return a list of all segmentations, where each segmentation is a list of words
     */
    public static List<List<String>> method1(String input, Iterable<String> dictionary) {
        int n = input.length();

        // dp[i] holds all segmentations (each a list of words) that form input[0..i)
        List<List<List<String>>> dp = new ArrayList<>(n + 1);
        for (int i = 0; i <= n; i++) {
            dp.add(new ArrayList<>());
        }

        // Base case: empty prefix has one valid segmentation (the empty list)
        dp.get(0).add(new ArrayList<>());

        for (int i = 0; i <= n; i++) {
            if (dp.get(i).isEmpty()) {
                continue;
            }

            for (String word : dictionary) {
                int nextIndex = i + word.length();
                if (nextIndex > n) {
                    continue;
                }

                if (!input.regionMatches(i, word, 0, word.length())) {
                    continue;
                }

                // Append the current word to all segmentations ending at position i
                for (List<String> segmentation : dp.get(i)) {
                    List<String> extendedSegmentation = new ArrayList<>(segmentation);
                    extendedSegmentation.add(word);
                    dp.get(nextIndex).add(extendedSegmentation);
                }
            }
        }

        return dp.get(n);
    }
}