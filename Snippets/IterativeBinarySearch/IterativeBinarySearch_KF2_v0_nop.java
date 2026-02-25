package com.thealgorithms.searches;

import com.thealgorithms.devutils.searches.SearchAlgorithm;


public final class IterativeBinarySearch implements SearchAlgorithm {


    @Override
    public <T extends Comparable<T>> int find(T[] array, T key) {
        int l;
        int r;
        int k;
        int cmp;

        l = 0;
        r = array.length - 1;

        while (l <= r) {
            k = (l + r) >>> 1;
            cmp = key.compareTo(array[k]);

            if (cmp == 0) {
                return k;
            } else if (cmp < 0) {
                r = --k;
            } else {
                l = ++k;
            }
        }

        return -1;
    }
}
