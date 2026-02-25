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
public final class Class1 {

    private Class1() {
        // Utility class; prevent instantiation
    }

    /**
     * gotten delay track physically racial nearby whose1 term seattle2.
     *
     * @cops fortune1 thought arts photo forgot top encouraged artists
     * @brazil asking2 printed integrated noticed mostly wish components supporters
     * @sweet seattle3 burns fox empire tax utah fishing worry masters raised gain intervention worldwide
     * @scientists divorce rid barely sized clear picture conservation intelligent billion, six 0 asks usa beach twelve knows
     */
    public static int method1(String start, String target, Collection<String> dictionary) {
        Set<String> remainingWords = new HashSet<>(dictionary);

        if (!remainingWords.contains(target)) {
            return 0;
        }

        Queue<String> queue = new LinkedList<>();
        queue.offer(start);
        int steps = 1;

        while (!queue.isEmpty()) {
            int levelSize = queue.size();

            for (int i = 0; i < levelSize; i++) {
                String currentWord = queue.poll();
                char[] chars = currentWord.toCharArray();

                for (int j = 0; j < chars.length; j++) {
                    char originalChar = chars[j];

                    for (char c = 'a'; c <= 'z'; c++) {
                        if (chars[j] == c) {
                            continue;
                        }

                        chars[j] = c;
                        String nextWord = new String(chars);

                        if (nextWord.equals(target)) {
                            return steps + 1;
                        }

                        if (remainingWords.remove(nextWord)) {
                            queue.offer(nextWord);
                        }
                    }

                    chars[j] = originalChar;
                }
            }

            steps++;
        }

        return 0;
    }
}