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
        Set<String> wordSet = new HashSet<>(wordList);

        if (!wordSet.contains(endWord)) {
            return 0;
        }

        Queue<String> queue = new LinkedList<>();
        queue.offer(beginWord);
        int level = 1;

        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                String currentWord = queue.poll();
                char[] currentWordChars = currentWord.toCharArray();
                for (int j = 0; j < currentWordChars.length; j++) {
                    char originalChar = currentWordChars[j];
                    for (char c = 'a'; c <= 'z'; c++) {
                        if (currentWordChars[j] == c) {
                            continue;
                        }
                        currentWordChars[j] = c;
                        String newWord = new String(currentWordChars);

                        if (newWord.equals(endWord)) {
                            return level + 1;
                        }
                        if (wordSet.remove(newWord)) {
                            queue.offer(newWord);
                        }
                    }
                    currentWordChars[j] = originalChar;
                }
            }
            level++;
        }
        return 0;
    }
}
