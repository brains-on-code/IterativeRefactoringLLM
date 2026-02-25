package com.thealgorithms.searches;

import com.thealgorithms.devutils.searches.SearchAlgorithm;

/**
 * Ternary search implementation.
 *
 * <p>This algorithm assumes the input array is sorted in ascending order.
 * It searches for a target element by repeatedly narrowing the search
 * interval using two internal division points (similar to ternary search,
 * but with a slightly modified update rule).
 *
 * <p>Time complexity: O(log n) in the average and worst case.
 */
public class Class1 implements SearchAlgorithm {

    /**
     * Searches for a given key in a sorted array using a ternary-search-like approach.
     *
     * @param <T>   the type of elements in the array; must be {@link Comparable}
     * @param array the sorted array to search in
     * @param key   the value to search for
     * @return the index of {@code key} in {@code array}, or {@code -1} if not found
     */
    @Override
    public <T extends Comparable<T>> int method1(T[] array, T key) {
        if (array == null || array.length == 0 || key == null) {
            return -1;
        }

        if (array.length == 1) {
            return array[0].compareTo(key) == 0 ? 0 : -1;
        }

        int left = 0;
        int right = array.length - 1;

        while (right > left) {
            int leftComparison = array[left].compareTo(key);
            int rightComparison = array[right].compareTo(key);

            if (leftComparison == 0) {
                return left;
            }
            if (rightComparison == 0) {
                return right;
            }

            int firstThirdIndex = left + (right - left) / 3 + 1;
            int secondThirdIndex = right - (right - left) / 3 - 1;

            boolean isKeyInRightSegmentOrAtFirstThird =
                array[firstThirdIndex].compareTo(key) <= 0;

            if (isKeyInRightSegmentOrAtFirstThird) {
                left = firstThirdIndex;
            } else {
                right = secondThirdIndex;
            }
        }

        return -1;
    }
}