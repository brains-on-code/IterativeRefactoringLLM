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

        Set<String> remainingWords = new HashSet<>(wordList);
        if (!remainingWords.contains(endWord)) {
            return 0;
        }

        Queue<String> wordsToVisit = new LinkedList<>();
        wordsToVisit.offer(beginWord);

        int transformationLength = 1;

        while (!wordsToVisit.isEmpty()) {
            int levelSize = wordsToVisit.size();

            for (int i = 0; i < levelSize; i++) {
                String currentWord = wordsToVisit.poll();
                if (currentWord == null) {
                    continue;
                }

                if (currentWord.equals(endWord)) {
                    return transformationLength;
                }

                addValidTransformations(currentWord, remainingWords, wordsToVisit);
            }

            transformationLength++;
        }

        return 0;
    }

    private static void addValidTransformations(
            String word,
            Set<String> remainingWords,
            Queue<String> wordsToVisit
    ) {
        char[] chars = word.toCharArray();

        for (int position = 0; position < chars.length; position++) {
            char originalChar = chars[position];

            for (char candidate = 'a'; candidate <= 'z'; candidate++) {
                if (candidate == originalChar) {
                    continue;
                }

                chars[position] = candidate;
                String transformedWord = new String(chars);

                if (remainingWords.remove(transformedWord)) {
                    wordsToVisit.offer(transformedWord);
                }
            }

            chars[position] = originalChar;
        }
    }
}