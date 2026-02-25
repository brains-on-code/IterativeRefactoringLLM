package com.thealgorithms.sorts;

/**
 * Cocktail Shaker Sort (bidirectional bubble sort).
 *
 * Repeatedly passes through the array in both directions:
 * - forward pass moves larger elements toward the end
 * - backward pass moves smaller elements toward the beginning
 */
class CocktailShakerSort implements SortAlgorithm {

    @Override
    public <T extends Comparable<T>> T[] sort(final T[] array) {
        if (array.length == 0) {
            return array;
        }

        int left = 0;
        int right = array.length - 1;

        while (left < right) {
            right = forwardPass(array, left, right);
            left = backwardPass(array, left, right);
        }

        return array;
    }

    /**
     * Forward pass: bubble larger elements toward the right boundary.
     *
     * @return index of the last swap (new right boundary)
     */
    private <T extends Comparable<T>> int forwardPass(final T[] array, final int left, final int right) {
        int lastSwap = left;

        for (int i = left; i < right; i++) {
            if (SortUtils.less(array[i + 1], array[i])) {
                SortUtils.swap(array, i, i + 1);
                lastSwap = i;
            }
        }

        return lastSwap;
    }

    /**
     * Backward pass: bubble smaller elements toward the left boundary.
     *
     * @return index of the last swap (new left boundary)
     */
    private <T extends Comparable<T>> int backwardPass(final T[] array, final int left, final int right) {
        int lastSwap = right;

        for (int i = right; i > left; i--) {
            if (SortUtils.less(array[i], array[i - 1])) {
                SortUtils.swap(array, i - 1, i);
                lastSwap = i;
            }
        }

        return lastSwap;
    }
}