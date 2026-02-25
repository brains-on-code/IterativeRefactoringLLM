package com.thealgorithms.sorts;

import static com.thealgorithms.sorts.SortUtils.less;

class MergeSort implements SortAlgorithm {

    private Comparable<?>[] aux;

    @Override
    public <T extends Comparable<T>> T[] sort(T[] array) {
        if (array == null || array.length < 2) {
            return array;
        }
        aux = new Comparable<?>[array.length];
        mergeSort(array, 0, array.length - 1);
        return array;
    }

    private <T extends Comparable<T>> void mergeSort(T[] array, int left, int right) {
        if (left >= right) {
            return;
        }

        int middle = left + ((right - left) >>> 1);
        mergeSort(array, left, middle);
        mergeSort(array, middle + 1, right);
        merge(array, left, middle, right);
    }

    @SuppressWarnings("unchecked")
    private <T extends Comparable<T>> void merge(T[] array, int left, int middle, int right) {
        System.arraycopy(array, left, aux, left, right - left + 1);

        int leftIndex = left;
        int rightIndex = middle + 1;

        for (int current = left; current <= right; current++) {
            if (leftIndex > middle) {
                array[current] = (T) aux[rightIndex++];
            } else if (rightIndex > right) {
                array[current] = (T) aux[leftIndex++];
            } else if (less(aux[rightIndex], aux[leftIndex])) {
                array[current] = (T) aux[rightIndex++];
            } else {
                array[current] = (T) aux[leftIndex++];
            }
        }
    }
}