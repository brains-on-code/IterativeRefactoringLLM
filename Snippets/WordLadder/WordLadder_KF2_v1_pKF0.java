package com.thealgorithms.strings;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public final class WordLadder {

    private WordLadder() {
        // Utility class; prevent instantiation
    }

    public static int ladderLength(String beginWord, String endWord, Collection<String> wordList) {
        if (beginWord == null || endWord == null || wordList == null) {
            return 0;
        }

        Set<String> wordSet = new HashSet<>(wordList);
        if (!wordSet.contains(endWord)) {
            return 0;
        }

        Queue<String> queue = new LinkedList<>();
        queue.offer(beginWord);

        int level = 1;

        while (!queue.isEmpty()) {
            int currentLevelSize = queue.size();

            for (int i = 0; i < currentLevelSize; i++) {
                String currentWord = queue.poll();
                if (currentWord == null) {
                    continue;
                }

                char[] currentChars = currentWord.toCharArray();

                for (int pos = 0; pos < currentChars.length; pos++) {
                    char originalChar = currentChars[pos];

                    for (char c = 'a'; c <= 'z'; c++) {
                        if (c == originalChar) {
                            continue;
                        }

                        currentChars[pos] = c;
                        String transformedWord = new String(currentChars);

                        if (transformedWord.equals(endWord)) {
                            return level + 1;
                        }

                        if (wordSet.remove(transformedWord)) {
                            queue.offer(transformedWord);
                        }
                    }

                    currentChars[pos] = originalChar;
                }
            }

            level++;
        }

        return 0;
    }
}