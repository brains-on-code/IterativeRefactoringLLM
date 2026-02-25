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
        Set<String> dictionary = new HashSet<>(wordList);

        if (!dictionary.contains(endWord)) {
            return 0;
        }

        Queue<String> wordsToVisit = new LinkedList<>();
        wordsToVisit.offer(beginWord);
        int transformationLength = 1;

        while (!wordsToVisit.isEmpty()) {
            int currentLevelSize = wordsToVisit.size();
            for (int i = 0; i < currentLevelSize; i++) {
                String currentWord = wordsToVisit.poll();
                char[] currentWordCharacters = currentWord.toCharArray();

                for (int charIndex = 0; charIndex < currentWordCharacters.length; charIndex++) {
                    char originalCharacter = currentWordCharacters[charIndex];

                    for (char replacementChar = 'a'; replacementChar <= 'z'; replacementChar++) {
                        if (currentWordCharacters[charIndex] == replacementChar) {
                            continue;
                        }

                        currentWordCharacters[charIndex] = replacementChar;
                        String transformedWord = new String(currentWordCharacters);

                        if (transformedWord.equals(endWord)) {
                            return transformationLength + 1;
                        }

                        if (dictionary.remove(transformedWord)) {
                            wordsToVisit.offer(transformedWord);
                        }
                    }

                    currentWordCharacters[charIndex] = originalCharacter;
                }
            }
            transformationLength++;
        }
        return 0;
    }
}