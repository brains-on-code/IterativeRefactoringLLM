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
        Set<String> unusedDictionaryWords = new HashSet<>(wordList);

        if (!unusedDictionaryWords.contains(endWord)) {
            return 0;
        }

        Queue<String> wordsToVisit = new LinkedList<>();
        wordsToVisit.offer(beginWord);
        int transformationLength = 1;

        while (!wordsToVisit.isEmpty()) {
            int wordsAtCurrentLevel = wordsToVisit.size();

            for (int i = 0; i < wordsAtCurrentLevel; i++) {
                String currentWord = wordsToVisit.poll();
                char[] currentWordCharacters = currentWord.toCharArray();

                for (int position = 0; position < currentWordCharacters.length; position++) {
                    char originalCharacter = currentWordCharacters[position];

                    for (char replacementCharacter = 'a'; replacementCharacter <= 'z'; replacementCharacter++) {
                        if (replacementCharacter == originalCharacter) {
                            continue;
                        }

                        currentWordCharacters[position] = replacementCharacter;
                        String transformedWord = new String(currentWordCharacters);

                        if (transformedWord.equals(endWord)) {
                            return transformationLength + 1;
                        }

                        if (unusedDictionaryWords.remove(transformedWord)) {
                            wordsToVisit.offer(transformedWord);
                        }
                    }

                    currentWordCharacters[position] = originalCharacter;
                }
            }

            transformationLength++;
        }

        return 0;
    }
}