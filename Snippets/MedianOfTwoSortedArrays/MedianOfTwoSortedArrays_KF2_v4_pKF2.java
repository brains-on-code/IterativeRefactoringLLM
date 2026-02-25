package com.thealgorithms.divideandconquer;

public final class MedianOfTwoSortedArrays {

    private MedianOfTwoSortedArrays() {
        // Prevent instantiation
    }

    /**
     * Computes the median of two sorted integer arrays in O(log(min(m, n))) time.
     *
     * @param nums1 first sorted array (non-decreasing order)
     * @param nums2 second sorted array (non-decreasing order)
     * @return median of the combined sorted arrays
     * @throws IllegalArgumentException if the input arrays are not sorted in non-decreasing order
     */
    public static double findMedianSortedArrays(int[] nums1, int[] nums2) {
        // Always binary search on the smaller array
        if (nums1.length > nums2.length) {
            return findMedianSortedArrays(nums2, nums1);
        }

        int m = nums1.length;
        int n = nums2.length;

        int low = 0;
        int high = m;

        while (low <= high) {
            int partition1 = (low + high) / 2;
            int partition2 = (m + n + 1) / 2 - partition1;

            int maxLeft1 = (partition1 == 0) ? Integer.MIN_VALUE : nums1[partition1 - 1];
            int minRight1 = (partition1 == m) ? Integer.MAX_VALUE : nums1[partition1];

            int maxLeft2 = (partition2 == 0) ? Integer.MIN_VALUE : nums2[partition2 - 1];
            int minRight2 = (partition2 == n) ? Integer.MAX_VALUE : nums2[partition2];

            boolean isValidPartition = maxLeft1 <= minRight2 && maxLeft2 <= minRight1;
            if (isValidPartition) {
                boolean isTotalLengthOdd = ((m + n) & 1) == 1;

                if (isTotalLengthOdd) {
                    return Math.max(maxLeft1, maxLeft2);
                } else {
                    int leftMax = Math.max(maxLeft1, maxLeft2);
                    int rightMin = Math.min(minRight1, minRight2);
                    return (leftMax + rightMin) / 2.0;
                }
            }

            boolean isPartition1TooRight = maxLeft1 > minRight2;
            if (isPartition1TooRight) {
                high = partition1 - 1;
            } else {
                low = partition1 + 1;
            }
        }

        throw new IllegalArgumentException("Input arrays are not sorted");
    }
}