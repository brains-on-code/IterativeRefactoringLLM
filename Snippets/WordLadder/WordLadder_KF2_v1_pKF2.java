package com.thealgorithms.strings;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public final class WordLadder {

    private WordLadder() {
        // Utility class; prevent instantiation
    }

    /**
     * Computes the length of the shortest transformation sequence from
     * {@code beginWord} to {@code endWord}, where each intermediate word
     * must exist in {@code wordList} and each step changes exactly one letter.
     *
     * @param beginWord the starting word
     * @param endWord   the target word
     * @param wordList  the collection of allowed intermediate words
     * @return the number of words in the shortest transformation sequence,
     *         or 0 if no such sequence exists
     */
    public static int ladderLength(String beginWord, String endWord, Collection<String> wordList) {
        Set<String> wordSet = new HashSet<>(wordList);

        // If the end word is not in the dictionary, no transformation is possible
        if (!wordSet.contains(endWord)) {
            return 0;
        }

        Queue<String> queue = new LinkedList<>();
        queue.offer(beginWord);
        int level = 1; // Number of transformation steps including beginWord

        while (!queue.isEmpty()) {
            int levelSize = queue.size();

            // Process all words at the current BFS level
            for (int i = 0; i < levelSize; i++) {
                String currentWord = queue.poll();
                if (currentWord == null) {
                    continue;
                }

                char[] currentChars = currentWord.toCharArray();

                // Try changing each character in the current word
                for (int pos = 0; pos < currentChars.length; pos++) {
                    char originalChar = currentChars[pos];

                    // Substitute with every possible lowercase letter
                    for (char c = 'a'; c <= 'z'; c++) {
                        if (c == originalChar) {
                            continue;
                        }

                        currentChars[pos] = c;
                        String candidate = new String(currentChars);

                        // Found the target word; return the sequence length
                        if (candidate.equals(endWord)) {
                            return level + 1;
                        }

                        // If candidate is in the dictionary and not yet visited,
                        // add it to the queue and mark as visited by removing it
                        if (wordSet.remove(candidate)) {
                            queue.offer(candidate);
                        }
                    }

                    // Restore original character before moving to next position
                    currentChars[pos] = originalChar;
                }
            }

            // Move to the next BFS level
            level++;
        }

        // No valid transformation sequence found
        return 0;
    }
}