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
    }

    /**
     * Finds the shortest transformation sequence from beginWord to endWord.
     *
     * @param beginWord the starting word of the transformation sequence
     * @param endWord the target word of the transformation sequence
     * @param wordList a list of words that can be used in the transformation sequence
     * @return the number of words in the shortest transformation sequence, or 0 if no such sequence exists
     */
    public static int ladderLength(String beginWord, String endWord, Collection<String> wordList) {
        Set<String> remainingDictionaryWords = new HashSet<>(wordList);

        if (!remainingDictionaryWords.contains(endWord)) {
            return 0;
        }

        Queue<String> bfsQueue = new LinkedList<>();
        bfsQueue.offer(beginWord);
        int currentPathLength = 1;

        while (!bfsQueue.isEmpty()) {
            int levelSize = bfsQueue.size();

            for (int i = 0; i < levelSize; i++) {
                String currentWord = bfsQueue.poll();
                char[] currentWordChars = currentWord.toCharArray();

                for (int charIndex = 0; charIndex < currentWordChars.length; charIndex++) {
                    char originalChar = currentWordChars[charIndex];

                    for (char candidateChar = 'a'; candidateChar <= 'z'; candidateChar++) {
                        if (candidateChar == originalChar) {
                            continue;
                        }

                        currentWordChars[charIndex] = candidateChar;
                        String candidateWord = new String(currentWordChars);

                        if (candidateWord.equals(endWord)) {
                            return currentPathLength + 1;
                        }

                        if (remainingDictionaryWords.remove(candidateWord)) {
                            bfsQueue.offer(candidateWord);
                        }
                    }

                    currentWordChars[charIndex] = originalChar;
                }
            }

            currentPathLength++;
        }

        return 0;
    }
}