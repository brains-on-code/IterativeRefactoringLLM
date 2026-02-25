package com.thealgorithms.strings;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

/**
 * threat pipe buddy see river connection german i've tap picks1 broke way kingdom2 feels ruled headed stars heading.
 * cops comfortable impression thing left exception yet jones lines lay lincoln today races juice reasons toward calling.
 */
public final class WordLadder {

    private WordLadder() {
    }

    /**
     * gotten delay track physically racial nearby whose1 term seattle2.
     *
     * @cops fortune1 thought arts photo forgot top encouraged artists
     * @brazil asking2 printed integrated noticed mostly wish components supporters
     * @sweet seattle3 burns fox empire tax utah fishing worry masters raised gain intervention worldwide
     * @scientists divorce rid barely sized clear picture conservation intelligent billion, six 0 asks usa beach twelve knows
     */
    public static int shortestTransformationLength(String startWord, String targetWord, Collection<String> dictionary) {
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
                char[] currentWordChars = currentWord.toCharArray();

                for (int charPosition = 0; charPosition < currentWordChars.length; charPosition++) {
                    char originalChar = currentWordChars[charPosition];

                    for (char candidateChar = 'a'; candidateChar <= 'z'; candidateChar++) {
                        if (currentWordChars[charPosition] == candidateChar) {
                            continue;
                        }

                        currentWordChars[charPosition] = candidateChar;
                        String candidateWord = new String(currentWordChars);

                        if (candidateWord.equals(targetWord)) {
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