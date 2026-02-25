package com.thealgorithms.dynamicprogramming;

import java.util.ArrayList;
import java.util.List;

/**
 * Dynamic programming solution to the "All Construct" problem.
 *
 * Given a target string and a collection of words (word bank), this class
 * computes all possible ways to construct the target by concatenating words
 * from the word bank. Words may be reused, and order matters.
 */
public final class AllConstruct {

    private AllConstruct() {
        // Prevent instantiation
    }

    /**
     * Returns all possible constructions of {@code target} using words from {@code wordBank}.
     *
     * Each result is a list of words whose concatenation equals {@code target}.
     * Words may be reused any number of times.
     *
     * Time Complexity: O(n * m * k), where:
     *  - n = length of target
     *  - m = number of words in wordBank
     *  - k = average length of a word
     *
     * Space Complexity: O(n * m), for the DP table of combinations.
     *
     * @param target   the string to construct
     * @param wordBank an iterable collection of words that may be used
     * @return a list of all constructions; each construction is a list of words
     */
    public static List<List<String>> allConstruct(String target, Iterable<String> wordBank) {
        int targetLength = target.length();

        // table[i] holds all combinations of words that form target.substring(0, i)
        List<List<List<String>>> table = new ArrayList<>(targetLength + 1);
        for (int i = 0; i <= targetLength; i++) {
            table.add(new ArrayList<>());
        }

        // Base case: one way to construct the empty string (no words)
        table.get(0).add(new ArrayList<>());

        for (int i = 0; i <= targetLength; i++) {
            List<List<String>> currentCombinations = table.get(i);
            if (currentCombinations.isEmpty()) {
                continue;
            }

            for (String word : wordBank) {
                int wordLength = word.length();
                int nextIndex = i + wordLength;

                if (nextIndex > targetLength) {
                    continue;
                }

                if (target.startsWith(word, i)) {
                    List<List<String>> extendedCombinations = new ArrayList<>(currentCombinations.size());

                    for (List<String> combination : currentCombinations) {
                        List<String> newCombination = new ArrayList<>(combination.size() + 1);
                        newCombination.addAll(combination);
                        newCombination.add(word);
                        extendedCombinations.add(newCombination);
                    }

                    table.get(nextIndex).addAll(extendedCombinations);
                }
            }
        }

        return table.get(targetLength);
    }
}