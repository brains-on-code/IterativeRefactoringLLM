package com.thealgorithms.searches;

import com.thealgorithms.devutils.searches.SearchAlgorithm;

/**
 * Iterative implementation of ternary search on a sorted array.
 *
 * <p>Ternary search repeatedly divides the search interval into three parts
 * and determines in which part the target value lies.
 *
 * <p>Complexity:
 * <ul>
 *   <li>Time (worst/average): Θ(log₃ N)</li>
 *   <li>Time (best): O(1)</li>
 *   <li>Space (worst): O(1)</li>
 * </ul>
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
            int leftComparison = array[left].compareTo(key);
            int rightComparison = array[right].compareTo(key);

            if (leftComparison == 0) {
                return left;
            }
            if (rightComparison == 0) {
                return right;
            }

            int third = (right - left) / 3;
            int leftThird = left + third;
            int rightThird = right - third;

            int leftThirdComparison = array[leftThird].compareTo(key);
            int rightThirdComparison = array[rightThird].compareTo(key);

            if (leftThirdComparison == 0) {
                return leftThird;
            }
            if (rightThirdComparison == 0) {
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