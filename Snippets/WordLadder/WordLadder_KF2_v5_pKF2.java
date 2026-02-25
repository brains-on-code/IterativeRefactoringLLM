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
     * Computes the length of the shortest transformation sequence from {@code beginWord}
     * to {@code endWord}, where:
     * <ul>
     *   <li>Only one letter can be changed at a time.</li>
     *   <li>Each intermediate word must exist in {@code wordList}.</li>
     * </ul>
     *
     * This uses a breadth-first search (BFS) over all valid one-letter transformations.
     *
     * @param beginWord the starting word
     * @param endWord   the target word
     * @param wordList  the collection of allowed intermediate words
     * @return the number of words in the shortest transformation sequence,
     *         or 0 if no such sequence exists
     */
    public static int ladderLength(String beginWord, String endWord, Collection<String> wordList) {
        Set<String> remainingWords = new HashSet<>(wordList);

        // If the end word is not in the dictionary, no transformation is possible.
        if (!remainingWords.contains(endWord)) {
            return 0;
        }

        Queue<String> queue = new LinkedList<>();
        queue.offer(beginWord);

        int currentLength = 1; // Length of the transformation sequence so far

        while (!queue.isEmpty()) {
            int wordsAtCurrentLevel = queue.size();

            // Process all words at the current BFS level
            for (int i = 0; i < wordsAtCurrentLevel; i++) {
                String currentWord = queue.poll();
                if (currentWord == null) {
                    continue;
                }

                char[] currentChars = currentWord.toCharArray();

                // Try changing each character in the word
                for (int position = 0; position < currentChars.length; position++) {
                    char originalChar = currentChars[position];

                    // Try all possible lowercase letters at this position
                    for (char candidateChar = 'a'; candidateChar <= 'z'; candidateChar++) {
                        if (candidateChar == originalChar) {
                            continue;
                        }

                        currentChars[position] = candidateChar;
                        String candidateWord = new String(currentChars);

                        // Found the target word; return the sequence length
                        if (candidateWord.equals(endWord)) {
                            return currentLength + 1;
                        }

                        // If this candidate is in the remaining dictionary, visit it and remove it
                        if (remainingWords.remove(candidateWord)) {
                            queue.offer(candidateWord);
                        }
                    }

                    // Restore original character before moving to the next position
                    currentChars[position] = originalChar;
                }
            }

            // Move to the next level in BFS
            currentLength++;
        }

        // No valid transformation sequence found
        return 0;
    }
}