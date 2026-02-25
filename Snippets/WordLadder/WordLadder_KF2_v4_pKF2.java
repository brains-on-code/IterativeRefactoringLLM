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
     * Returns the length of the shortest transformation sequence from {@code beginWord}
     * to {@code endWord}, where:
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

        if (!remainingWords.contains(endWord)) {
            return 0;
        }

        Queue<String> queue = new LinkedList<>();
        queue.offer(beginWord);

        int currentLength = 1;

        while (!queue.isEmpty()) {
            int wordsAtCurrentLevel = queue.size();

            for (int i = 0; i < wordsAtCurrentLevel; i++) {
                String currentWord = queue.poll();
                if (currentWord == null) {
                    continue;
                }

                char[] currentChars = currentWord.toCharArray();

                for (int position = 0; position < currentChars.length; position++) {
                    char originalChar = currentChars[position];

                    for (char candidateChar = 'a'; candidateChar <= 'z'; candidateChar++) {
                        if (candidateChar == originalChar) {
                            continue;
                        }

                        currentChars[position] = candidateChar;
                        String candidateWord = new String(currentChars);

                        if (candidateWord.equals(endWord)) {
                            return currentLength + 1;
                        }

                        if (remainingWords.remove(candidateWord)) {
                            queue.offer(candidateWord);
                        }
                    }

                    currentChars[position] = originalChar;
                }
            }

            currentLength++;
        }

        return 0;
    }
}