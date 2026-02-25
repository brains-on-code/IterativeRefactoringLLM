package com.thealgorithms.divideandconquer;

public final class MedianOfTwoSortedArrays {

    private MedianOfTwoSortedArrays() {
        // Utility class; prevent instantiation
    }

    /**
     * Computes the median of two individually sorted integer arrays in O(log(min(m, n))) time,
     * where m and n are the lengths of the input arrays.
     *
     * @param nums1 first sorted array
     * @param nums2 second sorted array
     * @return median of the combined sorted elements from both arrays
     * @throws IllegalArgumentException if a valid median cannot be determined
     */
    public static double findMedianSortedArrays(int[] nums1, int[] nums2) {
        // Always binary search on the smaller array to keep the search space minimal
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

            boolean validPartition =
                maxLeft1 <= minRight2 &&
                maxLeft2 <= minRight1;

            if (validPartition) {
                boolean isTotalLengthOdd = ((m + n) & 1) == 1;

                if (isTotalLengthOdd) {
                    return Math.max(maxLeft1, maxLeft2);
                } else {
                    int leftMax = Math.max(maxLeft1, maxLeft2);
                    int rightMin = Math.min(minRight1, minRight2);
                    return (leftMax + rightMin) / 2.0;
                }
            }

            if (maxLeft1 > minRight2) {
                // Move partition1 left
                high = partition1 - 1;
            } else {
                // Move partition1 right
                low = partition1 + 1;
            }
        }

        throw new IllegalArgumentException("Input arrays are not sorted or are invalid for median computation.");
    }
}