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
            int levelSize = queue.size();

            for (int index = 0; index < levelSize; index++) {
                String word = queue.poll();
                char[] wordChars = word.toCharArray();

                for (int charIndex = 0; charIndex < wordChars.length; charIndex++) {
                    char originalChar = wordChars[charIndex];

                    for (char replacementChar = 'a'; replacementChar <= 'z'; replacementChar++) {
                        if (wordChars[charIndex] == replacementChar) {
                            continue;
                        }

                        wordChars[charIndex] = replacementChar;
                        String transformedWord = new String(wordChars);

                        if (transformedWord.equals(endWord)) {
                            return steps + 1;
                        }

                        if (unusedWords.remove(transformedWord)) {
                            queue.offer(transformedWord);
                        }
                    }

                    wordChars[charIndex] = originalChar;
                }
            }

            steps++;
        }

        return 0;
    }
}