package com.thealgorithms.strings;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public final class WordLadder {

    private WordLadder() {
    }

    public static int ladderLength(String startWord, String targetWord, Collection<String> dictionary) {
        Set<String> remainingWords = new HashSet<>(dictionary);

        if (!remainingWords.contains(targetWord)) {
            return 0;
        }

        Queue<String> wordsToVisit = new LinkedList<>();
        wordsToVisit.offer(startWord);
        int transformationLength = 1;

        while (!wordsToVisit.isEmpty()) {
            int currentLevelSize = wordsToVisit.size();

            for (int i = 0; i < currentLevelSize; i++) {
                String currentWord = wordsToVisit.poll();
                char[] currentWordCharacters = currentWord.toCharArray();

                for (int position = 0; position < currentWordCharacters.length; position++) {
                    char originalCharacter = currentWordCharacters[position];

                    for (char candidateCharacter = 'a'; candidateCharacter <= 'z'; candidateCharacter++) {
                        if (currentWordCharacters[position] == candidateCharacter) {
                            continue;
                        }

                        currentWordCharacters[position] = candidateCharacter;
                        String candidateWord = new String(currentWordCharacters);

                        if (candidateWord.equals(targetWord)) {
                            return transformationLength + 1;
                        }

                        if (remainingWords.remove(candidateWord)) {
                            wordsToVisit.offer(candidateWord);
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