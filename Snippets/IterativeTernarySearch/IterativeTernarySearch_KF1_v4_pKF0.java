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

            int firstThirdIndex = left + (right - left) / 3;
            int secondThirdIndex = right - (right - left) / 3;

            T firstThirdValue = array[firstThirdIndex];
            T secondThirdValue = array[secondThirdIndex];

            int firstThirdComparison = firstThirdValue.compareTo(target);
            if (firstThirdComparison == 0) {
                return firstThirdIndex;
            }

            int secondThirdComparison = secondThirdValue.compareTo(target);
            if (secondThirdComparison == 0) {
                return secondThirdIndex;
            }

            boolean isTargetLessThanFirstThird = target.compareTo(firstThirdValue) < 0;
            boolean isTargetGreaterThanSecondThird = target.compareTo(secondThirdValue) > 0;

            if (isTargetLessThanFirstThird) {
                right = firstThirdIndex - 1;
            } else if (isTargetGreaterThanSecondThird) {
                left = secondThirdIndex + 1;
            } else {
                left = firstThirdIndex + 1;
                right = secondThirdIndex - 1;
            }
        }

        return -1;
    }
}