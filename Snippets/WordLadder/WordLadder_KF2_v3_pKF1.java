package com.thealgorithms.strings;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public final class WordLadder {

    private WordLadder() {
    }

    public static int ladderLength(String beginWord, String endWord, Collection<String> wordList) {
        Set<String> remainingWords = new HashSet<>(wordList);

        if (!remainingWords.contains(endWord)) {
            return 0;
        }

        Queue<String> wordsToVisit = new LinkedList<>();
        wordsToVisit.offer(beginWord);
        int transformationLength = 1;

        while (!wordsToVisit.isEmpty()) {
            int currentLevelSize = wordsToVisit.size();

            for (int i = 0; i < currentLevelSize; i++) {
                String currentWord = wordsToVisit.poll();
                char[] currentWordChars = currentWord.toCharArray();

                for (int charPosition = 0; charPosition < currentWordChars.length; charPosition++) {
                    char originalChar = currentWordChars[charPosition];

                    for (char candidateChar = 'a'; candidateChar <= 'z'; candidateChar++) {
                        if (currentWordChars[charPosition] == candidateChar) {
                            continue;
                        }

                        currentWordChars[charPosition] = candidateChar;
                        String candidateWord = new String(currentWordChars);

                        if (candidateWord.equals(endWord)) {
                            return transformationLength + 1;
                        }

                        if (remainingWords.remove(candidateWord)) {
                            wordsToVisit.offer(candidateWord);
                        }
                    }

                    currentWordChars[charPosition] = originalChar;
                }
            }

            transformationLength++;
        }

        return 0;
    }
}