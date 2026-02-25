package com.thealgorithms.dynamicprogramming;

import java.util.ArrayList;
import java.util.List;

/**
 * Provides a solution to the "All Construct" problem.
 *
 * The problem is to determine all the ways a target string can be constructed
 * from a given list of substrings. Each substring in the word bank can be used
 * multiple times, and the order of substrings matters.
 */
public final class AllConstruct {

    private AllConstruct() {
        // Utility class; prevent instantiation
    }

    /**
     * Finds all possible ways to construct the target string using substrings
     * from the given word bank.
     *
     * Time Complexity: O(n * m * k), where:
     *  - n = length of the target
     *  - m = number of words in wordBank
     *  - k = average length of a word
     *
     * Space Complexity: O(n * m) due to the size of the table storing combinations.
     *
     * @param target   the target string to construct
     * @param wordBank an iterable collection of substrings that can be used to construct the target
     * @return a list of lists, where each inner list represents one possible
     *         way of constructing the target string using the given word bank
     */
    public static List<List<String>> allConstruct(String target, Iterable<String> wordBank) {
        int targetLength = target.length();

        // table[i] will store all combinations of words that construct the prefix target[0..i)
        List<List<List<String>>> table = new ArrayList<>(targetLength + 1);
        for (int i = 0; i <= targetLength; i++) {
            table.add(new ArrayList<>());
        }

        // Base case: there is one way to construct the empty string: use no words
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
                    List<List<String>> newCombinations = new ArrayList<>();

                    for (List<String> combination : currentCombinations) {
                        List<String> extendedCombination = new ArrayList<>(combination);
                        extendedCombination.add(word);
                        newCombinations.add(extendedCombination);
                    }

                    table.get(nextIndex).addAll(newCombinations);
                }
            }
        }

        return table.get(targetLength);
    }
}