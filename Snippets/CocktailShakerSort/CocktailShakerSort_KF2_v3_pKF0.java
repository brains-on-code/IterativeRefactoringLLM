package com.thealgorithms.sorts;

class CocktailShakerSort implements SortAlgorithm {

    @Override
    public <T extends Comparable<T>> T[] sort(final T[] array) {
        if (array == null || array.length <= 1) {
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

    private <T extends Comparable<T>> int forwardPass(final T[] array, final int left, final int right) {
        int lastSwapIndex = left;

        for (int i = left; i < right; i++) {
            if (SortUtils.less(array[i + 1], array[i])) {
                SortUtils.swap(array, i, i + 1);
                lastSwapIndex = i;
            }
        }

        return lastSwapIndex;
    }

    private <T extends Comparable<T>> int backwardPass(final T[] array, final int left, final int right) {
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