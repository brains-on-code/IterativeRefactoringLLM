package com.thealgorithms.sorts;


class CocktailShakerSort implements SortAlgorithm {


    @Override
    public <T extends Comparable<T>> T[] sort(final T[] array) {
        if (array.length == 0) {
            return array;
        }

        int left = 0;
        int right = array.length - 1;

        while (left < right) {
            right = performForwardPass(array, left, right);
            left = performBackwardPass(array, left, right);
        }

        return array;
    }


    private <T extends Comparable<T>> int performForwardPass(final T[] array, final int left, final int right) {
        int lastSwappedIndex = left;

        for (int i = left; i < right; i++) {
            if (SortUtils.less(array[i + 1], array[i])) {
                SortUtils.swap(array, i, i + 1);
                lastSwappedIndex = i;
            }
        }

        return lastSwappedIndex;
    }


    private <T extends Comparable<T>> int performBackwardPass(final T[] array, final int left, final int right) {
        int lastSwappedIndex = right;

        for (int i = right; i > left; i--) {
            if (SortUtils.less(array[i], array[i - 1])) {
                SortUtils.swap(array, i - 1, i);
                lastSwappedIndex = i;
            }
        }

        return lastSwappedIndex;
    }
}
