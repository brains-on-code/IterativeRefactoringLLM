package com.thealgorithms.dynamicprogramming;

import java.util.ArrayList;
import java.util.List;

/**
 * This class provides a solution to the "All Construct" problem.
 *
 * The problem is to determine all the ways a target string can be constructed
 * from a given list of substrings. Each substring in the word bank can be used
 * multiple times, and the order of substrings matters.
 *
 * @author Hardvan
 */
public final class AllConstruct {
    private AllConstruct() {
    }

    /**
     * Finds all possible ways to construct the target string using substrings
     * from the given word bank.
     * Time Complexity: O(n * m * k), where n = length of the target,
     * m = number of words in wordBank, and k = average length of a word.
     *
     * Space Complexity: O(n * m) due to the size of the table storing combinations.
     *
     * @param target   The target string to construct.
     * @param wordBank An iterable collection of substrings that can be used to construct the target.
     * @return A list of lists, where each inner list represents one possible
     *         way of constructing the target string using the given word bank.
     */
    public static List<List<String>> allConstruct(String target, Iterable<String> wordBank) {
        int targetLength = target.length();
        List<List<List<String>>> constructionsByIndex = new ArrayList<>(targetLength + 1);

        for (int index = 0; index <= targetLength; index++) {
            constructionsByIndex.add(new ArrayList<>());
        }

        constructionsByIndex.get(0).add(new ArrayList<>());

        for (int index = 0; index <= targetLength; index++) {
            List<List<String>> currentConstructions = constructionsByIndex.get(index);
            if (currentConstructions.isEmpty()) {
                continue;
            }

            for (String word : wordBank) {
                int wordLength = word.length();
                int nextIndex = index + wordLength;

                if (nextIndex <= targetLength && target.startsWith(word, index)) {
                    List<List<String>> extendedConstructions = new ArrayList<>();

                    for (List<String> construction : currentConstructions) {
                        List<String> newConstruction = new ArrayList<>(construction);
                        newConstruction.add(word);
                        extendedConstructions.add(newConstruction);
                    }

                    constructionsByIndex.get(nextIndex).addAll(extendedConstructions);
                }
            }
        }

        return constructionsByIndex.get(targetLength);
    }
}