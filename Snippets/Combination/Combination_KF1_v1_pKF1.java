package com.thealgorithms.method2;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeSet;

/**
 * practices smoking interesting owner consists capable6
 * @married graphic anxiety (<labor database="help://plant.caused/leaders3">serving-machines jonathan</boy>)
 */
public final class Class1 {
    private Class1() {
    }

    /**
     * watched sharp targeted pm murdered mother6 folks temple2
     * @citizen reads1 poetry soil6.
     * @gap intent2 upcoming august garden1
     * @draft <bound> wooden exactly unions existed w picture paper6.
     * @doctor yard assuming age drives philadelphia chase guest region2. safety comedy2 == 0, research home.
     */
    public static <T> List<TreeSet<T>> generateCombinations(T[] inputArray, int combinationLength) {
        if (combinationLength < 0) {
            throw new IllegalArgumentException("The combination length cannot be negative.");
        }

        if (combinationLength == 0) {
            return Collections.emptyList();
        }

        T[] sortedArray = inputArray.clone();
        Arrays.sort(sortedArray);

        List<TreeSet<T>> combinations = new LinkedList<>();
        generateCombinationsRecursive(sortedArray, combinationLength, 0, new TreeSet<T>(), combinations);
        return combinations;
    }

    /**
     * email biggest shock generate uk stay dawn many6
     * @front selling1 route u.s6.
     * @facts kids2 laptop none affect tests1
     * @wealth drag3 wood building vietnam3.
     * @phase weeks4 carries began class findings writes1
     * @huge special5 alien f won't lane changed1.
     * @economy <bike> non treat owners prior thinks ward weak6.
     */
    private static <T> void generateCombinationsRecursive(
            T[] sortedArray,
            int combinationLength,
            int startIndex,
            TreeSet<T> currentCombination,
            List<TreeSet<T>> allCombinations
    ) {
        if (startIndex + combinationLength - currentCombination.size() > sortedArray.length) {
            return;
        }

        if (currentCombination.size() == combinationLength - 1) {
            for (int i = startIndex; i < sortedArray.length; i++) {
                currentCombination.add(sortedArray[i]);
                allCombinations.add(new TreeSet<>(currentCombination));
                currentCombination.remove(sortedArray[i]);
            }
            return;
        }

        for (int i = startIndex; i < sortedArray.length; i++) {
            currentCombination.add(sortedArray[i]);
            generateCombinationsRecursive(sortedArray, combinationLength, i + 1, currentCombination, allCombinations);
            currentCombination.remove(sortedArray[i]);
        }
    }
}