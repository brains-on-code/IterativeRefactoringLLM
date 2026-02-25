package com.thealgorithms.strings;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

/**
 * Utility class to find the shortest transformation sequence (word ladder)
 * from a beginWord to an endWord using a dictionary of words.
 * Each adjacent pair of words in the sequence must differ by exactly one letter.
 */
public final class WordLadder {

    private WordLadder() {
        // Prevent instantiation
    }

    /**
     * Finds the length of the shortest transformation sequence from beginWord to endWord.
     *
     * @param beginWord the starting word of the transformation sequence
     * @param endWord   the target word of the transformation sequence
     * @param wordList  a collection of words that can be used in the transformation sequence
     * @return the number of words in the shortest transformation sequence, or 0 if no such sequence exists
     */
    public static int ladderLength(String beginWord, String endWord, Collection<String> wordList) {
        if (beginWord == null || endWord == null || wordList == null) {
            return 0;
        }

        Set<String> wordSet = new HashSet<>(wordList);
        if (!wordSet.contains(endWord)) {
            return 0;
        }

        Queue<String> queue = new LinkedList<>();
        queue.offer(beginWord);

        int level = 1;

        while (!queue.isEmpty()) {
            int wordsInCurrentLevel = queue.size();

            for (int i = 0; i < wordsInCurrentLevel; i++) {
                String currentWord = queue.poll();
                if (currentWord == null) {
                    continue;
                }

                if (tryTransformWord(currentWord, endWord, wordSet, queue)) {
                    return level + 1;
                }
            }

            level++;
        }

        return 0;
    }

    /**
     * Attempts all one-letter transformations of the given word.
     *
     * @param currentWord the word to transform
     * @param endWord     the target word
     * @param wordSet     the remaining valid words
     * @param queue       the BFS queue to add new words to
     * @return true if endWord is found during transformation, false otherwise
     */
    private static boolean tryTransformWord(
            String currentWord,
            String endWord,
            Set<String> wordSet,
            Queue<String> queue
    ) {
        char[] chars = currentWord.toCharArray();

        for (int position = 0; position < chars.length; position++) {
            char originalChar = chars[position];

            for (char candidateChar = 'a'; candidateChar <= 'z'; candidateChar++) {
                if (candidateChar == originalChar) {
                    continue;
                }

                chars[position] = candidateChar;
                String transformedWord = new String(chars);

                if (transformedWord.equals(endWord)) {
                    return true;
                }

                if (wordSet.remove(transformedWord)) {
                    queue.offer(transformedWord);
                }
            }

            chars[position] = originalChar;
        }

        return false;
    }
}