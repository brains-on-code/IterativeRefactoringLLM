package com.thealgorithms.divideandconquer;

public final class MedianOfTwoSortedArrays {

    private MedianOfTwoSortedArrays() {
    }

    /**
     * Finds the median of two sorted integer arrays.
     *
     * @param firstArray  first sorted array
     * @param secondArray second sorted array
     * @return median value as a double
     * @throws IllegalArgumentException if input arrays are not sorted in non-decreasing order
     */
    public static double findMedianSortedArrays(int[] firstArray, int[] secondArray) {
        if (firstArray.length > secondArray.length) {
            return findMedianSortedArrays(secondArray, firstArray);
        }

        int lengthShortArray = firstArray.length;
        int lengthLongArray = secondArray.length;
        int left = 0;
        int right = lengthShortArray;

        while (left <= right) {
            int partitionShort = (left + right) / 2;
            int partitionLong = (lengthShortArray + lengthLongArray + 1) / 2 - partitionShort;

            int maxLeftShort =
                (partitionShort == 0) ? Integer.MIN_VALUE : firstArray[partitionShort - 1];
            int minRightShort =
                (partitionShort == lengthShortArray)
                    ? Integer.MAX_VALUE
                    : firstArray[partitionShort];

            int maxLeftLong =
                (partitionLong == 0)
                    ? Integer.MIN_VALUE
                    : secondArray[partitionLong - 1];
            int minRightLong =
                (partitionLong == lengthLongArray)
                    ? Integer.MAX_VALUE
                    : secondArray[partitionLong];

            boolean isValidPartition =
                maxLeftShort <= minRightLong && maxLeftLong <= minRightShort;

            if (isValidPartition) {
                boolean isTotalLengthOdd =
                    ((lengthShortArray + lengthLongArray) & 1) == 1;
                if (isTotalLengthOdd) {
                    return Math.max(maxLeftShort, maxLeftLong);
                } else {
                    int maxOfLeft = Math.max(maxLeftShort, maxLeftLong);
                    int minOfRight = Math.min(minRightShort, minRightLong);
                    return (maxOfLeft + minOfRight) / 2.0;
                }
            } else if (maxLeftShort > minRightLong) {
                right = partitionShort - 1;
            } else {
                left = partitionShort + 1;
            }
        }

        throw new IllegalArgumentException("Input arrays are not sorted");
    }
}