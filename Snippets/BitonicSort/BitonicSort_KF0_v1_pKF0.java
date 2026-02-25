package com.thealgorithms.sorts;

import java.util.Arrays;
import java.util.function.BiPredicate;

/**
 * BitonicSort class implements the SortAlgorithm interface using the bitonic sort technique.
 */
public class BitonicSort implements SortAlgorithm {

    private enum Direction {
        ASCENDING,
        DESCENDING
    }

    /**
     * Sorts the given array using the Bitonic Sort algorithm.
     *
     * @param <T>   the type of elements in the array, which must implement the Comparable interface
     * @param array the array to be sorted
     * @return the sorted array
     */
    @Override
    public <T extends Comparable<T>> T[] sort(T[] array) {
        if (array.length == 0) {
            return array;
        }

        int paddedSize = nextPowerOfTwo(array.length);
        T[] paddedArray = Arrays.copyOf(array, paddedSize);

        T maxValue = max(array);
        Arrays.fill(paddedArray, array.length, paddedSize, maxValue);

        bitonicSort(paddedArray, 0, paddedSize, Direction.ASCENDING);
        return Arrays.copyOf(paddedArray, array.length);
    }

    private <T extends Comparable<T>> void bitonicSort(
        T[] array,
        int low,
        int length,
        Direction direction
    ) {
        if (length <= 1) {
            return;
        }

        int half = length / 2;

        bitonicSort(array, low, half, Direction.ASCENDING);
        bitonicSort(array, low + half, length - half, Direction.DESCENDING);

        bitonicMerge(array, low, length, direction);
    }

    /**
     * Merges the bitonic sequence in the specified direction.
     *
     * @param <T>       the type of elements in the array, which must be Comparable
     * @param array     the array containing the bitonic sequence to be merged
     * @param low       the starting index of the sequence to be merged
     * @param length    the number of elements in the sequence to be merged
     * @param direction the direction of sorting
     */
    private <T extends Comparable<T>> void bitonicMerge(
        T[] array,
        int low,
        int length,
        Direction direction
    ) {
        if (length <= 1) {
            return;
        }

        int half = length / 2;
        BiPredicate<T, T> inCorrectOrder = getOrderPredicate(direction);

        for (int i = low; i < low + half; i++) {
            if (!inCorrectOrder.test(array[i], array[i + half])) {
                SortUtils.swap(array, i, i + half);
            }
        }

        bitonicMerge(array, low, half, direction);
        bitonicMerge(array, low + half, length - half, direction);
    }

    private static <T extends Comparable<T>> BiPredicate<T, T> getOrderPredicate(Direction direction) {
        return direction == Direction.ASCENDING
            ? (a, b) -> a.compareTo(b) <= 0
            : (a, b) -> a.compareTo(b) >= 0;
    }

    /**
     * Finds the next power of two greater than or equal to the given number.
     *
     * @param n the number
     * @return the next power of two
     */
    private static int nextPowerOfTwo(int n) {
        if (n <= 0) {
            return 1;
        }

        if ((n & (n - 1)) == 0) {
            return n;
        }

        int power = 1;
        while (power < n) {
            power <<= 1;
        }

        return power;
    }

    /**
     * Finds the maximum element in the given array.
     *
     * @param <T>   the type of elements in the array, which must implement the Comparable interface
     * @param array the array to be searched
     * @return the maximum element in the array
     */
    private static <T extends Comparable<T>> T max(T[] array) {
        return Arrays.stream(array).max(Comparable::compareTo).orElseThrow();
    }
}