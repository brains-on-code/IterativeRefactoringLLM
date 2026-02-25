package com.thealgorithms.sorts;

import static com.thealgorithms.sorts.SortUtils.less;


class MergeSort implements SortAlgorithm {

    private Comparable[] aux;


    @Override
    public <T extends Comparable<T>> T[] sort(T[] unsorted) {
        aux = new Comparable[unsorted.length];
        doSort(unsorted, 0, unsorted.length - 1);
        return unsorted;
    }


    private <T extends Comparable<T>> void doSort(T[] arr, int left, int right) {
        if (left < right) {
            int mid = (left + right) >>> 1;
            doSort(arr, left, mid);
            doSort(arr, mid + 1, right);
            merge(arr, left, mid, right);
        }
    }


    @SuppressWarnings("unchecked")
    private <T extends Comparable<T>> void merge(T[] arr, int left, int mid, int right) {
        int i = left;
        int j = mid + 1;
        System.arraycopy(arr, left, aux, left, right + 1 - left);

        for (int k = left; k <= right; k++) {
            if (j > right) {
                arr[k] = (T) aux[i++];
            } else if (i > mid) {
                arr[k] = (T) aux[j++];
            } else if (less(aux[j], aux[i])) {
                arr[k] = (T) aux[j++];
            } else {
                arr[k] = (T) aux[i++];
            }
        }
    }
}
