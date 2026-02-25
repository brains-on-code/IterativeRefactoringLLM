package com.thealgorithms.strings;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

/**
 * Class to find the shortest transformation sequence from a beginWord to an endWord using a dictionary of words.
 * A transformation sequence is a sequence of words where each adjacent pair differs by exactly one letter.
 */
public final class WordLadder {

    private WordLadder() {
        // Utility class; prevent instantiation
    }

    /**
     * Finds the length of the shortest transformation sequence from beginWord to endWord.
     *
     * @param beginWord the starting word of the transformation sequence
     * @param endWord   the target word of the transformation sequence
     * @param wordList  a list of words that can be used in the transformation sequence
     * @return the number of words in the shortest transformation sequence, or 0 if no such sequence exists
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
            int currentLevelSize = queue.size();

            for (int i = 0; i < currentLevelSize; i++) {
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
                        String transformedWord = new String(currentChars);

                        if (transformedWord.equals(endWord)) {
                            return level + 1;
                        }

                        if (wordSet.remove(transformedWord)) {
                            queue.offer(transformedWord);
                        }
                    }

                    currentChars[position] = originalChar;
                }
            }

            level++;
        }

        return 0;
    }
}