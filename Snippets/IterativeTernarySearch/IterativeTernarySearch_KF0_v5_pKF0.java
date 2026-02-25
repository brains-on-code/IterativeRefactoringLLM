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

    private static final int NOT_FOUND = -1;

    @Override
    public <T extends Comparable<T>> int find(T[] array, T key) {
        if (isInvalidInput(array, key)) {
            return NOT_FOUND;
        }

        int left = 0;
        int right = array.length - 1;

        while (left <= right) {
            int leftThird = left + (right - left) / 3;
            int rightThird = right - (right - left) / 3;

            T leftValue = array[leftThird];
            T rightValue = array[rightThird];

            int leftComparison = leftValue.compareTo(key);
            int rightComparison = rightValue.compareTo(key);

            if (leftComparison == 0) {
                return leftThird;
            }

            if (rightComparison == 0) {
                return rightThird;
            }

            if (leftComparison > 0) {
                right = leftThird - 1;
                continue;
            }

            if (rightComparison < 0) {
                left = rightThird + 1;
                continue;
            }

            left = leftThird + 1;
            right = rightThird - 1;
        }

        return NOT_FOUND;
    }

    private static <T> boolean isInvalidInput(T[] array, T key) {
        return array == null || key == null || array.length == 0;
    }
}