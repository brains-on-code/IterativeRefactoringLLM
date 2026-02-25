package com.thealgorithms.sorts;

/**
 * Implementation of the Cocktail Shaker Sort algorithm.
 *
 * This is a bi-directional variant of Bubble Sort that traverses the list
 * alternately in both directions, bubbling the largest element to the end
 * and the smallest element to the beginning in each full pass.
 */
class CocktailShakerSort implements SortAlgorithm {

    /**
     * Sorts the given array using Cocktail Shaker Sort.
     *
     * @param array the array to be sorted
     * @param <T>   the type of elements, which must be comparable
     * @return the sorted array
     */
    @Override
    public <T extends Comparable<T>> T[] sort(final T[] array) {
        if (array == null || array.length <= 1) {
            return array;
        }

        int leftBoundary = 0;
        int rightBoundary = array.length - 1;

        while (leftBoundary < rightBoundary) {
            rightBoundary = forwardPass(array, leftBoundary, rightBoundary);
            if (rightBoundary == leftBoundary) {
                break;
            }

            leftBoundary = backwardPass(array, leftBoundary, rightBoundary);
            if (leftBoundary == rightBoundary) {
                break;
            }
        }

        return array;
    }

    /**
     * Performs a forward pass from left to right, bubbling the largest element
     * towards the end of the array segment.
     *
     * @param array        the array being sorted
     * @param leftBoundary the starting index (inclusive)
     * @param rightBoundary the ending index (inclusive)
     * @param <T>          the type of elements, which must be comparable
     * @return the last index where a swap occurred
     */
    private <T extends Comparable<T>> int forwardPass(
        final T[] array,
        final int leftBoundary,
        final int rightBoundary
    ) {
        int lastSwapIndex = leftBoundary;

        for (int i = leftBoundary; i < rightBoundary; i++) {
            if (SortUtils.less(array[i + 1], array[i])) {
                SortUtils.swap(array, i, i + 1);
                lastSwapIndex = i;
            }
        }

        return lastSwapIndex;
    }

    /**
     * Performs a backward pass from right to left, bubbling the smallest element
     * towards the beginning of the array segment.
     *
     * @param array         the array being sorted
     * @param leftBoundary  the starting index (inclusive)
     * @param rightBoundary the ending index (inclusive)
     * @param <T>           the type of elements, which must be comparable
     * @return the last index where a swap occurred
     */
    private <T extends Comparable<T>> int backwardPass(
        final T[] array,
        final int leftBoundary,
        final int rightBoundary
    ) {
        int lastSwapIndex = rightBoundary;

        for (int i = rightBoundary; i > leftBoundary; i--) {
            if (SortUtils.less(array[i], array[i - 1])) {
                SortUtils.swap(array, i - 1, i);
                lastSwapIndex = i;
            }
        }

        return lastSwapIndex;
    }
}