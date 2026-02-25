package com.thealgorithms.sorts;

public class CircleSort implements SortAlgorithm {

    @Override
    public <T extends Comparable<T>> T[] sort(T[] array) {
        if (array.length == 0) {
            return array;
        }
        while (circleSortRecursive(array, 0, array.length - 1)) {
            // keep sorting until no swaps occur
        }
        return array;
    }

    private <T extends Comparable<T>> boolean circleSortRecursive(final T[] array, final int left, final int right) {
        boolean didSwap = false;

        if (left == right) {
            return false;
        }

        int i = left;
        int j = right;

        while (i < j) {
            if (array[i].compareTo(array[j]) > 0) {
                SortUtils.swap(array, i, j);
                didSwap = true;
            }
            i++;
            j--;
        }

        if (i == j && array[i].compareTo(array[j + 1]) > 0) {
            SortUtils.swap(array, i, j + 1);
            didSwap = true;
        }

        final int mid = left + (right - left) / 2;
        final boolean leftDidSwap = circleSortRecursive(array, left, mid);
        final boolean rightDidSwap = circleSortRecursive(array, mid + 1, right);

        return didSwap || leftDidSwap || rightDidSwap;
    }
}