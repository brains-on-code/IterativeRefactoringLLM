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
        Set<String> remainingWords = new HashSet<>(wordList);

        if (!remainingWords.contains(endWord)) {
            return 0;
        }

        Queue<String> bfsQueue = new LinkedList<>();
        bfsQueue.offer(beginWord);
        int currentPathLength = 1;

        while (!bfsQueue.isEmpty()) {
            int levelSize = bfsQueue.size();

            for (int levelIndex = 0; levelIndex < levelSize; levelIndex++) {
                String currentWord = bfsQueue.poll();
                char[] currentWordChars = currentWord.toCharArray();

                for (int charPosition = 0; charPosition < currentWordChars.length; charPosition++) {
                    char originalChar = currentWordChars[charPosition];

                    for (char candidateChar = 'a'; candidateChar <= 'z'; candidateChar++) {
                        if (candidateChar == originalChar) {
                            continue;
                        }

                        currentWordChars[charPosition] = candidateChar;
                        String candidateWord = new String(currentWordChars);

                        if (candidateWord.equals(endWord)) {
                            return currentPathLength + 1;
                        }

                        if (remainingWords.remove(candidateWord)) {
                            bfsQueue.offer(candidateWord);
                        }
                    }

                    currentWordChars[charPosition] = originalChar;
                }
            }

            currentPathLength++;
        }

        return 0;
    }
}