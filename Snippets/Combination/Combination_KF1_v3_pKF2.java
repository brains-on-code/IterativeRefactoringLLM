package com.thealgorithms.method2;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeSet;

/**
 * Utility class for generating combinations of elements.
 */
public final class Class1 {

    private Class1() {
        // Prevent instantiation
    }

    /**
     * Generates all combinations of the given length from the provided array.
     *
     * @param <T>        the type of elements in the input array
     * @param elements   the input array
     * @param combLength the desired combination length (must be >= 0)
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

        T[] sortedElements = elements.clone();
        Arrays.sort(sortedElements);

        List<TreeSet<T>> combinations = new LinkedList<>();
        method2(sortedElements, combLength, 0, new TreeSet<>(), combinations);
        return combinations;
    }

    /**
     * Recursively builds all combinations of a given length.
     *
     * @param <T>          the type of elements
     * @param elements     sorted input array
     * @param combLength   desired combination length
     * @param startIndex   current index in {@code elements} to consider
     * @param currentCombo current partial combination
     * @param result       list collecting all generated combinations
     */
    private static <T> void method2(
            T[] elements,
            int combLength,
            int startIndex,
            TreeSet<T> currentCombo,
            List<TreeSet<T>> result
    ) {
        int remainingSlots = combLength - currentCombo.size();
        int remainingElements = elements.length - startIndex;

        // Stop if there are not enough elements left to complete a combination
        if (remainingElements < remainingSlots) {
            return;
        }

        // Base case: only one more element needed
        if (remainingSlots == 1) {
            for (int i = startIndex; i < elements.length; i++) {
                currentCombo.add(elements[i]);
                result.add(new TreeSet<>(currentCombo));
                currentCombo.remove(elements[i]);
            }
            return;
        }

        // Recursive case: try each possible next element
        for (int i = startIndex; i < elements.length; i++) {
            currentCombo.add(elements[i]);
            method2(elements, combLength, i + 1, currentCombo, result);
            currentCombo.remove(elements[i]);
        }
    }
}