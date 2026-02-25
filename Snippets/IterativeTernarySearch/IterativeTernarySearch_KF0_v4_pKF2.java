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
            // Check boundaries first
            int leftComparison = array[left].compareTo(key);
            if (leftComparison == 0) {
                return left;
            }

            int rightComparison = array[right].compareTo(key);
            if (rightComparison == 0) {
                return right;
            }

            // Split current range into three parts
            int third = (right - left) / 3;
            int leftThird = left + third;
            int rightThird = right - third;

            // Check the two trisection points
            int leftThirdComparison = array[leftThird].compareTo(key);
            if (leftThirdComparison == 0) {
                return leftThird;
            }

            int rightThirdComparison = array[rightThird].compareTo(key);
            if (rightThirdComparison == 0) {
                return rightThird;
            }

            // Decide which segment to search next:
            // [left, leftThird - 1], [leftThird + 1, rightThird - 1], or [rightThird + 1, right]
            boolean inLeftSegment = key.compareTo(array[leftThird]) < 0;
            boolean inRightSegment = key.compareTo(array[rightThird]) > 0;

            if (inLeftSegment) {
                right = leftThird - 1;
            } else if (inRightSegment) {
                left = rightThird + 1;
            } else {
                left = leftThird + 1;
                right = rightThird - 1;
            }
        }

        return -1;
    }
}