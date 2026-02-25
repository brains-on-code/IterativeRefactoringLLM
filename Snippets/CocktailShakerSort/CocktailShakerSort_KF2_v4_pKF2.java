package com.thealgorithms.sorts;

class CocktailShakerSort implements SortAlgorithm {

    @Override
    public <T extends Comparable<T>> T[] sort(final T[] array) {
        if (array.length == 0) {
            return array;
        }

        int leftBoundary = 0;
        int rightBoundary = array.length - 1;

        while (leftBoundary < rightBoundary) {
            rightBoundary = sweepRight(array, leftBoundary, rightBoundary);
            leftBoundary = sweepLeft(array, leftBoundary, rightBoundary);
        }

        return array;
    }

    /**
     * Performs a forward pass (left to right) over the array segment [left, right].
     * Swaps adjacent out-of-order elements and returns the index of the last swap.
     * This index becomes the new right boundary for the next iteration.
     */
    private <T extends Comparable<T>> int sweepRight(final T[] array, final int left, final int right) {
        int lastSwapIndex = left;

        for (int i = left; i < right; i++) {
            if (SortUtils.less(array[i + 1], array[i])) {
                SortUtils.swap(array, i, i + 1);
                lastSwapIndex = i;
            }
        }

        return lastSwapIndex;
    }

    /**
     * Performs a backward pass (right to left) over the array segment [left, right].
     * Swaps adjacent out-of-order elements and returns the index of the last swap.
     * This index becomes the new left boundary for the next iteration.
     */
    private <T extends Comparable<T>> int sweepLeft(final T[] array, final int left, final int right) {
        int lastSwapIndex = right;

        for (int i = right; i > left; i--) {
            if (SortUtils.less(array[i], array[i - 1])) {
                SortUtils.swap(array, i - 1, i);
                lastSwapIndex = i;
            }
        }

        return lastSwapIndex;
    }
}