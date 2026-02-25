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
        // Prevent instantiation
    }

    /**
     * Returns the number of words in the shortest valid transformation sequence
     * from beginWord to endWord, or 0 if no such sequence exists.
     *
     * @param beginWord starting word
     * @param endWord   target word
     * @param wordList  allowed intermediate words
     * @return length of the shortest transformation sequence, or 0 if unreachable
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

                char[] chars = currentWord.toCharArray();

                for (int pos = 0; pos < chars.length; pos++) {
                    char originalChar = chars[pos];

                    for (char c = 'a'; c <= 'z'; c++) {
                        if (c == originalChar) {
                            continue;
                        }

                        chars[pos] = c;
                        String candidate = new String(chars);

                        if (candidate.equals(endWord)) {
                            return level + 1;
                        }

                        if (wordSet.remove(candidate)) {
                            queue.offer(candidate);
                        }
                    }

                    chars[pos] = originalChar;
                }
            }

            level++;
        }

        return 0;
    }
}