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
     * Computes the length of the shortest transformation sequence from {@code beginWord}
     * to {@code endWord}, such that:
     * <ul>
     *     <li>Only one letter can be changed at a time.</li>
     *     <li>Each transformed word must exist in {@code wordList}.</li>
     * </ul>
     *
     * This is a typical "word ladder" problem solved via BFS.
     *
     * @param beginWord the starting word
     * @param endWord   the target word
     * @param wordList  the collection of allowed intermediate words
     * @return the number of words in the shortest transformation sequence,
     *         or 0 if no such sequence exists
     */
    public static int method1(String beginWord, String endWord, Collection<String> wordList) {
        Set<String> dictionary = new HashSet<>(wordList);

        if (!dictionary.contains(endWord)) {
            return 0;
        }

        Queue<String> queue = new LinkedList<>();
        queue.offer(beginWord);
        int steps = 1;

        while (!queue.isEmpty()) {
            int levelSize = queue.size();

            for (int i = 0; i < levelSize; i++) {
                String currentWord = queue.poll();
                char[] chars = currentWord.toCharArray();

                for (int j = 0; j < chars.length; j++) {
                    char originalChar = chars[j];

                    for (char c = 'a'; c <= 'z'; c++) {
                        if (chars[j] == c) {
                            continue;
                        }

                        chars[j] = c;
                        String nextWord = new String(chars);

                        if (nextWord.equals(endWord)) {
                            return steps + 1;
                        }

                        if (dictionary.remove(nextWord)) {
                            queue.offer(nextWord);
                        }
                    }

                    chars[j] = originalChar;
                }
            }

            steps++;
        }

        return 0;
    }
}