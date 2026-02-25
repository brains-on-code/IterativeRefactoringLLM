package com.thealgorithms.strings;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

/**
 * Utility class for string transformation operations.
 */
public final class Class1 {

    private Class1() {
        // Prevent instantiation
    }

    /**
     * Computes the length of the shortest transformation sequence from {@code start} to {@code target},
     * such that:
     * <ul>
     *     <li>Only one character can be changed at a time.</li>
     *     <li>Each intermediate word must exist in the given {@code dictionary}.</li>
     * </ul>
     *
     * If no such transformation is possible, returns {@code 0}.
     *
     * @param start      the starting word
     * @param target     the target word
     * @param dictionary the collection of allowed intermediate words
     * @return the number of steps in the shortest transformation sequence, or {@code 0} if none exists
     */
    public static int method1(String start, String target, Collection<String> dictionary) {
        if (start == null || target == null || dictionary == null) {
            return 0;
        }

        if (start.equals(target)) {
            return 1;
        }

        Set<String> remainingWords = new HashSet<>(dictionary);
        if (!remainingWords.contains(target)) {
            return 0;
        }

        Queue<String> queue = new LinkedList<>();
        queue.offer(start);
        int steps = 1;

        while (!queue.isEmpty()) {
            int levelSize = queue.size();

            for (int i = 0; i < levelSize; i++) {
                String currentWord = queue.poll();
                if (currentWord == null) {
                    continue;
                }

                for (String neighbor : generateNeighbors(currentWord, remainingWords)) {
                    if (neighbor.equals(target)) {
                        return steps + 1;
                    }
                    queue.offer(neighbor);
                }
            }

            steps++;
        }

        return 0;
    }

    private static Iterable<String> generateNeighbors(String word, Set<String> remainingWords) {
        Set<String> neighbors = new HashSet<>();
        char[] chars = word.toCharArray();

        for (int position = 0; position < chars.length; position++) {
            char originalChar = chars[position];

            for (char candidate = 'a'; candidate <= 'z'; candidate++) {
                if (candidate == originalChar) {
                    continue;
                }

                chars[position] = candidate;
                String nextWord = new String(chars);

                if (remainingWords.remove(nextWord)) {
                    neighbors.add(nextWord);
                }
            }

            chars[position] = originalChar;
        }

        return neighbors;
    }
}