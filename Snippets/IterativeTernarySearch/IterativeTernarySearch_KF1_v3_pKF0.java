package com.thealgorithms.searches;

import com.thealgorithms.devutils.searches.SearchAlgorithm;

/**
 * Ternary-like search implementation.
 *
 * Searches for a target element in a sorted array by repeatedly narrowing the
 * search interval using two internal division points.
 */
public class Class1 implements SearchAlgorithm {

    @Override
    public <T extends Comparable<T>> int method1(T[] array, T target) {
        if (array == null || target == null || array.length == 0) {
            return -1;
        }

        if (array.length == 1) {
            return array[0].compareTo(target) == 0 ? 0 : -1;
        }

        int left = 0;
        int right = array.length - 1;

        while (left <= right) {
            int leftComparison = array[left].compareTo(target);
            if (leftComparison == 0) {
                return left;
            }

            int rightComparison = array[right].compareTo(target);
            if (rightComparison == 0) {
                return right;
            }

            if (right - left < 3) {
                break;
            }

            int firstThird = left + (right - left) / 3;
            int secondThird = right - (right - left) / 3;

            T firstThirdValue = array[firstThird];
            T secondThirdValue = array[secondThird];

            int firstThirdComparison = firstThirdValue.compareTo(target);
            if (firstThirdComparison == 0) {
                return firstThird;
            }

            int secondThirdComparison = secondThirdValue.compareTo(target);
            if (secondThirdComparison == 0) {
                return secondThird;
            }

            if (target.compareTo(firstThirdValue) < 0) {
                right = firstThird - 1;
            } else if (target.compareTo(secondThirdValue) > 0) {
                left = secondThird + 1;
            } else {
                left = firstThird + 1;
                right = secondThird - 1;
            }
        }

        return -1;
    }
}