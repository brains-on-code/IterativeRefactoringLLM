package com.thealgorithms.strings;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

/**
 * Computes the length of the shortest transformation sequence from a start word
 * to a target word, changing one letter at a time such that each intermediate
 * word exists in the given dictionary.
 */
public final class WordLadder {

    private WordLadder() {
    }

    /**
     * Returns the length of the shortest transformation sequence from
     * {@code startWord} to {@code targetWord}, or 0 if no such sequence exists.
     *
     * @param startWord the word to start from
     * @param targetWord the word to transform to
     * @param dictionary the collection of allowed intermediate words
     * @return the length of the shortest transformation sequence, or 0 if none
     */
    public static int shortestTransformationLength(String startWord, String targetWord, Collection<String> dictionary) {
        Set<String> remainingWords = new HashSet<>(dictionary);

        if (!remainingWords.contains(targetWord)) {
            return 0;
        }

        Queue<String> wordQueue = new LinkedList<>();
        wordQueue.offer(startWord);
        int transformationLength = 1;

        while (!wordQueue.isEmpty()) {
            int wordsAtCurrentLevel = wordQueue.size();

            for (int i = 0; i < wordsAtCurrentLevel; i++) {
                String currentWord = wordQueue.poll();
                char[] currentWordChars = currentWord.toCharArray();

                for (int charIndex = 0; charIndex < currentWordChars.length; charIndex++) {
                    char originalChar = currentWordChars[charIndex];

                    for (char candidateChar = 'a'; candidateChar <= 'z'; candidateChar++) {
                        if (currentWordChars[charIndex] == candidateChar) {
                            continue;
                        }

                        currentWordChars[charIndex] = candidateChar;
                        String candidateWord = new String(currentWordChars);

                        if (candidateWord.equals(targetWord)) {
                            return transformationLength + 1;
                        }

                        if (remainingWords.remove(candidateWord)) {
                            wordQueue.offer(candidateWord);
                        }
                    }

                    currentWordChars[charIndex] = originalChar;
                }
            }

            transformationLength++;
        }

        return 0;
    }
}