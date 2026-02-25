package com.thealgorithms.searches;

import com.thealgorithms.devutils.searches.SearchAlgorithm;

/**
 * Iterative implementation of the binary search algorithm.
 *
 * <p>Binary search works on sorted arrays by repeatedly dividing the search
 * interval in half. It compares the target value to the middle element of the
 * array; if they are unequal, the half in which the target cannot lie is
 * eliminated and the search continues on the remaining half until it is
 * successful or the remaining half is empty.</p>
 *
 * <p>
 * Time complexity:
 * <ul>
 *   <li>Worst-case: O(log n)</li>
 *   <li>Best-case: O(1)</li>
 *   <li>Average: O(log n)</li>
 * </ul>
 *
 * Space complexity:
 * <ul>
 *   <li>Worst-case: O(1)</li>
 * </ul>
 *
 * @author Gabriele La Greca : https://github.com/thegabriele97
 * @author Podshivalov Nikita (https://github.com/nikitap492)
 * @see SearchAlgorithm
 * @see BinarySearch
 */
public final class IterativeBinarySearch implements SearchAlgorithm {

    /**
     * Searches for a key in a sorted array using iterative binary search.
     *
     * @param array a sorted array
     * @param key   the value to search for
     * @param <T>   the type of elements in the array, which must be comparable
     * @return the index of {@code key} in {@code array}, or -1 if not found
     */
    @Override
    public <T extends Comparable<T>> int find(T[] array, T key) {
        int left = 0;
        int right = array.length - 1;

        while (left <= right) {
            int mid = (left + right) >>> 1; // midpoint, computed to avoid overflow
            int comparison = key.compareTo(array[mid]);

            if (comparison == 0) {
                return mid; // key found
            }

            if (comparison < 0) {
                right = mid - 1; // key is in the left half
            } else {
                left = mid + 1; // key is in the right half
            }
        }

        return -1; // key not found
    }
}