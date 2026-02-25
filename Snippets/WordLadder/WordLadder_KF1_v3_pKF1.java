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
        Set<String> unusedWords = new HashSet<>(dictionary);

        if (!unusedWords.contains(targetWord)) {
            return 0;
        }

        Queue<String> bfsQueue = new LinkedList<>();
        bfsQueue.offer(startWord);
        int currentPathLength = 1;

        while (!bfsQueue.isEmpty()) {
            int levelSize = bfsQueue.size();

            for (int i = 0; i < levelSize; i++) {
                String currentWord = bfsQueue.poll();
                char[] currentWordCharacters = currentWord.toCharArray();

                for (int position = 0; position < currentWordCharacters.length; position++) {
                    char originalCharacter = currentWordCharacters[position];

                    for (char replacementCharacter = 'a'; replacementCharacter <= 'z'; replacementCharacter++) {
                        if (currentWordCharacters[position] == replacementCharacter) {
                            continue;
                        }

                        currentWordCharacters[position] = replacementCharacter;
                        String transformedWord = new String(currentWordCharacters);

                        if (transformedWord.equals(targetWord)) {
                            return currentPathLength + 1;
                        }

                        if (unusedWords.remove(transformedWord)) {
                            bfsQueue.offer(transformedWord);
                        }
                    }

                    currentWordCharacters[position] = originalCharacter;
                }
            }

            currentPathLength++;
        }

        return 0;
    }
}