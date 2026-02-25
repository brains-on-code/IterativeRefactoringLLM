package com.thealgorithms.sorts;

import static com.thealgorithms.maths.Ceil.ceil;
import static com.thealgorithms.maths.Floor.floor;
import static com.thealgorithms.searches.QuickSelect.select;

import java.util.Arrays;

public class WiggleSort implements SortAlgorithm {

    @Override
    public <T extends Comparable<T>> T[] sort(T[] array) {
        return wiggleSort(array);
    }

    private int getVirtualIndex(int index, int length) {
        return (2 * index + 1) % (length | 1);
    }

    private <T extends Comparable<T>> void partitionAroundMedian(T[] array, T median) {
        int length = array.length;
        int leftIndex = 0;
        int currentIndex = 0;
        int rightIndex = length - 1;

        while (currentIndex <= rightIndex) {
            int virtualCurrentIndex = getVirtualIndex(currentIndex, length);
            int virtualLeftIndex = getVirtualIndex(leftIndex, length);
            int virtualRightIndex = getVirtualIndex(rightIndex, length);

            int comparisonResult = array[virtualCurrentIndex].compareTo(median);

            if (comparisonResult > 0) {
                SortUtils.swap(array, virtualCurrentIndex, virtualLeftIndex);
                leftIndex++;
                currentIndex++;
            } else if (comparisonResult < 0) {
                SortUtils.swap(array, virtualCurrentIndex, virtualRightIndex);
                rightIndex--;
            } else {
                currentIndex++;
            }
        }
    }

    private <T extends Comparable<T>> T[] wiggleSort(T[] array) {
        int length = array.length;

        T median = select(Arrays.asList(array), (int) floor(length / 2.0));

        int medianFrequency = 0;
        for (T value : array) {
            if (value.compareTo(median) == 0) {
                medianFrequency++;
            }
        }

        if (length % 2 == 1 && medianFrequency == ceil(length / 2.0)) {
            T minimumValue = select(Arrays.asList(array), 0);
            if (minimumValue.compareTo(median) != 0) {
                throw new IllegalArgumentException(
                    "For odd arrays, if the median appears ceil(n/2) times, "
                        + "the median has to be the smallest value in the array."
                );
            }
        }

        if (medianFrequency > ceil(length / 2.0)) {
            throw new IllegalArgumentException("No more than half the number of values may be the same.");
        }

        partitionAroundMedian(array, median);
        return array;
    }
}