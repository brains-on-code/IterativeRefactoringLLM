package com.thealgorithms.strings;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

/**
 * Solves the classic "Word Ladder" problem:
 * Find the length of the shortest transformation sequence from beginWord to endWord,
 * where each step changes exactly one letter and each intermediate word must be in wordList.
 */
public final class WordLadder {

    private WordLadder() {
        // Utility class; prevent instantiation
    }

    /**
     * Computes the length of the shortest transformation sequence from {@code beginWord}
     * to {@code endWord}. Each step may change exactly one character, and every
     * intermediate word must be contained in {@code wordList}.
     *
     * If no such sequence exists, this method returns {@code 0}.
     *
     * @param beginWord the starting word
     * @param endWord   the target word
     * @param wordList  the collection of allowed intermediate words
     * @return the length of the shortest valid transformation sequence, or {@code 0} if none exists
     */
    public static int ladderLength(String beginWord, String endWord, Collection<String> wordList) {
        Set<String> remainingWords = new HashSet<>(wordList);

        // If the target word is not in the dictionary, no transformation is possible
        if (!remainingWords.contains(endWord)) {
            return 0;
        }

        Queue<String> queue = new LinkedList<>();
        queue.offer(beginWord);

        int level = 1; // Current transformation length (beginWord itself is level 1)

        while (!queue.isEmpty()) {
            int levelSize = queue.size();

            // Process all words at the current transformation level
            for (int i = 0; i < levelSize; i++) {
                String currentWord = queue.poll();
                if (currentWord == null) {
                    continue;
                }

                char[] chars = currentWord.toCharArray();

                // Try changing each character in the current word
                for (int pos = 0; pos < chars.length; pos++) {
                    char originalChar = chars[pos];

                    // Substitute with every possible lowercase letter
                    for (char c = 'a'; c <= 'z'; c++) {
                        if (c == originalChar) {
                            continue;
                        }

                        chars[pos] = c;
                        String candidate = new String(chars);

                        // Found the target word; return the next level
                        if (candidate.equals(endWord)) {
                            return level + 1;
                        }

                        // If candidate is in the remaining dictionary, enqueue it and mark as visited
                        if (remainingWords.remove(candidate)) {
                            queue.offer(candidate);
                        }
                    }

                    // Restore original character before moving to the next position
                    chars[pos] = originalChar;
                }
            }

            level++;
        }

        // No valid transformation sequence found
        return 0;
    }
}