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

        int lengthFirst = firstArray.length;
        int lengthSecond = secondArray.length;
        int totalLength = lengthFirst + lengthSecond;

        int low = 0;
        int high = lengthFirst;

        while (low <= high) {
            int partitionFirst = (low + high) / 2;
            int partitionSecond = (totalLength + 1) / 2 - partitionFirst;

            int maxLeftFirst =
                (partitionFirst == 0) ? Integer.MIN_VALUE : firstArray[partitionFirst - 1];
            int minRightFirst =
                (partitionFirst == lengthFirst) ? Integer.MAX_VALUE : firstArray[partitionFirst];

            int maxLeftSecond =
                (partitionSecond == 0) ? Integer.MIN_VALUE : secondArray[partitionSecond - 1];
            int minRightSecond =
                (partitionSecond == lengthSecond) ? Integer.MAX_VALUE : secondArray[partitionSecond];

            boolean isValidPartition =
                maxLeftFirst <= minRightSecond && maxLeftSecond <= minRightFirst;

            if (isValidPartition) {
                boolean isTotalLengthOdd = (totalLength & 1) == 1;
                if (isTotalLengthOdd) {
                    return Math.max(maxLeftFirst, maxLeftSecond);
                } else {
                    int maxOfLeft = Math.max(maxLeftFirst, maxLeftSecond);
                    int minOfRight = Math.min(minRightFirst, minRightSecond);
                    return (maxOfLeft + minOfRight) / 2.0;
                }
            } else if (maxLeftFirst > minRightSecond) {
                high = partitionFirst - 1;
            } else {
                low = partitionFirst + 1;
            }
        }

        throw new IllegalArgumentException("Input arrays are not sorted");
    }
}