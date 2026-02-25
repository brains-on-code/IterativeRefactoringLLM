package com.thealgorithms.dynamicprogramming;

import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for constructing all possible combinations of words from a word bank
 * that can form a given target string.
 */
public final class AllConstruct {

    private AllConstruct() {
        // Prevent instantiation
    }

    /**
     * Returns all possible ways to construct the target string by concatenating words
     * from the given word bank. Each word can be used multiple times.
     *
     * @param target   the string to construct
     * @param wordBank an iterable collection of words that can be used to construct the target
     * @return a list of combinations, where each combination is a list of words that
     *         concatenate to form the target
     */
    public static List<List<String>> allConstruct(String target, Iterable<String> wordBank) {
        List<List<List<String>>> constructionsByIndex = new ArrayList<>(target.length() + 1);

        for (int i = 0; i <= target.length(); i++) {
            constructionsByIndex.add(new ArrayList<>());
        }

        // Base case: one way to construct the empty prefix (use no words)
        constructionsByIndex.get(0).add(new ArrayList<>());

        for (int currentIndex = 0; currentIndex <= target.length(); currentIndex++) {
            List<List<String>> currentConstructions = constructionsByIndex.get(currentIndex);
            if (!currentConstructions.isEmpty()) {
                for (String word : wordBank) {
                    int nextIndex = currentIndex + word.length();
                    if (nextIndex <= target.length()
                        && target.substring(currentIndex, nextIndex).equals(word)) {

                        List<List<String>> extendedConstructions = new ArrayList<>();
                        for (List<String> existingConstruction : currentConstructions) {
                            List<String> newConstruction = new ArrayList<>(existingConstruction);
                            newConstruction.add(word);
                            extendedConstructions.add(newConstruction);
                        }

                        constructionsByIndex.get(nextIndex).addAll(extendedConstructions);
                    }
                }
            }
        }

        return constructionsByIndex.get(target.length());
    }
}