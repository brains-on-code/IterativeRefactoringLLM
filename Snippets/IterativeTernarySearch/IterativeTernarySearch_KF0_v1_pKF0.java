package com.thealgorithms.searches;

import com.thealgorithms.devutils.searches.SearchAlgorithm;

/**
 * An iterative implementation of the Ternary Search algorithm.
 *
 * <p>
 * Ternary search is a divide-and-conquer algorithm that splits the array into three parts
 * instead of two, as in binary search. This implementation is iterative, reducing the overhead
 * associated with recursive function calls. However, the recursive version can also be optimized
 * by the Java compiler to resemble the iterative version, resulting in similar performance.
 *
 * <p>
 * Worst-case performance: Θ(log3(N))<br>
 * Best-case performance: O(1)<br>
 * Average performance: Θ(log3(N))<br>
 * Worst-case space complexity: O(1)
 *
 * <p>
 * This class implements the {@link SearchAlgorithm} interface, providing a generic search method
 * for any comparable type.
 *
 * @see SearchAlgorithm
 * @see TernarySearch
 * @since 2018-04-13
 */
public class IterativeTernarySearch implements SearchAlgorithm {

    @Override
    public <T extends Comparable<T>> int find(T[] array, T key) {
        if (array == null || key == null || array.length == 0) {
            return -1;
        }

        if (array.length == 1) {
            return array[0].compareTo(key) == 0 ? 0 : -1;
        }

        int left = 0;
        int right = array.length - 1;

        while (left <= right) {
            int leftThird = left + (right - left) / 3;
            int rightThird = right - (right - left) / 3;

            int leftThirdCmp = array[leftThird].compareTo(key);
            int rightThirdCmp = array[rightThird].compareTo(key);

            if (leftThirdCmp == 0) {
                return leftThird;
            }
            if (rightThirdCmp == 0) {
                return rightThird;
            }

            if (key.compareTo(array[leftThird]) < 0) {
                right = leftThird - 1;
            } else if (key.compareTo(array[rightThird]) > 0) {
                left = rightThird + 1;
            } else {
                left = leftThird + 1;
                right = rightThird - 1;
            }
        }

        return -1;
    }
}