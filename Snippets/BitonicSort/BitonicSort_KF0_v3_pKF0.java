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

    @Override
    public <T extends Comparable<T>> T[] sort(T[] array) {
        int length = array.length;
        if (length == 0) {
            return array;
        }

        int paddedSize = nextPowerOfTwo(length);
        T[] paddedArray = Arrays.copyOf(array, paddedSize);

        T maxValue = findMax(array);
        Arrays.fill(paddedArray, length, paddedSize, maxValue);

        bitonicSort(paddedArray, 0, paddedSize, Direction.ASCENDING);
        return Arrays.copyOf(paddedArray, length);
    }

    private <T extends Comparable<T>> void bitonicSort(
        T[] array,
        int startIndex,
        int length,
        Direction direction
    ) {
        if (length <= 1) {
            return;
        }

        int halfLength = length / 2;

        bitonicSort(array, startIndex, halfLength, Direction.ASCENDING);
        bitonicSort(array, startIndex + halfLength, halfLength, Direction.DESCENDING);

        bitonicMerge(array, startIndex, length, direction);
    }

    private <T extends Comparable<T>> void bitonicMerge(
        T[] array,
        int startIndex,
        int length,
        Direction direction
    ) {
        if (length <= 1) {
            return;
        }

        int halfLength = length / 2;
        BiPredicate<T, T> isInCorrectOrder = getOrderPredicate(direction);

        int middleIndex = startIndex + halfLength;
        for (int leftIndex = startIndex; leftIndex < middleIndex; leftIndex++) {
            int rightIndex = leftIndex + halfLength;
            if (!isInCorrectOrder.test(array[leftIndex], array[rightIndex])) {
                SortUtils.swap(array, leftIndex, rightIndex);
            }
        }

        bitonicMerge(array, startIndex, halfLength, direction);
        bitonicMerge(array, startIndex + halfLength, halfLength, direction);
    }

    private static <T extends Comparable<T>> BiPredicate<T, T> getOrderPredicate(Direction direction) {
        return direction == Direction.ASCENDING
            ? (first, second) -> first.compareTo(second) <= 0
            : (first, second) -> first.compareTo(second) >= 0;
    }

    private static int nextPowerOfTwo(int number) {
        if (number <= 0) {
            return 1;
        }

        if (isPowerOfTwo(number)) {
            return number;
        }

        int power = 1;
        while (power < number) {
            power <<= 1;
        }

        return power;
    }

    private static boolean isPowerOfTwo(int number) {
        return (number & (number - 1)) == 0;
    }

    private static <T extends Comparable<T>> T findMax(T[] array) {
        return Arrays.stream(array)
            .max(Comparable::compareTo)
            .orElseThrow();
    }
}