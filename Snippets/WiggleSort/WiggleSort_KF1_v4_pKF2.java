package com.thealgorithms.sorts;

import static com.thealgorithms.maths.Ceil.ceil;
import static com.thealgorithms.maths.Floor.floor;
import static com.thealgorithms.searches.QuickSelect.select;

import java.util.Arrays;

/**
 * Sorts an array using a three-way partition around its median.
 *
 * <p>Algorithm outline:
 * <ul>
 *   <li>Find the median of the array using QuickSelect.</li>
 *   <li>Validate constraints on how often the median may appear.</li>
 *   <li>Partition the array into three groups: greater than, equal to, and less than the median.</li>
 * </ul>
 */
public class Class1 implements SortAlgorithm {

    @Override
    public <T extends Comparable<T>> T[] method1(T[] array) {
        return partitionAroundMedian(array);
    }

    /**
     * Maps a logical index into a "virtual" index used for in-place partitioning.
     *
     * <p>This mapping is commonly used in wiggle-sort-style algorithms to
     * rearrange elements without extra space while controlling their final
     * positions.
     *
     * @param index  logical index
     * @param length array length
     * @return remapped index
     */
    private int virtualIndex(int index, int length) {
        return (2 * index + 1) % (length | 1);
    }

    /**
     * Three-way partitions the array around the given pivot value.
     *
     * <p>After this method:
     * <ul>
     *   <li>Elements greater than {@code pivot} are placed in the "front" region.</li>
     *   <li>Elements equal to {@code pivot} are in the "middle" region.</li>
     *   <li>Elements less than {@code pivot} are in the "back" region.</li>
     * </ul>
     *
     * @param array the array to partition
     * @param pivot the pivot value (typically the median)
     * @param <T>   element type
     */
    private <T extends Comparable<T>> void threeWayPartition(T[] array, T pivot) {
        int length = array.length;
        int left = 0;
        int current = 0;
        int right = length - 1;

        while (current <= right) {
            int mappedCurrent = virtualIndex(current, length);
            int comparison = array[mappedCurrent].compareTo(pivot);

            if (comparison > 0) {
                SortUtils.swap(array, mappedCurrent, virtualIndex(left, length));
                left++;
                current++;
            } else if (comparison < 0) {
                SortUtils.swap(array, mappedCurrent, virtualIndex(right, length));
                right--;
            } else {
                current++;
            }
        }
    }

    /**
     * Sorts the array by:
     * <ol>
     *   <li>Finding the median using QuickSelect.</li>
     *   <li>Ensuring the median does not appear more than half the time.</li>
     *   <li>For odd-length arrays, ensuring that if the median appears exactly
     *       {@code ceil(n/2)} times, it is also the smallest element.</li>
     *   <li>Partitioning the array into three groups around the median.</li>
     * </ol>
     *
     * @param array the array to sort
     * @param <T>   element type
     * @return the partitioned array
     */
    private <T extends Comparable<T>> T[] partitionAroundMedian(T[] array) {
        int length = array.length;
        boolean isOddLength = length % 2 == 1;
        int halfLengthCeil = (int) ceil(length / 2.0);

        T median = select(Arrays.asList(array), (int) floor(length / 2.0));

        int medianCount = 0;
        for (T value : array) {
            if (value.compareTo(median) == 0) {
                medianCount++;
            }
        }

        if (isOddLength && medianCount == halfLengthCeil) {
            T smallest = select(Arrays.asList(array), 0);
            if (smallest.compareTo(median) != 0) {
                throw new IllegalArgumentException(
                    "For odd arrays, if the median appears ceil(n/2) times, "
                        + "the median must be the smallest value in the array."
                );
            }
        }

        if (medianCount > halfLengthCeil) {
            throw new IllegalArgumentException(
                "No more than half the number of values may be the same."
            );
        }

        threeWayPartition(array, median);
        return array;
    }
}