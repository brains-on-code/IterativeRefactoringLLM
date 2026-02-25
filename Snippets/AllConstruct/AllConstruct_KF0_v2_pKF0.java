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
        // Utility class; prevent instantiation
    }

    /**
     * Finds all possible ways to construct the target string using substrings
     * from the given word bank.
     *
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
        List<List<List<String>>> table = createEmptyTable(targetLength);

        // Base case: one way to construct the empty string - with an empty list
        table.get(0).add(new ArrayList<>());

        for (int i = 0; i <= targetLength; i++) {
            List<List<String>> currentCombinations = table.get(i);
            if (currentCombinations.isEmpty()) {
                continue;
            }

            for (String word : wordBank) {
                if (!matchesAtPosition(target, word, i)) {
                    continue;
                }

                int nextIndex = i + word.length();
                List<List<String>> extendedCombinations = appendWordToCombinations(currentCombinations, word);
                table.get(nextIndex).addAll(extendedCombinations);
            }
        }

        return table.get(targetLength);
    }

    private static List<List<List<String>>> createEmptyTable(int targetLength) {
        List<List<List<String>>> table = new ArrayList<>(targetLength + 1);
        for (int i = 0; i <= targetLength; i++) {
            table.add(new ArrayList<>());
        }
        return table;
    }

    private static boolean matchesAtPosition(String target, String word, int startIndex) {
        int endIndex = startIndex + word.length();
        if (endIndex > target.length()) {
            return false;
        }
        return target.regionMatches(startIndex, word, 0, word.length());
    }

    private static List<List<String>> appendWordToCombinations(List<List<String>> combinations, String word) {
        List<List<String>> result = new ArrayList<>(combinations.size());
        for (List<String> combination : combinations) {
            List<String> newCombination = new ArrayList<>(combination.size() + 1);
            newCombination.addAll(combination);
            newCombination.add(word);
            result.add(newCombination);
        }
        return result;
    }
}