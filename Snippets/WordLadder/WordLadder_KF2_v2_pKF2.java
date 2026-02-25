package com.thealgorithms.strings;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public final class WordLadder {

    private WordLadder() {
        // Prevent instantiation
    }

    /**
     * Returns the length of the shortest transformation sequence from
     * {@code beginWord} to {@code endWord}, where:
     * <ul>
     *   <li>Only one letter can be changed at a time.</li>
     *   <li>Each intermediate word must exist in {@code wordList}.</li>
     * </ul>
     *
     * @param beginWord the starting word
     * @param endWord   the target word
     * @param wordList  the collection of allowed intermediate words
     * @return the number of words in the shortest transformation sequence,
     *         or 0 if no such sequence exists
     */
    public static int ladderLength(String beginWord, String endWord, Collection<String> wordList) {
        Set<String> wordSet = new HashSet<>(wordList);

        if (!wordSet.contains(endWord)) {
            return 0;
        }

        Queue<String> queue = new LinkedList<>();
        queue.offer(beginWord);

        int level = 1;

        while (!queue.isEmpty()) {
            int levelSize = queue.size();

            for (int i = 0; i < levelSize; i++) {
                String currentWord = queue.poll();
                if (currentWord == null) {
                    continue;
                }

                char[] currentChars = currentWord.toCharArray();

                for (int pos = 0; pos < currentChars.length; pos++) {
                    char originalChar = currentChars[pos];

                    for (char c = 'a'; c <= 'z'; c++) {
                        if (c == originalChar) {
                            continue;
                        }

                        currentChars[pos] = c;
                        String candidate = new String(currentChars);

                        if (candidate.equals(endWord)) {
                            return level + 1;
                        }

                        if (wordSet.remove(candidate)) {
                            queue.offer(candidate);
                        }
                    }

                    currentChars[pos] = originalChar;
                }
            }

            level++;
        }

        return 0;
    }
}