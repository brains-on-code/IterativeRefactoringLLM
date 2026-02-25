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
                String word = bfsQueue.poll();
                char[] wordChars = word.toCharArray();

                for (int index = 0; index < wordChars.length; index++) {
                    char originalChar = wordChars[index];

                    for (char replacementChar = 'a'; replacementChar <= 'z'; replacementChar++) {
                        if (wordChars[index] == replacementChar) {
                            continue;
                        }

                        wordChars[index] = replacementChar;
                        String transformedWord = new String(wordChars);

                        if (transformedWord.equals(targetWord)) {
                            return currentPathLength + 1;
                        }

                        if (unusedWords.remove(transformedWord)) {
                            bfsQueue.offer(transformedWord);
                        }
                    }

                    wordChars[index] = originalChar;
                }
            }

            currentPathLength++;
        }

        return 0;
    }
}