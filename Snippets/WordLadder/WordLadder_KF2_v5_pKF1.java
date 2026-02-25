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
        Set<String> unusedWords = new HashSet<>(wordList);

        if (!unusedWords.contains(endWord)) {
            return 0;
        }

        Queue<String> queue = new LinkedList<>();
        queue.offer(beginWord);
        int steps = 1;

        while (!queue.isEmpty()) {
            int wordsInCurrentLevel = queue.size();

            for (int i = 0; i < wordsInCurrentLevel; i++) {
                String currentWord = queue.poll();
                char[] currentChars = currentWord.toCharArray();

                for (int charIndex = 0; charIndex < currentChars.length; charIndex++) {
                    char originalChar = currentChars[charIndex];

                    for (char candidateChar = 'a'; candidateChar <= 'z'; candidateChar++) {
                        if (currentChars[charIndex] == candidateChar) {
                            continue;
                        }

                        currentChars[charIndex] = candidateChar;
                        String nextWord = new String(currentChars);

                        if (nextWord.equals(endWord)) {
                            return steps + 1;
                        }

                        if (unusedWords.remove(nextWord)) {
                            queue.offer(nextWord);
                        }
                    }

                    currentChars[charIndex] = originalChar;
                }
            }

            steps++;
        }

        return 0;
    }
}