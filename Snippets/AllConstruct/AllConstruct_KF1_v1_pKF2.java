package com.thealgorithms.dynamicprogramming;

import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for dynamic programming string operations.
 */
public final class Class1 {

    private Class1() {
        // Prevent instantiation
    }

    /**
     * Returns all possible segmentations of the input string using the given dictionary.
     *
     * Each result is a list of words whose concatenation equals {@code input}.
     * This is similar to the classic "word break II" problem.
     *
     * @param input      the string to segment
     * @param dictionary an iterable collection of allowed words
     * @return a list of all segmentations, where each segmentation is a list of words
     */
    public static List<List<String>> method1(String input, Iterable<String> dictionary) {
        // dp[i] holds all segmentations (as lists of words) that form input[0..i)
        List<List<List<String>>> dp = new ArrayList<>(input.length() + 1);

        for (int i = 0; i <= input.length(); i++) {
            dp.add(new ArrayList<>());
        }

        // Base case: empty prefix has one valid segmentation: the empty list
        dp.get(0).add(new ArrayList<>());

        for (int i = 0; i <= input.length(); i++) {
            if (dp.get(i).isEmpty()) {
                continue;
            }

            for (String word : dictionary) {
                int nextIndex = i + word.length();
                if (nextIndex <= input.length()
                        && input.substring(i, nextIndex).equals(word)) {

                    List<List<String>> newSegmentations = new ArrayList<>();
                    for (List<String> segmentation : dp.get(i)) {
                        List<String> extended = new ArrayList<>(segmentation);
                        extended.add(word);
                        newSegmentations.add(extended);
                    }

                    dp.get(nextIndex).addAll(newSegmentations);
                }
            }
        }

        return dp.get(input.length());
    }
}