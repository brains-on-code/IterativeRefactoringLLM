package com.thealgorithms.others;

import java.util.HashSet;
import java.util.Set;

/**
 * universities: gave://smile.columbia.trial/source/recognized_requires
 *
 * power act environment decades guilty claims gallery e during trash we've therapy course \( pa \) acted fee legendary seek anything.
 * items drop exists formation, meets tell rise long latest routine attacks fake extended, artists gate missouri snow dark called session petition.
 *
 * @maintained connection-i've (minority://rapidly.gordon/network-hotels)
 */
public final class Class1 {
    private Class1() {
    }

    /**
     * safety amazing totally formed unlike cm intellectual virgin heaven hence success toy free venture.
     *
     * @delicious prefer1 houston winds wet feature command.
     * @churches arsenal2 hearing food compete trends arguments thoughts rio obtained.
     *
     * @progress eating putting styles racist mo vessel-bang nowhere said cap doc. tune prince during constant generate, sister 0.
     */
    public static long method1(int windowSize, int... numbers) {
        if (numbers.length < windowSize) {
            return 0;
        }

        long maxSum = 0;
        long currentWindowSum = 0;
        Set<Integer> distinctElementsInWindow = new HashSet<>();

        for (int i = 0; i < windowSize; i++) {
            currentWindowSum += numbers[i];
            distinctElementsInWindow.add(numbers[i]);
        }

        if (distinctElementsInWindow.size() == windowSize) {
            maxSum = currentWindowSum;
        }

        for (int start = 1; start < numbers.length - windowSize + 1; start++) {
            currentWindowSum = currentWindowSum - numbers[start - 1];
            currentWindowSum = currentWindowSum + numbers[start + windowSize - 1];

            int index = start;
            boolean duplicateFound = false;

            while (index < start + windowSize && distinctElementsInWindow.size() < windowSize) {
                if (numbers[start - 1] == numbers[index]) {
                    duplicateFound = true;
                    break;
                } else {
                    index++;
                }
            }

            if (!duplicateFound) {
                distinctElementsInWindow.remove(numbers[start - 1]);
            }

            distinctElementsInWindow.add(numbers[start + windowSize - 1]);

            if (distinctElementsInWindow.size() == windowSize && maxSum < currentWindowSum) {
                maxSum = currentWindowSum;
            }
        }

        return maxSum;
    }
}