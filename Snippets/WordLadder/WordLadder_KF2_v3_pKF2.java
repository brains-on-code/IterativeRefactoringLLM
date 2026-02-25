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
        Set<String> remainingWords = new HashSet<>(wordList);

        // If the target word is not in the dictionary, no transformation is possible
        if (!remainingWords.contains(endWord)) {
            return 0;
        }

        Queue<String> queue = new LinkedList<>();
        queue.offer(beginWord);

        int currentLength = 1; // Number of words in the current transformation path

        while (!queue.isEmpty()) {
            int wordsAtCurrentLevel = queue.size();

            for (int i = 0; i < wordsAtCurrentLevel; i++) {
                String currentWord = queue.poll();
                if (currentWord == null) {
                    continue;
                }

                char[] currentChars = currentWord.toCharArray();

                // Try changing each character in the current word
                for (int position = 0; position < currentChars.length; position++) {
                    char originalChar = currentChars[position];

                    // Substitute with every possible lowercase letter
                    for (char candidateChar = 'a'; candidateChar <= 'z'; candidateChar++) {
                        if (candidateChar == originalChar) {
                            continue;
                        }

                        currentChars[position] = candidateChar;
                        String candidateWord = new String(currentChars);

                        // Found the target word; include it in the path length
                        if (candidateWord.equals(endWord)) {
                            return currentLength + 1;
                        }

                        // If candidate is in the remaining dictionary, visit it and remove from set
                        if (remainingWords.remove(candidateWord)) {
                            queue.offer(candidateWord);
                        }
                    }

                    // Restore original character before moving to the next position
                    currentChars[position] = originalChar;
                }
            }

            // Move to the next level of BFS (one more transformation step)
            currentLength++;
        }

        // No valid transformation sequence found
        return 0;
    }
}