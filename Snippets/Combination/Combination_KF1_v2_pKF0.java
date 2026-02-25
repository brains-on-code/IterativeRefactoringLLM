package com.thealgorithms.method2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.TreeSet;

/**
 * Utility class for generating combinations of elements.
 */
public final class Class1 {

    private Class1() {
        // Utility class; prevent instantiation
    }

    /**
     * Generates all combinations of the given length from the provided array.
     *
     * @param <T>        the type of elements
     * @param elements   the source array
     * @param combLength the desired combination length
     * @return a list of combinations, each represented as a {@link TreeSet}
     * @throws IllegalArgumentException if {@code combLength} is negative
     */
    public static <T> List<TreeSet<T>> method1(T[] elements, int combLength) {
        if (combLength < 0) {
            throw new IllegalArgumentException("The combination length cannot be negative.");
        }
        if (combLength == 0) {
            return Collections.emptyList();
        }
        if (elements == null || elements.length == 0 || combLength > elements.length) {
            return Collections.emptyList();
        }

        T[] sortedElements = elements.clone();
        Arrays.sort(sortedElements);

        List<TreeSet<T>> combinations = new ArrayList<>();
        generateCombinations(sortedElements, combLength, 0, new TreeSet<>(), combinations);
        return combinations;
    }

    /**
     * Recursively generates combinations.
     *
     * @param <T>          the type of elements
     * @param elements     sorted source array
     * @param combLength   desired combination length
     * @param startIndex   current index in the source array
     * @param currentCombo current partial combination
     * @param result       list collecting all combinations
     */
    private static <T> void generateCombinations(
            T[] elements,
            int combLength,
            int startIndex,
            TreeSet<T> currentCombo,
            List<TreeSet<T>> result
    ) {
        int remainingNeeded = combLength - currentCombo.size();
        int remainingAvailable = elements.length - startIndex;

        if (remainingNeeded > remainingAvailable) {
            return;
        }

        if (currentCombo.size() == combLength) {
            result.add(new TreeSet<>(currentCombo));
            return;
        }

        for (int i = startIndex; i < elements.length; i++) {
            T element = elements[i];
            currentCombo.add(element);
            generateCombinations(elements, combLength, i + 1, currentCombo, result);
            currentCombo.remove(element);
        }
    }
}