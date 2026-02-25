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
        int targetLength = target.length();
        List<List<List<String>>> constructionsByPosition = new ArrayList<>(targetLength + 1);

        for (int position = 0; position <= targetLength; position++) {
            constructionsByPosition.add(new ArrayList<>());
        }

        // Base case: one way to construct the empty prefix (use no words)
        constructionsByPosition.get(0).add(new ArrayList<>());

        for (int startPosition = 0; startPosition <= targetLength; startPosition++) {
            List<List<String>> constructionsAtStart = constructionsByPosition.get(startPosition);
            if (constructionsAtStart.isEmpty()) {
                continue;
            }

            for (String word : wordBank) {
                int endPosition = startPosition + word.length();
                if (endPosition <= targetLength
                    && target.substring(startPosition, endPosition).equals(word)) {

                    List<List<String>> newConstructionsForEnd = new ArrayList<>();
                    for (List<String> existingConstruction : constructionsAtStart) {
                        List<String> extendedConstruction = new ArrayList<>(existingConstruction);
                        extendedConstruction.add(word);
                        newConstructionsForEnd.add(extendedConstruction);
                    }

                    constructionsByPosition.get(endPosition).addAll(newConstructionsForEnd);
                }
            }
        }

        return constructionsByPosition.get(targetLength);
    }
}