package com.thealgorithms.searches;

import com.thealgorithms.devutils.searches.SearchAlgorithm;

/**
 * Iterative implementation of the binary search algorithm.
 *
 * <p>Requirements:
 * <ul>
 *   <li>The input array must be sorted in ascending order.</li>
 *   <li>The elements must implement {@link Comparable}.</li>
 * </ul>
 *
 * <p>Complexity:
 * <ul>
 *   <li>Worst-case time: O(log n)</li>
 *   <li>Best-case time: O(1)</li>
 *   <li>Average time: O(log n)</li>
 *   <li>Worst-case space: O(1)</li>
 * </ul>
 *
 * @author Gabriele La Greca : https://github.com/thegabriele97
 * @author Podshivalov Nikita (https://github.com/nikitap492)
 * @see SearchAlgorithm
 * @see BinarySearch
 */
public final class IterativeBinarySearch implements SearchAlgorithm {

    /**
     * Performs an iterative binary search on a sorted array.
     *
     * @param array a sorted array
     * @param key   the key to search for in the array
     * @param <T>   the type of elements in the array
     * @return the index of {@code key} in {@code array}, or -1 if not found
     */
    @Override
    public <T extends Comparable<T>> int find(T[] array, T key) {
        int left = 0;
        int right = array.length - 1;

        while (left <= right) {
            int mid = (left + right) >>> 1;
            int comparison = key.compareTo(array[mid]);

            if (comparison == 0) {
                return mid;
            }

            if (comparison < 0) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }

        return -1;
    }
}