package com.thealgorithms.divideandconquer;

public final class MedianOfTwoSortedArrays {

    private MedianOfTwoSortedArrays() {
    }

    /**
     * Finds the median of two sorted arrays in logarithmic time.
     *
     * @param firstArray  the first sorted array
     * @param secondArray the second sorted array
     * @return the median of the combined sorted array
     * @throws IllegalArgumentException if the input arrays are not sorted
     */
    public static double findMedianSortedArrays(int[] firstArray, int[] secondArray) {
        if (firstArray.length > secondArray.length) {
            return findMedianSortedArrays(secondArray, firstArray); // Ensure firstArray is the smaller array
        }

        int firstLength = firstArray.length;
        int secondLength = secondArray.length;
        int left = 0;
        int right = firstLength;

        while (left <= right) {
            int partitionFirst = (left + right) / 2; // Partition in the first array
            int partitionSecond = (firstLength + secondLength + 1) / 2 - partitionFirst; // Partition in the second array

            int maxLeftFirst =
                (partitionFirst == 0) ? Integer.MIN_VALUE : firstArray[partitionFirst - 1];
            int minRightFirst =
                (partitionFirst == firstLength) ? Integer.MAX_VALUE : firstArray[partitionFirst];

            int maxLeftSecond =
                (partitionSecond == 0) ? Integer.MIN_VALUE : secondArray[partitionSecond - 1];
            int minRightSecond =
                (partitionSecond == secondLength) ? Integer.MAX_VALUE : secondArray[partitionSecond];

            boolean isValidPartition =
                maxLeftFirst <= minRightSecond && maxLeftSecond <= minRightFirst;

            if (isValidPartition) {
                boolean isTotalLengthOdd = ((firstLength + secondLength) & 1) == 1;

                if (isTotalLengthOdd) {
                    return Math.max(maxLeftFirst, maxLeftSecond);
                } else {
                    int maxOfLeft = Math.max(maxLeftFirst, maxLeftSecond);
                    int minOfRight = Math.min(minRightFirst, minRightSecond);
                    return (maxOfLeft + minOfRight) / 2.0;
                }
            }

            if (maxLeftFirst > minRightSecond) {
                right = partitionFirst - 1;
            } else {
                left = partitionFirst + 1;
            }
        }

        throw new IllegalArgumentException("Input arrays are not sorted");
    }
}