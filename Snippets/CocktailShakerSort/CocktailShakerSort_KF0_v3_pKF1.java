package com.thealgorithms.sorts;

/**
 * CocktailShakerSort class implements the Cocktail Shaker Sort algorithm,
 * which is a bidirectional bubble sort. It sorts the array by passing
 * through it back and forth, progressively moving the largest elements
 * to the end and the smallest elements to the beginning.
 *
 * @author Mateus Bizzo (https://github.com/MattBizzo)
 * @author Podshivalov Nikita (https://github.com/nikitap492)
 */
class CocktailShakerSort implements SortAlgorithm {

    /**
     * Sorts the given array using the Cocktail Shaker Sort algorithm.
     *
     * @param <T>   The type of elements in the array, which must be comparable
     * @param array The array to be sorted
     * @return The sorted array
     */
    @Override
    public <T extends Comparable<T>> T[] sort(final T[] array) {
        if (array.length == 0) {
            return array;
        }

        int leftBoundary = 0;
        int rightBoundary = array.length - 1;

        while (leftBoundary < rightBoundary) {
            rightBoundary = performForwardPass(array, leftBoundary, rightBoundary);
            leftBoundary = performBackwardPass(array, leftBoundary, rightBoundary);
        }

        return array;
    }

    /**
     * Performs a forward pass through the array, moving larger elements to the end.
     *
     * @param <T>          The type of elements in the array, which must be comparable
     * @param array        The array being sorted
     * @param leftBoundary The current left boundary of the sorting area
     * @param rightBoundary The current right boundary of the sorting area
     * @return The index of the last swapped element during this pass
     */
    private <T extends Comparable<T>> int performForwardPass(
            final T[] array,
            final int leftBoundary,
            final int rightBoundary
    ) {
        int lastSwapPosition = leftBoundary;

        for (int currentIndex = leftBoundary; currentIndex < rightBoundary; currentIndex++) {
            if (SortUtils.less(array[currentIndex + 1], array[currentIndex])) {
                SortUtils.swap(array, currentIndex, currentIndex + 1);
                lastSwapPosition = currentIndex;
            }
        }

        return lastSwapPosition;
    }

    /**
     * Performs a backward pass through the array, moving smaller elements to the beginning.
     *
     * @param <T>           The type of elements in the array, which must be comparable
     * @param array         The array being sorted
     * @param leftBoundary  The current left boundary of the sorting area
     * @param rightBoundary The current right boundary of the sorting area
     * @return The index of the last swapped element during this pass
     */
    private <T extends Comparable<T>> int performBackwardPass(
            final T[] array,
            final int leftBoundary,
            final int rightBoundary
    ) {
        int lastSwapPosition = rightBoundary;

        for (int currentIndex = rightBoundary; currentIndex > leftBoundary; currentIndex--) {
            if (SortUtils.less(array[currentIndex], array[currentIndex - 1])) {
                SortUtils.swap(array, currentIndex - 1, currentIndex);
                lastSwapPosition = currentIndex;
            }
        }

        return lastSwapPosition;
    }
}